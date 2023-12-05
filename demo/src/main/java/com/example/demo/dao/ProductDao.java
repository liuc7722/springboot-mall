package com.example.demo.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

import jakarta.validation.Valid;

@Component
public class ProductDao extends BaseDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 查詢商品列表
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        List<Product> productList = new ArrayList<>();

        try {
            connect();
            StringBuilder sqlBuider = new StringBuilder("SELECT * FROM product WHERE 1=1");

            // 加上查詢條件
            addFilteringSql(sqlBuider, productQueryParams);
            
            // 加上排序條件 (預設值已寫在Controller,故不用檢查null)
            sqlBuider.append(" ORDER BY ").append(productQueryParams.getOrderBy()).append(" ")
                    .append(productQueryParams.getSort());

            // 加上分頁條件 (同上，已有預設值)
            sqlBuider.append(" LIMIT ").append(productQueryParams.getLimit()).append(" OFFSET ")
                    .append(productQueryParams.getOffset());

            pstmt = conn.prepareStatement(sqlBuider.toString());

            // 設置查詢參數
            int paramIndex = 1;
            if (productQueryParams.getCategory() != null) {
                pstmt.setString(paramIndex++, productQueryParams.getCategory().name()); // 參數要String
            }
            if (productQueryParams.getSearch() != null) {
                pstmt.setString(paramIndex, "%" + productQueryParams.getSearch() + "%");
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setPhotoUrl(rs.getString("photo_url"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setStoreUrl(rs.getString("store_url"));
                product.setStoreName(rs.getString("store_name"));
                product.setCreatedDate(rs.getDate("created_date"));
                product.setLastModifiedDate(rs.getDate("last_modified_date"));
                product.setStock(rs.getInt("stock"));
                // 將String轉成enum
                String categoryStr = rs.getString("category");
                ProductCategory productCategory = ProductCategory.valueOf(categoryStr);
                product.setCategory(productCategory); // product需要set一個enum成員
                productList.add(product);
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }

    // 取得商品總數 (根據查詢條件不同，總數也不同)
    public Integer countProduct(ProductQueryParams productQueryParams) {

        try {
            connect();
            StringBuilder sqBuilder = new StringBuilder("SELECT count(*) as c FROM product WHERE 1=1");

            // 加上查詢條件
            addFilteringSql(sqBuilder, productQueryParams);

            pstmt = conn.prepareStatement(sqBuilder.toString());

            // 設置查詢參數
            int paramIndex = 1;
            if (productQueryParams.getCategory() != null)
                pstmt.setString(paramIndex++, productQueryParams.getCategory().name());
            if (productQueryParams.getSearch() != null)
                pstmt.setString(paramIndex, "%" + productQueryParams.getSearch() + "%");

            // 執行查詢並取得總數
            rs = pstmt.executeQuery();
            rs.next();
            int total = rs.getInt("c");

            return total;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 查詢商品
    public Product getProductById(Integer productId) {
        Product product = null;
        try {
            connect();
            String sql = "SELECT * FROM product WHERE product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setPhotoUrl(rs.getString("photo_url"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setStoreUrl(rs.getString("store_url"));
                product.setStoreName(rs.getString("store_name"));
                product.setCreatedDate(rs.getDate("created_date"));
                product.setLastModifiedDate(rs.getDate("last_modified_date"));
                product.setStock(rs.getInt("stock"));
                // 將String轉成enum
                String categoryStr = rs.getString("category");
                ProductCategory category = ProductCategory.valueOf(categoryStr);
                product.setCategory(category); // product需要set一個enum成員
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("getProductById的SQL問題");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("getProductById的ClassNotFound問題");
            System.out.println(e.getMessage());
        }
        return product;
    }

    // 新增商品
    public Integer createProduct(@Valid ProductRequest productRequest) {
        Integer productId = null;
        try {
            connect();
            String sql = "INSERT INTO product(photo_url,title,description,price,store_url," +
                    "store_name,stock,category, created_date, last_modified_date) VALUES(?,?,?,?,?,?,?,?,NOW(),NOW())";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, productRequest.getPhotoUrl());
            pstmt.setString(2, productRequest.getTitle());
            pstmt.setString(3, productRequest.getDescription());
            pstmt.setInt(4, productRequest.getPrice());
            pstmt.setString(5, productRequest.getStoreUrl());
            pstmt.setString(6, productRequest.getStoreName());
            pstmt.setInt(7, productRequest.getStock());
            pstmt.setString(8, productRequest.getCategory().name()); // 將enum改成String
            // pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis())); // 添加当前时间戳
            // pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

            // 注意創建時間並沒有實作，而是使用NOW()給資料庫自己生成
            int affectedRows = pstmt.executeUpdate();
            System.out.println("affectedRows: " + affectedRows);
            // 若新增成功，回傳ID
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys(); // 取得主鍵值
                if (rs.next()) {
                    productId = rs.getInt(1); // 假設userID是第一列(那rs是什麼呢?)
                    System.out.println("productId: " + productId);
                }
            }
            if (rs != null)
                rs.close();
            if (conn != null)
                conn.close();
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return productId;
    }

    // 修改商品
    public void updateProduct(Integer productId, @Valid ProductRequest productRequest) {
        try {
            connect();
            String sql = "UPDATE product\n" + //
                    "\tSET photo_url=?, title=?, description=?, price=?, store_url=?, store_name=?\n" + //
                    "\t,last_modified_date = ?, stock=?, category=? WHERE product_id=?;";
            pstmt = conn.prepareStatement(sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productRequest.getPhotoUrl());
            pstmt.setString(2, productRequest.getTitle());
            pstmt.setString(3, productRequest.getDescription());
            pstmt.setInt(4, productRequest.getPrice());
            pstmt.setString(5, productRequest.getStoreUrl());
            pstmt.setString(6, productRequest.getStoreName());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // 設置當前時間 ?
            pstmt.setInt(8, productRequest.getStock());
            pstmt.setString(9, productRequest.getCategory().name()); // 將enum轉成String
            pstmt.setInt(10, productId); // 除了商品ID前端獨立出來給我，其他要修改的資訊包在productRequest

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 刪除商品
    public void deleteProductById(Integer productId) {
        try {
            connect();
            String sql = "DELETE FROM product WHERE product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 到資料庫刪除多筆商品(學習一下這種寫法吧XD)
    public void deleteBatchProductsById(List<Integer> productIds) {
        try {
            connect();
            String sql = "DELETE FROM product WHERE product_id IN (" +
                    productIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            for (Integer id : productIds) {
                pstmt.setInt(index++, id);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 拼接查詢條件(拼接SQL字串)
    private void addFilteringSql(StringBuilder sqBuilder, ProductQueryParams productQueryParams) {
        // 檢查並加上category條件
        if (productQueryParams.getCategory() != null) {
            sqBuilder.append(" AND category = ?");
        }
        // 檢查並加上search條件
        if (productQueryParams.getSearch() != null) {
            sqBuilder.append(" AND title LIKE ?");
        }
        // 目前只有商品種類和名稱的查詢條件，若將來有其他查詢條件(如價格區間)，便可加在這邊，使得上面的方法呼叫addFilteringSql
        // 即可，這就是提煉程式
        // 補，為何排序和分頁不寫進來? 因為SQL上不一樣
    }

    // 更新商品庫存
    public void updateStock(int productId, int stock) {
        try {
            connect();
            String sql = "UPDATE product SET stock = ?, last_modified_date = NOW()" + 
            " WHERE product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stock);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
















    // 到資料庫查詢所有商品
    // public ArrayList<Product> getAllProducts() {
    // ArrayList<Product> products = new ArrayList<>();



    // try {
    // connect();
    // String sql = "SELECT * FROM product";
    // pstmt = conn.prepareStatement(sql);
    // rs = pstmt.executeQuery();
    // while (rs.next()) {
    // Product product = new Product();
    // product.setId(rs.getInt("id"));
    // product.setPhotoUrl(rs.getString("photo_url"));
    // product.setTitle(rs.getString("title"));
    // product.setDescription(rs.getString("description"));
    // product.setPrice(rs.getInt("price"));
    // product.setStoreUrl(rs.getString("store_url"));
    // product.setStoreName(rs.getString("store_name"));
    // product.setCreatedDate(rs.getDate("created_date"));
    // product.setLastModifiedDate(rs.getDate("last_modified_date"));
    // product.setStock(rs.getInt("stock"));
    // product.setCategory(rs.getString("category"));
    // products.add(product);
    // }
    // pstmt.close();
    // rs.close();
    // conn.close();
    // } catch (SQLException e) {
    // System.out.println(e.getMessage());
    // } catch (ClassNotFoundException e) {
    // System.out.println(e.getMessage());
    // }
    // return products;
    // }

}

package com.example.demo.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

import jakarta.validation.Valid;

@Component
public class ProductDao extends BaseDao {

    // 到資料庫撈取商品資訊，若無回傳null
    public Product getProductById(Integer productId) {
        Product product = null;
        try {
            connect();
            String sql = "SELECT * from product WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setPhotoUrl(rs.getString("photo_url"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setStoreUrl(rs.getString("store_url"));
                product.setStoreName(rs.getString("store_name"));
                product.setCreatedDate(rs.getDate("created_date"));
                product.setLastModifiedDate(rs.getDate("last_modified_date"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    // 在資料庫新增一筆商品，並回傳此商品的ID
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
            pstmt.setString(8, productRequest.getCategory());
            // pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis())); // 添加当前时间戳
            // pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

            // 注意創建時間並沒有實作，而是給資料庫自己生成，待會測試看資料庫是否有生成時間
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

    // 到資料庫修改此商品
    public void updateProduct(Integer productId, @Valid ProductRequest productRequest) {
        try {
            connect();
            String sql = "UPDATE product\n" + //
                    "\tSET photo_url=?, title=?, description=?, price=?, store_url=?, store_name=?\n" + //
                    "\t,last_modified_date = ?, stock=?, category=? WHERE id=?;";
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
            pstmt.setString(9, productRequest.getCategory());
            pstmt.setInt(10, productId); // 除了商品ID前端獨立出來給我，其他要修改的資訊包在productRequest

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 到資料庫刪除此商品
    public void deleteProductById(Integer productId) {
        try {
            connect();
            String sql = "DELETE FROM product WHERE id = ?";
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
            String sql = "DELETE FROM product WHERE id IN (" +
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

    // 到資料庫查詢所有商品
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try {
            connect();
            String sql = "SELECT * FROM product";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setPhotoUrl(rs.getString("photo_url"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setStoreUrl(rs.getString("store_url"));
                product.setStoreName(rs.getString("store_name"));
                product.setCreatedDate(rs.getDate("created_date"));
                product.setLastModifiedDate(rs.getDate("last_modified_date"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

}

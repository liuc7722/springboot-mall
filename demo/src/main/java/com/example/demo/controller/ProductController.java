package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Response.BaseResponse;
import com.example.Response.ProductResponse;
import com.example.Response.ProductV2Response;
import com.example.Response.SingleProductResponse;
import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductPage;
import com.example.demo.service.ProductService;
import com.example.demo.util.Page;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.PackagePrivate;

// @Hidden
@Validated // 加上它@MAX,@MIN才會生效
@CrossOrigin(origins = "*") // 允許不同網域的網頁呼叫API
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢商品列表(包含商品總數等資訊)
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
        // 查詢條件 Filtering
        @RequestParam(required = false) ProductCategory category, // 種類
        @RequestParam(required = false) String search,            // 搜尋
        // 排序 Sorting
        @RequestParam(defaultValue = "created_date") String orderBy, // 預設依照創建日期排序
        @RequestParam(defaultValue = "desc") String sort,            // 預設降序
        // 分頁Pagination
        @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 要取得幾筆商品數據
        @RequestParam(defaultValue = "0") @Min(0) Integer offset            // 要跳過多少筆數據

    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得product list
        List<Product> productList = productService.getProducts(productQueryParams);
        // 取得 product 總數
        Integer total = productService.countProduct(productQueryParams); // 傳參數是因為不同條件下的總筆數也會不同

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total); 
        page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    // 查詢商品
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return new ResponseEntity<>(new SingleProductResponse(0, "查詢成功", product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SingleProductResponse(1, "查詢失敗", product), HttpStatus.NOT_FOUND);
        }
    }

    // 新增商品
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        // 先新增商品取得商品ID，再藉由商品ID查詢商品資訊並回傳
        Integer productId = productService.creatrProduct(productRequest);

        Product product = productService.getProductById(productId);

        // 若product為null，表示無此商品(這邊可以看看是要productID為null還是produtct為null為新增失敗)
        if (product != null) {
            return new ResponseEntity<>(new SingleProductResponse(0, "新增成功", product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SingleProductResponse(1, "新增失敗", product), HttpStatus.NOT_FOUND);
        }
    }

    // 修改商品
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId,
            @RequestBody @Valid ProductRequest productRequest) {

        // 先查詢是否有此商品
        Product product = productService.getProductById(productId);
        if (product == null)
            return new ResponseEntity<>(new SingleProductResponse(1, "無此商品ID", product), HttpStatus.NOT_FOUND);

        // 修改商品的數據
        productService.updateProduct(productId, productRequest);

        // 先修改商品再使用商品ID查詢修改後的商品資訊
        Product updatedProduct = productService.getProductById(productId);
        return new ResponseEntity<>(new SingleProductResponse(0, "修改成功", updatedProduct), HttpStatus.OK);
    }

    // 刪除商品
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        // 刪除商品無須檢查商品ID
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 刪除多筆商品
    @DeleteMapping("/products")
    public ResponseEntity<?> deleteBatchProducts(@RequestBody List<Integer> productIds) {
        productService.deleteBatchProductsById(productIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 查詢所有商品(不含總數)
    // @GetMapping("/products")
    // public ResponseEntity getAllProducts() {
    //     ArrayList<Product> products = productService.getAllProducts();

    //     if (!products.isEmpty()) {
    //         return new ResponseEntity<>(new ProductResponse(0, "查詢成功", products), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(new ProductResponse(1, "無商品", new ArrayList<>()), HttpStatus.NOT_FOUND);
    //     }
    // }

    // 查詢所有商品v2 (含總數)
 

    // // 取得所有商品API
    // @RequestMapping(value = "/product", method = RequestMethod.GET, produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity getProduct() {

    // ArrayList<Product> result = getProductList();

    // if (result == null) // 查詢資料庫失敗
    // return new ResponseEntity<Object>(new ProductResponse(1, "失敗", result),
    // HttpStatus.OK);
    // else
    // return new ResponseEntity<Object>(new ProductResponse(0, "成功", result),
    // HttpStatus.OK);
    // }

    // // 取得所有商品API v2
    // @RequestMapping(value = "/v2/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity getProduct(int limit, int offset, int sortMode) {

    //     ArrayList<Product> result = getProductList(limit, offset, sortMode);
    //     int total = getProductCount();

    //     ProductPage data = new ProductPage();
    //     data.setProducts(result);
    //     data.setTotal(total);

    //     if (result == null) // 查詢資料庫失敗
    //         return new ResponseEntity<Object>(new ProductV2Response(1, "失敗", data),
    //                 HttpStatus.OK);
    //     else
    //         return new ResponseEntity<Object>(new ProductV2Response(0, "成功", data),
    //                 HttpStatus.OK);
    // }

    // 新增商品API
    // @RequestMapping(value = "/product", method = RequestMethod.POST, consumes =
    // MediaType.APPLICATION_JSON_VALUE, // 前端傳來JSON
    // produces = MediaType.APPLICATION_JSON_VALUE) // 回應JSON
    // public ResponseEntity addProduct(@RequestBody Product productModel) {

    // String result = insertProduct(productModel);

    // if (result.length() == 0)
    // return new ResponseEntity<>(new BaseResponse(0, "成功"), HttpStatus.OK);
    // else
    // return new ResponseEntity<>(new BaseResponse(1, result), HttpStatus.OK);
    // }

    // // 修改商品API
    // @RequestMapping(value = "/product", method = RequestMethod.PUT, consumes =
    // MediaType.APPLICATION_JSON_VALUE, produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity updateProduct(@RequestBody Product productModel) {
    // String result = update(productModel);

    // if (result.length() == 0)
    // return new ResponseEntity<>(new BaseResponse(0, "成功"), HttpStatus.OK);
    // else
    // return new ResponseEntity<>(new BaseResponse(1, result), HttpStatus.OK);

    // }

    // // 刪除商品API
    // @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE,
    // produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity deleteProduct(@PathVariable Integer id) {
    // String result = delProduct(id);

    // if (result.length() == 0)
    // return new ResponseEntity<>(new BaseResponse(0, "成功"), HttpStatus.OK);
    // else
    // return new ResponseEntity<>(new BaseResponse(1, result), HttpStatus.OK);
    // }

    // 刪除多筆商品API
    // @DeleteMapping("/products/delete-batch")
    // public ResponseEntity deleteBatchProduct(@RequestBody Integer[]
    // selectedproducts) {

    // String result = deleteAllProducts(selectedproducts);

    // if (result.length() == 0)
    // return new ResponseEntity<>(new BaseResponse(0, "刪除成功"), HttpStatus.OK);
    // else
    // return new ResponseEntity<>(new BaseResponse(1, result), HttpStatus.OK);
    // }

    // -------------------------------------------------------------------------

    // 到資料庫獲取商品
    // protected ArrayList<Product> getProductList() {
    // ArrayList<Product> products = new ArrayList<>();

    // try {
    // connect();

    // // 3. 取得Statement物件
    // String sql = "SELECT * FROM product";
    // stmt = conn.prepareStatement(sql);
    // // 4. 執行查詢
    // rs = stmt.executeQuery(sql);
    // // 5. 將sql回傳的資料存到變數
    // while (rs.next()) {
    // Product product = new Product();
    // product.setId(rs.getInt("id"));
    // product.setPhotoUrl(rs.getString("photo_url"));
    // product.setTitle(rs.getString("title"));
    // product.setDescription(rs.getString("description"));
    // product.setPrice(rs.getInt("price"));
    // product.setStoreUrl(rs.getString("store_url"));
    // product.setStoreName(rs.getString("store_name"));
    // products.add(product);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // } finally {
    // try {
    // if (rs != null)
    // rs.close();
    // if (stmt != null)
    // stmt.close();
    // if (conn != null)
    // conn.close();
    // } catch (SQLException se) {
    // se.printStackTrace();
    // }
    // }
    // return products;
    // }

    // 到資料庫獲取商品 v2
    // protected ArrayList<Product> getProductList(int limit, int offset, int
    // sortMode) {
    // ArrayList<Product> products = new ArrayList<>();

    // try {
    // connect();

    // String sort = sortMode == 0 ? "ASC" : "DESC";

    // // 3. 取得Statement物件
    // String sql = "SELECT * FROM product ORDER BY price " + sort + " limit " +
    // limit + " offset " + offset;
    // // 字串數字混用的SQL語法??

    // stmt = conn.prepareStatement(sql);
    // // 4. 執行查詢
    // rs = stmt.executeQuery(sql);
    // // 5. 將sql回傳的資料存到變數
    // while (rs.next()) {
    // Product product = new Product();
    // product.setId(rs.getInt("id"));
    // product.setPhotoUrl(rs.getString("photo_url"));
    // product.setTitle(rs.getString("title"));
    // product.setDescription(rs.getString("description"));
    // product.setPrice(rs.getInt("price"));
    // product.setStoreUrl(rs.getString("store_url"));
    // product.setStoreName(rs.getString("store_name"));
    // products.add(product);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // } finally {
    // try {
    // if (rs != null)
    // rs.close();
    // if (stmt != null)
    // stmt.close();
    // if (conn != null)
    // conn.close();
    // } catch (SQLException se) {
    // se.printStackTrace();
    // }
    // }
    // return products;
    // }

    // 新增商品到資料庫
    // protected String insertProduct(Product product) {

    // // ResultSet要return的，搭配executeQuery用，也就是查詢資料(SELECT)，INSERT、UPDATE 或DELETE皆不用
    // try {
    // connect();

    // String sql = "INSERT INTO product VALUES (null, ?, ?, ?, ?, ?, ?)";
    // pstmt = conn.prepareStatement(sql);
    // pstmt.setString(1, product.getPhotoUrl());
    // pstmt.setString(2, product.getTitle());
    // pstmt.setString(3, product.getDescription());
    // pstmt.setInt(4, product.getPrice());
    // pstmt.setString(5, product.getStoreUrl());
    // pstmt.setString(6, product.getStoreName());

    // pstmt.executeUpdate();

    // return "";
    // } catch (SQLException e) {
    // return e.getMessage();
    // } catch (ClassNotFoundException e) {
    // return "ClassNotFoundException";
    // }
    // }

    // 到資料庫更新資料
    // protected String update(Product productModel) {
    // try {
    // connect();

    // String sql = "UPDATE product\n" + //
    // "\tSET photo_url=?, title=?, description=?, price=?, store_url=?,
    // store_name=?\n" + //
    // "\tWHERE id=?;";

    // pstmt = conn.prepareStatement(sql);
    // pstmt.setString(1, productModel.getPhotoUrl());
    // pstmt.setString(2, productModel.getTitle());
    // pstmt.setString(3, productModel.getDescription());
    // pstmt.setInt(4, productModel.getPrice());
    // pstmt.setString(5, productModel.getStoreUrl());
    // pstmt.setString(6, productModel.getStoreName());
    // pstmt.setInt(7, productModel.getId());

    // pstmt.executeUpdate();
    // return "";
    // } catch (SQLException e) {
    // return e.getMessage();
    // } catch (ClassNotFoundException e) {
    // return "ClassNotFoundException";
    // }
    // }

    // 到資料庫刪除資料
    // protected String delProduct(int id) {

    // try {
    // connect();
    // String sql = "DELETE FROM product WHERE id = ?";
    // pstmt = conn.prepareStatement(sql);
    // pstmt.setInt(1, id);
    // pstmt.executeUpdate();

    // return "";
    // } catch (SQLException e) {
    // return e.getMessage();
    // } catch (ClassNotFoundException e) {
    // return "lassNotFoundException";
    // }
    // }

    // // 到資料庫刪除多筆資料
    // private String deleteAllProducts(Integer[] selectedProducts) {

    // try {
    // connect();

    // // 將整數陣列轉換為逗號分隔的字串
    // String joinedProducts = Arrays.stream(selectedProducts)
    // .map(Object::toString)
    // .collect(Collectors.joining(", "));
    // String sql = "DELETE FROM product WHERE id IN (" + joinedProducts + ");";
    // pstmt = conn.prepareStatement(sql);
    // pstmt.executeUpdate();

    // return "";
    // } catch (SQLException e) {
    // return e.getMessage();
    // } catch (ClassNotFoundException e) {
    // return "lassNotFoundException";
    // }
    // }

    // 取得商品總數
    // protected int getProductCount() {
    //     try {
    //         connect();

    //         // 3. 取得Statement物件
    //         stmt = conn.createStatement();

    //         rs = stmt.executeQuery("SELECT count(*) as c FROM product");

    //         // 取得總筆數
    //         rs.next();
    //         int total = rs.getInt("c");

    //         rs.close();
    //         stmt.close();
    //         ;
    //         conn.close();

    //         return total;
    //     } catch (ClassNotFoundException e) {
    //         System.out.println(e.getMessage());
    //         return -1;
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //         return -1;
    //     }
    // }

}

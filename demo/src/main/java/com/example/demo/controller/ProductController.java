package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response.PageResponse;
import com.example.demo.Response.SingleProductResponse;
import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.util.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// @Hidden
@Validated // 加上它@MAX,@MIN才會生效
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢商品列表(包含商品總數等資訊)
    @GetMapping("/products")
    @Tag(name = "商品API")
    @Operation(summary = "查詢商品列表", description = "可附上查詢條件")
    public ResponseEntity<PageResponse<Product>> getProducts(
        // 查詢條件 Filtering
        @RequestParam(required = false) ProductCategory category, // 種類
        @RequestParam(required = false) String search,            // 搜尋
        // 排序 Sorting
        @RequestParam(defaultValue = "created_date") String orderBy, // 預設依照創建日期排序
        @RequestParam(defaultValue = "desc") String sort,            // 預設降序
        // 分頁Pagination
        @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 要取得幾筆商品數據
        @RequestParam(defaultValue = "0") @Min(0) Integer offset,            // 要跳過多少筆數據
        HttpSession session
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
        PageResponse<Product> pageResponse = new PageResponse<>(0, "查询成功", page);   

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    // 隨機查詢商品列表
    @GetMapping("/products/random")
    public ResponseEntity<PageResponse<Product>> getRandomProducts(
        @RequestParam(defaultValue = "12")@Max(1000) @Min(0) Integer limit
    ){
        // 取得product list
        List<Product> productList = productService.getRandomProducts(limit);
        // total總數懶得給了，這邊通常前端要給limit，而且只有一頁

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(0);
        page.setTotal(0);
        page.setResults(productList);
        PageResponse<Product> pageResponse = new PageResponse<>(0, "查詢成功", page);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    // 查詢商品
    @GetMapping("/product/{productId}")
    @Tag(name = "商品API")
    @Operation(summary = "查詢商品", description = "輸入商品ID")
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
    @Tag(name = "商品API")
    @Operation(summary = "新增商品", description = "輸入商品資訊")
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
    @Tag(name = "商品API")
    @Operation(summary = "修改商品", description = "輸入商品ID")
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
    @Tag(name = "商品API")
    @Operation(summary = "刪除商品", description = "輸入商品ID")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        // 刪除商品無須檢查商品ID
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 刪除多筆商品
    @DeleteMapping("/products")
    @Tag(name = "商品API")
    @Operation(summary = "刪除多筆商品", description = "輸入要刪除的商品ID")
    public ResponseEntity<?> deleteBatchProducts(@RequestBody List<Integer> productIds) {
        productService.deleteBatchProductsById(productIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

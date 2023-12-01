package com.example.demo.service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductPage;

import jakarta.validation.Valid;

@Component
public class ProductService {
    
    @Autowired
    private ProductDao productDao;

    // 查詢商品
    public Product getProductById(Integer productId){
         return productDao.getProductById(productId);
    }

    // 新增商品
    public Integer creatrProduct(@Valid ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    // 修改商品
    public void updateProduct(Integer productId, @Valid ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    // 刪除商品
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    // 刪除多筆商品
    public void deleteBatchProductsById(List<Integer> productIds) {
        productDao.deleteBatchProductsById(productIds);
    }

    // 查詢所有商品
    public ArrayList<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    // 查詢所有商品V2
    public ProductPage getAllProductsV2() {
        ArrayList<Product> products = productDao.getAllProducts();
        int total = products.size(); // V2版只是要加個總數，所以使用V1版後再呼叫size()即可，就不用重寫Dao的V2版了

        ProductPage productPage = new ProductPage();
        productPage.setProducts(products);
        productPage.setTotal(total);
        
        return productPage;
    }    
}

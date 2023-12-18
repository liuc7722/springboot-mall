package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Component
public class ProductService {

    @Autowired
    private ProductDao productDao;

    // 查詢商品列表
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    // 取得商品總數
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    // 查詢商品
    public Product getProductById(Integer productId) {
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

    // 隨機查詢商品列表
    public List<Product> getRandomProducts(@Max(1000) @Min(0) Integer limit) {
        return productDao.getRandomProducts(limit);
    }


}

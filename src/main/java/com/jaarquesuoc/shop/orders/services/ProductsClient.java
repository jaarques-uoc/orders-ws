package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("products-ws")
public interface ProductsClient {

    @GetMapping("/products/{productId}")
    ProductDto getProduct(@PathVariable("productId") final String productId);

    @GetMapping("/products")
    List<ProductDto> getProducts(@RequestParam("ids") final List<String> productIds);
}

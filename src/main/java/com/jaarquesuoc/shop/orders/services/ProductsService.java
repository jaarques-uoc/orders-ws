package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsService {

    private final ProductsClient productsClient;

    public ProductDto getProduct(final String productId) {
        return productsClient.getProduct(productId);
    }

    public List<ProductDto> getProducts(final List<String> productIds) {
        return productsClient.getProducts(productIds);
    }
}

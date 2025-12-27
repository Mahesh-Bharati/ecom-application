package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product = new Product();
        updateProductFromRequest(product , productRequest);
        productRepository.save(product);
        return mapToPrductResponse(product);
    }
    private void updateProductFromRequest(Product product , ProductRequest productRequest)
    {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }
    private ProductResponse mapToPrductResponse(Product product)
    {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setActive(product.getActive());
        productResponse.setCategory(product.getCategory());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setStockQuantity(product.getStockQuantity());
        return productResponse;
    }

    public Optional<ProductResponse> updateProduct(Long id , ProductRequest productRequest)
    {
       return productRepository.findById(id).
                map(existingProduct ->{
                    updateProductFromRequest(existingProduct , productRequest);
                    Product saveProduct = productRepository.save(existingProduct);
                    return mapToPrductResponse(saveProduct);
                });
    }
    public List<ProductResponse> getAllProduct()
    {
        List<Product> productList = productRepository.findByActiveTrue();

        return productList
                .stream()
                .map(this::mapToPrductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id)
    {
        return productRepository.findById(id)
                .map(this::mapToPrductResponse)
                .orElse(null);
    }
    public String deleteProductById(Long id)
    {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null)
            return "Product is not available";
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    public List<ProductResponse> searchProduct(String keyword)
    {
        return productRepository.searchProduct(keyword)
                .stream()
                .map(this::mapToPrductResponse)
                .collect(Collectors.toList());
    }

}

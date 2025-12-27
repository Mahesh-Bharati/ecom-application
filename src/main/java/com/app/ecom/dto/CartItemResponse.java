package com.app.ecom.dto;

import com.app.ecom.model.Product;
import lombok.Data;

import java.util.List;
@Data
public class CartItemResponse {
    private Long userId;
    private List<ProductResponse> productList;

}


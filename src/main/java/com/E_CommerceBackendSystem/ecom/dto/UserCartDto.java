package com.E_CommerceBackendSystem.ecom.dto;

public record UserCartDto(String categoryName,long productId, String productName, double price, int qunatity, double finalPrice) {

}

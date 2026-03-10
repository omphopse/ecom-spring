package com.E_CommerceBackendSystem.ecom.dto;

public record UserCartDto(String categoryName, String productName, double price, int qunatity, double finalPrice) {

}

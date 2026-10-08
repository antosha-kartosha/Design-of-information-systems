package com.magicshop.model;
import java.math.BigDecimal;import java.time.LocalDateTime;
public class Models {
 public record User(Long id,String username,String email,String password,String displayName,String phone,String role){}
 public record Product(Long id,String name,String description,BigDecimal price,Integer quantity){}
 public record Order(Long id,Long userId,String status,BigDecimal totalPrice,String deliveryAddress,String paymentMethod,LocalDateTime createdAt){}
}

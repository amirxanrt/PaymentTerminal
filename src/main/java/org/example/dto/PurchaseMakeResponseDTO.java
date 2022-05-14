package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseMakeResponseDTO {
    private long id;
    private long productId;
    private int qty;
    private String productName;
    private  int productPrice;
    private long userId;
    private  int sum;


}

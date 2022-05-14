package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSearchResponseDTO {
    private long id;
    private String name;
    private int price;
    private int qty;
    private String photo;
}

package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewGetAllResponseDTO {
    private long id;
    private long  userId;
    private long productId;
    private String review;
    private String userLogin;

}

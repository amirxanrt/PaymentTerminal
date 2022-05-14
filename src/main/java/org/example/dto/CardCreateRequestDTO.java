package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardCreateRequestDTO {
    private long id;
    private long userId;
    private String city;
    private long phoneNumber;
    private long bonusCard;
    private String qrCode;

}

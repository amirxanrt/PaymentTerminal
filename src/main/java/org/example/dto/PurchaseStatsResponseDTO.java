package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseStatsResponseDTO {
    private int sum;
    private int min;
    private int max;
    private int avg;

}

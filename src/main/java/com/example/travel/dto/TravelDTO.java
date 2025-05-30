package com.example.travel.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelDTO {
    private int id;
    private String title;
    private String district; //권역
    private String description;
    private String address;
    private String phone;
}

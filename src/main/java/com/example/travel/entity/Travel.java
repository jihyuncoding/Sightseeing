package com.example.travel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "travel_spots" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Travel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String district;
    private String description;
    private String address;
    private String phone;
}

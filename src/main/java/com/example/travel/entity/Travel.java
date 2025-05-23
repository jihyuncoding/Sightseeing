package com.example.travel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "travel" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Travel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String district;
    private String title;
    private String description;
    private String address;
    private String phone;
}

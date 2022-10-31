package com.example.productcategoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {

    private int id;
    private String name;
    private String surname;
    private String email;

}

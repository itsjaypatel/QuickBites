package com.itsjaypatel.zomatoapp.dtos;

import com.itsjaypatel.zomatoapp.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    private UserEntityDto user;

    private Double rating;

    private PointDto location;
}

package com.itsjaypatel.quickbites.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private final String type = "Point";

    private double[] coordinates;

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}

package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageDTO {
    private Integer carriageNumber;
    private Integer carriageType;
    private Integer seatCount;
    private Integer noSeatCount;
}

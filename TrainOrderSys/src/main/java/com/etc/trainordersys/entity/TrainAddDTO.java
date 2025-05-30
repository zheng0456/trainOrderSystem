package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainAddDTO {
    private Integer trainCode;
    private Integer trainType;
    private Integer status;
    private List<CarriageDTO> carriages;//一趟火车有多个车厢
}

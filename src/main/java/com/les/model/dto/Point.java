package com.les.model.dto;

import lombok.Data;

@Data
public class Point<X, Y> {

    private final X x;
    private final Y y;
}

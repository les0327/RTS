package com.les.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class Lab4ControllerTest {

    @Test
    public void getFFT() {
        log.info("{}", Arrays.toString(new Lab4Controller().getFFT(1024)));
    }
}
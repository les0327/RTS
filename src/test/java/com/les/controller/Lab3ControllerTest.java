package com.les.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

@Slf4j
public class Lab3ControllerTest {

    @Test
    public void getDFT() {
        log.info("{}", Arrays.toString(new Lab3Controller().getDFT(1024)));
    }

}
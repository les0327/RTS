package com.les.controller;

import com.les.service.FftService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

@Slf4j
public class Lab4ControllerTest {

    private final Lab4Controller controller = new Lab4Controller(new FftService());

    @Test
    public void time() {
      log.info("{}", Arrays.toString(controller.time(1)));
    }
}
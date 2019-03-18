package com.les.util;

import com.les.model.dto.Complex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

@Slf4j
public class MathUtilsTest {

    @Test
    public void getW() {
        Complex[][] W = MathUtils.getW(2);

        for (Complex[] cof : W) {
            log.info("{}", Arrays.toString(cof));
        }
    }
}
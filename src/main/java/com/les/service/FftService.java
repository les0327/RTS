package com.les.service;

import com.les.model.dto.Complex;
import com.les.util.MathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.les.util.MathUtils.getW;
import static com.les.util.Utils.divideArr;

@Service
public class FftService {

    private final ForkJoinPool forkJoinPool;

    public FftService() {
        this.forkJoinPool = ForkJoinPool.commonPool();
    }

    public Complex[] FFT(Double[] x) {
        FftAction forkJoinTask = new FftAction(x);

        forkJoinPool.invoke(forkJoinTask);

        return forkJoinTask.join();
    }

    @RequiredArgsConstructor
    private class FftAction extends RecursiveTask<Complex[]> {

        private final Double[] x;

        @Override
        protected Complex[] compute() {
            int N = x.length;

            if (N != 4) {
                Complex[] F = new Complex[N];

                Double[] evenX = divideArr(x, true);
                Double[] oddX = divideArr(x, false);

                FftAction evenAction = new FftAction(evenX);
                FftAction oddAction = new FftAction(oddX);

                ForkJoinTask.invokeAll(evenAction, oddAction);

                Complex[] evenF = evenAction.join();
                Complex[] oddF = oddAction.join();

                Complex[] W = getW(N, 1);

                for (int p = 0; p < N / 2; p++) {
                    F[p] = evenF[p].add(W[p].mul(oddF[p]));
                    F[p + N / 2] = evenF[p].sub(W[p].mul(oddF[p]));
                }

                return F;
            }

            return computeDirectly();
        }

        private Complex[] computeDirectly() {
            return MathUtils.FFT(x);
        }
    }
}

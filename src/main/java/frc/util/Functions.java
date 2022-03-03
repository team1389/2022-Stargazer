package frc.util;

import java.util.Arrays;

public class Functions {
    public static double sum(double[] arr) {
        return Arrays.stream(arr).reduce(0.0, Double::sum);
    }
}

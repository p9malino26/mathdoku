package com.patryk.mathdoku;

public class ArrayConversions {
    public static float[] toNative(Float[] arr) {
        float[] out = new float[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }

        return out;
    }

    public static int[] toNative(Object[] arr) {

        int[] out = new int[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            out[i] = (Integer)arr[i];
        }

        return out;
    }

    public static double[] toDouble(float[] arr) {
        double[] out = new double[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }

        return out;
    }

    public static Float[] fromNative(float[] arr) {
        Float[] out = new Float[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }

        return out;
    }

}


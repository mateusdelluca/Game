package com.mygdx.game.services;

public class WorkServices {

    public static <T extends Comparable<T>> T highest(T... field) {
        T max = field[0];
        for (T value : field) {
            if (max.compareTo(value) < 0) {
                max = value;
            }
        }return max;
    }


}

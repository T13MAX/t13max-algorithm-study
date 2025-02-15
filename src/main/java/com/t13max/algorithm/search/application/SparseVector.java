package com.t13max.algorithm.search.application;

import java.util.HashMap;

/**
 * 能够完成点乘的稀疏向量
 *
 * @Author 呆呆
 * @Datetime 2021/9/18 21:48
 */
public class SparseVector {
    private HashMap<Integer, Double> st;

    private SparseVector() {
        st = new HashMap<>();
    }

    public int size() {
        return st.size();
    }

    public void put(int i, double x) {
        st.put(i, x);
    }

    public double get(int i) {
        if (!st.containsKey(i)) return 0.0;
        else return st.get(i);
    }

    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keySet()) {
            sum += that[i] * this.get(i);
        }
        return sum;
    }



}

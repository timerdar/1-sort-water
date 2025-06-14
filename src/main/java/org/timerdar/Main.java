package org.timerdar;

import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        LiquidReader lr = new LiquidReader();
        int n = lr.getN();
        int v = lr.getV();
        List<Stack<Integer>> liquids = lr.readDataFromTerminal(n, v);
        new LiquidsSorter().sort(liquids, v);
    }
}
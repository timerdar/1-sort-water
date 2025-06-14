package org.timerdar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class LiquidReader {

    private final Scanner sc = new Scanner(System.in);

    public List<Stack<Integer>> readDataFromTerminal(int n, int v){
        ArrayList<Stack<Integer>> liquids = new ArrayList<>();
        for (int i = 0; i < n; i++){
            liquids.add(readOneLiquid(v));
        }
        return liquids;
    }

    private Stack<Integer> readOneLiquid(int v){
        Stack<Integer> liquid = new Stack<>();
        System.out.println("Введите последовательно снизу-вверх слои (" + v + " шт.), если слоя нет (например в пробирке 2/4 слоя) - 0:");
        for (int i = 0; i < v; i++){
            int color = sc.nextInt();
            if (color != 0){
                liquid.push(color);
            }
        }
        return liquid;
    }

    public int getV(){
        System.out.println("Введите объем пробирки (v):");
        return sc.nextInt();
    }

    public int getN(){
        System.out.println("Введите количество пробирок (n): ");
        return sc.nextInt();
    }
}

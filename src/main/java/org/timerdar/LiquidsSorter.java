package org.timerdar;

import java.util.*;

public class LiquidsSorter {

    public void sort(List<Stack<Integer>> liquids, int v){
        if (isAllLiquidsFull(liquids, v)){
            Stack<Integer> liquidA = null;
            Stack<Integer> liquidB = null;
            int mvpColor = getMoreVailableColor(liquids);
            for (Stack<Integer> liquid: liquids){
                if (liquid.empty()){
                    liquidB = liquid;
                }else{
                    if (liquid.size() == v && liquid.peek() == mvpColor){
                        liquidA = liquid;
                    }
                }
            }
            if(liquidA != null && liquidB != null){
                replaceFromAtoB(liquidA, liquidB, v);
                System.out.print("(" + liquids.indexOf(liquidA) + " , " + liquids.indexOf(liquidB) + ") ");
            }
        }
        while (!isAllLiquidsGood(liquids, v)){
            for (int actualVolume = 1; actualVolume < v; actualVolume++){
                List<Integer> liquidsWithNeededVolume = getLiquidsWithNeededVolume(liquids, actualVolume);
                for (int liquidNum: liquidsWithNeededVolume){
                    Stack<Integer> liquidB = liquids.get(liquidNum);
                    for(Stack<Integer> liquidA: liquids){
                        if (!liquidA.equals(liquidB)
                                && !isLiquidDone(liquidB, v)
                                && !isLiquidDone(liquidA, v)
                                && !liquidA.empty()
                                && Objects.equals(liquidA.peek(), liquidB.peek())){
                            replaceFromAtoB(liquidA, liquidB, v);
                            System.out.print("(" + liquids.indexOf(liquidA) + " , " + liquids.indexOf(liquidB) + ") ");
                        }
                    }
                }
            }
        }
    }

    private int getMoreVailableColor(List<Stack<Integer>> liquids) {
        HashMap<Integer, Integer> peekColorsCounts = new HashMap<>();
        for (Stack<Integer> liquid: liquids){
            if (!liquid.empty()){
                Stack<Integer> copyLiquid = (Stack<Integer>) liquid.clone();
                int peekColor = copyLiquid.peek();
                int count = 0;
                while (copyLiquid.pop() == peekColor){
                    count++;
                }
                if (peekColorsCounts.containsKey(peekColor)) {
                    peekColorsCounts.put(peekColor, peekColorsCounts.get(peekColor) + count);
                }else{
                    peekColorsCounts.put(peekColor, count);
                }
            }
        }
        int maxCount = 0;
        int maxColor = 0;
        for (int color: peekColorsCounts.keySet()){
            if (peekColorsCounts.get(color) > maxCount){
                maxColor = color;
                maxCount = peekColorsCounts.get(color);
            }
        }
        return maxColor;

    }

    private void replaceFromAtoB(Stack<Integer> a, Stack<Integer> b, int v){
        if (b.empty() && !isLiquidDone(a, v)){
            int aPeekColor = a.pop();
            b.push(aPeekColor);
            while (aPeekColor == a.peek()){
                int color = a.pop();
                b.push(color);
            }
            return;
        }
        if (b.size() < v){
            int bPeekColor = b.peek();
            if ((a.size() == 1)  && (a.peek() == bPeekColor)){
                b.push(a.pop());
            }else{
                while ((a.size() > 0) && (a.peek() == bPeekColor) && (b.size() < v)){
                    b.push(a.pop());
                }
            }
        }
    }

    private boolean isLiquidDone(Stack<Integer> liquid, int v){
        if(liquid.empty()){
            return true;
        }
        Stack<Integer> copyLiquid = (Stack<Integer>) liquid.clone();
        int color = copyLiquid.peek();
        int count = 0;
        while(!copyLiquid.empty()){
            int nextColor = copyLiquid.pop();
            if (nextColor == color){
                count ++;
            }
        }
        return count == v;
    }

    private List<Integer> getLiquidsWithNeededVolume(List<Stack<Integer>> liquids, int actualVolume){
        ArrayList<Integer> liquidsWithNeededVolume = new ArrayList<>();
        for(Stack<Integer> liquid: liquids){
            if (liquid.size() == actualVolume){
                liquidsWithNeededVolume.add(liquids.indexOf(liquid));
            }
        }
        return liquidsWithNeededVolume;
    }

    private boolean isAllLiquidsGood(List<Stack<Integer>> liquids, int v){
        for (Stack<Integer> liquid: liquids){
            if (!isLiquidDone(liquid, v)){
                return false;
            }
        }
        return true;
    }

    //для обработки ситуации, когда есть только два типа пробирок - пустые и полные
    private boolean isAllLiquidsFull(List<Stack<Integer>> liquids, int v) {
        for (Stack<Integer> liquid: liquids){
            if (!liquid.isEmpty() && liquid.size() != v){
                return false;
            }
        }
        return true;
    }
}

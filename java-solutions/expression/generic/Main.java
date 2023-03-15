package expression.generic;

import java.util.*;

public class Main {
    private static class A {
        public A(){}
    }

    public static void main(String[] args) {
        Map<Integer, Integer> relations = new HashMap<>();
        relations.put(1, 2);
        relations.put(3, 4);
        relations.forEach((k, v) -> relations.put(v, k));
    }
}

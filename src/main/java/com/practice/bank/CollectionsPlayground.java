package com.practice.bank;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;

public class CollectionsPlayground {

    public static void main(String[] args) {

        String[] holders = new String[3];
        holders[0] = "George";
        holders[1] = "Anna";
        holders[2] = "Maria";
        System.out.println("1. Length: " + holders.length);
        System.out.println("1. Printed directly: " + holders);
        System.out.println("1. Arrays.toString: " + Arrays.toString(holders));

        List<String> names = new ArrayList<>();
        names.add("George");
        names.add("Anna");
        names.add("Maria");
        names.add("Nikos");
        names.add("Anna");
        System.out.println("2. " + names + ", size " + names.size());
        System.out.println("2. get(1): " + names.get(1));
        System.out.println("2. contains Maria? " + names.contains("Maria"));
        names.remove("Anna");
        System.out.println("2. After remove: " + names);


        for (String name : names) {
            System.out.println("3. Hello, " + name);
        }

        Set<String> unique = new HashSet<>(List.of("George", "Anna", "George", "Maria", "Anna"));
        System.out.println("4. " + unique + ", size " + unique.size());

        Map<String, BigDecimal> balances = new HashMap<>();
        balances.put("George", new BigDecimal("100.00"));
        balances.put("Anna", new BigDecimal("200.00"));
        System.out.println("5. George: " + balances.get("George"));
        System.out.println("5. Nikos: " + balances.get("Nikos"));
        balances.put("George", new BigDecimal("300.00"));
        System.out.println("5. George after a second put: " + balances.get("George"));
        System.out.println("5. Size: " + balances.size());

        for (Map.Entry<String, BigDecimal> entry : balances.entrySet()) {
            System.out.println("5. " + entry.getKey() + " -> " + entry.getValue());
        }

        Map<Long, String> owners = new HashMap<>();
        System.out.println("6. get(1001L): " + owners.get(1001L));
        System.out.println("6. get(1001): " + owners.get(1001));

    }
}

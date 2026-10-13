package com.practice.bank;

import java.math.BigDecimal;

public class ObjectsPlayground {
    public static void main(String[] args) {

        Account george = new Account("George", new BigDecimal("100.00"));
        Account anna = new Account("Anna");
        System.out.println("1. " + george);
        System.out.println("1. " + anna);

        george.deposit(new BigDecimal("50"));
        System.out.println("2. George: " + george.getBalance());
        System.out.println("2. Anna: " + anna.getBalance());

        Account sameAsGeorge = george;
        sameAsGeorge.withdraw(new BigDecimal("30"));
        System.out.println("3. George after withdrawing through the alias: " + george.getBalance());


        System.out.println("4. george == sameAsGeorge: " + (george == sameAsGeorge));
        System.out.println("4. george == anna: " + (george == anna));
        System.out.println("4. george.equals(anna): " + george.equals(anna));
        System.out.println("4. george == george " + (george == george));

        String typed = new String("1234");
        System.out.println("5. typed == \"1234\":     " + (typed == "1234"));
        System.out.println("5. typed.equals(\"1234\"): " + typed.equals("1234"));

        Account third = new Account("Maria");
        System.out.println("6. " + third);

        Account nobody = null;
        System.out.println("7. nobody: " + nobody);
    }
}

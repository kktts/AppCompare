package com.konstantinost.appcompare;

import java.util.Random;

/**
 * Created by Konstantinos on 5/3/2016.
 */
public class Security {

    private static final String siteUrl = "http://www.appcompare.eu.pn/communicate/attach.php";

    public static String codeGenerator() {
        String code = "";
        Random r = new Random();

        int number1 = r.nextInt(10);
        while (number1 % 2 != 0) {
            number1 = r.nextInt(10);
        }
        code += String.valueOf(number1);

        int number2 = r.nextInt(10);
        while (number2 % 2 == 0) {
            number2 = r.nextInt(10);
        }
        code += String.valueOf(number2);

        int number3 = r.nextInt(90) + 10;
        while (number3 % 13 != 0) {
            number3 = r.nextInt(90) + 10;
        }
        code += String.valueOf(number3);

        int number4 = r.nextInt(10);
        while (number4 % 2 != 0) {
            number4 = r.nextInt(10);
        }
        code += String.valueOf(number4);
        return code;
    }

    public static String getSiteUrl() {
        return siteUrl;
    }
}

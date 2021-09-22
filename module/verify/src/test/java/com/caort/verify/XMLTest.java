package com.caort.verify;

import java.util.Base64;

/**
 * @author Reed
 * @date 2021/9/14 上午10:15
 */
public class XMLTest {
    public static void main(String[] args) {
        String str = "";
        byte[] decode = Base64.getDecoder().decode(str);
        String newStr = new String(decode);
        String s = newStr.replaceAll("\\(", "_");
        String s1 = s.replaceAll("\\)", "");
        System.out.println(s1);
    }
}

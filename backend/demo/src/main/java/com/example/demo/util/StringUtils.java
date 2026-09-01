package com.example.demo.util;

public class StringUtils {
    
    //ヌルまたは空欄確認
    public static boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}

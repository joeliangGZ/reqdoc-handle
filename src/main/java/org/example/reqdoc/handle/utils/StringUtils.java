package org.example.reqdoc.handle.utils;

public  class StringUtils {
    public static String trimString(String input) {
        int startIndex = input.indexOf('[');
        int endIndex = input.lastIndexOf(']');

        // 检查是否存在'['和']'，并且顺序正确
        if (startIndex == -1 || endIndex == -1 || startIndex > endIndex) {
            return ""; // 或根据需求返回null、原字符串等
        }

        return input.substring(startIndex, endIndex + 1);
    }
}

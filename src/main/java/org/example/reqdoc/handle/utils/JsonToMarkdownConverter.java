package org.example.reqdoc.handle.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonToMarkdownConverter {
    public static String convertJsonToMarkdown(String jsonString) throws Exception {
        // 解析 JSON 字符串为 JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);

        // 检查是否为数组
        if (!rootNode.isArray()) {
            throw new IllegalArgumentException("Input JSON must be an array of objects");
        }

        // 获取所有字段名（动态提取）
        List<String> headers = new ArrayList<>();
        rootNode.forEach(node -> {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (!headers.contains(fieldName)) {
                    headers.add(fieldName);
                }
            }
        });

        // 构建 Markdown 表格
        StringBuilder markdown = new StringBuilder();

        // 表头
        markdown.append("| ");
        markdown.append(String.join(" | ", headers));
        markdown.append(" |");
        markdown.append("\n");

        // 分隔线
        markdown.append("| ");
        for (int i = 0; i < headers.size(); i++) {
            markdown.append("---");
            if (i < headers.size() - 1) {
                markdown.append(" | ");
            }
        }
        markdown.append(" |");
        markdown.append("\n");


        // 表格内容
        for (JsonNode node : rootNode) {
            markdown.append("| ");
            for (int i = 0; i < headers.size(); i++) {
                String fieldName = headers.get(i);
                JsonNode valueNode = node.get(fieldName);
                String value = formatValue(valueNode);
                markdown.append(value);
                if (i < headers.size() - 1) {
                    markdown.append(" | ");
                }
            }
            markdown.append(" |");
            //markdown.append("<br>");
            markdown.append("\n");
        }

        return markdown.toString();
    }

    // 处理字段值，包括 null、换行符和 Markdown 特殊字符
    private static String formatValue(JsonNode valueNode) {
        if (valueNode == null || valueNode.isNull()) {
            return "";
        }
        String rawValue = valueNode.asText();
        // 转义 Markdown 中的特殊字符 '|'
        rawValue = rawValue.replace("|", "\\|");
        // 将换行符替换为 <br> 以在 Markdown 中显示
        //rawValue = rawValue.replace("", "<br>");
        return rawValue;
    }
}

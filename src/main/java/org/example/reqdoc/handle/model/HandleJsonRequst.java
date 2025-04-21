package org.example.reqdoc.handle.model;

import lombok.Data;

@Data
public class HandleJsonRequst {
    private String originalJson;
    private String llmJson;
    private String transferField;
}

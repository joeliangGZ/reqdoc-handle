package org.example.reqdoc.handle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reqdoc.handle.model.RequirementDOC;
import org.example.reqdoc.handle.model.RequirementDocOutput;
import org.example.reqdoc.handle.utils.JsonToMarkdownConverter;
import org.example.reqdoc.handle.utils.StringUtils;
import org.example.reqdoc.handle.utils.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReqDocController {
    private static final Logger log = LoggerFactory.getLogger(ReqDocController.class);

    @PostMapping("/get-doc")
    public String getDOCFile(@RequestParam("fileInput") MultipartFile file) throws IOException {
        InputStream inputStream = null;
        inputStream = file.getInputStream();
        List<RequirementDOC> requirementDOCS = WordUtils.getWord(inputStream);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(requirementDOCS);
    }

    @PostMapping("/handle-json")
    public String handleJson(@RequestParam("originalJson") String originalJson, @RequestParam("llmJson") String llmJson, @RequestParam("transferField") String transferField) throws JsonProcessingException {
        llmJson = StringUtils.trimString(llmJson);
        ObjectMapper objectMapper = new ObjectMapper();

        List<RequirementDOC> originalList = objectMapper.readValue(originalJson, new TypeReference<List<RequirementDOC>>() {
        });
        List<RequirementDOC> llmList = objectMapper.readValue(llmJson, new TypeReference<List<RequirementDOC>>() {
        });
        for (RequirementDOC llm : llmList) {
            for (RequirementDOC original : originalList) {
                if (llm.getId() == original.getId()) {
                    if ("isRequirement".equals(transferField)) {
                        original.setIsRequirement(llm.getIsRequirement());

                    } else if ("keyword".equals(transferField)) {
                        original.setKeyword(llm.getKeyword());
                    }

                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(originalList);
    }

    @PostMapping("/valid-title")
    public String validIsRequirement(@RequestParam("originalJson") String originalJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<RequirementDOC> originalList = objectMapper.readValue(originalJson, new TypeReference<List<RequirementDOC>>() {
        });
        List<RequirementDOC> outputList = new ArrayList<>();
        for (RequirementDOC original : originalList) {
            if (original.getIsRequirement()) {
                outputList.add(original);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(outputList);
    }

    @PostMapping("final-handle")
    public String finalHandle(@RequestBody String originalJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> jsonArrayStrings = objectMapper.readValue(originalJson, new TypeReference<List<String>>() {
        });
        List<RequirementDocOutput> returnList = new ArrayList<>();
        for (String innerJson : jsonArrayStrings) {
            innerJson = StringUtils.trimString(innerJson);
            List<RequirementDocOutput> items = objectMapper.readValue(innerJson, new TypeReference<List<RequirementDocOutput>>() {
            });
            returnList.addAll(items);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(returnList);
    }
    @PostMapping("export-md")
    public String exportMd(@RequestParam("originalJson") String originalJson) throws Exception {
      return   JsonToMarkdownConverter.convertJsonToMarkdown(originalJson);
    }
}

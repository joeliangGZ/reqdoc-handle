package org.example.reqdoc.handle.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.example.reqdoc.handle.model.RequirementDOC;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WordUtils {
    public static List<RequirementDOC> getWord(InputStream inputStream) throws IOException {
        XWPFDocument doc = new XWPFDocument(inputStream);
        RequirementDOC requirementDOC = new RequirementDOC();
        int id = 1;
        requirementDOC.setId(id);
        List<RequirementDOC> requirementDOCS = new ArrayList<>();
        for (XWPFParagraph para : doc.getParagraphs()) {
            String style = para.getStyle();
            String text = para.getText();
            if (style == null) {
                if (StringUtils.hasText(requirementDOC.getTitle1())) {
                    String content = requirementDOC.getContent();
                    if (!StringUtils.hasText(content) || "null".equals(content)) {
                        content = text;
                    } else {
                        content = content + " " + text;
                    }

                    requirementDOC.setContent(content);
                }
                continue;
            }

            if ("af1".equals(style) || "ListBullet".equals(style) || "ListNumber".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getTitle1())) {
                    String content = requirementDOC.getContent();
                    if (!StringUtils.hasText(content) || "null".equals(content)) {
                        content = text;
                    } else {
                        content = content + " " + text;
                    }
                    requirementDOC.setContent(content);
                }
            } else if ("1".equals(style) || "Heading1".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getContent())) {
                    requirementDOCS.add(requirementDOC);
                    requirementDOC = new RequirementDOC();
                    id += 1;
                    requirementDOC.setId(id);
                }
                requirementDOC.setTitle1(text);
            } else if ("2".equals(style) || "Heading2".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getContent())) {
                    String title1 = requirementDOC.getTitle1();
                    requirementDOCS.add(requirementDOC);
                    requirementDOC = new RequirementDOC();
                    id += 1;
                    requirementDOC.setId(id);
                    requirementDOC.setTitle1(title1);

                }
                requirementDOC.setTitle2(text);
            } else if ("3".equals(style) || "Heading3".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getContent())) {
                    String title1 = requirementDOC.getTitle1();
                    String title2 = requirementDOC.getTitle2();
                    requirementDOCS.add(requirementDOC);
                    requirementDOC = new RequirementDOC();
                    id += 1;
                    requirementDOC.setId(id);
                    requirementDOC.setTitle1(title1);
                    requirementDOC.setTitle2(title2);
                }
                requirementDOC.setTitle3(text);
            } else if ("4".equals(style) || "Heading4".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getContent())) {
                    String title1 = requirementDOC.getTitle1();
                    String title2 = requirementDOC.getTitle2();
                    String title3 = requirementDOC.getTitle3();
                    requirementDOCS.add(requirementDOC);
                    requirementDOC = new RequirementDOC();
                    id += 1;
                    requirementDOC.setId(id);
                    requirementDOC.setTitle1(title1);
                    requirementDOC.setTitle2(title2);
                    requirementDOC.setTitle3(title3);
                }
                requirementDOC.setTitle4(text);
            } else if ("5".equals(style) || "Heading5".equals(style)) {
                if (StringUtils.hasText(requirementDOC.getContent())) {
                    String title1 = requirementDOC.getTitle1();
                    String title2 = requirementDOC.getTitle2();
                    String title3 = requirementDOC.getTitle3();
                    String title4 = requirementDOC.getTitle4();
                    requirementDOCS.add(requirementDOC);
                    requirementDOC = new RequirementDOC();
                    id += 1;
                    requirementDOC.setId(id);
                    requirementDOC.setTitle1(title1);
                    requirementDOC.setTitle2(title2);
                    requirementDOC.setTitle3(title3);
                    requirementDOC.setTitle4(title4);
                }
                requirementDOC.setTitle5(text);
            }


        }
        return requirementDOCS;
    }
}

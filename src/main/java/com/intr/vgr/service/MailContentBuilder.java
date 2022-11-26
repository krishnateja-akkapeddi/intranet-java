package com.intr.vgr.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    private MailContentBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    String build(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return  templateEngine.process("mailTemplate", context);
    }

}

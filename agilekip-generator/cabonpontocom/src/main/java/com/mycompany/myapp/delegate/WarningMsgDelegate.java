package com.mycompany.myapp.delegate;

import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import java.util.Locale;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class WarningMsgDelegate implements JavaDelegate {

    @Autowired
    MailService mailService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ClientOrderProcessDTO clientOrderProcess = (ClientOrderProcessDTO) delegateExecution.getVariable("processInstance");
        ClientOrderDTO clientOrder = clientOrderProcess.getClientOrder();
        String to = clientOrder.getClientEmail();
        String subject = "[Cabunpontocom] Your Order could not be completed...) " + clientOrder.getOrderID();
        Context context = new Context(Locale.getDefault());
        context.setVariable("clientOrder", clientOrder);
        String content = templateEngine.process("clientOrderProcess/warningMsgEmail", context);
        mailService.sendEmail(to, subject, content, false, true);
    }
}

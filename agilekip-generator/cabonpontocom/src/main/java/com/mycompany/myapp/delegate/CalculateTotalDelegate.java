package com.mycompany.myapp.delegate;

import static java.lang.Math.random;

import com.mycompany.myapp.domain.ClientOrder;
import com.mycompany.myapp.repository.ClientOrderRepository;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CalculateTotalDelegate implements JavaDelegate {

    private final ClientOrderRepository clientOrderRepo;

    public CalculateTotalDelegate(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepo = clientOrderRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("+++++++++++++++++++++++");
        System.out.println(delegateExecution.getVariable("processInstance").getClass().getName());
        System.out.println(delegateExecution.getVariable("processInstance"));
        System.out.println("+++++++++++++++++++++++");

        ClientOrderProcessDTO pi = (ClientOrderProcessDTO) delegateExecution.getVariable("processInstance");
        ClientOrderDTO clientOrderDTO = pi.getClientOrder();

        int total = 250 + (int) (random() * ((10000 - 250) + 1));
        ClientOrder clientOrderObj = clientOrderRepo.findById(clientOrderDTO.getId()).get();
        clientOrderObj.setOrderPrice(total);
        clientOrderDTO.setOrderPrice(total);

        // save the modified obj to the repo
        clientOrderRepo.save(clientOrderObj);

        System.out.println("=================================================");
        System.out.println("=============== Total = " + total + "======================");
        System.out.println("=================================================");
    }
}

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
public class CheckCompatibilityDelegate implements JavaDelegate {

    private final ClientOrderRepository clientOrderRepo;

    public CheckCompatibilityDelegate(ClientOrderRepository clientOrderRepository) {
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

        boolean state = random() < 0.5;
        ClientOrder clientOrderObj = clientOrderRepo.findById(clientOrderDTO.getId()).get();
        clientOrderObj.setIsCompatible(state);
        clientOrderDTO.setIsCompatible(state);

        // save the modified obj to the repo
        clientOrderRepo.save(clientOrderObj);

        System.out.println("=================================================");
        System.out.println("=============== State = " + state + "======================");
        System.out.println("=================================================");
    }
}

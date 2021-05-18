package ru.demo.camunda.db.access.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.demo.camunda.db.access.config.ProcessVariables;
import ru.demo.camunda.db.access.service.AccessRequestService;

@Component
public class CreateAccessRequest implements JavaDelegate {

    private AccessRequestService accessRequestService;

    public CreateAccessRequest(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long eId = (Long)delegateExecution.getVariable(ProcessVariables.ENTITY_ID);
        String user = (String)delegateExecution.getVariable(ProcessVariables.USERNAME);
        String comm = (String)delegateExecution.getVariable(ProcessVariables.COMMENT);
        Long id = accessRequestService.create(eId, user, comm);
        delegateExecution.setVariable(ProcessVariables.ID, id);
    }
}

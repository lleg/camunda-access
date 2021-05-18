package ru.demo.camunda.db.access.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.demo.camunda.db.access.config.ProcessVariables;
import ru.demo.camunda.db.access.service.AccessRequestService;

@Component
public class UpdateAccessRequest implements JavaDelegate {

    private final AccessRequestService accessRequestService;

    public UpdateAccessRequest(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long id = (Long) delegateExecution.getVariable(ProcessVariables.ENTITY_ID);
        String approver = (String) delegateExecution.getVariable(ProcessVariables.APPROVER);
        Boolean isApp = (Boolean) delegateExecution.getVariable(ProcessVariables.IS_APPROVED);

        accessRequestService.update(id, approver, isApp);
    }
}

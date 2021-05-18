package ru.demo.camunda.db.access.service;

import org.springframework.stereotype.Service;
import ru.demo.camunda.db.access.domain.AccessRequest;
import ru.demo.camunda.db.access.repository.AccessRequestRepository;

import java.util.Optional;

@Service
public class AccessRequestServiceImpl implements AccessRequestService {

    private AccessRequestRepository accessRequestRepository;

    public AccessRequestServiceImpl(AccessRequestRepository accessRequestRepository) {
        this.accessRequestRepository = accessRequestRepository;
    }

    @Override
    public Long create(Long entityId, String user, String comment) {
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setEntityId(entityId);
        accessRequest.setUserName(user);
        accessRequest.setComment(comment);
        return accessRequestRepository.saveAndFlush(accessRequest).getId();

    }

    @Override
    public void update(Long entityId, String approver, Boolean isApp) {
        Optional<AccessRequest> accessRequestOpt =
                accessRequestRepository.findOneByEntityId(entityId);
        if(accessRequestOpt.isPresent()){
            AccessRequest accessRequest = accessRequestOpt.get();
            accessRequest.setApprover(approver);
            accessRequest.setIsApproved(isApp);
            accessRequestRepository.saveAndFlush(accessRequest);
        }

    }
}

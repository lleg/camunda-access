package ru.demo.camunda.db.access.service;

public interface AccessRequestService {

    Long create(Long entityId, String user, String comment);

    void update(Long id, String approver, Boolean isApp);
}

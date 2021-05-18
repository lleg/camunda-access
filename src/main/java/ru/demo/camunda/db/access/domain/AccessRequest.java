package ru.demo.camunda.db.access.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "access_request")
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approver")
    private String approver;

    @Column(name = "is_approved")
    private Boolean isApproved;


}

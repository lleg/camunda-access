package ru.demo.camunda.db.access;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import ru.demo.camunda.db.access.delegate.CreateAccessRequest;
import ru.demo.camunda.db.access.delegate.UpdateAccessRequest;
import ru.demo.camunda.db.access.service.AccessRequestService;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CamundaDbApplicationTests {

	private static final String PROCESS_KEY = "accessRequestHandling";

	@Mock
	private AccessRequestService accessRequestService;


	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();

	@Before
	public void init() {


		CreateAccessRequest accessRequest = new CreateAccessRequest(accessRequestService);
		UpdateAccessRequest updateAccessRequest = new UpdateAccessRequest(accessRequestService);
		// You have to add all mock able methods

		Mocks.register("createAccessRequest", accessRequest);
		Mocks.register("updateAccessRequest", accessRequest);

	}

	@Test
	@Deployment(resources = {"access.bpmn"})
	public void testSampleCase_happyPath() {

		ProcessInstance instance = runtimeService().startProcessInstanceByKey(PROCESS_KEY,
				withVariables("entityId", Long.valueOf("23"),
						"userName",  "Petrov",
						"comment", "asfwefewrferwe"));

		assertThat(instance).isStarted();

		assertThat(instance)
				.isActive()
				.hasPassed("Event_0elmr4a")
				.hasPassed("Activity_1pipidp")
				.isWaitingAtExactly("Activity_1vk8pwi")
				.task().isNotAssigned();

		complete(task(), withVariables(
				"approver", "Appasvd",
				"isApproved", true
		));

		assertThat(instance)
				.hasPassedInOrder("Event_0elmr4a", "Activity_1vk8pwi", "Activity_0j6q95i")
				.isWaitingAtExactly("Activity_0a7bifc")
				.task().isNotAssigned();

		complete(task());

		assertThat(instance)
				.hasPassedInOrder("Event_0elmr4a", "Activity_1vk8pwi", "Activity_0j6q95i","Activity_0a7bifc")
				.isEnded();

		verify(accessRequestService, atLeast(1)).create(Long.valueOf("23"), "Petrov", "asfwefewrferwe");
		verify(accessRequestService, atMost(2)).update(Long.valueOf("23"), "Appasvd",true);
		verifyNoMoreInteractions(accessRequestService);



	}

	@After
	public void teardown() {
		Mocks.reset();
	}

}

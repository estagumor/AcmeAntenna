package useCases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import domain.MaintenanceRequest;
import services.AntennaService;
import services.HandyworkerService;
import services.MaintenanceRequestService;
import services.UserService;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 * - Register to the system as a user.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisteredHandyworker extends AbstractTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MaintenanceRequestService maintenanceRequestService;
    @Autowired
    private HandyworkerService handyworkerService;
    @Autowired
    private AntennaService antennaService;


    //Browse the catalogue of handyworkers.
    //Test getting the list of handyworkers
    @Test
    public void testListRequests()
    {
        this.unauthenticate();
        this.authenticate("handyworker1");
        Assert.isTrue(!this.handyworkerService.findServedMainteinanceRequest(this.handyworkerService.getPrincipal()).isEmpty());
        Assert.isTrue(!this.handyworkerService.findNotServedMainteinanceRequest(this.handyworkerService.getPrincipal()).isEmpty());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testServiceNullRequest() throws IllegalArgumentException
    {

        this.unauthenticate();
        this.authenticate("handyworker1");

        this.maintenanceRequestService.service(null);

    }

    @Test(expected = AccessDeniedException.class)
    public void testServiceAsAnUser() throws IllegalArgumentException
    {

        this.unauthenticate();
        this.authenticate("user1");
        final List<MaintenanceRequest> requests = new ArrayList<MaintenanceRequest>(this.maintenanceRequestService.findAll());
        this.maintenanceRequestService.service(requests.get(0));

    }

}

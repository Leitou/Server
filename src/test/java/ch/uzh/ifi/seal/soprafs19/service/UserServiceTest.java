package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword"); // needed for creation of new user
        testUser.setBirthDate(1234); // needed for creation of new user
    }

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));
        User createdUser = userService.createUser(testUser);
        Assert.assertNotNull(createdUser.getToken());
        Assert.assertNotNull(createdUser.getCreationDate());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.OFFLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));
        Assert.assertEquals(createdUser.getBirthDate(), 1234);
    }

    @Test
    public void updateUser(){
        Assert.assertNull(userRepository.findByUsername("testUsername"));
        User createdUser = userService.createUser(testUser); // saves user
        long id = createdUser.getId();
        testUser.setBirthDate(4321);
        testUser.setUsername("usernameTest");
        userService.updateUser(testUser, id);
        User dbUser = userRepository.findByToken(createdUser.getToken());

        Assert.assertEquals(dbUser.getBirthDate(),4321);
        Assert.assertEquals(dbUser.getUsername(),"usernameTest");


    }

}

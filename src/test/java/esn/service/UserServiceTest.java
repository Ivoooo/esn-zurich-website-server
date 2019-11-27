package esn.service;

import esn.Application;
import esn.entity.User;
import esn.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@WebAppConfiguration
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService eSNUSERService;

    public User setupTestUser1(){
        User testUser = new User();
        testUser.setFirstName("testFirstName");
        testUser.setNickname("testNickname");
        testUser.setPassword("testPassword");
        testUser.setToken("g");
        return testUser;
    }

    public User setupTestUser2(){
        User testUser = new User();
        testUser.setFirstName("testFirstName2");
        testUser.setNickname("testNickname2");
        testUser.setPassword("testPassword");
        testUser.setToken("b");
        return testUser;
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void createUserTest() {
        Assert.assertEquals(Optional.empty(), userRepository.findByNickname("testNickname"));
        User testUser = setupTestUser1();
        try {
            User createdUser = eSNUSERService.createUser(testUser);
            Assert.assertNotNull(createdUser.getToken());
            Assert.assertNotNull(createdUser.getId());
            Assert.assertEquals(createdUser, userRepository.findByNickname(createdUser.getNickname()).get());

        }
        catch (ResponseStatusException exception){
            throw new ResponseStatusException(exception.getStatus(),exception.getMessage(), exception);
        }
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void updateUserTest(){
        User testUser = setupTestUser1();

        User updateUser = setupTestUser2();
        updateUser.setToken("g");

        try {
            User updated = eSNUSERService.updateUser(testUser,updateUser);
            Assert.assertEquals(updateUser.getNickname(), updated.getNickname());
            Assert.assertEquals(updateUser.getFirstName(), updated.getFirstName());
            Assert.assertEquals(updateUser.getPassword(), updated.getPassword());

        }
        catch (ResponseStatusException exception){
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }
}

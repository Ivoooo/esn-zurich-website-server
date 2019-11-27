package esn.controller;

import esn.Application;
import esn.entity.User;
import esn.service.UserService;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes= Application.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    public User setupTestUser(){
        User testUser = new User();
        testUser.setFirstName("testName");
        testUser.setNickname("testNickname");
        testUser.setPassword("testPassword");
        testUser.setToken("g");
        return testUser;
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void fetchUsers() throws Exception {
        //get no users
        this.mvc.perform(get("/users"))
                .andExpect(status().is(200));

        //create a user
        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname\", \"password\": \"testPassword\"}"));

        //get one user
        this.mvc.perform(get("/users"))
                .andExpect(status().is(200));
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void getUser() throws Exception {

        //create a user
        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname\", \"password\": \"testPassword\"}"));

        //get valid user
        this.mvc.perform(get("/users/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$._links", notNullValue()))
                .andExpect(jsonPath("$.nickname", equalTo("testNickname")));

        //get invalid user
        this.mvc.perform(get("/users/0"))
                .andExpect(status().is(404));
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void createUser() throws Exception {

        //create a user
        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(201)) //andDo(print()).
                .andExpect(jsonPath("$._links", notNullValue()))
                .andExpect(jsonPath("$.nickname", equalTo("testNickname")));

        //create an already existing user (nickname)
        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(409));
    }


    @Ignore //todo fix this one
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void updateUser() throws Exception {

        //create a user
        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(201)) //andDo(print()).
                .andExpect(jsonPath("$._links", notNullValue()))
                .andExpect(jsonPath("$.nickname", equalTo("testNickname")));

        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"testNickname1\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(201)) //andDo(print()).
                .andExpect(jsonPath("$._links", notNullValue()))
                .andExpect(jsonPath("$.nickname", equalTo("testNickname1")));


        //get user
        MvcResult result =
                this.mvc.perform(get("/users/1"))
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$._links", notNullValue()))
                        .andExpect(jsonPath("$.nickname", equalTo("testNickname")))
                        .andReturn();

        String jsonAsString = result.getResponse().getContentAsString();

        JSONObject jsonObj = new JSONObject(jsonAsString);
        String token = jsonObj.getString("token");

        //update birthday + username
        String putBody = "{\"firstName\": \"firstName2\",\"lastName\": \"lastName2\" " +
                ", \"token\": \"" + token + "\"}";

        this.mvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(putBody))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$._links", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo("firstName2")))
                .andExpect(jsonPath("$.lastName", equalTo("lastName2")));
    }
}


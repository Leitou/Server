package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private User testUser, dbUser;

//    @Qualifier("userRepository")
//    @Autowired
    private UserRepository userRepository;


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
        testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword"); // needed for creation of new user
        testUser.setBirthDate(1234); // needed for creation of new user
//        dbUser = userService.createUser(testUser);
//        System.out.println("\n\n\n\n\n"+dbUser+"\n\n\n\n");
    }

    @Test
    public void all() throws Exception {

        Iterable<User> allUsers = singletonList(testUser);

        given(userController.all()).willReturn(allUsers);
        mockMvc.perform(get("/users")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testUser.getUsername())))
                .andExpect(jsonPath("$[0].name", is(testUser.getName())))
                .andExpect(jsonPath("$[0].password", is(testUser.getPassword())))
//                .andExpect(jsonPath("$[0].birthDate", is(1234)))
//                .andExpect(jsonPath("$[0].id", is(dbUser.getId())))
//                .andExpect(jsonPath("$[0].status", is(UserStatus.OFFLINE)))
                //.andExpect(jsonPath("$[0].creationDate", is(dbUser.getCreationDate())))
        ;
    }

    @Test
    public void getUser() throws Exception {
    }

    @Test
    public void createUser() throws Exception {
        String json = "{\n" +
                "  \"username\": \"testUsername\",\n" +
                "  \"password\": \"testPassword\",\n" +
                "  \"name\": \"testName\",\n" +
                "  \"birthDate\": \"1234\"\n" +
                "}";
        //System.out.println("\n\ndbUser token:"+dbUser+"\n\n");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(jsonPath("$.username", is("testUsername")))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.password", is("testPassword")))
                .andExpect(jsonPath("$.birthDate", is(1234)))
        ;

    }

    @Test
    public void updateUser() {
    }
}
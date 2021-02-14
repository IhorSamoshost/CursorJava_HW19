package com.cursor.library.controllers;

import com.cursor.library.daos.BookDao;
import com.cursor.library.daos.UserDao;
import com.cursor.library.models.Book;
import com.cursor.library.models.User;
import com.cursor.library.models.UserPermission;
import com.cursor.library.services.BookService;
import com.cursor.library.services.JwtUtil;
import com.cursor.library.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserControllerTest extends BaseControllerTest {
    private final String USER_NAME = "Any User";
    private final String PASSWORD = "Any Password";

    private UserDao userDao;
    private UserService userService;
    private UserController userController;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void prepareForTest() {
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao, encoder);
        userController = new UserController(userService, authenticationManager, jwtTokenUtil);
    }

    @Test
    void createAuthenticationTokenSuccessTest() throws Exception {
//        GIVEN
        UserController.AuthenticationRequest testAuthRequest = new UserController.AuthenticationRequest();
        testAuthRequest.setUserName(USER_NAME);
        testAuthRequest.setPassword(PASSWORD);
        User testUser = new User();
        testUser.setUserName(USER_NAME);
        testUser.setPassword(PASSWORD);
        testUser.setPermissions(Set.of(UserPermission.ROLE_ADMIN));
        Mockito.when(userDao.findByUserName(Mockito.any())).thenReturn(Optional.of(testUser));
//        WHEN
        String testResponseString = userController.createAuthenticationToken(testAuthRequest).getBody();
        System.out.println(testResponseString);
//        THEN
        Mockito.verify(userDao).findByUserName(Mockito.any());
        assert testResponseString != null;
        MatcherAssert.assertThat(testResponseString.startsWith("bearer "), Matchers.isA(String.class));
    }
}
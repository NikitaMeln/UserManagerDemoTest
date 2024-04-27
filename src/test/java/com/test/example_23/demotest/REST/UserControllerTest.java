package com.test.example_23.demotest.REST;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.test.example_23.demotest.entity.User;
import com.test.example_23.demotest.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void init() {
        testUser = User.builder()
                .id(1L)
                .email("mock@example.com")
                .password("mockTestPassword")
                .firstName("mockTestName")
                .lastName("mockTestName")
                .birthDay(LocalDate.of(1990, 5, 15))
                .address("mockAddress 1")
                .phone("mock123456789")
                .build();
    }

    @Test
    public void testCreateUser() throws Exception {
        String requestBody = "{\"user\": {\"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDay\": \"1990-01-01\"}, \"password\": \"password\"}";

        Mockito.when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("mock@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        String requestBody = "{\"id\": 1, \"email\": \"updated@example.com\", \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"birthDay\": \"1995-01-01\"}";

        Mockito.when(userService.update(any(User.class))).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("User updated by id 1"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.when(userService.getById(any())).thenReturn(testUser);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("User by id 1 deleted"));
    }

    @Test
    public void testSearchUsersByBirthDateRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/search")
                        .param("from", "1990-01-01")
                        .param("to", "2000-01-01"))
                .andExpect(status().isFound());
    }

    @Test
    public void testGetAllUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isFound());
    }
}

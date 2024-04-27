package com.test.example_23.demotest.service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.test.example_23.demotest.TestConfig;
import com.test.example_23.demotest.entity.User;
import com.test.example_23.demotest.exception.UserServiceProcessException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    private User testUser;

    private final int ageLimit = 18;

    @BeforeEach
    void setUp() {
        userService = new UserService();

        setAgeLimit(ageLimit);

        testUser = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .firstName("TestName")
                .lastName("TestName")
                .birthDay(LocalDate.of(1990, 5, 15))
                .address("Address 1")
                .phone("123456789")
                .build();
    }

    @AfterEach
    void clearStorage() {
        try {
            Field field = UserService.class.getDeclaredField("users");
            field.setAccessible(true);
            Map<Long, User> users = (Map<Long, User>) field.get(userService);
            users.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetById() {
        userService.save(testUser);

        User retrievedUser = userService.getById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
    }

    @Test
    void testGetAll() {
        userService.save(testUser);
        userService.save(testUser);

        List<User> allUsers = userService.getAll();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
    }

    @Test
    void testSave() {
        testUser.setBirthDay(LocalDate.of(2000, 1, 1));

        User savedUser = userService.save(testUser);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
    }

    @Test
    void testUpdate() {
        userService.save(testUser);

        testUser.setEmail("updated@example.com");

        userService.update(testUser);

        assertEquals("updated@example.com", userService.getById(1L).getEmail());
    }

    @Test
    void testDeleteUser() {
        userService.save(testUser);
        userService.deleteUser(testUser);
        assertNull(userService.getById(1L));
    }

    @Test
    void testFilterByBirthDate() {
        User user1 = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .firstName("TestName")
                .lastName("TestName")
                .birthDay(LocalDate.of(1992, 1, 1))
                .address("Address 1")
                .phone("123456789")
                .build();
        userService.save(user1);

        User user2 = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .firstName("TestName")
                .lastName("TestName")
                .birthDay(LocalDate.of(2005, 1, 1))
                .address("Address 1")
                .phone("123456789")
                .build();
        userService.save(user2);

        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        List<User> filteredUsers = userService.filterByBirthDate(userService.getAll(), fromDate, toDate);

        assertEquals(1, filteredUsers.size());
        assertEquals(1L, filteredUsers.get(0).getId());
    }

    @Test
    void testValidateUser() {
        User user = testUser;
        user.setBirthDay(LocalDate.now().minusYears(17));

        assertThrows(UserServiceProcessException.class, () -> userService.save(user));
    }

    private void setAgeLimit(int ageLimit) {
        try {
            Field field = UserService.class.getDeclaredField("ageLimit");
            field.setAccessible(true);
            field.set(userService, ageLimit);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

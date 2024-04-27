package com.test.example_23.demotest.service;

import com.test.example_23.demotest.entity.User;
import com.test.example_23.demotest.exception.UserServiceProcessException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${app.age-limit}")
    private Integer ageLimit;
    private final Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;

    public User getById(Long id) {
        try {
            return users.get(id);
        } catch (RuntimeException e) {
            throw new UserServiceProcessException("Can't find user by id " + id);
        }
    }

    public List<User> getAll(){
        return new ArrayList<>(users.values());
    }
    public User save(User user) {
        validateUser(user);
        initUser(user);
        users.put(nextId, user);
        nextId++;
        return user;
    }

    public User update(User user) {
        User existingUser = getById(user.getId());
        try {
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if (user.getBirthDay() != null) {
                validateUser(user);
                existingUser.setBirthDay(user.getBirthDay());
            }
            if (user.getAddress() != null) {
                existingUser.setAddress(user.getAddress());
            }
            if (user.getPhone() != null) {
                existingUser.setPhone(user.getPhone());
            }
        } catch (RuntimeException e) {
            throw new UserServiceProcessException("Can't update user credentials");
        }
        return existingUser;
    }

    public void deleteUser(User user) {
        try {
            users.remove(user.getId());
        } catch (RuntimeException e) {
            throw new UserServiceProcessException("Can't delete user");
        }
    }

    public List<User> filterByBirthDate(List<User> users, LocalDate fromDate, LocalDate toDate) {
        List<User> usersInRange = new ArrayList<>();

        for (User user : users) {
            LocalDate userBirthDate = user.getBirthDay();
            if (userBirthDate.isEqual(fromDate) || userBirthDate.isEqual(toDate) ||
                    (userBirthDate.isAfter(fromDate) && userBirthDate.isBefore(toDate))) {
                usersInRange.add(user);
            }
        }
        return usersInRange;
    }

    private void validateUser(User user) {
        LocalDate limitDate = LocalDate.now().minusYears(ageLimit);
        if (user.getBirthDay().isAfter(limitDate)) {
            throw new UserServiceProcessException("User must be 18 years old");
        }
    }

    private void initUser(User user) {
        user.setId(nextId);
    }
}

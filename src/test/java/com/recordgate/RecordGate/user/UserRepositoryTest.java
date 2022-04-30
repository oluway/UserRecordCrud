package com.recordgate.RecordGate.user;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddNew(){
        User user = new User();
        user.setFirstName("Ebene");
        user.setLastName("Way");
        user.setEmail("test@gmail.com");
        user.setPassword("anotherOl");
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testgetUsers(){
        Iterable<User> allUsers = userRepository.findAll();
        Assertions.assertThat(allUsers).hasSizeGreaterThan(0);

        for (User user : allUsers){
            System.out.println(user);
        }
    }
    @Test
    public void testupdateUser(){
        Optional<User> optionalUser = userRepository.findById(1L);
        User user = optionalUser.get();
        user.setEmail("hellohellohello@justtesting.com");
        userRepository.save(user);
        User updatedUser = userRepository.getById(1L);
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("hellohellohello@justtesting.com");
    }
    @Test
    public void testGetUser(){
        Optional<User> optionalUser = userRepository.findById(2L);
        Assertions.assertThat(optionalUser.get()).isNotNull();
        System.out.println(optionalUser.get().getFirstName());
    }
    @Test
    public void testDeleteUser(){
        Long userId = 2L;
        userRepository.deleteById(userId);
        Optional<User> optionalUser = userRepository.findById(2L);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
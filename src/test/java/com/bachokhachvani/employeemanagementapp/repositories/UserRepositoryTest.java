package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.domain.Role;
import com.bachokhachvani.employeemanagementapp.exceptions.UserRoleNotFoundException;
import com.bachokhachvani.employeemanagementapp.models.RoleModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static boolean dataLoaded = false;

    @BeforeAll
    public static void setup(@Autowired UserRepository userRepository,
                             @Autowired RoleRepository roleRepository) {
        if (!dataLoaded) {
            roleRepository.save(RoleModel.builder().name(Role.ADMIN).build());
            roleRepository.save(RoleModel.builder().name(Role.EMPLOYEE).build());
            dataLoaded = true;
        }
    }

    @Test
    public void saveUser() {
        var role = roleRepository
                .findByName(Role.ADMIN)
                .orElseThrow(() -> new UserRoleNotFoundException("User Role not found"));
        var user = UserModel.builder().username("Test1").password("test1").role(role).build();
        UserModel savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo("Test1");
        Assertions.assertThat(savedUser.getUserId()).isGreaterThan(0);
    }

    @Test
    public void findByUsername() {
        var role = roleRepository
                .findByName(Role.ADMIN)
                .orElseThrow(() -> new UserRoleNotFoundException("User Role not found"));
        var user = UserModel.builder().username("Test1").password("test1").role(role).build();
        UserModel savedUser = userRepository.save(user);

        UserModel foundUser = userRepository.findByUsername("Test1").orElse(null);

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("Test1");
        Assertions.assertThat(savedUser.getUserId()).isGreaterThan(0);
    }


}


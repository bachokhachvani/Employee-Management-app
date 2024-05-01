package com.bachokhachvani.employeemanagementapp.services;
import com.bachokhachvani.employeemanagementapp.domain.Role;
import com.bachokhachvani.employeemanagementapp.exceptions.UserRoleNotFoundException;
import com.bachokhachvani.employeemanagementapp.models.RoleModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.repositories.RoleRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;
    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_Success() {
        Role roleName = Role.ADMIN;
        UserModel user = new UserModel();
        user.setPassword("plainPassword");
        RoleModel role = new RoleModel();
        role.setName(roleName);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
        userService.addUser(user, roleName);
        verify(passwordEncoder).encode("plainPassword");
        verify(roleRepository).findByName(roleName);
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void addUser_RoleNotFound() {
        String roleName = "NON_EXISTENT_ROLE";
        UserModel user = new UserModel();
        user.setPassword("plainPassword");
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(roleRepository.findByName(null)).thenReturn(Optional.empty());
        assertThrows(UserRoleNotFoundException.class, () -> {
            userService.addUser(user, null);
        });
        verify(userRepository, never()).save(any());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}
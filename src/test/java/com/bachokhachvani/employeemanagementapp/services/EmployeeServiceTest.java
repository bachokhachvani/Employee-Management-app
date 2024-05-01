package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.exceptions.ActionForbiddenException;
import com.bachokhachvani.employeemanagementapp.exceptions.DetailsChangeRestrictedException;
import com.bachokhachvani.employeemanagementapp.exceptions.EmployeeNotFoundException;
import com.bachokhachvani.employeemanagementapp.exceptions.FailedToDeleteException;
import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.models.PositionModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import com.bachokhachvani.employeemanagementapp.utils.EmployeeMapperMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    EmployeeMapperMap employeeMapperMap;
    EmployeeService employeeService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);
        UserModel userModel = new UserModel();
        userModel.setUserId(1);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userModel));
        employeeService = new EmployeeService(employeeRepository, userRepository, employeeMapperMap);

    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Disabled
    @Test
    void testAllEmployees() {
    }

    @Test
    void testAddEmployee() {
        var department1 = DepartmentModel.builder().name("ANALYTICS").build();
        var position1 = PositionModel.builder().name("ANALYST").build();
        var mockEmployee = EmployeeModel.builder()
                .id(1)
                .email("test1@gmail.com")
                .phone(1111).birthday(new Date(12))
                .hireDate(new Date(123))
                .department(department1)
                .position(position1).
                name("test1")
                .salary(2000.0).build();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        when(employeeMapperMap.fromDTO(any(EmployeeDTO.class))).thenReturn(mockEmployee);
        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(mockEmployee);
        employeeService.addEmployee(employeeDTO);
        ArgumentCaptor<EmployeeModel> captor = ArgumentCaptor.forClass(EmployeeModel.class);
        verify(employeeRepository).save(captor.capture());
        EmployeeModel savedEmployee = captor.getValue();
        Assertions.assertThat("test1@gmail.com").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    void testUpdateContactInfo() {
        int mockUserId = 1;
        EmployeeContactInfoDTO contactInfo = new EmployeeContactInfoDTO();
        contactInfo.setPhone(2222);
        contactInfo.setEmail("update@test.com");
        var department1 = DepartmentModel.builder().name("ANALYTICS").build();
        var position1 = PositionModel.builder().name("ANALYST").build();
        var existingEmployee = EmployeeModel.builder()
                .id(1)
                .email("test1@gmail.com")
                .phone(1111).birthday(new Date(12))
                .hireDate(new Date(123))
                .department(department1)
                .position(position1).
                name("test1")
                .salary(2000.0).build();
        when(employeeRepository.findById(mockUserId)).thenReturn(Optional.of(existingEmployee));
        employeeService.updateContactInfo(contactInfo);
        ArgumentCaptor<EmployeeModel> employeeCaptor = ArgumentCaptor.forClass(EmployeeModel.class);
        verify(employeeRepository).save(employeeCaptor.capture());
        EmployeeModel capturedEmployee = employeeCaptor.getValue();
        Assertions.assertThat(contactInfo.getPhone()).isEqualTo(capturedEmployee.getPhone());
        Assertions.assertThat(contactInfo.getEmail()).isEqualTo(capturedEmployee.getEmail());
    }

    @Test
    void testUpdateEmployee() {
        int employeeId = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        DepartmentModel department1 = DepartmentModel.builder().name("ANALYTICS").build();
        PositionModel position1 = PositionModel.builder().name("ANALYST").build();
        EmployeeModel existingEmployee = EmployeeModel.builder()
                .id(employeeId)
                .email("test1@gmail.com")
                .phone(1111)
                .birthday(new Date(12))
                .hireDate(new Date(123))
                .department(department1)
                .position(position1)
                .name("test1")
                .salary(2000.0)
                .build();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapperMap.fromDTO(any(EmployeeDTO.class))).thenReturn(existingEmployee);
        Exception exception = assertThrows(DetailsChangeRestrictedException.class, () -> employeeService.updateEmployee(employeeDTO, existingEmployee.getId()));
        assertTrue(exception.getMessage().contains("You can't change your details"));
    }

    @Test
    void testFindEmployeeById() {
        int employeeId = 1;
        DepartmentModel department1 = DepartmentModel.builder().name("ANALYTICS").build();
        PositionModel position1 = PositionModel.builder().name("ANALYST").build();
        EmployeeModel mockEmployee = EmployeeModel.builder()
                .id(employeeId)
                .email("test1@gmail.com")
                .phone(1111)
                .birthday(new Date(12))
                .hireDate(new Date(123))
                .department(department1)
                .position(position1)
                .name("test1")
                .manager(EmployeeModel.builder().id(2).email("test2@gmail.com").phone(2222).birthday(new Date(122)).hireDate(new Date(123)).department(department1).position(position1).name("test2").salary(2000.0).build())
                .salary(2000.0)
                .build();
        EmployeeDTO expectedDTO = new EmployeeDTO();
        expectedDTO.setManagerName("test2");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockEmployee));
        when(employeeMapperMap.toDTO(mockEmployee)).thenReturn(expectedDTO);
        EmployeeDTO resultDTO = employeeService.findEmployeeById(employeeId);
        assertNotNull(resultDTO);
        assertEquals("test2", resultDTO.getManagerName());
        verify(employeeRepository).findById(employeeId);
        verify(employeeMapperMap).toDTO(mockEmployee);
    }

    @Test
    void testDeleteEmployee() {
        int currentUserId = 1;
        assertThrows(ActionForbiddenException.class, () -> employeeService.deleteEmployee(currentUserId));
    }

    @Test
    void testDeleteNonExistentEmployee() {
        int employeeId = 999;
        when(employeeRepository.findByManagerId(employeeId)).thenReturn(Optional.empty());
        assertThrows(FailedToDeleteException.class, () -> employeeService.deleteEmployee(employeeId));
    }

    @Test
    void testDeleteEmployeeWithSubordinates() {
        int employeeId = 2;
        EmployeeModel subordinate = new EmployeeModel();
        subordinate.setId(3);
        when(employeeRepository.findByManagerId(employeeId)).thenReturn(Optional.of(List.of(subordinate)));
        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository).save(subordinate);
        verify(employeeRepository).deleteById(employeeId);
        verify(userRepository).deleteById(employeeId);
    }

    @Test
    void testDeleteEmployeeHandlingExceptions() {
        int employeeId = 2;
        when(employeeRepository.findByManagerId(employeeId)).thenThrow(new RuntimeException("Database error"));
        assertThrows(FailedToDeleteException.class, () -> employeeService.deleteEmployee(employeeId));
    }

    @Test
    void testGetCurrentEmployeeDetails() {
        int currentUserId = 1;
        EmployeeModel manager = new EmployeeModel();
        manager.setName("Manager Name");
        EmployeeModel currentEmployee = new EmployeeModel();
        currentEmployee.setId(currentUserId);
        currentEmployee.setManager(manager);
        EmployeeDTO expectedDTO = new EmployeeDTO();
        expectedDTO.setManagerName("Manager Name");
        when(employeeRepository.findById(currentUserId)).thenReturn(Optional.of(currentEmployee));
        when(employeeMapperMap.toDTO(currentEmployee)).thenReturn(expectedDTO);
        EmployeeDTO resultDTO = employeeService.getCurrentEmployeeDetails();
        assertNotNull(resultDTO);
        assertEquals("Manager Name", resultDTO.getManagerName());
    }

    @Test
    void testGetCurrentEmployeeDetails_FoundNoManager() {
        int currentUserId = 1;
        EmployeeModel currentEmployee = new EmployeeModel();
        currentEmployee.setId(currentUserId);
        currentEmployee.setManager(null);
        EmployeeDTO expectedDTO = new EmployeeDTO();
        expectedDTO.setManagerName(null);
        when(employeeRepository.findById(currentUserId)).thenReturn(Optional.of(currentEmployee));
        when(employeeMapperMap.toDTO(currentEmployee)).thenReturn(expectedDTO);
        EmployeeDTO resultDTO = employeeService.getCurrentEmployeeDetails();
        assertNotNull(resultDTO);
        assertNull(resultDTO.getManagerName());
    }

    @Test
    void testGetCurrentEmployeeDetails_NotFound() {
        int currentUserId = 999;
        when(employeeRepository.findById(currentUserId)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getCurrentEmployeeDetails());
    }
}

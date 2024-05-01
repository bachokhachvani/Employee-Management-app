package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.domain.Role;
import com.bachokhachvani.employeemanagementapp.exceptions.EmployeeNotFoundException;
import com.bachokhachvani.employeemanagementapp.exceptions.UserRoleNotFoundException;
import com.bachokhachvani.employeemanagementapp.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;


    private static boolean dataLoaded = false;

    @BeforeAll
    public static void setup(@Autowired UserRepository userRepository,
                             @Autowired RoleRepository roleRepository) {
        if (!dataLoaded) {
            roleRepository.save(RoleModel.builder().name(Role.ADMIN).build());
            roleRepository.save(RoleModel.builder().name(Role.EMPLOYEE).build());
            var role = roleRepository.findByName(Role.ADMIN)
                    .orElseThrow(() -> new UserRoleNotFoundException("User Role not found"));
            var user1 = UserModel.builder().username("Test1").password("test1").userId(1).role(role).build();
            var user2 = UserModel.builder().username("Test2").password("test2").userId(2).role(role).build();
            userRepository.save(user1);
            userRepository.save(user2);
            dataLoaded = true;
        }

    }

    @Test
    public void saveEmployee() {
        var department1 = DepartmentModel.builder().name("ANALYTICS").build();
        var position1 = PositionModel.builder().name("ANALYST").build();
        DepartmentModel savedDepartment = departmentRepository.save(department1);
        PositionModel savedPosition = positionRepository.save(position1);
        var employee = EmployeeModel.builder().id(1).email("test1@gmail.com").phone(1111).birthday(new Date(12)).hireDate(new Date(123)).department(savedDepartment).position(savedPosition).name("test1").salary(2000.0).build();
        var savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getName()).isEqualTo("test1");
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void getEmployee() {
        createEmployees();
        List<EmployeeModel> employees = employeeRepository.findAll();
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    void findByEmail() throws EmployeeNotFoundException {
        createEmployees();
        EmployeeModel employee = employeeRepository.findByEmail("test1@gmail.com").orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getName()).isEqualTo("test1");
    }

    @Test
    void findByManagerId() throws EmployeeNotFoundException {
        createEmployees();
        List<EmployeeModel> employees = employeeRepository.findByManagerId(1).orElseThrow(() -> new EmployeeNotFoundException("Employees not found"));

        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(1);
        Assertions.assertThat(employees.get(0).getManager().getId()).isEqualTo(1);

    }


    public void createEmployees() {
        var department1 = DepartmentModel.builder().name("ANALYTICS").build();
        var position1 = PositionModel.builder().name("ANALYST").build();
        DepartmentModel savedDepartment = departmentRepository.save(department1);
        PositionModel savedPosition = positionRepository.save(position1);
        var employee1 = EmployeeModel.builder().id(1).email("test1@gmail.com").phone(1111).birthday(new Date(12)).hireDate(new Date(123)).department(savedDepartment).position(savedPosition).name("test1").salary(2000.0).build();
        var employee2 = EmployeeModel.builder().id(2).email("test2@gmail.com").phone(2222).birthday(new Date(122)).hireDate(new Date(123)).department(savedDepartment).position(savedPosition).name("test2").salary(2000.0).manager(employee1).build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

    }
}
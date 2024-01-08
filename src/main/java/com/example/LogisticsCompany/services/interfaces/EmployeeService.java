package com.example.LogisticsCompany.services.interfaces;

import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.web.dto.CreateEmployeeDTO;
import com.example.LogisticsCompany.web.dto.UpdateEmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployeeById(long id);

    Employee createEmployee(CreateEmployeeDTO employeeDTO);

    Employee updateEmployee(long id, UpdateEmployeeDTO updateEmployeeDTO);

    void deleteEmployee(long id);

    List<Employee> getAllEmployeesByOffice(Office office);

    Employee getEmployeeByName(String name);
}

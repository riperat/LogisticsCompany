package com.example.LogisticsCompany.services.implementations;

import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.repository.EmployeeRepository;
import com.example.LogisticsCompany.services.interfaces.EmployeeService;
import com.example.LogisticsCompany.web.dto.CreateEmployeeDTO;
import com.example.LogisticsCompany.web.dto.UpdateEmployeeDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop Id:" + id));

    }

    @Override
    public Employee createEmployee(CreateEmployeeDTO employeeDTO) {
        return employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
    }

    @Override
    public Employee updateEmployee(long id, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee Employee = modelMapper.map(updateEmployeeDTO, Employee.class);
        Employee.setId(id);
        return employeeRepository.save(Employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployeesByOffice(Office office) {
        return employeeRepository.getAllEmployeesByOffice(office);
    }

}

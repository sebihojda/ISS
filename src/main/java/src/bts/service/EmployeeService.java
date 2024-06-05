package src.bts.service;

import src.bts.domain.Employee;
import src.bts.repository.EmployeeDBRepository;

public class EmployeeService {
    private final EmployeeDBRepository employeeDBRepository;

    public EmployeeService(EmployeeDBRepository employeeDBRepository) {
        this.employeeDBRepository = employeeDBRepository;
    }

    public Employee findOneByUsernameAndPassword(String username, String password){
        if(username.isBlank() || password.isBlank())
            throw new IllegalArgumentException("(EmployeeService) Username/Password cannot be null!");
        return employeeDBRepository.findOneByUsernameAndPassword(username, password);
    }
}

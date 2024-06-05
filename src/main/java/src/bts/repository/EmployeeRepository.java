package src.bts.repository;

import src.bts.domain.Employee;

public interface EmployeeRepository extends RepositoryOptional<Long, Employee> {
    public Employee findOneByUsernameAndPassword(String username, String password);
}

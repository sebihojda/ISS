package src.bts.repository;

import src.bts.domain.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.bts.repository.config.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Properties;

public class EmployeeDBRepository implements EmployeeRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    public EmployeeDBRepository(Properties props) {
        logger.info("Initializing EmployeeDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Employee findOneByUsernameAndPassword(String username, String password) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from employees where username = ? and password = ?")) {

            preStmt.setString(1, username);
            preStmt.setString(2, password);
            try (ResultSet resultSet = preStmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id_employee = resultSet.getLong("id");
                    Employee employee = new Employee(username, password);
                    employee.setId(id_employee);
                    logger.traceExit(employee);
                    return employee;
                }
            } catch (Exception e) {
                logger.error(e);
                throw new Exception("DB Error " + e);
                //System.err.println("Error DB " + e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Connection Error " + e);
            //System.err.println(e);
        }
        return null;
    }

    @Override
    public Optional<Employee> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> save(Employee entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        return Optional.empty();
    }
}

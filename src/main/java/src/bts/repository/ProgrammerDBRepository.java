package src.bts.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.bts.domain.Employee;
import src.bts.domain.Programmer;
import src.bts.domain.Tester;
import src.bts.repository.config.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Properties;

public class ProgrammerDBRepository implements ProgrammerRepository{

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    public ProgrammerDBRepository(Properties props) {
        logger.info("Initializing ProgrammerDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Programmer> findOne(Long aLong) {
        Connection con = dbUtils.getConnection();
        Programmer programmer = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from programmers where id_programmer = ?")) {
            preStmt.setLong(1, aLong);
            try (ResultSet resultSet = preStmt.executeQuery()) {
                if (resultSet.next()) {
                    Integer numberOfFixedBugs = resultSet.getInt("number_of_fixed_bugs");
                    programmer = new Programmer("","");
                    programmer.setNumberOfFixedBugs(numberOfFixedBugs);
                    programmer.setId(aLong);
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
        logger.traceExit(programmer);
        return Optional.ofNullable(programmer);
    }

    @Override
    public Iterable<Programmer> findAll() {
        return null;
    }

    @Override
    public Optional<Programmer> save(Programmer entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Programmer> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Programmer> update(Programmer entity) {
        return Optional.empty();
    }
}

package src.bts.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.bts.domain.Employee;
import src.bts.domain.Tester;
import src.bts.repository.config.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

public class TesterDBRepository implements TesterRepository{

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    public TesterDBRepository(Properties props) {
        logger.info("Initializing TesterDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Tester> findOne(Long aLong) {
        Connection con = dbUtils.getConnection();
        Tester tester = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from testers where id_tester = ?")) {
            preStmt.setLong(1, aLong);
            try (ResultSet resultSet = preStmt.executeQuery()) {
                if (resultSet.next()) {
                    Integer numberOfRecordedBugs = resultSet.getInt("number_of_recorded_bugs");
                    tester = new Tester("","");
                    tester.setNumberOfRecordedBugs(numberOfRecordedBugs);
                    tester.setId(aLong);
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
        logger.traceExit(tester);
        return Optional.ofNullable(tester);
    }

    @Override
    public Iterable<Tester> findAll() {
        return null;
    }

    @Override
    public Optional<Tester> save(Tester entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Tester> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Tester> update(Tester entity) {
        return Optional.empty();
    }
}

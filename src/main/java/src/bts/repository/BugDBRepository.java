package src.bts.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.bts.domain.Bug;
import src.bts.repository.config.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BugDBRepository implements BugRepository{

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public BugDBRepository(Properties props) {
        logger.info("Initializing BugDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Bug> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Bug> findAll() {
        Connection con = dbUtils.getConnection();
        List<Bug> bugs = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from bugs")) {
            try (ResultSet resultSet = preStmt.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Integer severity = resultSet.getInt("severity");
                    String condition = resultSet.getString("condition");
                    Bug bug = new Bug(name, description, severity, condition);
                    bug.setId(id);
                    bugs.add(bug);
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
        logger.traceExit(bugs);
        return bugs;
    }

    @Override
    public Optional<Bug> save(Bug entity) {
        logger.traceEntry("saving bug {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into bugs(name, description, severity, condition) values (?, ?, ?, ?)")) {
            preStmt.setString(1, entity.getName());
            preStmt.setString(2, entity.getDescription());
            preStmt.setInt(3, entity.getSeverity());
            preStmt.setString(4, entity.getCondition());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<Bug> delete(Long aLong) {
        logger.traceEntry("deleting bug with id {}", aLong);
        if (aLong<1L){
            throw new IllegalArgumentException("Id must be greater than 0!");
        }
        try (Connection connection = dbUtils.getConnection()) {
            String sql = "delete from bugs where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, aLong);
                int answer = preparedStatement.executeUpdate();
                logger.trace("Deleted {} instances", answer);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Bug> update(Bug entity) {
        if (entity == null) {
            throw new IllegalArgumentException("(update) Bug cannot be null!");
        }

        logger.traceEntry("updating bug {}", entity);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("update bugs set name = ?, description = ?, severity = ?, condition = ? where id = ?")) {
            preStmt.setString(1, entity.getName());
            preStmt.setString(2, entity.getDescription());
            preStmt.setInt(3, entity.getSeverity());
            preStmt.setString(4, entity.getCondition());
            preStmt.setLong(5, entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
            return result == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            logger.traceExit(); // 2 lines added by me...
            throw new RuntimeException("Connection Error " + e);
            //System.err.println("Error DB " + e);
        }
    }
}

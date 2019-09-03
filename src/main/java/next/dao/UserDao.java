package next.dao;

import next.jdbc.PreparedStatementParameterSetterCreator;
import next.jdbc.QueryExecutor;
import next.model.User;

import java.util.List;

public class UserDao {

    final QueryExecutor queryExecutor;


    public UserDao() {
        this.queryExecutor = new QueryExecutor();
    }

    public void insert(final User user) {
        final String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        this.queryExecutor.executeUpdate(
                sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public void insertWithParameterSetter(final User user) {
        final String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        this.queryExecutor.executeUpdate(
                sql,
                PreparedStatementParameterSetterCreator.create(
                        user.getUserId(),
                        user.getPassword(),
                        user.getName(),
                        user.getEmail()));
    }

    public void update(final User user) {
        final String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        this.queryExecutor.executeUpdate(
                sql,
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUserId());
    }

    public void updateWithParameterSetter(final User user) {
        final String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        this.queryExecutor.executeUpdate(
                sql,
                PreparedStatementParameterSetterCreator.create(
                        user.getPassword(),
                        user.getName(),
                        user.getEmail(),
                        user.getUserId()));
    }

    public void delete(final User user) {
        final String sql = "DELETE FROM USERS WHERE userId=?";
        this.queryExecutor.executeUpdate(
                sql,
                user.getUserId());
    }

    public List<User> findAll() {
        final String sql = "SELECT userId, password, name, email FROM USERS";
        return this.queryExecutor.executeQuery(
                sql,
                User.class);
    }

    public List<User> findAllWithMapper() {
        final String sql = "SELECT userId, password, name, email FROM USERS";
        return this.queryExecutor.executeQuery(
                sql,
                rs -> {
                    final User user = new User(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4));
                    return user;
                });
    }

    public User findByUserId(final String userId) {
        final String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return this.queryExecutor.executeScalar(
                sql,
                User.class,
                userId);
    }

    public User findByUserIdWithMapper(final String userId) {
        final String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return this.queryExecutor.executeScalar(
                sql,
                rs -> {
                    final User user = new User(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4));
                    return user;
                },
                userId);
    }
}

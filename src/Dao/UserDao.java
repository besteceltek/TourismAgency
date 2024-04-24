package Dao;

import Core.Database;
import Entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {

    private final Connection connection;
    public UserDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<User> findAll() {
        return this.selectByQuery("SELECT * FROM public.user ORDER BY user_id ASC");
    }

    public User findByLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND  user_password = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> selectByQuery(String query) {
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getByID(int id) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Private Methods

    private User match(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getInt("user_id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setUserPassword(resultSet.getString("user_password"));
        user.setUserRole(User.UserRole.valueOf(resultSet.getString("user_role")));
        user.setUserFirstName(resultSet.getString("user_first_name"));
        user.setUserLastName(resultSet.getString("user_last_name"));
        return user;
    }
}

package Dao;

import Core.Database;
import Entity.Hotel;
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

    // Find User by login values
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


    // Get User by user ID
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

    // Save User
    public boolean save(User user) {
        String query = "INSERT INTO public.user " +
                "(" +
                "user_name, " +
                "user_password, " +
                "user_role, " +
                "user_first_name, " +
                "user_last_name" +
                ")" +
                " VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setUserValues(user, preparedStatement);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Update User
    public boolean update(User user) {
        String query = "UPDATE public.user SET " +
                "user_name = ? ," +
                "user_password = ? ," +
                "user_role = ? ," +
                "user_first_name = ? ," +
                "user_last_name = ? " +
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setUserValues(user, preparedStatement);
            preparedStatement.setInt(6, user.getUserID());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Delete User
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

    public ArrayList<User> searchForTable(User.UserRole userRole) {
        String select = "SELECT * FROM public.user";
        ArrayList<String> whereList = new ArrayList<>();

        if (userRole != null) {
            whereList.add("user_role = '" + userRole.toString() + "'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        return this.selectByQuery(query);
    }

    // Private Methods that is used only in this class

    private void setUserValues(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getUserPassword());
        preparedStatement.setString(3, user.getUserRole().toString());
        preparedStatement.setString(4, user.getUserFirstName());
        preparedStatement.setString(5, user.getUserLastName());
    }

    private ArrayList<User> selectByQuery(String query) {
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

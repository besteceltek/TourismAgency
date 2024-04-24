package Business;

import Core.Helper;
import Dao.UserDao;
import Entity.Hotel;
import Entity.User;

import java.util.ArrayList;

public class UserManager {
    private UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username,password);
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    public User getByID(int id) {
        return this.userDao.getByID(id);
    }

    public ArrayList<User> searchForTable(User.UserRole userRole) { return this.userDao.searchForTable(userRole); }

    // Get User values for admin view table
    public ArrayList<Object[]> getForTable(int colSize, ArrayList<User> userList) {
        ArrayList<Object[]> userRowList = new ArrayList<>();
        for (User user : userList) {
            Object[] rowObject = new Object[colSize];
            int i = 0;
            rowObject[i++] = user.getUserID();
            rowObject[i++] = user.getUserFirstName();
            rowObject[i++] = user.getUserLastName();
            rowObject[i++] = user.getUserName();
            rowObject[i++] = user.getUserPassword();
            rowObject[i++] = user.getUserRole();
            userRowList.add(rowObject);
        }
        return userRowList;
    }

    // Save User
    public boolean save(User user) {
        if (user.getUserID() != 0) {
            Helper.showMessage("User could not be saved");
        }
        return this.userDao.save(user);
    }

    //Update User
    public boolean update(User user) {
        if (this.getByID(user.getUserID()) == null) {
            Helper.showMessage("notFound");
        }
        return this.userDao.update(user);
    }

    // Delete User
    public boolean delete (int id) {
        if (this.getByID(id) == null) {
            Helper.showMessage("User ID " + id + " is not found");
            return false;
        }
        return this.userDao.delete(id);
    }
}

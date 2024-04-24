package Business;

import Core.Helper;
import Dao.UserDao;
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
        return this.userDao.selectByQuery(query);
    }

    public boolean delete (int id) {
        if (this.getByID(id) == null) {
            Helper.showMessage("User ID " + id + " is not found");
            return false;
        }
        return this.userDao.delete(id);
    }
}

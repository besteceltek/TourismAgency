package View;

import Business.UserManager;
import Core.Helper;
import Entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_title;
    private JLabel lbl_welcome;
    private JPanel w_button;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JButton btn_login;
    private JButton btn_exit;
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();
        this.add(container);
        this.initializeGui(300, 300);
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_password};
            if(Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMessage("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(),this.fld_password.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else if (loginUser.getUserRole() == User.UserRole.EMPLOYEE) {
                    EmployeeView employeeView = new EmployeeView(loginUser);
                    dispose();
                } else if(loginUser.getUserRole() == User.UserRole.ADMIN) {
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }
            }
        });
        btn_exit.addActionListener(e -> {
            System.exit(0);
        });
    }
}

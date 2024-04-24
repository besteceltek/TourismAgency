package View;

import Business.UserManager;
import Entity.User;

import javax.swing.*;

public class UserView extends Layout {

    private JPanel container;
    private JLabel lbl_welcome;
    private JLabel lbl_user_name;
    private JTextField fld_user_name;
    private JTextField fld_user_password;
    private JComboBox cmb_user_role;
    private JButton btn_save;
    private JLabel lbl_user_password;
    private JLabel lbl_user_role;
    private JPanel pnl_button;
    private JLabel lbl_user_first_name;
    private JTextField fld_user_first_name;
    private JLabel lbl_user_last_name;
    private JTextField fld_user_last_name;
    private User user;

    public UserView(User user) {
        this.add(container);
        this.initializeGui(275, 250);
        this.user = user;

        this.cmb_user_role.setModel(new DefaultComboBoxModel<>(User.UserRole.values()));
        this.cmb_user_role.setSelectedItem(null);

        if(this.user.getUserID() != 0) {
            this.fld_user_name.setText(this.user.getUserName());
            this.fld_user_password.setText(this.user.getUserPassword());
            this.fld_user_first_name.setText(this.user.getUserFirstName());
            this.fld_user_last_name.setText(this.user.getUserLastName());
            this.cmb_user_role.getModel().setSelectedItem(this.user.getUserRole());
        }
    }
}

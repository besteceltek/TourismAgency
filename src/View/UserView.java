package View;

import Business.UserManager;
import Core.Helper;
import Entity.User;

import javax.swing.*;

public class UserView extends Layout {

    private JPanel container;
    private JPanel pnl_button;

    private JLabel lbl_welcome;
    private JLabel lbl_user_name;
    private JLabel lbl_user_password;
    private JLabel lbl_user_role;
    private JLabel lbl_user_first_name;
    private JLabel lbl_user_last_name;

    private JTextField fld_user_name;
    private JTextField fld_user_password;
    private JTextField fld_user_first_name;
    private JTextField fld_user_last_name;

    private JComboBox cmb_user_role;

    private JButton btn_save;

    private User user;

    private UserManager userManager;

    public UserView(User user) {
        this.add(container);
        this.initializeGui(275, 250);
        this.user = user;
        this.userManager = new UserManager();

        // Define user role combobox
        this.cmb_user_role.setModel(new DefaultComboBoxModel<>(User.UserRole.values()));
        this.cmb_user_role.setSelectedItem(null);

        // Fill the fields if an existing user is going to be updated
        if(this.user.getUserID() != 0) {
            this.fld_user_name.setText(this.user.getUserName());
            this.fld_user_password.setText(this.user.getUserPassword());
            this.fld_user_first_name.setText(this.user.getUserFirstName());
            this.fld_user_last_name.setText(this.user.getUserLastName());
            this.cmb_user_role.getModel().setSelectedItem(this.user.getUserRole());
        }

        // Save button action listener
        this.btn_save.addActionListener(e -> {
            boolean result;

            // Check if any field is empty
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_user_name, this.fld_user_password,
                    this.fld_user_first_name, this.fld_user_last_name}) ||
                    Helper.isComboBoxEmpty(this.cmb_user_role)) {
                Helper.showMessage("fill");
            } else {
                // Set room values from fields
                this.user.setUserName(this.fld_user_name.getText());
                this.user.setUserPassword(this.fld_user_password.getText());
                this.user.setUserFirstName(this.fld_user_first_name.getText());
                this.user.setUserLastName(this.fld_user_last_name.getText());
                this.user.setUserRole(User.UserRole.valueOf(this.cmb_user_role.getSelectedItem().toString()));

                // Check if the save is successful
                if (this.user.getUserID() != 0) { // Update
                    result = this.userManager.update(this.user);
                } else { // Add
                    result = this.userManager.save(this.user);
                }

                // Show message according to save result
                if (result) {
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}

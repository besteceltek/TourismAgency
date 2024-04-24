package View;

import Business.UserManager;
import Core.Helper;
import Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JPanel pnl_welcome;
    private JPanel pnl_filter;
    private JPanel pnl_user;
    private JPanel pnl_button;
    private JLabel lbl_welcome;
    private JLabel lbl_filter_user_role;
    private JButton btn_logout;
    private JButton btn_add;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_search;
    private JComboBox<User.UserRole> cmb_filter_user_role;
    private JScrollPane scrl_user;
    private JTable tbl_user;
    private JButton btn_reset;
    private User user;
    private final UserManager userManager;
    private final DefaultTableModel mdl_user = new DefaultTableModel();
    private Object[] colUser;

    public AdminView(User user) {
        this.add(container);
        this.initializeGui(600, 250);
        this.user = user;
        this.userManager = new UserManager();

        if(this.user == null) {
            dispose();
        }

        this.lbl_welcome.setText("Welcome " + this.user.getUserName());

        loadUserTable(null);
        loadUserFilter();
        loadAdminComponents();
    }

    public void loadUserTable(ArrayList<Object[]> userList) {
        colUser = new Object[]{"User ID", "User First Name", "User Last Name", "Username", "User Password", "User Role"};
        if (userList == null) {
            userList = this.userManager.getForTable(colUser.length, this.userManager.findAll());
        }
        this.generateTable(this.mdl_user, this.tbl_user, colUser,userList);
    }

    public void loadUserFilter() {
        this.cmb_filter_user_role.setModel(new DefaultComboBoxModel<>(User.UserRole.values()));
        this.cmb_filter_user_role.setSelectedItem(null);
    }

    public void loadAdminComponents() {
        this.selectRow(this.tbl_user);

        this.btn_add.addActionListener(e -> {
            UserView userView = new UserView(new User());
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });

        this.btn_update.addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(this.tbl_user, 0);
            UserView userView = new UserView(this.userManager.getByID(selectUserId));
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });

        this.btn_delete.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectUserId = this.getTableSelectedRow(this.tbl_user, 0);
                if (this.userManager.delete(selectUserId)) {
                    Helper.showMessage("done");
                    loadUserTable(null);
                }
            }
        });

        this.btn_search.addActionListener(e -> {
            ArrayList<User> userListBySearch = this.userManager.searchForTable((User.UserRole) cmb_filter_user_role.getSelectedItem());
            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(this.colUser.length, userListBySearch);
            loadUserTable(userRowListBySearch);
        });

        this.btn_reset.addActionListener(e -> {
            this.cmb_filter_user_role.setSelectedItem(null);
            loadUserTable(null);
        });

        this.btn_logout.addActionListener(e -> {
            LoginView loginView = new LoginView();
            dispose();
        });
    }
}

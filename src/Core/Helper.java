package Core;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Helper {
    private static final Connection connection = Database.getInstance();

    public static void setTheme() {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static void showMessage(String str) {
        String title;
        String message = switch (str) {
            case "fill" -> {
                title = "Error";
                yield "Please fill out all the fields!";
            }
            case "done" -> {
                title = "Success";
                yield "Successful";
            }
            case "notFound" -> {
                title = "Error";
                yield "Not found";
            }
            case "error" -> {
                title = "Error";
                yield "Bad request";
            }
            default -> {
                title = "Info";
                yield str;
            }
        };

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isComboBoxEmpty(JComboBox comboBox) {
        return comboBox.getSelectedItem() == null;
    }

    public static boolean isComboBoxListEmpty(JComboBox[] comboList) {
        for (JComboBox comboBox : comboList) {
            if (isComboBoxEmpty(comboBox)) return true;
        }
        return false;
    }

    public static boolean confirm(String str) {
        String msg;
        if(str.equals("sure")) {
            msg = "Are you sure?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Are your sure?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isValidDate(String inputDate, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

        try {
            LocalDate date = LocalDate.parse(inputDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

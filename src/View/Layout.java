package View;

import Core.Cities;
import Core.ComboItem;
import Core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Layout extends JFrame {
    public void initializeGui(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Patika Tourism");
        this.setSize(width, height);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));
        this.setVisible(true);
    }

    public void generateTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows) {
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.setEnabled(false);

        // Table cleaner
        DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
        clearModel.setRowCount(0);

        if (rows == null) {
            rows = new ArrayList<>();
        }

        for(Object[] row : rows) {
            model.addRow(row);
        }
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    public void selectRow(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    public int getTableSelectedRow(JTable table, int index) {
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(), index).toString());
    }

    public void loadComboCities(JComboBox cityCombo) {
        Map<String, List<String>> cityMap = Cities.CityMap();
        cityCombo.removeAllItems();

        // Türkçe karakterlere göre sıralama için Collator kullanımı
        Collator collator = Collator.getInstance(new Locale("tr", "TR"));

        List<String> sortedCities = new ArrayList<>(cityMap.keySet());
        sortedCities.sort(collator::compare);

        int k = 0;
        for (String city : sortedCities) {
            cityCombo.addItem(new ComboItem(k++, city));
        }
        cityCombo.setSelectedItem(null);
    }

    public void loadComboZones(JComboBox cityCombo, JComboBox regionCombo) {
        String selectedCity = ((ComboItem) cityCombo.getSelectedItem()).getValue();

        if (selectedCity != null) {
            Map<String, List<String>> cityMap = Cities.CityMap();
            List<String> zones = cityMap.get(selectedCity);

            regionCombo.removeAllItems();
            int k = 0;
            if (zones != null) {
                for (String zone : zones) {
                    regionCombo.addItem(new ComboItem(k++, zone));
                }
            }
        }
        regionCombo.setSelectedItem(null);
    }

    public void printFrameSize(JTable table) {
        Dimension size = table.getSize();
        System.out.println("Dimension is: " + size);
    }

    public void resizeTable(JTable table, int tableWidth, int tableHeight, double... percentages) {
        table.setPreferredSize(new Dimension(tableWidth, tableHeight));

        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setMaxWidth((int) (tableWidth * (percentages[i] / total)));
        }
    }
}

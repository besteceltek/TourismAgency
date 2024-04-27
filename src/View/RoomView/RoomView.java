package View.RoomView;

import Business.HotelManager;
import Business.PensionManager;
import Business.RoomManager;
import Business.SeasonManager;
import Core.ComboItem;
import Core.Helper;
import Entity.Hotel;
import Entity.Pension;
import Entity.Room;
import Entity.Season;
import View.Layout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class RoomView extends Layout {
    private JPanel container;
    private JPanel pnl_features;
    private JPanel pnl_button;
    private JPanel pnl_pricing;
    private JPanel pnl_room_info;

    private JLabel lbl_room_pension;
    private JLabel lbl_room_features;
    private JLabel lbl_room_price_adult;
    private JLabel lbl_room_price_child;
    private JLabel lbl_hotel;
    private JLabel lbl_room_type;
    private JLabel lbl_bed_count;
    private JLabel lbl_room_area;
    private JLabel lbl_room_stock;
    private JLabel lbl_room;
    private JLabel lbl_room_season;

    private JComboBox<ComboItem> cmb_room_pension;
    private JComboBox<ComboItem> cmb_hotel;
    private JComboBox<Room.RoomType> cmb_room_type;
    private JComboBox<Object> cmb_room_season;

    private JCheckBox chk_tv;
    private JCheckBox chk_bar;
    private JCheckBox chk_console;
    private JCheckBox chk_safe;
    private JCheckBox chk_projection;

    private JButton btn_save;

    private JTextField fld_room_price_adult;
    private JTextField fld_room_price_child;
    private JTextField fld_bed_count;
    private JTextField fld_room_area;
    private JTextField fld_room_stock;

    private final Room room;
    private final RoomManager roomManager;
    private final HotelManager hotelManager;
    private final PensionManager pensionManager;
    private final SeasonManager seasonManager;

    private final ArrayList<String> roomFeaturesList = new ArrayList<>();

    public RoomView(Room room) {
        this.add(container);
        this.initializeGui(400, 450);
        this.room = room;
        this.roomManager = new RoomManager();
        this.hotelManager = new HotelManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();

        // Add features to room if the checkboxes are checked
        this.loadCheckBoxListener();

        // Set Combo Boxes
        this.loadComboBox();

        // Check if hotel is selected on Hotel tab
        if (this.room.getHotelId() != 0) {
            ComboItem defaultHotel = new ComboItem(this.room.getHotelId(), this.room.getHotel().getHotelName());
            this.cmb_hotel.getModel().setSelectedItem(defaultHotel);
            this.cmb_hotel.setEnabled(false);
            loadPensionComboBox();
            loadSeasonComboBox();
        }

        // Pension combo box updater for Hotels
        this.cmb_hotel.addActionListener(e -> {
            loadPensionComboBox();
            loadSeasonComboBox();
        });

        // Save button action listener
        this.btn_save.addActionListener(e -> {
            // Check if any field is empty
            if (Helper.isFieldListEmpty(new JTextField[] {this.fld_bed_count, this.fld_room_area, this.fld_room_stock,
                    this.fld_room_price_adult, this.fld_room_price_child}) ||
                    Helper.isComboBoxListEmpty(new JComboBox[]{this.cmb_room_pension, this.cmb_hotel, this.cmb_room_type})) {
                Helper.showMessage("fill");
            } else {
                // Set room values from fields
                this.room.setRoomArea(Integer.parseInt(fld_room_area.getText()));
                this.room.setRoomStock(Integer.parseInt(fld_room_stock.getText()));
                this.room.setRoomBed(Integer.parseInt(fld_bed_count.getText()));
                this.room.setRoomPriceAdult(fld_room_price_adult.getText());
                this.room.setRoomPriceChild(fld_room_price_child.getText());
                this.room.setRoomType(Room.RoomType.valueOf(cmb_room_type.getSelectedItem().toString()));
                this.room.setRoomFeatures(this.roomManager.arrayConversion(this.roomFeaturesList));
                ComboItem selectSeason = (ComboItem) cmb_room_season.getSelectedItem();
                this.room.setSeasonId(selectSeason.getKey());
                this.room.setSeason(this.seasonManager.getByID(selectSeason.getKey()));
                ComboItem selectPension = (ComboItem) cmb_room_pension.getSelectedItem();
                this.room.setPensionId(selectPension.getKey());
                this.room.setPension(this.pensionManager.getByID(selectPension.getKey()));
                ComboItem selectHotel = (ComboItem) cmb_hotel.getSelectedItem();
                this.room.setHotelId(selectHotel.getKey());
                this.room.setHotel(this.hotelManager.getByID(selectHotel.getKey()));

                // Check if the save is successful
                boolean result = this.roomManager.save(this.room);

                // Show message according to save result
                if (result) {
                    Helper.showMessage("Room is saved successfully");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    public void loadComboBox() {
        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_hotel.addItem(new ComboItem(hotel.getHotelId(),hotel.getHotelName()));
        }
        this.cmb_hotel.setSelectedItem(null);
        this.cmb_room_type.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        this.cmb_room_type.setSelectedItem(null);
        this.cmb_room_season.setModel(new DefaultComboBoxModel<>(new Object[]{"Summer", "Winter"}));
        this.cmb_room_season.setSelectedItem(null);
    }

    public void loadSeasonComboBox() {
        int selectedHotelId = ((ComboItem) this.cmb_hotel.getSelectedItem()).getKey();

        if (selectedHotelId != 0) {
            this.cmb_room_season.removeAllItems();
            for (Season season : this.seasonManager.getByListHotelId(selectedHotelId)) {
                cmb_room_season.addItem(season.getComboItem());
            }
        }
    }

    public void loadPensionComboBox() {
        int selectedHotelId = ((ComboItem) this.cmb_hotel.getSelectedItem()).getKey();

        if (selectedHotelId != 0) {
            this.cmb_room_pension.removeAllItems();
            for (Pension pension : this.pensionManager.getByListHotelId(selectedHotelId)) {
                cmb_room_pension.addItem(pension.getComboItem());
            }
        }

        this.cmb_room_pension.setSelectedItem(null);
    }

    public void loadCheckBoxListener() {
        this.chk_tv.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    roomFeaturesList.add(chk_tv.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    roomFeaturesList.remove(chk_tv.getText());
                }
            }
        });
        this.chk_bar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    roomFeaturesList.add(chk_bar.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    roomFeaturesList.remove(chk_bar.getText());
                }
            }
        });
        this.chk_console.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    roomFeaturesList.add(chk_console.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    roomFeaturesList.remove(chk_console.getText());
                }
            }
        });
        this.chk_safe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    roomFeaturesList.add(chk_safe.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    roomFeaturesList.remove(chk_safe.getText());
                }
            }
        });
        this.chk_projection.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    roomFeaturesList.add(chk_projection.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    roomFeaturesList.remove(chk_projection.getText());
                }
            }
        });
    }
}

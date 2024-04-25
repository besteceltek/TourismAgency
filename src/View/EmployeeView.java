package View;

import Business.HotelManager;
import Business.ReservationManager;
import Business.RoomManager;
import Core.ComboItem;
import Core.Helper;
import Entity.Hotel;
import Entity.Reservation;
import Entity.Room;
import Entity.User;
import View.RoomView.RoomView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JPanel container;
    private JPanel pnl_welcome;
    private JPanel pnl_tab;
    private JPanel pnl_hotel;
    private JPanel pnl_room;
    private JPanel pnl_hotel_button;
    private JPanel pnl_room_button;
    private JPanel pnl_filter_room;

    private JTabbedPane tab_menu;

    private JScrollPane scrl_otel;
    private JScrollPane scrl_room;

    private JTable tbl_otel;
    private JTable tbl_room;

    private JLabel lbl_welcome;
    private JLabel lbl_filter_hotel;
    private JLabel lbl_filter_city;
    private JLabel lbl_filter_region;
    private JLabel lbl_filter_checkin;
    private JLabel lbl_filter_checkout;
    private JLabel lbl_filter_bed;

    private JButton btn_logout;
    private JButton btn_hotel_room_add;
    private JButton btn_hotel_add;
    private JButton btn_room_add;
    private JButton btn_room_res_add;
    private JButton btn_search;

    private JComboBox cmb_filter_hotel;
    private JComboBox cmb_filter_city;
    private JComboBox cmb_filter_region;

    private JTextField fld_filter_checkin;
    private JTextField fld_filter_checkout;
    private JTextField fld_filter_bed;
    private JButton btn_reset;
    private JPanel pnl_reserevation;
    private JTable tbl_reservations;
    private JScrollPane scrl_reservations;
    private JTable tbl_hotel_features;
    private JTable tbl_hotel_season;
    private JScrollPane scrl_hotel_features;
    private JPanel pnl_hotel_features;
    private JPanel pnl_hotel_info;
    private JScrollPane scrl_hotel_season;
    private JPanel pnl_hotel_season;
    private JButton btn_delete;
    private JButton btn_update;

    private User user;

    private HotelManager hotelManager;
    private RoomManager roomManager;
    private ReservationManager reservationManager;

    private final DefaultTableModel mdl_hotel = new DefaultTableModel();
    private final DefaultTableModel mdl_room = new DefaultTableModel();
    private final DefaultTableModel mdl_res = new DefaultTableModel();
    private final DefaultTableModel mdl_hotelSeason = new DefaultTableModel();

    private Object[] colRoom;

    public EmployeeView(User user) {
        this.add(container);
        this.initializeGui(1000, 800);
        this.user = user;
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();

        this.lbl_welcome.setText("Welcome " + this.user.getUserName());

        loadEmployeeComponents();
        loadHotelTable();
        loadRoomTable(null);
        loadComboCities(cmb_filter_city);
        loadReservationTable();

        // !!!! UPDATE !!!!
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadHotelSeasonTable();
            }
        });

        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_filter_hotel.addItem(new ComboItem(hotel.getHotelId(), hotel.getHotelName()));
        }
        this.cmb_filter_hotel.setSelectedItem(null);

        this.cmb_filter_city.addActionListener(e -> {
            if (cmb_filter_city.getSelectedItem() != null) {
                loadComboZones(cmb_filter_city, cmb_filter_region);
            }
        });
    }

    public void loadHotelTable() {
        Object[] colHotel = {"Hotel ID", "Hotel Name", "Hotel City", "Hotel Region", "Hotel Address",
                "Hotel Mail", "Hotel Phone", "Hotel Star", "Hotel Features"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(colHotel.length, this.hotelManager.findAll());
        this.generateTable(this.mdl_hotel, this.tbl_otel, colHotel, hotelList);
    }

    public void loadRoomTable(ArrayList<Object[]> roomList) {
        colRoom = new Object[]{"Room ID", "Room Type", "Bed Count", "Room Area", "Room Features", "Room Stock",
                "Room Price (Adult)", "Room Price (Child)", "Hotel", "Pension Type", "Season Start Date", "Season End Date"};
        if (roomList == null) {
            roomList = this.roomManager.getForTable(colRoom.length, this.roomManager.findAll());
        }
        this.generateTable(this.mdl_room, this.tbl_room, colRoom, roomList);
    }

    public void loadReservationTable() {
        Object[] colRes = {"Reservation ID", "Room Id", "Name", "Mail", "Phone", "TC/Passport No", "Reservation Note", "Start Date", "End Date"};
        ArrayList<Object[]> reservationList = this.reservationManager.getForTable(colRes.length, this.reservationManager.findAll());
        this.generateTable(this.mdl_res, this.tbl_reservations, colRes, reservationList);
    }

    public void loadHotelSeasonTable() {
        int selectHotelId = this.getTableSelectedRow(this.tbl_otel, 0);
        Object[] colSeason = {"Season", "Season Start Date", "Season End Date"};
        ArrayList<Object[]> seasonList = this.hotelManager.getForSeasonTable(colSeason.length, this.hotelManager.getByID(selectHotelId));
        this.generateTable(this.mdl_hotelSeason, this.tbl_hotel_season, colSeason, seasonList);
    }

    public void loadEmployeeComponents() {
        this.selectRow(this.tbl_otel);
        this.selectRow(this.tbl_room);
        this.selectRow(this.tbl_reservations);

        this.btn_hotel_add.addActionListener(e -> {
            HotelView hotelView = new HotelView(new Hotel());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
        });

        this.btn_room_add.addActionListener(e -> {
            RoomView roomView = new RoomView(new Room());
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });

        this.btn_hotel_room_add.addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(this.tbl_otel, 0);
            Room room = new Room();
            room.setHotelId(selectHotelId);
            room.setHotel(this.hotelManager.getByID(selectHotelId));
            RoomView roomView = new RoomView(room);
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });

        this.btn_room_res_add.addActionListener(e -> {
            if (this.fld_filter_checkin.getText().isEmpty() || this.fld_filter_checkout.getText().isEmpty()) {
                Helper.showMessage("Please fill Check-in and Check-out date");
            } else {
                int selectRoomId = this.getTableSelectedRow(this.tbl_room, 0);
                Reservation reservation = new Reservation();
                reservation.setRoomId(selectRoomId);

                // Check if the input date is valid and set the reservation start date
                if (Helper.isValidDate(fld_filter_checkin.getText(), ("dd.MM.yyyy"))) {
                    reservation.setReservationStartDate(LocalDate.parse(fld_filter_checkin.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }

                // Check if the input date is valid and set the reservation end date
                if (Helper.isValidDate(fld_filter_checkout.getText(), ("dd.MM.yyyy"))) {
                    reservation.setReservationEndDate(LocalDate.parse(fld_filter_checkout.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }

                ReservationView reservationView = new ReservationView(reservation);
                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadReservationTable();
                        loadRoomTable(null);
                    }
                });
            }
        });

        this.btn_update.addActionListener(e -> {
            int selectReservationId = this.getTableSelectedRow(this.tbl_reservations, 0);
            ReservationView reservationView = new ReservationView(this.reservationManager.getByID(selectReservationId));
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable();
                }
            });
        });

        this.btn_delete.addActionListener(e -> {
            int selectReservationId = this.getTableSelectedRow(this.tbl_reservations, 0);

            // Get room info before deleting reservation
            Room room = this.roomManager.getByID(this.reservationManager.getByID(selectReservationId).getRoomId());
            int roomStock = room.getRoomStock();

            if (Helper.confirm("sure")) {
                if (this.reservationManager.delete(selectReservationId)) {
                    // If reservation delete successful, increase room stock by one
                    roomStock += 1;
                    this.roomManager.updateRoomStock(roomStock, room);

                    Helper.showMessage("Reservation deleted successfully");
                    loadReservationTable();
                    loadRoomTable(null);
                }
            }
        });

        this.btn_search.addActionListener(e -> {
            String selectedCity = null;
            String selectedHotel = null;
            String selectedRegion = null;
            String filterStartDate = null;
            String filterEndDate = null;
            int filterBed = 0;

            if (!this.fld_filter_bed.getText().isEmpty() && this.fld_filter_bed.getText() != null) {
                filterBed = Integer.parseInt(this.fld_filter_bed.getText());
            }
            if (this.cmb_filter_city.getSelectedItem() != null) {
                selectedCity = ((ComboItem)this.cmb_filter_city.getSelectedItem()).getValue();
            }
            if (this.cmb_filter_hotel.getSelectedItem() != null) {
                selectedHotel = ((ComboItem)this.cmb_filter_hotel.getSelectedItem()).getValue();
            }
            if (this.cmb_filter_region.getSelectedItem() != null) {
                selectedRegion = ((ComboItem)this.cmb_filter_region.getSelectedItem()).getValue();
            }
            if (this.fld_filter_checkin != null && !this.fld_filter_checkin.getText().isEmpty()){
                if (Helper.isValidDate(fld_filter_checkin.getText(), ("dd.MM.yyyy"))) {
                    filterStartDate = fld_filter_checkin.getText();
                }
            }
            if (this.fld_filter_checkout != null && !this.fld_filter_checkout.getText().isEmpty()){
                if (Helper.isValidDate(fld_filter_checkout.getText(), ("dd.MM.yyyy"))) {
                    filterEndDate = fld_filter_checkout.getText();
                }
            }

            ArrayList<Room> roomListBySearch = this.roomManager.searchForRooms(filterStartDate, filterEndDate, selectedCity, selectedHotel, selectedRegion, filterBed);
            ArrayList<Object[]> roomRowListBySearch = this.roomManager.getForTable(this.colRoom.length, roomListBySearch);
            loadRoomTable(roomRowListBySearch);
        });

        this.btn_reset.addActionListener(e -> {
            this.cmb_filter_hotel.setSelectedItem(null);
            this.cmb_filter_region.setSelectedItem(null);
            this.cmb_filter_city.setSelectedItem(null);
            this.fld_filter_checkin.setText(null);
            this.fld_filter_checkout.setText(null);
            this.fld_filter_bed.setText(null);
            loadRoomTable(null);
        });

        this.btn_logout.addActionListener(e -> {
            LoginView loginView = new LoginView();
            dispose();
        });
    }
}


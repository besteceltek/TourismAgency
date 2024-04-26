package View;

import Business.ReservationManager;
import Business.RoomManager;
import Core.ComboItem;
import Core.Helper;
import Entity.Reservation;
import Entity.Room;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ReservationView extends Layout {
    private JPanel container;
    private JPanel pnl_hotel_info;
    private JPanel pnl_res_info;
    private JPanel pnl_button;

    private JLabel lbl_reservation;
    private JLabel lbl_hotel;
    private JLabel lbl_pension;
    private JLabel lbl_hotel_features;
    private JLabel lbl_room_features;
    private JLabel lbl_room_type;
    private JLabel lbl_adult;
    private JLabel lbl_child;
    private JLabel lbl_checkin;
    private JLabel lbl_checkout;
    private JLabel lbl_name;
    private JLabel lbl_email;
    private JLabel lbl_phone;
    private JLabel lbl_res_note;
    private JLabel lbl_tc;
    private JLabel lbl_cost;

    private JTextField fld_hotel;
    private JTextField fld_pension_type;
    private JTextField fld_checkin;
    private JTextField fld_checkout;
    private JTextField fld_name;
    private JTextField fld_email;
    private JTextField fld_phone;
    private JTextField fld_room_type;
    private JTextField fld_tc;
    private JTextField fld_cost;

    private JTextArea txta_res_note;
    private JTextArea txta_room_features;
    private JTextArea txta_hotel_features;

    private JComboBox<ComboItem> cmb_adult;
    private JComboBox<ComboItem> cmb_child;

    private JButton btn_save;
    private JButton btn_calculate;

    private Reservation reservation;

    private RoomManager roomManager;
    private ReservationManager reservationManager;

    public ReservationView(Reservation reservation) {
        this.add(container);
        this.initializeGui(800, 500);
        this.reservation = reservation;
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();

        if (this.reservation.getRoomId() != 0) {
            setHotelValues(this.reservation);
            loadRoomComboBox(this.reservation);
            if (this.reservation.getReservationId() != 0) {
                setReservationValues(this.reservation);
            }
        }

        // Calculate button action listener
        this.btn_calculate.addActionListener(e -> {
            if (Helper.isComboBoxListEmpty(new JComboBox[]{this.cmb_adult, this.cmb_child})) {
                Helper.showMessage("Please add applicable guest count");
            } else {
                if (checkGuestCount()) {
                    ComboItem selectAdult = (ComboItem) this.cmb_adult.getSelectedItem();
                    ComboItem selectChild = (ComboItem) this.cmb_child.getSelectedItem();

                    int adultCount = Integer.parseInt(selectAdult.getValue());
                    int childCount = Integer.parseInt(selectChild.getValue());

                    int adultPricePerDay = Integer.parseInt(this.roomManager.getByID(this.reservation.getRoomId()).getRoomPriceAdult()) * adultCount;
                    int childPricePerDay = Integer.parseInt(this.roomManager.getByID(this.reservation.getRoomId()).getRoomPriceChild()) * childCount;

                    int totalPrice = (adultPricePerDay + childPricePerDay) * calculateDays();

                    this.fld_cost.setText(String.valueOf(totalPrice));
                }
            }
        });

        // Save button action listener
        this.btn_save.addActionListener(e -> {
            // Check if any field is empty
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_name, this.fld_email, this.fld_phone,
                    this.fld_checkin, this.fld_checkout, this.fld_tc}) ||
                    Helper.isComboBoxListEmpty(new JComboBox[]{this.cmb_adult, this.cmb_child})) {
                Helper.showMessage("fill");
            } else {
                if (Helper.isFieldEmpty(this.fld_cost)) {
                    Helper.showMessage("Please calculate your total cost");
                } else {
                    boolean result;

                    if (checkGuestCount()) {

                        // Set room values from fields
                        this.reservation.setReservationName(fld_name.getText());
                        this.reservation.setReservationTc(fld_tc.getText());
                        this.reservation.setReservationPhone(fld_phone.getText());
                        this.reservation.setReservationMail(fld_email.getText());
                        this.reservation.setReservationNote(txta_res_note.getText());
                        this.reservation.setReservationStartDate(LocalDate.parse(fld_checkin.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        this.reservation.setReservationEndDate(LocalDate.parse(fld_checkout.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        this.reservation.setTotalPrice(fld_cost.getText());

                        // Check if the save is successful
                        if (this.reservation.getReservationId() != 0) { // Update
                            result = this.reservationManager.update(this.reservation);
                        } else { // Add
                            result = this.reservationManager.save(this.reservation);

                            // If reservation save successful, decrease room stock by one
                            if (result) {
                                int roomStock = this.roomManager.getByID(this.reservation.getRoomId()).getRoomStock();
                                roomStock -= 1;
                                this.roomManager.updateRoomStock(roomStock, this.roomManager.getByID(this.reservation.getRoomId()));
                            }
                        }

                        if (result) {
                            Helper.showMessage("done");
                            dispose();
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
    }

    public int calculateDays() {
        LocalDate reservationStartDate = LocalDate.parse(fld_checkin.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate reservationEndDate = LocalDate.parse(fld_checkout.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return (int) ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate);
    }

    public boolean checkGuestCount() {
        boolean result = true;
        // Check if the guest count is applicable
        ComboItem selectAdult = (ComboItem) this.cmb_adult.getSelectedItem();
        ComboItem selectChild = (ComboItem) this.cmb_child.getSelectedItem();

        int guestCount = Integer.parseInt(selectAdult.getValue()) + Integer.parseInt(selectChild.getValue());

        if (guestCount > this.roomManager.getByID(this.reservation.getRoomId()).getRoomBed()) {
            Helper.showMessage("Not enough bed for the guests");
            result = false;
            this.cmb_adult.setSelectedItem(null);
            this.cmb_child.setSelectedItem(null);
        }
        return result;
    }

    public void loadRoomComboBox(Reservation reservation) {
        // Max adult count can be equal to bed count
        int bedCount = this.roomManager.getByID(reservation.getRoomId()).getRoomBed();
        for (int i = 0; i <= bedCount; i++) {
            this.cmb_adult.addItem(new ComboItem(i, String.valueOf(i)));
        }

        // There has to be at least one adult with children, so max children count is one less than the bed count
        for (int i = 0; i < bedCount; i++) {
            this.cmb_child.addItem(new ComboItem(i, String.valueOf(i)));
        }

        this.cmb_adult.setSelectedItem(null);
        this.cmb_child.setSelectedItem(null);
    }

    public void setReservationValues(Reservation reservation) {
        this.fld_name.setText(reservation.getReservationName());
        this.fld_tc.setText(reservation.getReservationTc());
        this.fld_email.setText(reservation.getReservationMail());
        this.fld_phone.setText(reservation.getReservationPhone());
        this.txta_res_note.setText(reservation.getReservationNote());
    }

    public void setHotelValues(Reservation reservation) {
        Room room = this.roomManager.getByID(reservation.getRoomId());

        this.fld_hotel.setText(room.getHotel().getHotelName());
        this.fld_pension_type.setText(room.getPension().getPensionType());
        this.fld_room_type.setText(room.getRoomType().toString());
        this.fld_checkin.setText(reservation.getReservationStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fld_checkout.setText(reservation.getReservationEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.txta_room_features.setText(room.getRoomFeaturesToString());
        this.txta_hotel_features.setText(room.getHotel().getHotelFeaturesToString());

        this.fld_hotel.setEnabled(false);
        this.fld_pension_type.setEnabled(false);
        this.fld_room_type.setEnabled(false);
        this.fld_checkin.setEnabled(false);
        this.fld_checkout.setEnabled(false);
        this.txta_room_features.setEnabled(false);
        this.txta_hotel_features.setEnabled(false);
    }
}

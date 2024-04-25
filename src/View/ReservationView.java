package View;

import Business.ReservationManager;
import Business.RoomManager;
import Core.Helper;
import Entity.Reservation;
import Entity.Room;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationView extends Layout {
    private JPanel container;
    private JPanel pnl_hotel_info;
    private JPanel pnl_res_info;

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

    private JTextField fld_hotel;
    private JTextField fld_pension_type;
    private JTextField fld_checkin;
    private JTextField fld_checkout;
    private JTextField fld_name;
    private JTextField fld_email;
    private JTextField fld_phone;

    private JComboBox cmb_adult;
    private JComboBox cmb_child;

    private JTextArea txta_res_note;
    private JPanel pnl_button;
    private JButton btn_save;
    private JButton btn_calculate;
    private JTextField fld_room_type;
    private JTextArea txta_room_features;
    private JTextArea txta_hotel_features;
    private JLabel lbl_tc;
    private JTextField fld_tc;
    private JLabel lbl_cost;
    private JTextField fld_cost;

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
            if (this.reservation.getReservationId() != 0) {
                setReservationValues(this.reservation);
            }
        }

        // Save button action listener
        this.btn_save.addActionListener(e -> {
            // Check if any field is empty
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_name, this.fld_email, this.fld_phone,
                    this.fld_checkin, this.fld_checkout, this.fld_tc})) {
                Helper.showMessage("fill");
            } else {
                boolean result;

                // Set room values from fields
                this.reservation.setReservationName(fld_name.getText());
                this.reservation.setReservationTc(fld_tc.getText());
                this.reservation.setReservationPhone(fld_phone.getText());
                this.reservation.setReservationMail(fld_email.getText());
                this.reservation.setReservationNote(txta_res_note.getText());
                this.reservation.setReservationStartDate(LocalDate.parse(fld_checkin.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                this.reservation.setReservationEndDate(LocalDate.parse(fld_checkout.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));

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
        });
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
        this.fld_hotel.setEnabled(false);
        this.fld_pension_type.setText(room.getPension().getPensionType());
        this.fld_pension_type.setEnabled(false);
        this.fld_room_type.setText(room.getRoomType().toString());
        this.fld_room_type.setEnabled(false);
        this.fld_checkin.setText(reservation.getReservationStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fld_checkin.setEnabled(false);
        this.fld_checkout.setText(reservation.getReservationEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fld_checkout.setEnabled(false);
        this.txta_room_features.setText(room.getRoomFeaturesToString());
        this.txta_room_features.setEnabled(false);
        this.txta_hotel_features.setText(room.getHotel().getHotelFeaturesToString());
        this.txta_hotel_features.setEnabled(false);
    }
}

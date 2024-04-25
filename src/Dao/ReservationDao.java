package Dao;

import Core.Database;
import Entity.Pension;
import Entity.Reservation;
import Entity.Room;
import Entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDao {
    private final Connection connection;

    public ReservationDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Reservation> findAll() {
        return this.selectByQuery("SELECT * FROM public.reservation ORDER BY reservation_id ASC");
    }

    // Get Reservation by reservation ID
    public Reservation getByID(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM public.reservation WHERE reservation_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    // Save Reservation
    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation " +
                "(" +
                "reservation_name, " +
                "reservation_mail, " +
                "reservation_phone, " +
                "reservation_note, " +
                "room_id, " +
                "reservation_start_date, " +
                "reservation_end_date, " +
                "reservation_tc" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setReservationValues(reservation, preparedStatement);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Update reservation
    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET " +
                "reservation_name = ?, " +
                "reservation_mail = ?, " +
                "reservation_phone = ?, " +
                "reservation_note = ?, " +
                "room_id = ?, " +
                "reservation_start_date = ?, " +
                "reservation_end_date = ?, " +
                "reservation_tc = ?" +
                "WHERE reservation_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setReservationValues(reservation, preparedStatement);
            preparedStatement.setInt(9, reservation.getReservationId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Delete reservation
    public boolean delete(int id) {
        String query = "DELETE FROM public.reservation WHERE reservation_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Private Methods that is used only in this class

    private void setReservationValues(Reservation reservation, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, reservation.getReservationName());
        preparedStatement.setString(2, reservation.getReservationMail());
        preparedStatement.setString(3, reservation.getReservationPhone());
        preparedStatement.setString(4, reservation.getReservationNote());
        preparedStatement.setInt(5, reservation.getRoomId());
        preparedStatement.setDate(6, Date.valueOf(reservation.getReservationStartDate()));
        preparedStatement.setDate(7, Date.valueOf(reservation.getReservationEndDate()));
        preparedStatement.setString(8, reservation.getReservationTc());
    }

    private ArrayList<Reservation> selectByQuery(String query) {
        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                reservationList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    private Reservation match(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(resultSet.getInt("reservation_id"));
        reservation.setRoomId(resultSet.getInt("room_id"));
        reservation.setReservationName(resultSet.getString("reservation_name"));
        reservation.setReservationMail(resultSet.getString("reservation_mail"));
        reservation.setReservationPhone(resultSet.getString("reservation_phone"));
        reservation.setReservationTc(resultSet.getString("reservation_tc"));
        reservation.setReservationNote(resultSet.getString("reservation_note"));
        reservation.setReservationStartDate(LocalDate.parse(resultSet.getString("reservation_start_date")));
        reservation.setReservationEndDate(LocalDate.parse(resultSet.getString("reservation_end_date")));
        return reservation;
    }
}

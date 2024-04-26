package Business;

import Core.Helper;
import Dao.ReservationDao;
import Entity.Reservation;
import Entity.Room;
import Entity.User;

import java.util.ArrayList;

public class ReservationManager {
    private ReservationDao reservationDao;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    public Reservation getByID(int id) {
        return this.reservationDao.getByID(id);
    }

    public boolean save(Reservation reservation) {
        if (reservation.getReservationId() != 0) {
            Helper.showMessage("res hata");
        }
        return this.reservationDao.save(reservation);
    }

    public boolean update(Reservation reservation) {
        if (this.getByID(reservation.getReservationId()) == null) {
            Helper.showMessage("notFound");
        }
        return this.reservationDao.update(reservation);
    }

    // Delete User
    public boolean delete (int id) {
        if (this.getByID(id) == null) {
            Helper.showMessage("Reservation ID " + id + " is not found");
            return false;
        }
        return this.reservationDao.delete(id);
    }

    // Get reservation values for employee view table
    public ArrayList<Object[]> getForTable(int colSize, ArrayList<Reservation> reservationList) {
        ArrayList<Object[]> reservationRowList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            Object[] rowObject = new Object[colSize];
            int i = 0;
            rowObject[i++] = reservation.getReservationId();
            rowObject[i++] = reservation.getRoomId();
            rowObject[i++] = reservation.getReservationName();
            rowObject[i++] = reservation.getReservationMail();
            rowObject[i++] = reservation.getReservationPhone();
            rowObject[i++] = reservation.getReservationTc();
            rowObject[i++] = reservation.getReservationNote();
            rowObject[i++] = reservation.getReservationStartDate();
            rowObject[i++] = reservation.getReservationEndDate();
            rowObject[i++] = reservation.getTotalPrice();
            reservationRowList.add(rowObject);
        }
        return reservationRowList;
    }
}

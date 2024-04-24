package Business;

import Core.Helper;
import Dao.ReservationDao;
import Entity.Reservation;
import Entity.Room;

import java.util.ArrayList;

public class ReservationManager {
    private ReservationDao reservationDao;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    public boolean save(Reservation reservation) {
        if (reservation.getReservationId() != 0) {
            Helper.showMessage("res hata");
        }
        return this.reservationDao.save(reservation);
    }

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
            reservationRowList.add(rowObject);
        }
        return reservationRowList;
    }
}

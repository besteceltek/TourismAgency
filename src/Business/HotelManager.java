package Business;

import Core.Helper;
import Dao.HotelDao;
import Entity.Hotel;
import Entity.Pension;
import Entity.Room;
import Entity.User;

import java.sql.Array;
import java.util.ArrayList;

public class HotelManager {
    private HotelDao hotelDao;
    private final RoomManager roomManager;
    private final PensionManager pensionManager;

    public HotelManager() {
        this.hotelDao = new HotelDao();
        this.roomManager = new RoomManager();
        this.pensionManager =new PensionManager();
    }

    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public Array arrayConversion(ArrayList<String> hotelFeaturesList) { return this.hotelDao.arrayConversion(hotelFeaturesList);}

    public int saveAndGetHotelId(Hotel hotel) { return this.hotelDao.saveAndGetHotelId(hotel); }

    public boolean save(Hotel hotel) {
        if (hotel.getHotelId() != 0) {
            Helper.showMessage("otel kaydedilemedi");
        }
        return this.hotelDao.save(hotel);
    }

    public Hotel getByID(int id) { return this.hotelDao.getByID(id); }

    public ArrayList<Object[]> getForTable(int colSize, ArrayList<Hotel> hotelList) {
        ArrayList<Object[]> hotelRowList = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            Object[] rowObject = new Object[colSize];
            int i = 0;
            rowObject[i++] = hotel.getHotelId();
            rowObject[i++] = hotel.getHotelName();
            rowObject[i++] = hotel.getHotelCity();
            rowObject[i++] = hotel.getHotelRegion();
            rowObject[i++] = hotel.getHotelAddress();
            rowObject[i++] = hotel.getHotelMail();
            rowObject[i++] = hotel.getHotelPhone();
            rowObject[i++] = hotel.getHotelStar();
            rowObject[i++] = hotel.getHotelFeatures();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }
}

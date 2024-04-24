package Business;

import Core.Helper;
import Dao.RoomDao;
import Entity.Hotel;
import Entity.Room;

import java.sql.Array;
import java.util.ArrayList;

public class RoomManager {
    private RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    public Array arrayConversion(ArrayList<String> roomFeaturesList) { return this.roomDao.arrayConversion(roomFeaturesList);}

    public ArrayList<Room> getByListHotelId(int hotelId) {
        return this.roomDao.getByListHotelId(hotelId);
    }

    public boolean save(Room room) {
        if (room.getRoomId() != 0) {
            Helper.showMessage("room kaydedilemedi");
        }
        return this.roomDao.save(room);
    }

    public Room getByID(int id) { return this.roomDao.getByID(id); }

    public ArrayList<Room> searchForRooms(String filterStartDate, String filterEndDate, String filterCity, String filterHotel, String filterRegion, int filterBed) {
        return this.roomDao.searchForRooms(filterStartDate, filterEndDate, filterCity, filterHotel, filterRegion, filterBed);
    }

    // Get Room values for employee view table
    public ArrayList<Object[]> getForTable(int colSize, ArrayList<Room> roomList) {
        ArrayList<Object[]> roomRowList = new ArrayList<>();
        for (Room room : roomList) {
            Object[] rowObject = new Object[colSize];
            int i = 0;
            rowObject[i++] = room.getRoomId();
            rowObject[i++] = room.getRoomType();
            rowObject[i++] = room.getRoomBed();
            rowObject[i++] = room.getRoomArea();
            rowObject[i++] = room.getRoomFeatures();
            rowObject[i++] = room.getRoomStock();
            rowObject[i++] = room.getRoomPriceAdult();
            rowObject[i++] = room.getRoomPriceChild();
            rowObject[i++] = room.getHotel().getHotelName();
            rowObject[i++] = room.getPension().getPensionType();
            rowObject[i++] = room.getSeason().getSeasonStartDate();
            rowObject[i++] = room.getSeason().getSeasonEndDate();
            roomRowList.add(rowObject);
        }
        return roomRowList;
    }

}

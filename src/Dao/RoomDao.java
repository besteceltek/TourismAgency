package Dao;

import Core.Database;
import Entity.Hotel;
import Entity.Pension;
import Entity.Room;
import Entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class RoomDao {
    private final Connection connection;
    private final HotelDao hotelDao = new HotelDao();
    private final PensionDao pensionDao = new PensionDao();
    private final SeasonDao seasonDao = new SeasonDao();

    private Hotel hotel;
    private Pension pension;

    public RoomDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    // Get Room list by hotel ID
    public ArrayList<Room> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.room WHERE otel_id = " + hotelId);
    }

    // Get Room by room ID
    public Room getByID(int id) {
        Room room = null;
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                room = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    // Cast ArrayList to SQL Array
    public Array arrayConversion(ArrayList<String> roomFeaturesList) {
        try {
            return this.connection.createArrayOf("text", roomFeaturesList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save Room
    public boolean save(Room room) {
        String query = "INSERT INTO public.room " +
                "(" +
                "room_type, " +
                "room_bed, " +
                "room_area, " +
                "room_features, " +
                "room_stock, " +
                "room_price_adult, " +
                "room_price_child, " +
                "otel_id, " +
                "pension_id, " +
                "season_id" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setRoomValues(room, preparedStatement);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Room filter queries
    public ArrayList<Room> searchForRooms(String filterStartDate, String filterEndDate, String filterCity, String filterHotel, String filterRegion, int bedCount) {
        String query = "SELECT * FROM public.room AS R INNER JOIN public.hotel AS H ON R.otel_id = H.hotel_id INNER JOIN public.season AS S ON R.season_id = S.season_id";
        ArrayList<String> where = new ArrayList<>();
        String whereStr = null;

        if (filterCity != null && !filterCity.isEmpty()) {
            where.add("H.hotel_city = '" + filterCity + "'");
        }

        if (filterRegion != null && !filterRegion.isEmpty()) {
            where.add("H.hotel_region = '" + filterRegion + "'");
        }

        if (filterHotel != null && !filterHotel.isEmpty()) {
            where.add("H.hotel_name = '" + filterHotel + "'");
        }

        if (bedCount > 0) {
            where.add("R.room_bed >= '" + bedCount + "'");
        }

        if (filterStartDate != null && !filterStartDate.isEmpty()) {
            LocalDate startDate = LocalDate.parse(filterStartDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            where.add("S.season_start_date <= '" + startDate + "'");
            where.add("S.season_end_date >= '" + startDate + "'");
        }

        if(filterEndDate != null && !filterEndDate.isEmpty()) {
            LocalDate endDate = LocalDate.parse(filterEndDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            where.add("S.season_end_date >= '" + endDate + "'");
            where.add("S.season_start_date <= '" + endDate + "'");
        }

        if (!where.isEmpty()) {
            whereStr = String.join(" AND ", where);
        }

        if (whereStr != null) {
            query += " WHERE " + whereStr;
        }

        System.out.println(query);
        ArrayList<Room> searchedRoomList = this.selectByQuery(query);

        return searchedRoomList;
    }

    // Private Methods that is used only in this class

    private ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                roomList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    private void setRoomValues(Room room, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, room.getRoomType().toString());
        preparedStatement.setInt(2, room.getRoomBed());
        preparedStatement.setInt(3, room.getRoomArea());
        preparedStatement.setArray(4, room.getRoomFeatures());
        preparedStatement.setInt(5, room.getRoomStock());
        preparedStatement.setString(6, room.getRoomPriceAdult());
        preparedStatement.setString(7, room.getRoomPriceChild());
        preparedStatement.setInt(8, room.getHotelId());
        preparedStatement.setInt(9, room.getPensionId());
        preparedStatement.setInt(10, room.getSeasonId());
    }

    private Room match(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setRoomId(resultSet.getInt("room_id"));
        room.setRoomBed(resultSet.getInt("room_bed"));
        room.setRoomArea(resultSet.getInt("room_area"));
        room.setRoomStock(resultSet.getInt("room_stock"));
        room.setHotelId(resultSet.getInt("otel_id"));
        room.setHotel(this.hotelDao.getByID(resultSet.getInt("otel_id")));
        room.setPensionId(resultSet.getInt("pension_id"));
        room.setPension(this.pensionDao.getByID(resultSet.getInt("pension_id")));
        room.setSeasonId(resultSet.getInt("season_id"));
        room.setSeason(this.seasonDao.getByID(resultSet.getInt("season_id")));
        room.setRoomType(Room.RoomType.valueOf(resultSet.getString("room_type")));
        room.setRoomPriceAdult(resultSet.getString("room_price_adult"));
        room.setRoomPriceChild(resultSet.getString("room_price_child"));
        room.setRoomFeatures(resultSet.getArray("room_features"));
        return room;
    }
}

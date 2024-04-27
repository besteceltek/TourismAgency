package Dao;

import Core.Database;
import Entity.Hotel;
import org.postgresql.jdbc2.ArrayAssistant;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {
    private final Connection connection;

    public HotelDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Hotel> findAll() {
        String query = "SELECT * FROM public.hotel ORDER BY hotel_id ASC";
        ArrayList<Hotel> hotelList = new ArrayList<>();

        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                hotelList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }

    // Get Hotel by hotel ID
    public Hotel getByID(int id) {
        Hotel hotel = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hotel = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    // Cast ArrayList to SQL Array
    public Array arrayConversion(ArrayList<String> list) {
        try {
            return this.connection.createArrayOf("text", list.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save Hotel
    public boolean save(Hotel hotel) {
        String query = "INSERT INTO public.hotel " +
                "(" +
                "hotel_name, " +
                "hotel_city, " +
                "hotel_region, " +
                "hotel_address, " +
                "hotel_mail, " +
                "hotel_phone, " +
                "hotel_star, " +
                "hotel_features" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            setHotelValues(hotel, preparedStatement);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Save Hotel and return hotel ID
    public int saveAndGetHotelId(Hotel hotel) {
        String query = "INSERT INTO public.hotel " +
                "(" +
                "hotel_name, " +
                "hotel_city, " +
                "hotel_region, " +
                "hotel_address, " +
                "hotel_mail, " +
                "hotel_phone, " +
                "hotel_star, " +
                "hotel_features" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setHotelValues(hotel, preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Private Methods that is used only in this class

    private void setHotelValues(Hotel hotel, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, hotel.getHotelName());
        preparedStatement.setString(2, hotel.getHotelCity());
        preparedStatement.setString(3, hotel.getHotelRegion());
        preparedStatement.setString(4, hotel.getHotelAddress());
        preparedStatement.setString(5, hotel.getHotelMail());
        preparedStatement.setString(6, hotel.getHotelPhone());
        preparedStatement.setInt(7, hotel.getHotelStar());
        preparedStatement.setArray(8, hotel.getHotelFeatures());
    }

    private Hotel match(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(resultSet.getInt("hotel_id"));
        hotel.setHotelName(resultSet.getString("hotel_name"));
        hotel.setHotelCity(resultSet.getString("hotel_city"));
        hotel.setHotelRegion(resultSet.getString("hotel_region"));
        hotel.setHotelAddress(resultSet.getString("hotel_address"));
        hotel.setHotelMail(resultSet.getString("hotel_mail"));
        hotel.setHotelPhone(resultSet.getString("hotel_phone"));
        hotel.setHotelStar(resultSet.getInt("hotel_star"));
        hotel.setHotelFeatures(resultSet.getArray("hotel_features"));
        return hotel;
    }
}

package Dao;

import Core.Database;
import Entity.HotelFeatures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelFeaturesDao {
    private final Connection connection;

    public HotelFeaturesDao() {
        this.connection = Database.getInstance();
    }

    // Get feature list by hotel ID
    public ArrayList<HotelFeatures> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.hotel_features WHERE hotel_id = " + hotelId);
    }

    // Save hotel features
    public boolean save(HotelFeatures hotelFeatures) {
        String query = "INSERT INTO public.hotel_features " +
                "(" +
                "hotel_id, " +
                "feature_name" +
                ")" +
                " VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelFeatures.getHotelId());
            preparedStatement.setString(2, hotelFeatures.getFeatureName());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Private Methods that is used only in this class

    private ArrayList<HotelFeatures> selectByQuery(String query) {
        ArrayList<HotelFeatures> featureList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                featureList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return featureList;
    }

    private HotelFeatures match(ResultSet resultSet) throws SQLException {
        HotelFeatures hotelFeatures = new HotelFeatures();
        hotelFeatures.setFeatueId(resultSet.getInt("feature_id"));
        hotelFeatures.setHotelId(resultSet.getInt("hotel_id"));
        hotelFeatures.setFeatureName(resultSet.getString("feature_name"));
        return hotelFeatures;
    }
}

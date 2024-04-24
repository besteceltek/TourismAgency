package Dao;

import Core.Database;
import Entity.Pension;
import Entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionDao {
    private final Connection connection;

    public PensionDao() {
        this.connection = Database.getInstance();
    }

    public Pension getByID(int id) {
        Pension pension = null;
        String query = "SELECT * FROM public.pension WHERE pension_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pension = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pension;
    }

    public ArrayList<Pension> findAll() {
        return this.selectByQuery("SELECT * FROM public.pension ORDER BY pension_id ASC");
    }

    public ArrayList<Pension> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.pension WHERE hotel_id = " + hotelId);
    }

    public boolean save(Pension pension) {
        String query = "INSERT INTO public.pension " +
                "(" +
                "pension_type, " +
                "hotel_id" +
                ")" +
                " VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, pension.getPensionType());
            preparedStatement.setInt(2, pension.getHotelId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Private Methods

    private Pension match(ResultSet resultSet) throws SQLException {
        Pension pension = new Pension();
        pension.setPensionId(resultSet.getInt("pension_id"));
        pension.setPensionType(resultSet.getString("pension_type"));
        pension.setHotelId(resultSet.getInt("hotel_id"));
        return pension;
    }

    private ArrayList<Pension> selectByQuery(String query) {
        ArrayList<Pension> pensionList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                pensionList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionList;
    }
}

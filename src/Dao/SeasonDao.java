package Dao;

import Core.Database;
import Entity.Pension;
import Entity.Room;
import Entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonDao {
    private final Connection connection;

    public SeasonDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Season> findAll() {
        return this.selectByQuery("SELECT * FROM public.season ORDER BY season_id ASC");
    }

    public ArrayList<Season> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.season WHERE hotel_id = " + hotelId);
    }

    public Season getByID(int id) {
        Season season = null;
        String query = "SELECT * FROM public.season WHERE season_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                season = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return season;
    }

    public boolean save(Season season) {
        String query = "INSERT INTO public.season " +
                "(" +
                "hotel_id, " +
                "season_start_date, " +
                "season_end_date, " +
                "season_name" +
                ")" +
                " VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, season.getHotelId());
            preparedStatement.setDate(2, Date.valueOf(season.getSeasonStartDate()));
            preparedStatement.setDate(3, Date.valueOf(season.getSeasonEndDate()));
            preparedStatement.setString(4, season.getSeasonName());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Private Methods

    private Season match(ResultSet resultSet) throws SQLException {
        Season season = new Season();
        season.setSeasonId(resultSet.getInt("season_id"));
        season.setSeasonName(resultSet.getString("season_name"));
        season.setHotelId(resultSet.getInt("hotel_id"));
        season.setSeasonStartDate(LocalDate.parse(resultSet.getString("season_start_date")));
        season.setSeasonEndDate(LocalDate.parse(resultSet.getString("season_end_date")));
        return season;
    }

    private ArrayList<Season> selectByQuery(String query) {
        ArrayList<Season> pensionList = new ArrayList<>();
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

package Entity;

import Business.RoomManager;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Room {
    private int roomId, roomBed, roomArea, roomStock, hotelId, pensionId, seasonId;
    private String roomPriceAdult, roomPriceChild;
    private Array roomFeatures;
    private RoomType roomType;
    private Hotel hotel;
    private Pension pension;
    private Season season;

    public enum RoomType {
        Single,
        Double,
        JuniorSuite,
        Suite
    }

    // Getters & Setters

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomBed() {
        return roomBed;
    }

    public void setRoomBed(int roomBed) {
        this.roomBed = roomBed;
    }

    public int getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(int roomArea) {
        this.roomArea = roomArea;
    }

    public int getRoomStock() {
        return roomStock;
    }

    public void setRoomStock(int roomStock) {
        this.roomStock = roomStock;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getPensionId() {
        return pensionId;
    }

    public void setPensionId(int pensionId) {
        this.pensionId = pensionId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getRoomPriceAdult() {
        return roomPriceAdult;
    }

    public void setRoomPriceAdult(String roomPriceAdult) {
        this.roomPriceAdult = roomPriceAdult;
    }

    public String getRoomPriceChild() {
        return roomPriceChild;
    }

    public void setRoomPriceChild(String roomPriceChild) {
        this.roomPriceChild = roomPriceChild;
    }

    public Array getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(Array roomFeatures) {
        this.roomFeatures = roomFeatures;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    // To String method for room features list
    public String getRoomFeaturesToString() {
        String str = "";
        ResultSet rs = null;
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            rs = this.getRoomFeatures().getResultSet();
            while (rs.next()) {
                arrayList.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String feature : arrayList) {
            str += "- " + feature + '\n';
        }

        return str;
    }
}

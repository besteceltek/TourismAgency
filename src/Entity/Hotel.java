package Entity;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Hotel {
    private int hotelId, hotelStar;
    private String hotelName, hotelCity, hotelRegion, hotelAddress, hotelMail, hotelPhone;
    private Array hotelFeatures;

    public Hotel() {
    }

    public Hotel(int hotelId, int hotelStar, String hotelName, String hotelCity, String hotelRegion, String hotelAddress, String hotelMail, String hotelPhone, Array hotelFeatures) {
        this.hotelId = hotelId;
        this.hotelStar = hotelStar;
        this.hotelName = hotelName;
        this.hotelCity = hotelCity;
        this.hotelRegion = hotelRegion;
        this.hotelAddress = hotelAddress;
        this.hotelMail = hotelMail;
        this.hotelPhone = hotelPhone;
        this.hotelFeatures = hotelFeatures;
    }

    // Getters & Setters

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(int hotelStar) {
        this.hotelStar = hotelStar;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public String getHotelRegion() {
        return hotelRegion;
    }

    public void setHotelRegion(String hotelRegion) {
        this.hotelRegion = hotelRegion;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelMail() {
        return hotelMail;
    }

    public void setHotelMail(String hotelMail) {
        this.hotelMail = hotelMail;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    public Array getHotelFeatures() {
        return hotelFeatures;
    }

    public void setHotelFeatures(Array hotelFeatures) {
        this.hotelFeatures = hotelFeatures;
    }

    // ToString method for hotel features list
    public String getHotelFeaturesToString() {
        String str = "";
        ResultSet rs = null;
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            rs = this.getHotelFeatures().getResultSet();
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

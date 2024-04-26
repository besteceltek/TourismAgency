package Entity;

import Core.ComboItem;

public class Pension {
    private int pensionId, hotelId;
    private String pensionType;
    private String[] pensionTypes = {
            "Ultra All Inclusive",
            "All Inclusive",
            "Room Breakfast",
            "Full Pension",
            "Half Pension",
            "Just Bed",
            "Excluding Alcohol Full Credit"
    };

    public Pension() {
    }

    public Pension(int pensionId, int hotelId, String pensionType) {
        this.pensionId = pensionId;
        this.hotelId = hotelId;
        this.pensionType = pensionType;
    }

    // Getters & Setters

    public int getPensionId() {
        return pensionId;
    }

    public void setPensionId(int pensionId) {
        this.pensionId = pensionId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getPensionType() {
        return pensionType;
    }

    public void setPensionType(String pensionType) {
        this.pensionType = pensionType;
    }

    // Combo Item for pension type
    public ComboItem getComboItem() {
        return new ComboItem(this.getPensionId(), this.getPensionType());
    }
}

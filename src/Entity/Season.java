package Entity;

import Core.ComboItem;

import java.sql.Date;
import java.time.LocalDate;

public class Season {
    private int seasonId, hotelId;
    private String seasonName;
    private LocalDate seasonStartDate, seasonEndDate;

    // Getters & Setters

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public LocalDate getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonStartDate(LocalDate seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public LocalDate getSeasonEndDate() {
        return seasonEndDate;
    }

    public void setSeasonEndDate(LocalDate seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    // Combo Item for season name
    public ComboItem getComboItem() { return new ComboItem(this.getSeasonId(), this.getSeasonName()); }

    }

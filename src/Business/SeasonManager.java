package Business;

import Core.Helper;
import Dao.SeasonDao;
import Entity.Pension;
import Entity.Season;

import java.util.ArrayList;

public class SeasonManager {
    private SeasonDao seasonDao;

    public SeasonManager() {
        this.seasonDao = new SeasonDao();
    }

    public ArrayList<Season> findAll() { return this.seasonDao.findAll(); }

    public ArrayList<Season> getByListHotelId(int hotelId) { return this.seasonDao.getByListHotelId(hotelId); }

    public Season getByID(int id) {
        return this.seasonDao.getByID(id);
    }

    public boolean save(Season season) {
        if (season.getSeasonId() != 0) {
            Helper.showMessage("sezon kaydedilemedi");
        }
        return this.seasonDao.save(season);
    }
}

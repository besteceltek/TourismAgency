package Business;

import Core.Helper;
import Dao.HotelFeaturesDao;
import Entity.HotelFeatures;

import java.util.ArrayList;

public class HotelFeaturesManager {
    private final HotelFeaturesDao hotelFeaturesDao;

    public HotelFeaturesManager() {
        this.hotelFeaturesDao = new HotelFeaturesDao();
    }

    public ArrayList<HotelFeatures> getByListHotelId(int hotelId) {
        return this.hotelFeaturesDao.getByListHotelId(hotelId);
    }

    public boolean save(HotelFeatures hotelFeatures) {
        if (hotelFeatures.getFeatueId() != 0) {
            Helper.showMessage("pension hata");
        }
        return this.hotelFeaturesDao.save(hotelFeatures);
    }
}

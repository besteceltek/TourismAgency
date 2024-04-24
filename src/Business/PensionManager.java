package Business;

import Core.Helper;
import Dao.PensionDao;
import Entity.Pension;
import Entity.Room;

import java.util.ArrayList;

public class PensionManager {
    private PensionDao pensionDao;

    public PensionManager() {
        this.pensionDao = new PensionDao();
    }

    public ArrayList<Pension> findAll() {
        return this.pensionDao.findAll();
    }

    public Pension getByID(int id) {
        return this.pensionDao.getByID(id);
    }

    public ArrayList<Pension> getByListHotelId(int hotelId) {
        return this.pensionDao.getByListHotelId(hotelId);
    }

    public boolean save(Pension pension) {
        if (pension.getPensionId() != 0) {
            Helper.showMessage("pension hata");
        }
        return this.pensionDao.save(pension);
    }
}

package viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.PlaceDAO;
import database.dao.WaterBalanceDAO;
import database.entities.Place;
import database.entities.WaterBalance;

public class WaterBalanceViewModel extends AndroidViewModel {
    private WaterBalanceDAO wbDAO;
    private LiveData<List<WaterBalance>> allData;
    private LiveData<WaterBalance> wbAt;

    public WaterBalanceViewModel(Application application) {
        super(application);
        Database database = Database.getDatabase(application);
        wbDAO = database.waterBalanceDao();
        allData = wbDAO.getAllWaterBalances();
    }

    public LiveData<List<WaterBalance>> getAllData() {
        return allData;
    }

    public LiveData<WaterBalance> getWbAt(long id)
    {
        wbAt = wbDAO.getWbAt(id);
        return wbAt;
    }

    public void insert(WaterBalance wb)
    {
        wbDAO.insert(wb);
    }

    public void update(WaterBalance wb)
    {
        wbDAO.update(wb);
    }

    public void delete(WaterBalance wb)
    {
        wbDAO.delete(wb);
    }
}

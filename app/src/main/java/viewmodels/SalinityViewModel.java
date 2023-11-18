package viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.PlaceDAO;
import database.dao.SalinityDAO;
import database.entities.Place;
import database.entities.Salinity;

public class SalinityViewModel extends AndroidViewModel {
    private SalinityDAO salinityDAO;
    private LiveData<List<Salinity>> allData;
    private LiveData<Salinity> salinityAt;

    public SalinityViewModel(Application application) {
        super(application);
        Database database = Database.getDatabase(application);
        salinityDAO = database.salinityDao();
        allData = salinityDAO.getAllSalinities();
    }

    public LiveData<List<Salinity>> getAllData() {
        return allData;
    }

    public LiveData<Salinity> getSalinityAt(long id)
    {
        salinityAt = salinityDAO.getSalinityAt(id);
        return salinityAt;
    }

    public void insert(Salinity salinity)
    {
        salinityDAO.insert(salinity);
    }

    public void update(Salinity salinity)
    {
        salinityDAO.update(salinity);
    }

    public void delete(Salinity salinity)
    {
        salinityDAO.delete(salinity);
    }
}

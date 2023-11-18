package viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.PlaceDAO;
import database.entities.Place;

public class PlaceViewModel extends AndroidViewModel {
    private PlaceDAO placeDAO;
    private LiveData<List<Place>> allData;
    private LiveData<Place> placeAt;

    public PlaceViewModel(Application application) {
        super(application);
        Database database = Database.getDatabase(application);
        placeDAO = database.placeDao();
        allData = placeDAO.getAllPlaces();
    }

    public LiveData<List<Place>> getAllData() {
        return allData;
    }

    public LiveData<Place> getPlaceAt(long id)
    {
        placeAt = placeDAO.getPlaceAt(id);
        return placeAt;
    }

    public void insert(Place place)
    {
        placeDAO.insert(place);
    }

    public void update(Place place)
    {
        placeDAO.update(place);
    }

    public void delete(Place place)
    {
        placeDAO.delete(place);
    }
}

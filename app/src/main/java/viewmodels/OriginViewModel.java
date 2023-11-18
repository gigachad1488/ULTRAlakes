package viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import database.Database;
import database.dao.OriginDAO;
import database.entities.Origin;
import kotlinx.coroutines.Dispatchers;

public class OriginViewModel extends AndroidViewModel {
    private OriginDAO originDAO;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<Origin>> allData = new MutableLiveData<>();
    private LiveData<Origin> originAt;

    Database db;
    public OriginViewModel(Application application) {
        super(application);
        db = Database.getDatabase(application);
        originDAO = db.originDao();
        loadData();
    }
    

    public LiveData<List<Origin>> getAllData() {
        return allData;
    }

    private void loadData() {
        isLoading.setValue(true);

        getAllOrigins().observeForever(yourEntities -> {
            allData.postValue(yourEntities);
            isLoading.postValue(false);
        });
    }

    public LiveData<List<Origin>> getAllOrigins() {
        MutableLiveData<List<Origin>> mutableLiveData = new MutableLiveData<>();
        Database.databaseWriteExecutor.execute(() -> {
            List<Origin> yourEntities = originDAO.getAllOrigins();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mutableLiveData.postValue(yourEntities);
        });

        return mutableLiveData;
    }
    public LiveData<Origin> getOriginAt(long id)
    {
      originAt = originDAO.getOriginAt(id);
      return  originAt;
    }

    public void insert(Origin origin)
    {
        originDAO.insert(origin);
        loadData();
    }

    public void update(Origin origin)
    {
        originDAO.update(origin);
        loadData();
    }

    public void delete(Origin origin)
    {
        originDAO.delete(origin);
        loadData();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}

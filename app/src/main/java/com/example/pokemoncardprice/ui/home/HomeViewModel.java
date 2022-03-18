package com.example.pokemoncardprice.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenue dans cette application qui permettra de mettre en évidence le cours des cartes pokemon.");
    }

   public LiveData<String> getText() {
        return mText;
    }

}
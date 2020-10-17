package com.example.logger.ui.syncdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SyncDataViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SyncDataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Syncing Data from server");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
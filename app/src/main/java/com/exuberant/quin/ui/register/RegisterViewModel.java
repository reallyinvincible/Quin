package com.exuberant.quin.ui.register;

import android.accounts.Account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.data.repository.UserRepository;

class RegisterViewModel extends ViewModel {

    private UserRepository repository;

    private MutableLiveData<Account> accountAdded = new MutableLiveData<>();

    public RegisterViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void addAccount(User user) {
        accountAdded.postValue(repository.addAccount(user));
    }

    public LiveData<Account> accountAdded() {
        return accountAdded;
    }

}

package com.exuberant.quin.ui.home;

import android.accounts.Account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.data.repository.UserRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private UserRepository repository;

    private MutableLiveData<List<User>> loggedInUsers = new MutableLiveData<>();
    private MutableLiveData<List<Account>> loggedInAccounts = new MutableLiveData<>();

    public HomeViewModel(UserRepository repository) {
        this.repository = repository;
        repository.getUsers().observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                loggedInUsers.postValue(users);
            }
        });
        repository.getAccounts().observeForever(new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                loggedInAccounts.postValue(accounts);
            }
        });
    }

    public LiveData<List<User>> getAllLoggedInUsers(){
        return loggedInUsers;
    }

    public LiveData<List<Account>> getAllLoggedInAccounts(){
        return loggedInAccounts;
    }

    public void removeUser(User user) {
        repository.removeUser(user);
    }

    public void removeAccount(Account account){
        repository.removeAccount(account);
    }

}

package com.exuberant.quin.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.data.repository.UserRepository;

import java.util.List;

public class AuthViewModel extends ViewModel {

    private UserRepository repository;

    private MutableLiveData<List<User>> loggedInUsers = new MutableLiveData<>();

    public AuthViewModel(UserRepository repository) {
        this.repository = repository;
        repository.getUsers().observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                loggedInUsers.postValue(users);
            }
        });
    }

    public LiveData<List<User>> getAllLoggedInUsers(){
        return loggedInUsers;
    }

    public void removeUser(User user) {
        repository.removeUser(user);
    }
}

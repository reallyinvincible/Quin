package com.exuberant.quin.data.repository;

import android.accounts.Account;

import androidx.lifecycle.LiveData;

import com.exuberant.quin.data.db.entity.User;

import java.util.List;

public interface UserRepository {

    LiveData<List<Account>> getAccounts();

    LiveData<List<User>> getUsers();

    Account addAccount(User user);

    void removeUser(User user);

    void removeAccount(Account account);

}

package com.exuberant.quin.data.repository;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.exuberant.quin.R;
import com.exuberant.quin.data.db.UserDatabase;
import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.internal.services.executors.AppExecutors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.ACCOUNT_SERVICE;

public class UserRepositoryImpl implements UserRepository {

    private Context context;
    private UserDatabase userDatabase;

    private MutableLiveData<List<User>> loggedInUsers = new MutableLiveData<>();
    private MutableLiveData<List<Account>> loggedInAccounts = new MutableLiveData<>();

    public UserRepositoryImpl(Context context) {
        this.context = context;
        this.userDatabase = UserDatabase.getInstance(context);
    }

    @Override
    public LiveData<List<Account>> getAccounts() {
        List<Account> accountList = new ArrayList<>();
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();
        accountList = Arrays.asList(accounts);
        loggedInAccounts.postValue(accountList);
        return loggedInAccounts;
    }

    @Override
    public LiveData<List<User>> getUsers() {
        fetchAllUsers();
        return loggedInUsers;
    }

    private void fetchAllUsers() {
        userDatabase.userDao().getAllUsers().observe((LifecycleOwner) context, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                loggedInUsers.postValue(users);
            }
        });
    }


    @Override
    public Account addAccount(User user) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(user.getName(), context.getString(R.string.account_type));
        final Bundle bundle = new Bundle();
        bundle.putString("phone", user.getPhoneNumber());
        bundle.putString("email", user.getEmail());
        boolean success = accountManager.addAccountExplicitly(account, user.getHashedPassword(), bundle);
        addToDatabase(user);
        if (success){
            return account;
        } else {
            return null;
        }
    }

    private void addToDatabase(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDao().insertUser(user);
            }
        });
    }

    @Override
    public void removeUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDao().deleteUser(user.getId());
            }
        });
    }

    @Override
    public void removeAccount(Account account) {
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        accountManager.removeAccount(account, null, null);
    }
}

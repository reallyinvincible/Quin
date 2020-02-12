package com.exuberant.quin.data.model;

import android.accounts.Account;

public class AccountWrapper {

    private Account account;

    public AccountWrapper(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

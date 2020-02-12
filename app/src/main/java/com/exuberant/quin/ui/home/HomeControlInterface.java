package com.exuberant.quin.ui.home;

import android.accounts.AccountManager;

public interface HomeControlInterface {

    HomeViewModel getViewModel();

    AccountManager getAccountManager();

}

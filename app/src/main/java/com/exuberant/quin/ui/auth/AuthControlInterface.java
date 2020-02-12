package com.exuberant.quin.ui.auth;

public interface AuthControlInterface {

    void switchToLogin();

    void switchToSignup();

    void showSnackbar(String message, int value);

    void hideKeyboard();

    void startLoading();

    void stopLoading();

    AuthViewModel getViewModel();

}

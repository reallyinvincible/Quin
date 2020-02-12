package com.exuberant.quin.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.exuberant.quin.data.repository.UserRepository;

public class AuthViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UserRepository repository;

    public AuthViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AuthViewModel viewModel = new AuthViewModel(repository);
        return ((T) viewModel);
    }

}

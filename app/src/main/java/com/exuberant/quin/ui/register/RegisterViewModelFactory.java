package com.exuberant.quin.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.exuberant.quin.data.repository.UserRepository;

public class RegisterViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UserRepository repository;

    public RegisterViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        RegisterViewModel viewModel = new RegisterViewModel(repository);
        return ((T) viewModel);
    }
}

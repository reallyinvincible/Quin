package com.exuberant.quin.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.exuberant.quin.data.repository.UserRepository;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UserRepository repository;

    public HomeViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        HomeViewModel viewModel = new HomeViewModel(repository);
        return ((T) viewModel);
    }
    
}

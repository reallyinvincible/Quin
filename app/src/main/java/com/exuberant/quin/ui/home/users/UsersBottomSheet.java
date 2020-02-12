package com.exuberant.quin.ui.home.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.exuberant.quin.R;
import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.ui.home.HomeActivity;
import com.exuberant.quin.ui.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class UsersBottomSheet extends BottomSheetDialogFragment {

    private TextView emptyTextView;
    private RecyclerView authUserRecyclerView;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_authenticated, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        emptyTextView = view.findViewById(R.id.tv_empty);
        authUserRecyclerView = view.findViewById(R.id.rv_authenticated_users);
        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        authUserRecyclerView.setLayoutAnimation(animation);
        viewModel = HomeActivity.getControlInterface().getViewModel();
        viewModel.getAllLoggedInUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size() > 0) {
                    emptyTextView.setVisibility(View.GONE);
                    UsersAdapter adapter = new UsersAdapter(users);
                    authUserRecyclerView.setAdapter(adapter);
                } else {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}

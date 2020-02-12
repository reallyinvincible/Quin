package com.exuberant.quin.ui.auth.authenticated;

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
import com.exuberant.quin.ui.auth.AuthActivity;
import com.exuberant.quin.ui.auth.AuthViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class AuthenticatedBottomSheet extends BottomSheetDialogFragment {

    private TextView emptyTextView;
    private RecyclerView authUserRecyclerView;
    private AuthViewModel viewModel;

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
        viewModel = AuthActivity.getFragmentControlInterface().getViewModel();
        viewModel.getAllLoggedInUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size() > 0) {
                    emptyTextView.setVisibility(View.GONE);
                    AuthenticatedUserAdapter adapter = new AuthenticatedUserAdapter(users, getContext());
                    authUserRecyclerView.setAdapter(adapter);
                } else {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

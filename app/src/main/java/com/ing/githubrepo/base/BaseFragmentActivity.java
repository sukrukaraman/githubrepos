package com.ing.githubrepo.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * Created by karamans on 13.02.2020.
 */

public class BaseFragmentActivity extends AppCompatActivity {

    int trans = FragmentTransaction.TRANSIT_FRAGMENT_FADE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void changeScreen(Intent intent, int enterAnim, int exitAnim) {
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    protected void changeScreenForResult(Intent intent, int requestCode, int enterAnim, int exitAnim) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(enterAnim, exitAnim);
    }

    protected void add(@NonNull FragmentManager fragmentManager, @IdRes int layout, @NonNull Fragment newFragment, String name, boolean isAddToStack) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(layout, newFragment);

        if (isAddToStack) {
            transaction.addToBackStack(name);
        }

        transaction.setTransition(trans).commit();

    }

    protected void replace(@NonNull FragmentManager fragmentManager, int layout, @NonNull Fragment newFragment, boolean isAddToStack) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, newFragment);

        if (isAddToStack) {
            transaction.addToBackStack(newFragment.getClass().getSimpleName());
        }
        transaction.setTransition(trans).commit();

    }

    protected void add(FragmentManager fragmentManager, int layout, Fragment newFragment, boolean isAddToStack) {
        add(fragmentManager, layout, newFragment, null, isAddToStack);
    }

    protected void setTransition(int transition) {
        trans = transition;
    }

    protected void clearBackStack(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        int s = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < s; i++) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager() == null) {
            return;
        }

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count < 2) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}

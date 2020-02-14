package com.ing.githubrepo.base;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ing.githubrepo.R;


/**
 * Created by karamans on 13.02.2020.
 */
public class BaseActivity extends BaseFragmentActivity {

    private void replaceFragment(FragmentManager fragmentManager, Fragment fragment, boolean isAddToBackStack) {
        super.replace(fragmentManager, R.id.content_frame, fragment, isAddToBackStack);
    }

    private void addFragment(FragmentManager fragmentManager, Fragment fragment, boolean isAddToBackStack) {
        super.add(fragmentManager, R.id.content_frame, fragment, isAddToBackStack);
    }

    public void changeScreen(Intent intent) {
        super.changeScreen(intent, R.anim.fade_in, R.anim.fade_out);
    }

    public void replaceFragment(Fragment fragment) {
        replaceFragment(getSupportFragmentManager(), fragment, true);
    }

}

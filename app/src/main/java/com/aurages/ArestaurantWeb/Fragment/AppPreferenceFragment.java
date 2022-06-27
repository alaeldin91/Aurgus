package com.aurages.ArestaurantWeb.Fragment;

import android.os.Bundle;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.aurages.ArestaurantWeb.R;

public abstract class AppPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(
                ContextCompat.getColor(getContext(), R.color.background_material_light));

    }

    @Override
    protected void onBindPreferences() {
        super.onBindPreferences();
    }
}

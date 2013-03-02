package com.example.alexfed.myweight;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class MyPreferences extends PreferenceActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            
            PreferenceScreen screen = getPreferenceScreen();
            Preference prefViewportStart = (Preference) findPreference("prefViewportStart");
            Preference prefViewportSize = (Preference) findPreference("prefViewportSize");
            
            screen.removePreference(prefViewportSize);
            screen.removePreference(prefViewportStart);
    }
}

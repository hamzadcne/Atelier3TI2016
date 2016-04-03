package com.example.lvsc.tp3ti;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class ConfigActivity extends AppCompatPreferenceActivity{

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ConfigFragment()).commit();
        setupActionBar();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    private Preference.OnPreferenceChangeListener bindSummaryToValueListner =new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue=newValue.toString();
            if(preference instanceof ListPreference)
            {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                 CharSequence summary=(index >= 0
                        ? listPreference.getEntries()[index]
                        : null);
                preference.setSummary(summary);
            }
            else
            {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ConfigFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.config);


            ConfigActivity activity = (ConfigActivity)getActivity();
            Preference.OnPreferenceChangeListener onPreferenceChangeListener =
                    activity.bindSummaryToValueListner;

            Preference searchPref=findPreference(getString(R.string.search_distance_pref));
            Preference languagePref=findPreference(getString(R.string.app_language_pref));

            searchPref.setOnPreferenceChangeListener(onPreferenceChangeListener);
            languagePref.setOnPreferenceChangeListener(onPreferenceChangeListener);

            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(searchPref.getContext());
            String newSearchValue = preferences.getString(getString(R.string.search_distance_pref), getString(R.string.search_distance_default_value));
            onPreferenceChangeListener.onPreferenceChange(searchPref, newSearchValue);

            String newLanguageValue = preferences.getString(getString(R.string.app_language_pref),getString(R.string.language_default_value));
            onPreferenceChangeListener.onPreferenceChange(languagePref, newLanguageValue);


        }
    }
}

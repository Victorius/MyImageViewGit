package com.mftech.mindmap.settingsactivites;


import java.util.Locale;

import com.mftech.mindmap.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class MainPrefFragment extends PreferenceFragment {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Locale.getDefault().getDisplayLanguage().equals("English")){
        	addPreferencesFromResource(R.xml.englishsetting);
        }else{
        	addPreferencesFromResource(R.xml.setting);
        }
        
        MainPreferanceActivity.listPref = (ListPreference)getPreferenceScreen().findPreference("setting");
    }
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	Preference pref = findPreference(key);
    	System.out.println(key);
        if (key.equals("setting")) {
        	MainPreferanceActivity.listPref.setSummary("Current value is " + MainPreferanceActivity.listPref.getEntry().toString()); 
        }
    }
}

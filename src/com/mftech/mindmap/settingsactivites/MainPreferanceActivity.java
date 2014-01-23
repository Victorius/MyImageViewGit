package com.mftech.mindmap.settingsactivites;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

public class MainPreferanceActivity extends PreferenceActivity{
	public static ListPreference listPref;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPrefFragment() ).commit();
	}
}

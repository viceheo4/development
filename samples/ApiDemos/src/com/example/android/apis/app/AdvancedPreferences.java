/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 2. Launching Preferences-> AdvancedPreferences
 * 과정A
 * 2. Launching Preferences에서 button을 click하기전에 TextView에 Counter 값이 출력된다.
 * 이때 default 값을 가져오기 위해서 PreferenceManager.setDefaultValues(R.xml.advanced_preferences)를 
 * 해주게 되면 advanced_preferences.xml 에 정의된 항목을 access할수 있게 되어 초기값을 가져올수 있게 된다.
 * 즉, preference activity를 실행하기전에 미리 preference값을 가져올수 있는것이다 
 *과정B 
 * 이후 실제 onCreate에서 advanced_preferences.xml을 inplate하여 사용자의 눈에 보여주게되고
 * 여기서 preference 와 Textview에 checkbox가 달린 list을 보여주게 되는데, 
 * checkbox 경우 advanced_preferences.xml에서 해당 field를 읽어와 code에서 주기적으로
 * checkbox를 en/disable 한다.

 *   KJK_TALK APIDEMOS: App-> Preferences-> 6. AdvancedPreferences 과정B만 하게 된다.    
 
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.app;

import com.example.android.apis.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.CheckBoxPreference;
import android.widget.Toast;

/**
 * Example that shows finding a preference from the hierarchy and a custom preference type.
 */// KJK_TALK: Preference를 읽어오기 위해 PreferenceActivity를 상속했다.
 // 여기에 preference가 변경할때마다 호출하기위해 OnSharedPreferenceChangeListener를 달았다.
public class AdvancedPreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    public static final String KEY_MY_PREFERENCE = "my_preference";
    public static final String KEY_ADVANCED_CHECKBOX_PREFERENCE = "advanced_checkbox_preference";

    private CheckBoxPreference mCheckBoxPreference;
    //KJK_CHECK: Handler: Handler를 만들었다는 것은 현재 msg에 대해 handler를 별도로 두겠다는 뜻!!
    // 이렇게 만들어진 handler는 현재 process의 Looper에 자동적으로 연결된다.
    private Handler mHandler = new Handler();
    
    /**
     * This is a simple example of controlling a preference from code.
     */
    private Runnable mForceCheckBoxRunnable = new Runnable() {
        public void run() {
            if (mCheckBoxPreference != null) {
                //주기적으로 checkbox를 check/uncheck한다.
                mCheckBoxPreference.setChecked(!mCheckBoxPreference.isChecked());
            }
            
            // Force toggle again in a second
            // KJK_TALK: 현재 runnable이 mHandler가 동작하는 thread(현재 thread)에서 해당 시간 이후에 
            // msg.callback으로 등록되어 thread가 아닌 일반 함수 call로 동작하도록 만든다.
            // 즉 thread로 동작하지 않고 msg handler로 동작한다.
            mHandler.postDelayed(this, 1000);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the XML preferences file, preference.xml을 읽어와
        addPreferencesFromResource(R.xml.advanced_preferences);
        
        // Get a reference to the checkbox preference
        //KJK_TALK:preference.xml에서"advanced_checkbox_preference" 문자열을 찾아 checkbox의 setting을 읽어온다.
        //ROM에서 가져오는것이 아님. 
        //일반적으로 무조건 xml에서 가져오는데, 사용자 정의에 의해 ROM에 실제 xml로 된 preference를 저장한후 
        //가져올수 있다.
        mCheckBoxPreference = (CheckBoxPreference)getPreferenceScreen().findPreference(
                KEY_ADVANCED_CHECKBOX_PREFERENCE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start the force toggle, thread.start가 아닌 일반 함수call로 실행
        mForceCheckBoxRunnable.run();
        
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        
        mHandler.removeCallbacks(mForceCheckBoxRunnable);
    }

    //preference가 변경할때마다 호출된다.
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Let's do something when my counter preference value changes
        if (key.equals(KEY_MY_PREFERENCE)) {
            Toast.makeText(this, "Thanks! You increased my count to "
                    + sharedPreferences.getInt(key, 0), Toast.LENGTH_SHORT).show();
        }
    }
    
}

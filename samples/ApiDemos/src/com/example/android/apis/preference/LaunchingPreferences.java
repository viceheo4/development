/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 2. Launching Preferences
 * 다른 activity에 정의된 preference를 읽어서 뿌려준다.
 setting을 구성할때 static한 preference.xml로 부터 값을 읽어올수 있는데 
 이 예제에서는 그러한 xml에 정의된 값을 미리 읽어오고 있다.  

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

package com.example.android.apis.preference;

import com.example.android.apis.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * Demonstrates launching a PreferenceActivity and grabbing a value it saved.
 */
public class LaunchingPreferences extends Activity implements OnClickListener {

    private static final int REQUEST_CODE_PREFERENCES = 1;

    private TextView mCounterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * These preferences have defaults, so before using them go apply those
         * defaults.  This will only execute once -- when the defaults are applied
         * a boolean preference is set so they will not be applied again.
         */
        //KJK_TALK: preference를 설정하는 advanced_preferences.xml에서 정의한 data를 읽어오는 부분으
        //main act에서 sub act의 default값을 획득하기 위해 읽어온다.
        //이 data는 preference로 사용자 정의에 의해 실제 rom에 저장되게 된다.
        //readAgain이 false이므로 최초 한번만 loading한다. true는 다시 default값을 매번 읽어오게 한다.
        PreferenceManager.setDefaultValues(this, R.xml.advanced_preferences, false);

        // Simple layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        // Create a simple button that will launch the preferences
        Button launchPreferences = new Button(this);
        launchPreferences.setText(getString(R.string.launch_preference_activity));
        //button에 setOnClickListener을 단다. 
        //KJK_TALK: 현재 this가 OnClickListener를 implements하였으며, OnClickListener는 
        //onClick을 method가 존재하면 된다. 아래 참조.
        launchPreferences.setOnClickListener(this);
        //button을 layout에 달되 이때 layoutparam을 생성하여 위치를 조정한다. 
        layout.addView(launchPreferences, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        //textview를 단다.
        mCounterText = new TextView(this);
        layout.addView(mCounterText, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        updateCounterText();
    }

    public void onClick(View v) {

        // When the button is clicked, launch an activity through this intent
        Intent launchPreferencesIntent = new Intent().setClass(this, AdvancedPreferences.class);

        // Make it a subactivity so we know when it returns
        startActivityForResult(launchPreferencesIntent, REQUEST_CODE_PREFERENCES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // The preferences returned if the request code is what we had given
        // earlier in startSubActivity
        if (requestCode == REQUEST_CODE_PREFERENCES) {
            // Read a sample value they have set
            updateCounterText();
        }
    }

    private void updateCounterText() {
        // Since we're in the same package, we can use this context to get
        // the default shared preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //KJK_TALK: 위에서 PreferenceManager.setDefaultValues로 advanced_preferences를 읽어왓으니, 
        //xml에 기록된 Rom에 저장된 data를 사용할수 있게된다. 
        //AdvancedPreferences.KEY_MY_PREFERENCE 에는 defaultValue 값이 저장되어 있다.
        final int counter = sharedPref.getInt(AdvancedPreferences.KEY_MY_PREFERENCE, 0);
        //KJK_TALK: 호출할 activity가 저장해논 값을 preference에서 읽어와 현재 화면에 뿌려준다.
        mCounterText.setText(getString(R.string.counter_value_is) + " " + counter);
    }
}

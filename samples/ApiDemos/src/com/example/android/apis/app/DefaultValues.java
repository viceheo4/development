/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 4. Default Values
* 역시 마찬가지로 1. Preferences from XML 예제와 완전히 code는 같으나 xml 파일이 다르다. 
 * 즉, 단순히 preference가 기록된 xml을 inflate시키게 되는데, default value가 존재하는 xml이 되겠다. 
  * xml 참조

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

import com.example.android.apis.ApiDemosApplication;
import com.example.android.apis.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * This activity is an example of a simple settings screen that has default
 * values.
 * <p>
 * In order for the default values to be populated into the
 * {@link SharedPreferences} (from the preferences XML file), the client must
 * call
 * {@link PreferenceManager#setDefaultValues(android.content.Context, int, boolean)}.
 * <p>
 * This should be called early, typically when the application is first created.
 * This ensures any of the application's activities, services, etc. will have
 * the default values present, even if the user has not wandered into the
 * application's settings. For ApiDemos, this is {@link ApiDemosApplication},
 * and you can find the call to
 * {@link PreferenceManager#setDefaultValues(android.content.Context, int, boolean)}
 * in its {@link ApiDemosApplication#onCreate() onCreate}.
 */
public class DefaultValues extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.default_values);
    }

}

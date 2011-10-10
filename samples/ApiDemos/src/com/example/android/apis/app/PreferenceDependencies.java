/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 3. Preference dependencies
 * 1. Preferences from XML 예제와 완전히 code는 같으나 xml 파일이 다르다. 
 * 즉, 단순히 preference가 기록된 xml을 inflate시키게 되는데, dependency가 존재하는 xml이 되겠다. 


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

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceDependencies extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preference_dependencies);
    }

}

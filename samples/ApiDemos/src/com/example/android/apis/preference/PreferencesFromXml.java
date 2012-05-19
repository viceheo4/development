/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 1. Preferences from XML
   preferences.xml에 기술된대로 UI를 구성한다.
   이는 Settings에서 사용될 UI 구성을 일컫는 전형적인 xml을 말한다.
   ApiDemos분석.xls 파일 참고


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

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesFromXml extends PreferenceActivity { //KJK_TALK: PreferenceActivity 를 상속

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}

/*
 * KJK_TALK APIDEMOS: App-> Intents NEEDTOSTUDY
 * 특정한 intent를 만들고 그 intent를 처리할수 있는 candidate application을
 * 선택할수 있는 popup으로 띄워 user가 고르면 start시킨다.
 * 이런 popup은 ACTION_CHOOSER intent action으로 띄우게 된다.

 * Copyright (C) 2008 The Android Open Source Project
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Intents extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intents);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.get_music);
        button.setOnClickListener(mGetMusicListener);
    }

    private OnClickListener mGetMusicListener = new OnClickListener() {
        public void onClick(View v) {
            //Intent의 용법중에 ACTION_GET_CONTENT로 설정하여
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //관련 type을 다룰수 있는 app를 launch할수 잇도록 한다.
            intent.setType("audio/*");
            //제목과 audio type을 다루는 activity를 실행해라 
            //Select music을 제목으로 한 dialog을 launch다.
            //intentaction으로 ACTION_CHOOSER를 넣는다.
            startActivity(Intent.createChooser(intent, "Select music"));
        }
    };
}

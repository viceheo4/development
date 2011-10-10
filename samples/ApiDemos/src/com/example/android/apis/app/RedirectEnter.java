/*
 * KJK_TALK APIDEMOS: App-> Activity-> Redirection
act A-> B-> C가 있는데, B를 사용자의 눈에 보여주지 않고, 바로 C로 가는 효과를  나타내는 예제로 
만약 C에서 어떤 값을 insert 한다면 B가 생성되어 돌아갈때 B를 거쳐서 돌아가도록 하고, 
 그냥 back key로 취소한다면 C-> A로 바로 가도록 구성하였다.
 

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

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * Entry into our redirection example, describing what will happen.
 */
public class RedirectEnter extends Activity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.redirect_enter);

        // Watch for button clicks.
        Button goButton = (Button)findViewById(R.id.go);
        goButton.setOnClickListener(mGoListener);
    }

    private OnClickListener mGoListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            // Here we start up the main entry point of our redirection
            // example.
            Intent intent = new Intent(RedirectEnter.this, RedirectMain.class);
            startActivity(intent);
            //KJK_TALK: finish()하지 않으므로 act stack에 존재한다.
        }
    };
}


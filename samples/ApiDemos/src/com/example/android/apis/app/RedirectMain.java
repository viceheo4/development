/*
 * KJK_TALK APIDEMOS: App-> Activity-> Redirection ->RedirectMain.java
 * act A-->B-->C 순으로 호출될때, 처음실행만 A-->C로 실행되는것처럼 보이는 예제
 * 참고로 default 값을 보여주는 UI에서 처음값이 없으면 바로 설정화면(act C)으로 가고
 * 잇으면 현재 설정값(act B)를 보여주는 시나리오이다.


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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Entry into our redirection example, describing what will happen.
 */
public class RedirectMain extends Activity {
    static final int INIT_TEXT_REQUEST = 0;
    static final int NEW_TEXT_REQUEST = 1;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.redirect_main);

        // Watch for button clicks.
        Button clearButton = (Button)findViewById(R.id.clear);
        clearButton.setOnClickListener(mClearListener);
        Button newButton = (Button)findViewById(R.id.newView);
        newButton.setOnClickListener(mNewListener);

        // Retrieve the current text preference.  If there is no text
        // preference set, we need to get it from the user by invoking the
        // activity that retrieves it.  To do this cleanly, we will
        // temporarily hide our own activity so it is not displayed until the
        // result is returned.
        // KJK_TALK: onCreate가 끝나야 현재 act가 보이게 되는데, onCreate에서 다른 act를 호출하므로
        // 현재 act는 stack에는 들어가지만, 곧바로 두번재 activity의 onCreate가 호출되므로 두번째 act가 동작하게 된다.
        // 다른 act가 fininsh되면 현 act로 복귀하게 된다.
        if (!loadPrefs()) { //KJK_TALK: 기존 data가 없을 경우(false), input화면으로 이동한다. 
            Intent intent = new Intent(this, RedirectGetter.class);
            startActivityForResult(intent, INIT_TEXT_REQUEST);
            //finish()가 없으므로 현재 act는 act stack에 들어간다.
        }
    }
    //KJK_TALK: 2개 이상의 startActivityForResult가 사용되엇으므로 requestCode로 어느 act 가 
    //종료되어 들어호출되엇는지 검사한다. 이 예제에서는 같은 act를 2가지 다른 경우에 호출할때
    //구별하는 용도로 사용하고 있다.
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data) {
        if (requestCode == INIT_TEXT_REQUEST) {

            // If the request was cancelled, then we are cancelled as well.
            if (resultCode == RESULT_CANCELED) { //사용자가 그냥 back키를 눌럿을경우
                finish();//이 경우 A->C 이동이므로 복귀도 C->A로 바로하기 위해서 현재 act를 종료한다.

            // Otherwise, there now should be text...  reload the prefs,
            // and show our UI.  (Optionally we could verify that the text
            // is now set and exit if it isn't.)
            } else {
                loadPrefs();
            }

        } else if (requestCode == NEW_TEXT_REQUEST) {

            // In this case we are just changing the text, so if it was
            // cancelled then we can leave things as-is.
            if (resultCode != RESULT_CANCELED) {
                loadPrefs();
            }

        }
    }

    private final boolean loadPrefs() {
        // Retrieve the current redirect values.
        // NOTE: because this preference is shared between multiple
        // activities, you must be careful about when you read or write
        // it in order to keep from stepping on yourself.
        SharedPreferences preferences = getSharedPreferences("RedirectData", 0);

        mTextPref = preferences.getString("text", null);
        if (mTextPref != null) {
            TextView text = (TextView)findViewById(R.id.text);
            text.setText(mTextPref);
            return true;
        }

        return false;
    }

    private OnClickListener mClearListener = new OnClickListener() {
        public void onClick(View v) {
            // Erase the preferences and exit!
            SharedPreferences preferences = getSharedPreferences("RedirectData", 0);
            preferences.edit().remove("text").commit(); //edit interface에서 text항목을 지우고 저장한다.
            finish();	//현재 act를 삭제한다.
        }
    };

    private OnClickListener mNewListener = new OnClickListener() {
        public void onClick(View v) {
            // Retrieve new text preferences.
            Intent intent = new Intent(RedirectMain.this, RedirectGetter.class); //input 화면으로 이동한다.
            startActivityForResult(intent, NEW_TEXT_REQUEST);
        }
    };

    private String mTextPref;
}

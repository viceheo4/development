/*
 * KJK_TALK APIDEMOS: App-> Activity-> PersistentState
   act를 시작한후 이전키를 누르거나 act를 stack에서 종료시키거나,
   app 자체를 exit하여 종료시킬수 있는데, 이때 재 진입시 기존값을 복구하는 방법

   두개의 EditText중
   첫번째 것은 pause시 문자열 저장하고 resume시 다시 읽어오는 작업을  하여 지속적으로 이전 데이터를 보여주게 한다.
   두번째  EditText에는 아무것도 하지 않아,저장되지 않는것을 보여준다.

   그리고 버튼을 누르면 새로은 act가 추가되는데, 이 경우 stack에서 지속적으로 머물고 있으므로 따로 저장할 필요가 없게 된다. 

Android에서는 data를 저장하는 방법을 3개 제공하고 있다, 
1. preference기법으로 xml형식으로 apk가 설치된 data영역에 저장하는 방법(이 방법 사용)
2. Contents Provider를 통해 SQLite DB로 저장하는 방법
3. File Operation을 통해 그냥 일반 file로 저장하는 방법


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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Simple example of using persistent preferences to retain a screen's state.
 * <p>This can be used as an alternative to the normal
 * <code>onSaveInstanceState()</code> mechanism, if you
 * wish the state to persist even after an activity is finished.</p>
 *
 * <p>Note that using this approach requires more care, since you are sharing
 * the persistent state potentially across multiple instances of the activity.
 * In particular, if you allow a new instance of the activity to be launched
 * directly on top of the existing instance, the state can get out of sync
 * because the new instance is resumed before the old one is paused.</p>
 *
 * <p>For any persistent state that is not simplistic, a content
 * provider is often a better choice.</p>
 *
 * <p>In this example we are currently saving and restoring the state of the
 * top text editor, but not of the bottom text editor.  You can see the difference
 * by editing the two text fields, then going back from the activity and
 * starting it again.</p>
 *
 * <h4>Demo</h4>
 * App/Activity/Save &amp; Restore State
 *
 * <h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td class="LinkColumn">src/com.example.android.apis/app/PersistentState.java</td>
 *             <td class="DescrColumn">The Save/Restore Screen implementation</td>
 *         </tr>
 *         <tr>
 *             <td class="LinkColumn">/res/any/layout/save_restore_state.xml</td>
 *             <td class="DescrColumn">Defines contents of the screen</td>
 *         </tr>
 * </table>
 *
 */
public class PersistentState extends Activity
{
    /**
     * Initialization of the Activity after it is first created.  Here we use
     * {@link android.app.Activity#setContentView setContentView()} to set up
     * the Activity's content, and retrieve the EditText widget whose state we
     * will persistent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/save_restore_state.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.save_restore_state);

        // Set message to be appropriate for this screen.
        ((TextView)findViewById(R.id.msg)).setText(R.string.persistent_msg);
        Button twoButton = (Button) findViewById(R.id.added); 
        twoButton.setOnClickListener(mClickListener);

        // Retrieve the EditText widget whose state we will save.
        mSaved = (EditText)findViewById(R.id.saved);
    }

    /**
     * Upon being resumed we can retrieve the current state.  This allows us
     * to update the state if it was changed at any time while paused.
     재시작시 저장된 문자열을 복구하고 selection 영역을 복구한다.
     */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(0); 
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            mSaved.setText(restoredText, TextView.BufferType.EDITABLE);

            int selectionStart = prefs.getInt("selection-start", -1);
            int selectionEnd = prefs.getInt("selection-end", -1);
            if (selectionStart != -1 && selectionEnd != -1) {
                mSaved.setSelection(selectionStart, selectionEnd);
            }
        }
    }

    /**
     * Any time we are paused we need to save away the current state, so it
     * will be restored correctly when we are resumed.
     * 정지시 text와 selection 영역을 저장한다. 
     * preference에 넣는 함수로는 putString,putInt,putBoolean,putFloat,putLong등이 존재한다.
     * 
     */
    @Override
    protected void onPause() {
        super.onPause();
        //getPreferences(mode): mode로는 Private, Read, Write가 존재하며,
        //private은 자기 apk만, read는 other의 read, write는 other의 write권한.
        SharedPreferences.Editor editor = getPreferences(0).edit();
        editor.putString("text", mSaved.getText().toString());//text label에 text를 
        editor.putInt("selection-start", mSaved.getSelectionStart());//selection-start에 선택위치를
        editor.putInt("selection-end", mSaved.getSelectionEnd());//selection-end에 선택끝위치를
        editor.commit();//저장 

    }

    private final OnClickListener mClickListener = new OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(PersistentState.this, HelloWorld.class));
        }
    };


    private EditText mSaved;
}

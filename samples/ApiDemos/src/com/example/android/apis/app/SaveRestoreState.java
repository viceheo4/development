/*
 * KJK_TALK APIDEMOS: App-> Activity-> Save & Restore State
 * 갑자기 메로리 부족등으로 background app가 종료될대, 그 마지막 상태를 저장해놧다 
 * 복구하는 예제
 * onPause시 performPauseActivity에서 savedInstanceState으로 현재 상태를 저장해놧다가, onResume시 
 * mInstrumentation.callActivityOnSaveInstanceState에 의해 onSaveInstanceState가 호출되어
 * savedInstanceState를 저장하게 된다. 
 * 그러면 강제로 system에 의해 종료되어도 차후 재실행시 복귀된다.
 * 재현방법
 * 1.ApiDemos Save & Restore 실행, 2.HOME키로 HOME으로 이동, 3.다른 app실행 message같은걸로
 * 4. DDMS로 ApiDemos 강제로 kill, 5.다시 HOME을 눌러 HOME으로 이동, 
 * 6. HOME longkey를 눌러 이전 실행 app중 ApiDemo를 선택
 * 참고, 만약 4번 단계에서 Menu를 통해 실행하면 ApiDemos process위에 새로운 Save & Restore가 
 * 생성되므로 재현되지 않는다.
 * 참고로 이렇게 강제종료에 의해 save & Restore되는 widget은 ID를 가진 widget대상으로 실시한다.
 * ID가 넣지 않으면 save & restore 안됨 
 * 이경우 첫번째 두번째 edit text 모두 ID가 있므으로 복구된다 

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
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * <p>Demonstrates required behavior of saving and restoring dynamic activity
 * state, so that an activity will restart with the correct state if it is
 * stopped by the system.</p>
 *
 * <p>In general, any activity that has been paused may be stopped by the system
 * at any time if it needs more resources for the currently running activity.
 * To handle this, before being paused the
 * {@link android.app.Activity#onSaveInstanceState onSaveInstanceState()} method is called before
 * an activity is paused, allowing it to supply its current state.  If that
 * activity then needs to be stopped, upon restarting it will receive its
 * last saved state in
 * {@link android.app.Activity#onCreate}.</p>
 * <p>In this example we are currently saving and restoring the state of the
 * top text editor, but not of the bottom text editor.  You can see the difference
 * by editing the two text fields, then going to a couple different
 * applications while the demo is running and then returning back to it.  The
 * system takes care of saving a view's state as long as an id has been
 * assigned to the view, so we assign an ID to the view being saved but not
 * one to the view that isn't being saved.</p>
 * <h4>Demo</h4>
 * App/Activity/Save &amp; Restore State
 * <h4>Source files</h4>
 * <table class="LinkTable">
        <tr>
            <td class="LinkColumn">src/com.example.android.apis/app/SaveRestoreState.java</td>
            <td class="DescrColumn">The Save/Restore Screen implementation</td>
        </tr>
        <tr>
            <td class="LinkColumn">/res/any/layout/save_restore_state.xml</td>
            <td class="DescrColumn">Defines contents of the screen</td>
        </tr>
</table>
 */
public class SaveRestoreState extends Activity
{
    /**
     * Initialization of the Activity after it is first created.  Here we use
     * {@link android.app.Activity#setContentView setContentView()} to set up
     * the Activity's content, and retrieve the EditText widget whose state we
     * will save/restore.
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
        ((TextView)findViewById(R.id.msg)).setText(R.string.save_restore_msg);
    }

    /** 아래 2개의 함수는 save & restore 기능과 상관이 없다.
     * Retrieve the text that is currently in the "saved" editor.
     */
    CharSequence getSavedText() {
        return ((EditText)findViewById(R.id.saved)).getText();
    }

    /**
     * Change the text that is currently in the "saved" editor.
     */
    void setSavedText(CharSequence text) {
        ((EditText)findViewById(R.id.saved)).setText(text);
    }
}


/*
 * KJK_TALK APIDEMOS: App-> Activity-> Custom Title
 * title bar에 user defined xml layout을 적용하는 예제
 * 참고로 title bar는 notification window (indicator)바로 아래 존재하는 title 영역을 말함
 * title 영역을 변경하기 위해서는 requestWindow(feature)가 필요하며, 이때 요청할수 있는 feature로는 Window.java에 기술되어 있다.

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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.apis.R;


/**
 * Example of how to use a custom title {@link android.view.Window#FEATURE_CUSTOM_TITLE}.
 * <h3>CustomTitle</h3>

<p>This demonstrates how a custom title can be used.</p>

<h4>Demo</h4>
App/Title/Custom Title
 
<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/app/CustomTitle.java</td>
 *             <td >The Custom Title implementation</td>
 *         </tr>
 *         <tr>
 *             <td >/res/any/layout/custom_title.xml</td>
 *             <td >Defines contents of the screen</td>
 *         </tr>
 * </table> 
 */
public class CustomTitle extends Activity {
    
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView(int)} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//title bar에 extended 기능을  enable한다.
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.custom_title);
		//titil bar에 사용자 text값을 바꾸기 위해 id가 존재하는 사용자 layout을  적용한다.
		//id를 알아야지 어떤게 바굴건지 알게 된다.
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_1);
        
        final TextView leftText = (TextView) findViewById(R.id.left_text);
        final TextView rightText = (TextView) findViewById(R.id.right_text);
        final EditText leftTextEdit = (EditText) findViewById(R.id.left_text_edit);
        final EditText rightTextEdit = (EditText) findViewById(R.id.right_text_edit);
        Button leftButton = (Button) findViewById(R.id.left_text_button);
        Button rightButton = (Button) findViewById(R.id.right_text_button);
        
        leftButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                leftText.setText(leftTextEdit.getText());
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                rightText.setText(rightTextEdit.getText());
            }
        });
    }
}

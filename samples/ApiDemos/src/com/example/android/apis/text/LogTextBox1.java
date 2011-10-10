/*
 * KJK_TALK APIDEMOS: Text-> LogTextBox1
   사용자가 정의한 overriding class를 이용하여 XML layout을 정의한 예제.
   TextView를 Overiding한 LogTextBox class를 이용하여 자신의 layout을 넘어
   갔을때 자동적으로 scroll 되게 하는것을 보여준다. 
   기존 TextView는 scrollbar는 생성되지만 자동적으로 scroll되지는 않는다.


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

package com.example.android.apis.text;

import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Using a LogTextBox to display a scrollable text area
 * to which text is appended.
 *
 */
public class LogTextBox1 extends Activity {
    
	private LogTextBox mLText;
    private LogTextBox mText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //KJK_TALK: log_text_box_1.xml에서 customized class인 LogTextBox 를 사용하여 
        //layout을 기술하였다. 
        setContentView(R.layout.log_text_box_1);
        
        mText = (LogTextBox) findViewById(R.id.text);
        
        Button addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mLText.append("This is a testaaaaaㅇㅇㅁㄻㅇㄻㄴㄹㅇㅁadfasdfadfasfdasadfadfasfda\n");
            } });
		Button nothingButton = (Button) findViewById(R.id.nothing);
		nothingButton.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				mText.append("This is a testaaaaaㅇㅇㅁㄻㅇㄻㄴㄹㅇㅁadfasdfadfasfdasadfadfasfda\n");
			}
		});

	}
}

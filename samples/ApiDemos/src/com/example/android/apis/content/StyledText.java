/*
 * KJK_TALK APIDEMOS: App-> Content-> Resources-> Styled Text
   string의 속성을 html tag로 기록해 논걸 읽어와 보여주는 예제로
   1)그냥 layout xml로 해당 string값을 읽어오는 방법과
   2)코드로 textView에 해당 string값을 읽어오는 방법을 보여준다. 둘다 결과는 같다.


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

package com.example.android.apis.content;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Demonstration of styled text resources.
 */
public class StyledText extends Activity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/styled_text.xml for this
        // view layout definition.
        /* Strings.xml file
        <string name="activity_styled_text">Content/Resources/<i>Styled</i> <b>Text</b></string>
        <string name="styled_text_rsrc">Initialized from a resource:</string>
        <string name="styled_text">Plain, <b>bold</b>, <i>italic</i>, <b><i>bold-italic</i></b></string>
        <string name="styled_text_prog">Assigned programmatically:</string>
        */
        //KJK_TALK: string의 속성을 html tag로 기록해 논걸 xml inflate로 그대로 보여준다.
        setContentView(R.layout.styled_text);

        // Programmatically retrieve a string resource with style
        // information and apply it to the second text view.  Note the
        // use of CharSequence instead of String so we don't lose
        // the style info.
        //KJK_TALK: string의 속성을 html tag로 기록해 논걸 code에서 불러와 보여준다.
        //참고로 string 자체 속성(italic,etc)을 code로 동적으로 handling하는 예제는 여기에 없다.
        CharSequence str = getText(R.string.styled_text);
        TextView tv = (TextView)findViewById(R.id.text);
        tv.setText(str);
    }
}


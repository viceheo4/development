/*
 * KJK_TALK APIDEMOS: App-> Content-> Resources-> Resources
 * CharSequence와 String의 차이점을 보여주는 예제로 CharSequence는 Style 속성을 가지고 있다.
 * 일반 view에서 Activity의 resource를 가져오기 위해 context.getResources()를 사용했다. 


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
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Demonstration of loading resources.
 * 
 * <p>
 * Each context has a resources object that you can access.  Additionally,
 * the Context class (an Activity is a Context) has a getString convenience
 * method getString() that looks up a string resource.
 *
 * @see StyledText for more depth about using styled text, both with getString()
 *                 and in the layout xml files.
 */
public class ResourcesSample extends Activity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // See res/any/layout/resources.xml for this view layout definition.
        setContentView(R.layout.resources);

        TextView tv;
        CharSequence cs;
        String str;

        // ====== Using the Context.getString() convenience method ===========
        // KJK_TALK: CharSequence와 String과의 차이점
        // Using the getString() conevenience method, retrieve a string
        // resource that hapepns to have style information.  Note the use of
        // CharSequence instead of String so we don't lose the style info.
        // CharSequence
        cs = getText(R.string.styled_text);
        tv = (TextView)findViewById(R.id.styled_text);
        tv.setText(cs);

        // Use the same resource, but convert it to a string, which causes it
        // to lose the style information.
        //KJK_TALK: String, CharSequence을 String으로 바꾸면 Style 속성을 잃는다.
        str = getString(R.string.styled_text);
        tv = (TextView)findViewById(R.id.plain_text);
        tv.setText(str);

        // ====== Using the Resources object =================================
        
        // You might need to do this if your code is not in an activity.
        // For example View has a protected mContext field you can use.
        // In this case it's just 'this' since Activity is a context.
        //KJK_TALK: 일반 view에서 activity에 달린 Char등의 resource를 읽어오는 방법
        Context context = this;

        // Get the Resources object from our context
        Resources res = context.getResources();

        // Get the string resource, like above.
        cs = res.getText(R.string.styled_text);
        tv = (TextView)findViewById(R.id.res1);
        tv.setText(cs);

        //KJK_TALK: 이미 theme 정보는 resource에 들어 있으므로 getXXX함수로 가져다 쓰면 된다.
        // Note that the Resources class has methods like getColor(),
        // getDimen(), getDrawable() because themes are stored in resources.
        // You can use them, but you might want to take a look at the view
        // examples to see how to make custom widgets.

    }
}


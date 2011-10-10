/*
 * KJK_TALK APIDEMOS: Text-> Linkify.Link
   String에 link를 거는 방법을 4가지 방법으로 보여주는 예제 
   -xml에 기록된 일반문자열을 layout으로 읽어 처리하는 방법1
   -string에 HTML tag를 달은 문자열을 layout으로 읽어 처리하는 방법2
   -sting을 html문자열로 만들어 놓고 HTML 문자열을 code로 처리하는 방법3
   -일반 문자열을 code로 처리하는 방법4


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
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

public class Link extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //KJK_TALK: 일반문자열을 layout으로 읽어 처리하는 방법1
        // text1 shows the android:autoLink property, which
        // automatically linkifies things like URLs and phone numbers
        // found in the text.  No java code is needed to make this
        // work. 
        //layout.xml인 link.xml에서 android:autoLink="all"로 표기함으로서 가능
        //strings.xml에 기록된 string에서 URI과 전화번호에 대한 링크를 자동으로 달아줌 
        setContentView(R.layout.link);

        //KJK_TALK: string에 HTML tag를 달은 문자열을 layout으로 읽어 처리하는 방법2
        //tag에 달린 link가 동작하기 위해 아래 setMovementMethod 함수를 호출해 준다.

        // text1 shows the android:autoLink property, which
        // automatically linkifies things like URLs and phone numbers
        // found in the text.  No java code is needed to make this
        // work.

        // text2 has links specified by putting <a> tags in the string
        // resource.  By default these links will appear but not
        // respond to user input.  To make them active, you need to
        // call setMovementMethod() on the TextView object.

        TextView t2 = (TextView) findViewById(R.id.text2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        //KJK_TALK: sting을 html문자열로 만들어 놓고 HTML 문자열을 code로 처리하는 방법3
        //직접 string에 Html parser를 돌려 text를 표시한다.
        //localization 때문에 위의 방법들이 더 좋은 방법임 
        // text3 shows creating text with links from HTML in the Java
        // code, rather than from a string resource.  Note that for a
        // fixed string, using a (localizable) resource as shown above
        // is usually a better way to go; this example is intended to
        // illustrate how you might display text that came from a
        // dynamic source (eg, the network).

        TextView t3 = (TextView) findViewById(R.id.text3);
        t3.setText(
            Html.fromHtml(
                "<b>text3:</b>  Text with a " +
                "<a href=\"http://www.google.com\">link</a> " +
                "created in the Java source code using HTML."));
        t3.setMovementMethod(LinkMovementMethod.getInstance());

        //KJK_TALK: 일반 문자열을 code로 처리하는 방법4
        //문자열을 객체로 지정하고 그 객체에 특성을 준다.
        // text4 illustrates constructing a styled string containing a
        // link without using HTML at all.  Again, for a fixed string
        // you should probably be using a string resource, not a
        // hardcoded value.

        SpannableString ss = new SpannableString(
            "text4: Click here to dial the phone.");
        //각 문자열마다 특성을 준다.
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6,    //text4: /
                   Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new URLSpan("tel:4155551212"), 13, 17, //here /
                   Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView t4 = (TextView) findViewById(R.id.text4);
        t4.setText(ss);
        t4.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

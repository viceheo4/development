/*
 * KJK_TALK APIDEMOS: Text-> LogTextBox1 : LogTextBox
   LogTextBox1에서 LogTextBox class를 사용한다.
   link는 import가 아닌 build system에서 하게 된다.
   아래에서 scrolling과 TextView 속성을 default로 editable로 하기위해서
   TextView를 재정의 하게 되었다. 


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

import android.widget.TextView;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.text.method.MovementMethod;
import android.text.Editable;
import android.util.AttributeSet;

/**
 * This is a TextView that is Editable and by default scrollable,
 * like EditText without a cursor.
 *
 * <p>
 * <b>XML attributes</b>
 * <p>
 * See
 * {@link android.R.styleable#TextView TextView Attributes},
 * {@link android.R.styleable#View View Attributes}
 */
public class LogTextBox extends TextView {
    public LogTextBox(Context context) {
        this(context, null);
    }

    public LogTextBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public LogTextBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //KJK_TALK: scrolling을 하기 위해서는 반드시 overriding해서 
    //scrolling method를 지정해야 한다.
    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ScrollingMovementMethod.getInstance();
    }

    //KJK_TALK: return 값을 Editable로 변경해서 보내기 위해 overriding하였다. 
    @Override
    public Editable getText() {
        return (Editable) super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, BufferType.EDITABLE);
    }
}

/*
 * KJK_TALK APIDEMOS: App-> Activity-> HelloWorld
 단순히 XML에 적힌 HelloWorld widget을 inflate하여 보여주는 역활을 한다.
 TextView를 사용하엿으며, View Group은 지정되지 않앗다.

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
import android.util.Log;




/**
 * Simple example of writing an application Activity.
 * Hello World</a></h3>

<p>This demonstrates the basic code needed to write a Screen activity.</p>

<h4>Demo</h4>
App/Activity/Hello World
 
<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/app/HelloWorld.java</td>
 *             <td >The Hello World Screen implementation</td>
 *         </tr>
 *         <tr>
 *             <td >/res/any/layout/hello_world.xml</td>
 *             <td >Defines contents of the screen</td>
 *         </tr>
 * </table> 
 */
public class HelloWorld extends Activity
{
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        // Be sure to call the super class.
		//강제로 app가 종료되었을때, 마지막 순간을 저장해놨다가 다시 그 app를 시작하면 이전 상태로 loading해주기 위해 현재 상태를 저장한다. 
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/hello_world.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        // KJK_QUESTION : hello_world는 integer인데, 실제로 integer를 어떻게 mapping시키지?
        setContentView(R.layout.hello_world);
        Log.v(this.getClass().toString(), this.toString());
    }
}

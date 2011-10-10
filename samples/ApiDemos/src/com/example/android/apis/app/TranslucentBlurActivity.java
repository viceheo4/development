/*
 * KJK_TALK APIDEMOS: App-> Activity-> TranslucentBlur
 * AndroidManifest.xml파일에서 @style/Theme.Transparent를 이용하여 
 * 현재 Foreground를 투명하게 하고, getWindow().setFlags(FLAG_BLUR_BEHIND)로
 * 이전 화면을 blur하게 하여 겹쳐지는 효과를 만듦.


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
import android.view.WindowManager;


/**
 * <h3>Fancy Blur Activity</h3>
 * 
 * <p>This demonstrates the how to write an activity that is translucent,
 * allowing windows underneath to show through, with a fancy blur
 * compositing effect.</p>
 */
public class TranslucentBlurActivity extends Activity {
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle icicle) {
        // Be sure to call the super class.
        super.onCreate(icicle);

        // Have the system blur any windows behind this one.
        // 실제로 뒷배경을 blur 시키는 method, 참고로 FLAG_DIM_BEHIND로 dimming시킬수도 잇다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        // See assets/res/any/layout/translucent_background.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.translucent_background);
    }
}

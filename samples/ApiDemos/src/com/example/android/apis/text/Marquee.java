/* 
 * KJK_TALK APIDEMOS: Text-> Marquee
 *
 * 해당 button에 key를 이용하여 "focus"를 주면 button보다 긴 text가
 * 자동적으로 scrolling된다. 이 횟수를 test하는 example이다.
 * layout xml 파일의 button 속성에 marquee를 주면 default로 3번 
 * 추가적으로 android:marqueeRepeatLimit에 횟수를 강제로 입력가능하며
 * marquee_forever를 주면 무한 반복한다.


 * Copyright (C) 2007 Google Inc.
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

public class Marquee extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.marquee);
    }
}

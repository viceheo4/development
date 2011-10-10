/*
 * KJK_TALK APIDEMOS: App-> Content->Assets->Read Asset
 * 일반 file을 읽는 방법을 보여준다. 로딩시는 ASCII파일으므로 
 * UNI-code, UTF-16형식으로 변경하여 사용한다.


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

import java.io.IOException;
import java.io.InputStream;


/**
 * Demonstration of styled text resources.
 */
public class ReadAsset extends Activity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/styled_text.xml for this
        // view layout definition.
        setContentView(R.layout.read_asset); //빈 화면을 읽는다.

        // Programmatically load text from an asset and place it into the
        // text view.  Note that the text we are loading is ASCII, so we
        // need to convert it to UTF-16.
        try {
            InputStream is = getAssets().open("read_asset.txt"); //KJK_TALK: ascii file, 득 txt파일을 읽는다.
            //KJK_TALK: 이제 input stream으로 읽어들였으므로 1byte가 2byte가 되었다.
            //즉 기존 file이 2GB라면 4GB가 된다. 
            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();
            
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            
            // Convert the buffer into a string. //KJK_TALK: String의 생성자로 txt.file txt을 String으로 만든다.
            String text = new String(buffer);
            
            // Finally stick the string into the text view.
            TextView tv = (TextView)findViewById(R.id.text);
            tv.setText(text); //만든 text 출력
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}


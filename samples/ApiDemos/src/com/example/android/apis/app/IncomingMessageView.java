/*
 * KJK_TALK APIDEMOS: App-> Notification-> IncommingMessage-> IncomingMessageView
 * status bar에서 notification을 확인하기 위해 drag하여 notification panel을 연다.
 * 여기 pannel에서 list된(새로온)noti를 확인하기 위해 click하면 현재 activity가 구동된다.
 * 아마도 IncomingMessage.java에서 해당 act를 등록하면 사용자가 누르는 
 * touch event가 들어왔을 경우 notification manager에서 현재 act를 띄우 준것으로 생각된다. 

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

import com.example.android.apis.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * This activity is run as the click activity for {@link IncomingMessage}.
 * When it comes up, it also clears the notification, because the "message"
 * has been "read."
 */
public class IncomingMessageView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_message_view);//noti pannel에서 noti를 click했을대 보여주는 화면

        // look up the notification manager service
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // status bar와 notification pannel자체에서 noti 정보 자체를 없앤다.
        // cancel the notification that we started in IncomingMessage
        nm.cancel(R.string.imcoming_message_ticker_text);
    }
}


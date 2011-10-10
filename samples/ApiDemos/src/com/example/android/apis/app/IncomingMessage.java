/*
 * KJK_TALK APIDEMOS: App-> Notification-> IncommingMessage
 * 사용자 customized toast를 띄우고,
 * 일정/스케쥴등의 noti가 Status bar에 뜨고,
 * vibrate도 설정하고
 * notification panel에 해당 noti를 보여주고,
 * 그것을 클릭하면 해당 noti가 담긴 act로 이동하는  예제이다.

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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IncomingMessage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.incoming_message);

        Button button = (Button) findViewById(R.id.notify);
        button.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    showToast();
                    showNotification();
                }
            });
    }

    /**
     * The toast pops up a quick message to the user showing what could be
     * the text of an incoming message.  It uses a custom view to do so.
     */
    protected void showToast() {
        // create the view
        // XML Layout file을 Instance화 하여 view로 설정한다.
        View view = inflateView(R.layout.incoming_message_panel);

        // set the text in the view, 해당 view의 textView을 얻어와 새로이 설정한다.
        TextView tv = (TextView)view.findViewById(R.id.message);
        tv.setText("khtx. meet u for dinner. cul8r");//toast에서 보여줄 문자열

        // show the toast
        Toast toast = new Toast(this);
        //설정된 view를 toast의 view로 설정하고 display한다.
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private View inflateView(int resource) {//XML layout inflater service 획득
        LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(resource, null);
    }

    /**
     * The notification is the icon and associated expanded entry in the
     * status bar.
     */
    protected void showNotification() {
        // look up the notification manager service, Notificatoin service 획득
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // The details of our fake message, notification에서 보여줄 문자열
        CharSequence from = "Joe";
        CharSequence message = "kthx. meet u for dinner. cul8r";

        // The PendingIntent to launch our activity if the user selects this notification
        // 실제 notification pannel에서 해당 noti를 click했을때 호출되어야 하는 activity생성
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, IncomingMessageView.class), 0);

        // The ticker text, this uses a formatted string so our message could be localized
        String tickerText = getString(R.string.imcoming_message_ticker_text, message);

        // construct the Notification object.
        Notification notif = new Notification(R.drawable.stat_sample, tickerText,
                System.currentTimeMillis());

        // Set the info for the views that show in the notification panel.
        // from: 보낸사람, message: 요약메세지, contentIntent: Full msg를 보여줄 activity
        notif.setLatestEventInfo(this, from, message, contentIntent);

        // after a 100ms delay, vibrate for 250ms, pause for 100 ms and
        // then vibrate for 500ms. 어떻게 noti 할지를 설정, vibrate도 설정한다.
        notif.vibrate = new long[] { 100, 250, 100, 500};

        // Note that we use R.layout.incoming_message_panel as the ID for
        // the notification.  It could be any integer you want, but we use
        // the convention of using a resource id for a string related to
        // the notification.  It will always be a unique number within your
        // application.
        //KJK_TALK: register할 notification ID로 R.string.imcoming_message_ticker_text
        //해제할때도 위의 ID로 해제를 하게된다.
        nm.notify(R.string.imcoming_message_ticker_text, notif);
    }
}

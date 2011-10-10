/*
 * KJK_TALK APIDEMOS: App-> Alarm-> Alarm Controller
 * AlaramController는 OneShotTimer와 RepeatTimer BroadCast Receiver를 등록하는데
 * 두 경우 다  다른 process에서 잠시 동작하고 종료하게 된다. RepeatTimer같은 경우
 * 등록된 Alarm이 SystemServer에 의해 구동되고 그때마다 RepeatTimer를 동작시키게 되므로 
 * 해당 process를 강제로 죽이더라도 SystemServer에 의해 새로 생성되어 실행되게 된다.

   KJK_TALK PENDING INTENT: 
   일반 intent와 다르게 제 3자에게 intent를 대신 보내달라고 할때 사용하는 intents다.
   그러므로 잠시 제 3자가 가지고 있다가 전달하게 된다.
   아래와 같은 경우에는 AlarmManager가 PendingIntent를 대신보내게 된다. 
   

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
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Example of scheduling one-shot and repeating alarms.  See
 * {@link OneShotAlarm} for the code run when the one-shot alarm goes off, and
 * {@link RepeatingAlarm} for the code run when the repeating alarm goes off.
 * <h4>Demo</h4>
App/Service/Alarm Controller
 
<h4>Source files</h4>
<table class="LinkTable">
        <tr>
            <td class="LinkColumn">src/com.example.android.apis/app/AlarmController.java</td>
            <td class="DescrColumn">The activity that lets you schedule alarms</td>
        </tr>
        <tr>
            <td class="LinkColumn">src/com.example.android.apis/app/OneShotAlarm.java</td>
            <td class="DescrColumn">This is an intent receiver that executes when the
                one-shot alarm goes off</td>
        </tr>
        <tr>
            <td class="LinkColumn">src/com.example.android.apis/app/RepeatingAlarm.java</td>
            <td class="DescrColumn">This is an intent receiver that executes when the
                repeating alarm goes off</td>
        </tr>
        <tr>
            <td class="LinkColumn">/res/any/layout/alarm_controller.xml</td>
            <td class="DescrColumn">Defines contents of the screen</td>
        </tr>
</table>

 */
public class AlarmController extends Activity {
    Toast mToast;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alarm_controller);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.one_shot);
        button.setOnClickListener(mOneShotListener);
        button = (Button)findViewById(R.id.start_repeating);
        button.setOnClickListener(mStartRepeatingListener);
        button = (Button)findViewById(R.id.stop_repeating);
        button.setOnClickListener(mStopRepeatingListener);
    }

    private OnClickListener mOneShotListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            //KJK_TALK: alarm시 실행해야할 동작을 intent로 만든다.OneShotAlarm:remote을 실행한다.
            Intent intent = new Intent(AlarmController.this, OneShotAlarm.class);

            //broadcast로 보낼 intent를 획득한다.
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
                    0, intent, 0);

            // We want the alarm to go off 5 seconds from now.
            Calendar calendar = Calendar.getInstance();
            // setTimeInMillis는 calender에 설정된 현재 시간정보를 가져오는 함수.
            calendar.setTimeInMillis(System.currentTimeMillis());//현재 시간을 설정하여 가져온다.
            calendar.add(Calendar.SECOND, 5);//현재시각에 5초 지난 시각을 더해 재설정한다. 이후 이시각으로 alarm을 호출한다.
            //여기서는 calendar를 사용하여 time format으로 알람을 설정하였다.
            // Schedule the alarm!, 알람설정
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            //5초지난 시각에 알람이 설정되고, alarm시 sender가 호출되도록 pending intent를 보내게 된다.
            // 이후 pending intents는 OneShotAlarm remote process를 만들게 된다.
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(AlarmController.this, R.string.one_shot_scheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    private OnClickListener mStartRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            // Note that unlike above, this IntentSender is configured to
            // allow itself to be sent multiple times.
            Intent intent = new Intent(AlarmController.this, RepeatingAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
                    0, intent, 0);
            
            // We want the alarm to go off 30 seconds from now.
            long firstTime = SystemClock.elapsedRealtime();//booting후 부터 지나간 시간 구하기
            firstTime += 5*1000;
            //여기서는 long type을 사용하여 long format으로 알람을 설정하였다.
            // Schedule the alarm!
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            firstTime, 5*1000, sender);//KJK_TALK: RepeatingAlarm:remote을 실행한다.

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(AlarmController.this, R.string.repeating_scheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    private OnClickListener mStopRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // Create the same intent, and thus a matching IntentSender, for
            // the one that was scheduled.
            Intent intent = new Intent(AlarmController.this, RepeatingAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
                    0, intent, 0);
            
            // And cancel the alarm. 알람 서비스에게 중지 명령을 날린다.
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

            //IAlarmManager.java의 remove method를 통하여 mRemote.transact(Stub.TRANSACTION_remove, _data, _reply, 0); 호출
            am.cancel(sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(AlarmController.this, R.string.repeating_unscheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };
}


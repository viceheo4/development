/*
/*
 * KJK_TALK APIDEMOS: App-> Alarm -> Alarm Service
 * 이 act는 단순히 Alarm Controller demo와 같이 Repeat 알람을 등록하고 해제하는 역활을 한다.
 * 여기서 호출하는 AlarmService_Service 는 remote process의 main이 아닌 
 * thread로 생성되고 5초 동안 기다렷다가 종료한다. 그러나 Alarm Service Manager에 
 * Repeated 하도록 등록되어 잇으므로 지속적으로 생성->대기->종료를 반복한다.
 *
 * 즉 앞의 Alarm Controller 예제와 다른점은 
 * remote thread로 receiver가 아닌 service로 동작된다는 점이다.
 * receiver경우에는 10초 이상의 작업하게 되면, 응답이 없으므로 강제 종료 popup이 발생하지만
 * service로 할경우 발생하지 않는다. 그리고 service를 thread로 만든 이유는 만약
 * remote로 실행되어야 할 작업이 ui와 service 동시에 2개를 갖는다면 ui는 기존대로 
 * blocking 없이 동작해야하고, service 또한 지속적으로 동작하여야 하기 때문이다.
 * 

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


/**
 * This demonstrates how you can schedule an alarm that causes a service to
 * be started.  This is useful when you want to schedule alarms that initiate
 * long-running operations, such as retrieving recent e-mails.
 */
public class AlarmService extends Activity {
    private PendingIntent mAlarmSender;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an IntentSender that will launch our service, to be scheduled
        // with the alarm manager. AlarmService_Service:remote를 실행하게 한다.
        mAlarmSender = PendingIntent.getService(AlarmService.this,
                0, new Intent(AlarmService.this, AlarmService_Service.class), 0);
        
        setContentView(R.layout.alarm_service);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.start_alarm);
        button.setOnClickListener(mStartAlarmListener);
        button = (Button)findViewById(R.id.stop_alarm);
        button.setOnClickListener(mStopAlarmListener);
    }

    private OnClickListener mStartAlarmListener = new OnClickListener() {
        public void onClick(View v) {
            // We want the alarm to go off 30 seconds from now.
            long firstTime = SystemClock.elapsedRealtime();
            //KJK_TALK: alarm은 SystemServer에게 등록한것이므로 거기서 지속적으로 발생시킨다.
            // Schedule the alarm! 
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            firstTime, 5*1000, mAlarmSender);

            // Tell the user about what we did.
            Toast.makeText(AlarmService.this, R.string.repeating_scheduled,
                    Toast.LENGTH_LONG).show();
        }
    };

    private OnClickListener mStopAlarmListener = new OnClickListener() {
        public void onClick(View v) {
            // And cancel the alarm.
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.cancel(mAlarmSender);

            // Tell the user about what we did.
            Toast.makeText(AlarmService.this, R.string.repeating_unscheduled,
                    Toast.LENGTH_LONG).show();

        }
    };
}

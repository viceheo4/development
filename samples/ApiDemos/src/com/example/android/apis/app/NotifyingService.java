/*
 * KJK_TALK APIDEMOS: App-> Notification-> Notifying Service Controller-> Notifying Service
 * service에서 thread를 만들어 Notification을 바꾸는 예제 인데, 주기적으로 notification display를 
 * 호출해 주기만 한다. notification panel에 바뀌는 image는 notification manager가 자동으로 처리해준다.


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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * This is an example of service that will update its status bar balloon 
 * every 5 seconds for a minute.
 * 
 */
public class NotifyingService extends Service {
    
    // Use a layout id for a unique identifier
    private static int MOOD_NOTIFICATIONS = R.layout.status_bar_notifications;

    // variable which controls the notification thread 
    private ConditionVariable mCondition;
 
    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.
        //KJK_TALK: main thread에서 ui랑 같이 돌리면 block되므로 따로 thread를 만든다 
        Thread notifyingThread = new Thread(null, mTask, "NotifyingService");
        //여러 thread에서 access가능한 condition value를 default 값 false(lock)로 만든다.
        mCondition = new ConditionVariable(false);
        notifyingThread.start();
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(MOOD_NOTIFICATIONS);
        // Stop the thread from generating further notifications, lock해제
        mCondition.open();
    }

    private Runnable mTask = new Runnable() {
        public void run() {
            for (int i = 0; i < 4; ++i) {
                showNotification(R.drawable.stat_happy,
                        R.string.status_bar_notifications_happy_message);
                //500ms동안 lock,이후에는 자동적으로 unlock
                if (mCondition.block(1 * 500)) 
                    break;
                showNotification(R.drawable.stat_neutral,
                        R.string.status_bar_notifications_ok_message);
                
                if (mCondition.block(1 * 500)) 
                    break;
                showNotification(R.drawable.stat_sad,
                        R.string.status_bar_notifications_sad_message);
                
                if (mCondition.block(1 * 500)) 
                    break;
            }
            // Done with our work...  stop the service! 스스를 멈춘다.
            NotifyingService.this.stopSelf();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    private void showNotification(int moodId, int textId) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(textId);

        // Set the icon, scrolling text and timestamp.
        // Note that in this example, we pass null for tickerText.  We update the icon enough that
        // it is distracting to show the ticker text every time it changes.  We strongly suggest
        // that you do this as well.  (Think of of the "New hardware found" or "Network connection
        // changed" messages that always pop up)
        Notification notification = new Notification(moodId, null, System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NotifyingController.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.status_bar_notifications_mood_title),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(MOOD_NOTIFICATIONS, notification);
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

    private NotificationManager mNM;
}

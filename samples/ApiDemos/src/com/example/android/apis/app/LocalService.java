/*
 * KJK_TALK APIDEMOS: App-> Service-> Local Service Controller-> LocalService.java
 * KJK_TALK APIDEMOS: App-> Service-> Local Service Binding-> LocalService.java

 * 현재 class는 Service class로 Act와 다르게 UI를 갖지 않는다 
 * Act와 별도로 하나의 handler안에서 동작하게 된다. 
 * 어떤 서비스냐 하면, 시작과 동시에 status bar에 service가 시작되었음을 
 * 알리고, 종료되면 toast를 띄워 알려주는 기능을 하게 된다
 * 
 * 어떤 apk가 service를 가지고 있는지 아닌지 판별하려면 adb shell dumpsys activity services 명령으로 확인이 가능하다.

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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application.  The {@link LocalServiceActivities.Controller}
 * and {@link LocalServiceActivities.Binding} classes show how to interact with the
 * service.
 *
 * <p>Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service.  This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */
//BEGIN_INCLUDE(service)
public class LocalService extends Service {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        //onBind이후 service가 직접적으로connect 되었을때 호출되게 된다.
        LocalService getService() {
            return LocalService.this;
        }
    }
    
    @Override
    public void onCreate() {
        //KJK_TALK SYSTEM SERVICE: getSystemService를 이용하여 얻는 방법
/*
        WINDOW_SERVICE ("window"):
            The top-level window manager in which you can place custom windows. The returned object is a android.view.WindowManager. 
        LAYOUT_INFLATER_SERVICE ("layout_inflater") 
            A android.view.LayoutInflater for inflating layout resources in this context. 
        ACTIVITY_SERVICE ("activity") 
            A android.app.ActivityManager for interacting with the global activity state of the system. 
        POWER_SERVICE ("power") 
            A android.os.PowerManager for controlling power management. 
        ALARM_SERVICE ("alarm") 
            A android.app.AlarmManager for receiving intents at the time of your choosing. 
        NOTIFICATION_SERVICE ("notification") 
            A android.app.NotificationManager for informing the user of background events. 
        KEYGUARD_SERVICE ("keyguard") 
            A android.app.KeyguardManager for controlling keyguard. 
        LOCATION_SERVICE ("location") 
            A android.location.LocationManager for controlling location (e.g., GPS) updates. 
        SEARCH_SERVICE ("search") 
            A android.app.SearchManager for handling search. 
        VIBRATOR_SERVICE ("vibrator") 
            A android.os.Vibrator for interacting with the vibrator hardware. 
        CONNECTIVITY_SERVICE ("connection") 
            A ConnectivityManager for handling management of network connections. 
        WIFI_SERVICE ("wifi") 
            A WifiManager for management of Wi-Fi connectivity. 
        INPUT_METHOD_SERVICE ("input_method") 
            An InputMethodManager for management of input methods. 
        UI_MODE_SERVICE ("uimode") 
            An android.app.UiModeManager for controlling UI modes. 
*/
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
        mHandler.sendMessage(mHandler.obtainMessage(BUMP_MSG, 100, 0));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        //KJK_TALK: status bar에 등록한 NOTI를 취소한다. 
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped. KJK_TALK: message box 출력: Context, 문자열, 출력 duration  
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    //KJK_TALK: LocalServiceBinding.java에서 bindService method를 호출할때 
    //이 class의 onBind가 호출되게 된다. 이때 만약 현재 class가 instance화 되지 않은 상태라면
    //Framework에서 LocalService의 생성자를 이용해 생성후 onBind를 호출하게 된다. 
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        //KJK_TALK: getText는 context class로 부터, 
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.stat_sample, text,
                System.currentTimeMillis());//statusbar icon, statusbar text, showuptime now

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LocalServiceActivities.Controller.class), 0);

        // Set the info for the views that show in the notification panel.
        // notification panel에 보여줄 정보설정.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        //KJK_TALK: status bar에 보여줄 NOTI를 등록한다. 
        mNM.notify(NOTIFICATION, notification);
    }
    
    private static final int BUMP_MSG = 1;
    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            int temp =0 ;
            switch (msg.what) {
                case BUMP_MSG:
                    Log.i("LocalService", "MyService " + temp++);
                    sendMessageDelayed(mHandler.obtainMessage(BUMP_MSG, 50, 0),500);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
        
    };
}
//END_INCLUDE(service)

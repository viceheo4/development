/*
 * KJK_TALK APIDEMOS: App-> Service-> Service Start Arguments Controller
 * 이 예재는 하나의 process에 새로운 thread로 service를 구동하는 방법으로
   START_REDELIVER_INTENT, START_NOT_STICKY, START_STICKY등으로 해당 service가
   kill시 다시 살아날지 말지를 결정하는 보여주는 예제이다. 
   CASE 1/2번은 같은 예제로 1/2번을 실행후 kill process를 눌렀을 경우, 살아나지 않는다. 
   CASE 3번은 실행후 kill process를 눌렀을 경우, 살아난다.
   
 * 이는 kill process경우에는 START_REDELIVER_INTENT, START_NOT_STICKY, START_STICKY의 flag에 의해서
   Activity Manager가 다시 살리게 되기 때문이다.
 * 
 * 아래 2 버튼은 죽이는 방법으로 
   start failed delivery경우에는 onStart에서 죽는 경우를 simulation해주는데, 이때는  flag에 상관없이 무조건  다시 살아난다. 
   kill button은 ddms에서 kill process와 동일한 것으로 service Flag값에 따라 다르게 살아나거나 살아나지 않게 된다.      

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

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application.  The {@link Controller}
 * class shows how to interact with the service. 
 *
 * <p>Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service.  This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 * 
 * <p>For applications targeting Android 1.5 or beyond, you may want consider
 * using the {@link android.app.IntentService} class, which takes care of all the
 * work of creating the extra thread and dispatching commands to it.
 *///KJK_TALK: Service Class가 Activity를 감싸고 있는 구조. 
public class ServiceStartArguments extends Service {
    private NotificationManager mNM;
    private Intent mInvokeIntent;
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);//KJK_TALK: handler를 생성하고 어떤 looper를 사용하는지 알려준다.
        }
        
        @Override
        public void handleMessage(Message msg) {
            Bundle arguments = (Bundle)msg.obj;
        
            String txt = arguments.getString("name");
            
            Log.i("ServiceStartArguments", "Message: " + msg + ", "
                    + arguments.getString("name"));

            //KJK_TALK 1/2nd 명령: 그냥 txt만을 뿌린다.
            if ((msg.arg2&Service.START_FLAG_REDELIVERY) == 0) {
                txt = "New cmd #" + msg.arg1 + ": " + txt;
            } else {//START_FLAG_REDELIVERY는 START_REDELIVER_INTENT, START_STICKY인 경우
                txt = "Re-delivered #" + msg.arg1 + ": " + txt;
            }
            
            showNotification(txt);
        
            // Normally we would do some work here...  for our sample, we will
            // just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
        
            hideNotification();
            
            Log.i("ServiceStartArguments", "Done with #" + msg.arg1);
            stopSelf(msg.arg1);//스스로를 멈춘다.
        }

    };
    
    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Toast.makeText(this, R.string.service_created,
                Toast.LENGTH_SHORT).show();
        
        // This is who should be launched if the user selects our persistent
        // notification.
        mInvokeIntent = new Intent(this, Controller.class);

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
		//KJK_TALK: looper를 가진 thread를 생성한다.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();//thread의 run함수가 호출된다. entry point
        
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    /* KJK_TALK: startService 로 service를 시작할때 들어오는 intent에서 parameter를 구별하기 위해
      onStartCommand가 항상 한번 실행되게 된다. 
      intent : service 시작시 전달된 intents
      flags : service를 재시작할지 말지 결정하는 flag로서 START_REDELIVER_INTENT, START_NOT_STICKY, START_STICKY
      startId : 
    */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceStartArguments",
                "Starting #" + startId + ": " + intent.getExtras());
        //KJK_TALK: free pool에서 msg를 하나 가져와 설정하고
        Message msg = mServiceHandler.obtainMessage(); 
        msg.arg1 = startId;
        msg.arg2 = flags;
        msg.obj = intent.getExtras();//intent로 넘어온 arguement를 읽어와서 
        mServiceHandler.sendMessage(msg); //msg queue에 넣어준다.
        Log.i("ServiceStartArguments", "Sending: " + msg);
        
        // For the start fail button, we will simulate the process dying
        // for some reason in onStartCommand().

        //그러면 되살아 나지 않는다. 
        if (intent.getBooleanExtra("fail", false)) {
            // Don't do this if we are in a retry... the system will
            // eventually give up if we keep crashing.
            if ((flags&START_FLAG_RETRY) == 0) {
                // Since the process hasn't finished handling the command,
                // it will be restarted with the command again, regardless of
                // whether we return START_REDELIVER_INTENT.
                Process.killProcess(Process.myPid());
            }
        }
        
        // Normally we would consistently return one kind of result...
        // however, here we will select between these two, so you can see
        // how they impact the behavior.  Try killing the process while it
        // is in the middle of executing the different commands.
        // KJK_TALK: 1/2번 경우에는 START_NOT_STICKY 되살리지 않는다, 3번 경우는 START_REDELIVER_INTENT으로 되살린다.
        // getBooleanExtra 함수는 기존값이 있으면 그값을 없으면 default(여기서는 false) 값을 리턴 
        return intent.getBooleanExtra("redeliver", false)
                ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();

        hideNotification();

        // Tell the user we stopped.
        Toast.makeText(ServiceStartArguments.this, R.string.service_destroyed,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification(String text) {
        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.stat_sample, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Controller.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.service_start_arguments_label),
                       text, contentIntent);

        // We show this for as long as our service is processing a command.
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        
        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.service_created, notification);
    }
    
    private void hideNotification() {
        mNM.cancel(R.string.service_created);
    }
    
    // ----------------------------------------------------------------------

    /**
     * Example of explicitly starting the {@link ServiceStartArguments}.
     * 
     * <p>Note that this is implemented as an inner class only keep the sample
     * all together; typically this code would appear in some separate class.
     */
    public static class Controller extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.service_start_arguments_controller);

            // Watch for button clicks.
            Button button = (Button)findViewById(R.id.start1);
            button.setOnClickListener(mStart1Listener);
            button = (Button)findViewById(R.id.start2);
            button.setOnClickListener(mStart2Listener);
            button = (Button)findViewById(R.id.start3);
            button.setOnClickListener(mStart3Listener);
            button = (Button)findViewById(R.id.startfail);
            button.setOnClickListener(mStartFailListener);
            button = (Button)findViewById(R.id.kill);
            button.setOnClickListener(mKillListener);
        }

        private OnClickListener mStart1Listener = new OnClickListener() {
            public void onClick(View v) {
                startService(new Intent(Controller.this,
                        ServiceStartArguments.class)
                                .putExtra("name", "One"));
            }
        };

        private OnClickListener mStart2Listener = new OnClickListener() {
            public void onClick(View v) {
                startService(new Intent(Controller.this,
                        ServiceStartArguments.class)
                                .putExtra("name", "Two"));
            }
        };

        private OnClickListener mStart3Listener = new OnClickListener() {
            public void onClick(View v) {
                startService(new Intent(Controller.this,
                        ServiceStartArguments.class)
                                .putExtra("name", "Three")
                                .putExtra("redeliver", true));
            }
        };

        private OnClickListener mStartFailListener = new OnClickListener() {
            public void onClick(View v) {
                startService(new Intent(Controller.this,
                        ServiceStartArguments.class)
                                .putExtra("name", "Failure")
                                .putExtra("fail", true));
            }
        };

        private OnClickListener mKillListener = new OnClickListener() {
            public void onClick(View v) {
                // This is to simulate the service being killed while it is
                // running in the background.
                //KJK_TALK: 현재 process를 죽이나, ApiDemos 실행시 재실행하도록 등록되어 있어 다시 실행됨
                Process.killProcess(Process.myPid()); 
            }
        };
    }
}


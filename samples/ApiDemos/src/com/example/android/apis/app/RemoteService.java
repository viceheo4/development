/*
  * KJK_TALK APIDEMOS: App-> Service-> Remote Service Controller
 * 다른 process의 main thread내에서 service를 생성하는 예제로서 
 * 현재 process의 종료와 상관없이 동작된다.
 * LocalService의 handler에서 activity로 분기하듯이 service로 분기하는 방식으로 
 * 다른 process의 handler에게 해당 요청을 전달하게 된다.

 
  * KJK_TALK APIDEMOS: App-> Service-> Remote Service Binding
 * App에서 함수를 만들대 callback으로 호출되어야 하면 객체로 전달
 * 해줘야 하므로 익명 class로 만든다. 그러나 그렇게 전달할필요가 없는 경우 
 * 그냥 method로 만든다. 참고로 익명 class는 interface나 class 둘다 가능하다.
 * new [interface|class](){ body}
 * Remote Service 에서 Client를 호출할때는 IRemoteServiceCallback Proxy를 사용
 * Remote Service Binding에서 Server를 호출할때는 IRemoteService Proxy를 사용한다.
 
  
 * KJK_TALK APIDEMOS: App-> Service-> Remote Service Controller-> RemoteService.java
 * KJK_TALK APIDEMOS: App-> Service-> Remote Service Binding-> RemoteService.java
   앞의 예제인 LocalService와 동일하게 시작과 동시에 status bar에 service가 시작되었음을 
 * 알리고, 종료되면 toast를 띄워 알려주는 기능을 하게 된다 
 * 그러나 차이점으로 AndroidManifest.xml 파일에         
 * <service android:name=".app.RemoteService" android:process=":remote">를 기록함으로
 * remote process의 main thread로 동작하게 된다.


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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Stage Hunk Test
// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

/**
 * KJK_TALK: 실제 RemoteService로 새로운 process로 동작
 * This is an example of implementing an application service that runs in a
 * different process than the application.  Because it can be in another
 * process, we must use IPC to interact with it.  The
 * {@link Controller} and {@link Binding} classes
 * show how to interact with the service.
 * 
 * <p>Note that most applications <strong>do not</strong> need to deal with
 * the complexity shown here.  If your application simply has a service
 * running in its own process, the {@link LocalService} sample shows a much
 * simpler way to interact with it.
 */
public class RemoteService extends Service {
    /**
     * This is a list of callbacks that have been registered with the
     * service.  Note that this is package scoped (instead of private) so
     * that it can be accessed more efficiently from inner classes.
     */
    final RemoteCallbackList<IRemoteServiceCallback> mCallbacks
            = new RemoteCallbackList<IRemoteServiceCallback>();
    
    int mValue = 0;
    NotificationManager mNM;
    
    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.
        showNotification();
        
        // While this service is running, it will continually increment a
        // number.  Send the first message that is used to perform the
        // increment.
        mHandler.sendEmptyMessage(REPORT_MSG);
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(R.string.remote_service_started);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.remote_service_stopped, Toast.LENGTH_SHORT).show();
        
        // Unregister all callbacks.
        mCallbacks.kill();
        
        // Remove the next pending message to increment the counter, stopping
        // the increment loop. 현재 msg는 다음 msg를 trigger하므로 
        // msg Queue에서 현재 msg를 제거하면 더이상 오지 않게 된다.
        mHandler.removeMessages(REPORT_MSG);
    }
    
// BEGIN_INCLUDE(exposing_a_service)
    @Override
    public IBinder onBind(Intent intent) {
        // Select the interface to return.  If your service only implements
        // a single interface, you can just return it here without checking
        // the Intent.
        if (IRemoteService.class.getName().equals(intent.getAction())) {
            return mBinder;
        }
        if (ISecondary.class.getName().equals(intent.getAction())) {
            return mSecondaryBinder;
        }
        return null;
    }

    /**
     * The IRemoteInterface is defined through IDL
     */
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        //cb는 IRemoteServiceCallback$Stub$Proxy로 client에게 msg를 보내는함수
        public void registerCallback(IRemoteServiceCallback cb) {
            //RemoteCallbackList.register()호출하여 main thread구동시 cb가 
            //호출될수 잇도록 cb를 등록한다.
            if (cb != null) mCallbacks.register(cb);
        }// ApiDemos:Remote binder thread에서 호출된다.
        public void unregisterCallback(IRemoteServiceCallback cb) {
            if (cb != null) mCallbacks.unregister(cb);
        }
    };

    /**
     * A secondary interface to the service.
     */
    private final ISecondary.Stub mSecondaryBinder = new ISecondary.Stub() {
        public int getPid() {
            return Process.myPid();
        }
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                float aFloat, double aDouble, String aString) {
        }
    };
// END_INCLUDE(exposing_a_service)
    
    private static final int REPORT_MSG = 1;
    
    /**
     * Our Handler used to execute operations on the main thread.  This is used
     * to schedule increments of our value.
     */
    //KJK_TALK: Handler type의 익명 클래스로 handleMessage만을 overiding하였다.
    //sendEmptyMessage에서 msg의 target을 현재 handler로 만듦으서 
    // looper에서 default handler대신에 여기서 정의한 handler가 호출된게 된다.
    private final Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                
                // It is time to bump the value!
                case REPORT_MSG: {
                    // Up it goes.
                    int value = ++mValue;
                    
                    // Broadcast to all clients the new value.
                    final int N = mCallbacks.beginBroadcast();
                    for (int i=0; i<N; i++) {
                        try {
                            mCallbacks.getBroadcastItem(i).valueChanged(value);
                        } catch (RemoteException e) {
                            // The RemoteCallbackList will take care of removing
                            // the dead object for us.
                        }
                    }
                    mCallbacks.finishBroadcast();
                    
                    // Repeat every 1 second.
                    sendMessageDelayed(obtainMessage(REPORT_MSG), 200);
                } break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.remote_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.stat_sample, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Controller.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.remote_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.remote_service_started, notification); //KJK_TALK: notificatin을 날린다.
    }
    
    // ----------------------------------------------------------------------
    
    /**
     * KJK_TALK: client로 remote server를 create하고 destory한다.
     * <p>Example of explicitly starting and stopping the remove service.
     * This demonstrates the implementation of a service that runs in a different
     * process than the rest of the application, which is explicitly started and stopped
     * as desired.</p>
     * 
     * <p>Note that this is implemented as an inner class only keep the sample
     * all together; typically this code would appear in some separate class.
     */
    public static class Controller extends Activity {
    	static int count =0;
    	
    	@Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.remote_service_controller);

            // Watch for button clicks.
            Button button = (Button)findViewById(R.id.start);
            button.setOnClickListener(mStartListener);
            button = (Button)findViewById(R.id.stop);
            button.setOnClickListener(mStopListener);
        }

        private OnClickListener mStartListener = new OnClickListener() {
            public void onClick(View v) {
            	count ++;
            	// Make sure the service is started.  It will continue running
                // until someone calls stopService().
                // We use an action code here, instead of explictly supplying
                // the component name, so that other packages can replace
                // the service.
                //KJK_TALK: intent를 explicit하게 component name을 명시하지 않고
                //implicit action으로 지정하엿다. 받은 녀석은 androidmanifest.xml에 기록해줘야한다.
                //<action android:name="com.example.android.apis.app.REMOTE_SERVICE" />
                startService(new Intent(
                        "com.example.android.apis.app.REMOTE_SERVICE"));
            }
        };

        private OnClickListener mStopListener = new OnClickListener() {
            public void onClick(View v) {
            	count--;
                // Cancel a previous call to startService().  Note that the
                // service will not actually stop at this point if there are
                // still bound clients.
                stopService(new Intent(
                        "com.example.android.apis.app.REMOTE_SERVICE"));
            }
        };
    }
    
    // ----------------------------------------------------------------------
    
    /**
     * KJK_TALK: client로 remote server와 binding할수 있는 interface를 가지고 있다.
     * Example of binding and unbinding to the remote service.
     * This demonstrates the implementation of a service which the client will
     * bind to, interacting with it through an aidl interface.</p>
     * 
     * <p>Note that this is implemented as an inner class only keep the sample
     * all together; typically this code would appear in some separate class.
     */
 // BEGIN_INCLUDE(calling_a_service)
    public static class Binding extends Activity {
        /** The primary interface we will be calling on the service. */
        IRemoteService mService = null;
        /** Another interface we use on the service. */
        ISecondary mSecondaryService = null;
        
        Button mKillButton;
        TextView mCallbackText;

        private boolean mIsBound;

        /**
         * Standard initialization of this activity.  Set up the UI, then wait
         * for the user to poke it before doing anything.
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.remote_service_binding);

            // Watch for button clicks.
            Button button = (Button)findViewById(R.id.bind);
            button.setOnClickListener(mBindListener);
            button = (Button)findViewById(R.id.unbind);
            button.setOnClickListener(mUnbindListener);
            mKillButton = (Button)findViewById(R.id.kill);
            mKillButton.setOnClickListener(mKillListener);
            mKillButton.setEnabled(false);
            
            mCallbackText = (TextView)findViewById(R.id.callback);
            mCallbackText.setText("Not attached.");
        }

    /** KJK_TALK: 서버와 통신에 사용될 instance 를 생성한다.
     * Class for interacting with the main interface of the service.
     *///KJK_TALK: interface를 상속받는 익명클래스(connection을 관리)를 생성한다
        private ServiceConnection mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className,
                	IBinder service) { //msg.callback으로 handler에서 호출된다.
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  We are communicating with our
                // service through an IDL interface, so get a client-side
                // representation of that from the raw service object.
                mService = IRemoteService.Stub.asInterface(service); //KJK_TALK: 연결된 service를 얻어온다.
                mKillButton.setEnabled(true);
                mCallbackText.setText("Attached.");

                // We want to monitor the service for as long as we are
                // connected to it.
            	try {//KJK_TALK: 연결된 service를 통해 callback object를 등록하여 
                	mService.registerCallback(mCallback); //차후에 server로 부터 cb이 IDL을 통해 호출되게 만든다.
            	} catch (RemoteException e) { //IRemoteService$Stub$Proxy.registerCallback(IRemoteServiceCallback)가 호출된다.
                    // In this case the service has crashed before we could even
                    // do anything with it; we can count on soon being
                    // disconnected (and then reconnected if it can be restarted)
                    // so there is no need to do anything here.
                }
                
                // As part of the sample, tell the user what happened.
                Toast.makeText(Binding.this, R.string.remote_service_connected,
                        Toast.LENGTH_SHORT).show();
        	}//KJK_TALK: connection이 맺어지면 호출된다.

            public void onServiceDisconnected(ComponentName className) {
                // This is called when the connection with the service has been
                // unexpectedly disconnected -- that is, its process crashed.
                mService = null;
                mKillButton.setEnabled(false);
                mCallbackText.setText("Disconnected.");

                // As part of the sample, tell the user what happened.
                Toast.makeText(Binding.this, R.string.remote_service_disconnected,
                        Toast.LENGTH_SHORT).show();
       	     }//connection이 강제로 연결해제되면 호출된다.
        };

        /**
         * Class for interacting with the secondary interface of the service.
     	*///KJK_TALK: 서버와 통신에 사용될 instance를 생성한다.
        private ServiceConnection mSecondaryConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className,
                    IBinder service) {
                // Connecting to a secondary interface is the same as any
                // other interface.
                mSecondaryService = ISecondary.Stub.asInterface(service);
                mKillButton.setEnabled(true);
        	}//connection이 맺어지면 호출된다.

            public void onServiceDisconnected(ComponentName className) {
                mSecondaryService = null;
                mKillButton.setEnabled(false);
        	}//connection이 강제로 연결해제되면 호출된다.
        };

        private OnClickListener mBindListener = new OnClickListener() {
            public void onClick(View v) {
                // Establish a couple connections with the service, binding
                // by interface names.  This allows other applications to be
                // installed that replace the remote service by implementing
                // the same interface.
                bindService(new Intent(IRemoteService.class.getName()),
                        mConnection, Context.BIND_AUTO_CREATE);
                bindService(new Intent(ISecondary.class.getName()),
                        mSecondaryConnection, Context.BIND_AUTO_CREATE);
                mIsBound = true;
                mCallbackText.setText("Binding.");
            }
        };

        private OnClickListener mUnbindListener = new OnClickListener() {
            public void onClick(View v) {
                if (mIsBound) {
                    // If we have received the service, and hence registered with
                    // it, then now is the time to unregister.
                    if (mService != null) {
                        try {
                            mService.unregisterCallback(mCallback);
                        } catch (RemoteException e) {
                            // There is nothing special we need to do if the service
                            // has crashed.
                        }
                    }
                    
                    // Detach our existing connection.
                	unbindService(mConnection); //정상적인 연결해제 
                    unbindService(mSecondaryConnection);
                    mKillButton.setEnabled(false);
                    mIsBound = false;
                    mCallbackText.setText("Unbinding.");
                }
            }
        };

        private OnClickListener mKillListener = new OnClickListener() {
            public void onClick(View v) {
                // To kill the process hosting our service, we need to know its
                // PID.  Conveniently our service has a call that will return
                // to us that information.
                if (mSecondaryService != null) {
                    try {
                        int pid = mSecondaryService.getPid();
                        // Note that, though this API allows us to request to
                        // kill any process based on its PID, the kernel will
                        // still impose standard restrictions on which PIDs you
                        // are actually able to kill.  Typically this means only
                        // the process running your application and any additional
                        // processes created by that app as shown here; packages
                        // sharing a common UID will also be able to kill each
                        // other's processes.
                    	Process.killProcess(pid); //KJK_TALK: 비정상적인 해제
                        mCallbackText.setText("Killed service process.");
                    } catch (RemoteException ex) {
                        // Recover gracefully from the process hosting the
                        // server dying.
                        // Just for purposes of the sample, put up a notification.
                        Toast.makeText(Binding.this,
                                R.string.remote_call_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        
        // ----------------------------------------------------------------------
        // Code showing how to deal with callbacks.
        // ----------------------------------------------------------------------
        
        /**
         * This implementation is used to receive callbacks from the remote
         * service.
     	*/ //KJK_TALK: cb가 호출되도록 binder 객체를 생성한다.
        private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
            /**
             * This is called by the remote service regularly to tell us about
             * new values.  Note that IPC calls are dispatched through a thread
             * pool running in each process, so the code executing here will
             * NOT be running in our main thread like most other things -- so,
             * to update the UI, we need to use a Handler to hop over there.
        	 *///KJK_TALK: 단 이렇게 호출되는 cb는 current process의 main thread에서 호출되지 않고
        	public void valueChanged(int value) { // binder thread에서 호출된다.그러므로 msg로 main thread로 보낸다.
        		mHandler.sendMessage(mHandler.obtainMessage(BUMP_MSG, value, 0));
            }
        };
        
        private static final int BUMP_MSG = 1;
        
        private Handler mHandler = new Handler() {
            @Override public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BUMP_MSG:
                        mCallbackText.setText("Received from service: " + msg.arg1);
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
            
        };
    }
// END_INCLUDE(calling_a_service)
}

/*
 * KJK_TALK APIDEMOS: App-> Notification-> Status Bar->StatusBarNotifications.java
 * StatusBarNotifications.java에서 해당 icon을 click했을경우 notification panel에
 * 등록되는데, 이때 해당 list를 click하면 icon 그림 activity를 띄워준다.
 * image자체를 하나의 Activity로 띄운다. 현재 image Activity 까지는 ApiDemos에서 실행된다.


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
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Activity used by StatusBarNotification to show the notification to the user.
 */
public class NotificationDisplay extends Activity implements View.OnClickListener {
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle icicle) {
        // Be sure to call the super class.
        super.onCreate(icicle);

        // Have the system blur any windows behind this one.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //relative layout을 만들고
        RelativeLayout container = new RelativeLayout(this);
        
        ImageButton button = new ImageButton(this);
        button.setImageResource(getIntent().getIntExtra("moodimg", 0));
        button.setOnClickListener(this);

        //만들어진 relative layout에 layoutparam을 붙이고
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        //layout에 button과 layoutparam을 붙인다.
        container.addView(button, lp);
        
        setContentView(container);
    }

    public void onClick(View v) {
        // The user has confirmed this notification, so remove it.
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                .cancel(R.layout.status_bar_notifications);
        
        // Pressing on the button brings the user back to our mood ring,
        // as part of the api demos app.  Note the use of NEW_TASK here,
        // since the notification display activity is run as a separate task.
        if (true){ 
            //KJK_CHECK true or disable, activity 실행방법 
            //true: 현재 activity가 contact실행중에 notifcation pannel에서 동작했다면
            //ApiDemos를 찾아서 이전 화면으로 돌아가게 된다. 
            // FLAG_ACTIVITY_NEW_TASK가 있으므로 다른 ApiDemos task로 실행하고 
            // xml에서 StatusBarNotification.java이 launchMode="singleTop"로 설정되어 
            // 있으므로 기존 top act를 재활용하게 된다.

            //false: 그냥 이전화면으로 돌아가니 만약, contact을 실행중에
            //notifcation pannel에서 동작시킨다면 ApiDemos로 돌아가지 않고, 
            //그냥 contact의 이전화면으로 돌아가게 된다. 
            Intent intent = new Intent(this, StatusBarNotifications.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        // We're done.
        finish();
    }
}

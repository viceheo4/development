/*
 * KJK_TALK APIDEMOS: 

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

package com.example.android.apis;

import android.app.Application;

/**
 * This is an example of a {@link android.app.Application} class.  This can
 * be used as a central repository for per-process information about your app;
 * however it is recommended to use singletons for that instead rather than merge
 * all of these globals from across your application into one place here.
 * 
 * In this case, we have not defined any specific work for this Application.
 * 
 * See samples/ApiDemos/tests/src/com.example.android.apis/ApiDemosApplicationTests for an example
 * of how to perform unit tests on an Application object.
 */
public class ApiDemosApplication extends Application {
    @Override
    public void onCreate() {
    	//KJK_TALK: preferenece에 대한 default값을 xml에다 기록하는데,
    	//그 default값을 가져오는 부분으로 여기서는 preference를 
    	//사용하지 않으므로 필요없을것 같다. 
    	//다른 activity에서 해당 preference를 사용한다면 필요하다. 이부분 확인 못함.
    	//true이면 맨처음 초기에 한번 읽어오고, false이면 default값을 매번 읽어오게 된다.
    }
}

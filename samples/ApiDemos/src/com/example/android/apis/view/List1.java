/*
 * KJK_TALK APIDEMOS: Views-> Lists-> 1.Array
  List를 구현할수 있는 Act인 ListActivity 사용법으로 기존 Act에 List기능을 추가한것이다.
  간단하게 setListAdapter만을 호출하고 이때, listlayout에 들어갈 text layout만 (simple_list_item_1)정해주고
  list로 구성할 string을 mStrings에 넣어주면 해당 string으로 list를 구성해준다.


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

package com.example.android.apis.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;


/**
 * A list view example where the 
 * data for the list comes from an array of strings.
 */
public class List1 extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use an existing ListAdapter that will map an array
        // of strings to TextViews,
        //KJK_TALK: ListView에 ArraryAdapter를 이용하여 data를 공급해주면 된다.
        //그러면 내부적으로 관리하는 listview에 ArrayAdapter를 붙이게 된다.
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mStrings));
        //KJK_TALK: 입력한 글자와 match되는 단어가 들어있는 item만 list up되는 기능을 on
        getListView().setTextFilterEnabled(true);
    }

    private String[] mStrings = Cheeses.sCheeseStrings;
}

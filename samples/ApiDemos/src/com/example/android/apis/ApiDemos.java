/*
 * KJK_TALK APIDEMOS: Entry Point 

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

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiDemos extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        //아래 browseIntent에서 넣어준 값이 있으면 읽어와서 
        String path = intent.getStringExtra("com.example.android.apis.Path");
        
        if (path == null) {
            path = "";
        }


        //SimpleAdapter는 2nd param으로 map<string,?>을 item으로 갖는 list를 인자로 받는다.
        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }

    //현재 dir를 기준으로 list에 출력된 data와 그 data가 호출할 intent를 설정하는 역활을 한다.
    protected List<Map<String, Object>> getData(String prefix) {
        //KJK_TALK: Map(key, value)을 item으로 가지는 ArrayList생성 
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        //intent를 하나 만들고, apidemos가 속한 sample category를 설정한후
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);

        PackageManager pm = getPackageManager();
        //위에서 만들어진 intent로 호출될수 있는 모든 activity list를 얻어와 
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        
        if (prefix.equals("")) {
            prefixPath = null;//맨처음 getData call시
        } else {
            prefixPath = prefix.split("/"); //현재 dir 아래에서만 list를 구성하도록 한다.
        }

        //얻어온 activity list의 item 갯수를 세어, 현재 199개 
        int len = list.size();

        //<string, boolean>을 item으로 갖는 HashMap 생성
        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            //list의 첫번째 act를 가져와서 info를 추출.
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            //title 이름을 정하고.
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            
            if (prefix.length() == 0 || label.startsWith(prefix)) {
                //App/Activity/Hello World
                String[] labelPath = label.split("/");
                //App, Activity, Hello World
                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                //마지막 leaf node인경우, 즉 act를 호출하는 경우 
                String className = info.activityInfo.name;	
                    addItem(myData, nextLabel + className.substring(className.lastIndexOf(".")) , activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        //App와 같은 처음에 오는 dir일 경우
                        //App, Media, OS, Contents, Graphics, Text, View .. 등의 첫번째 dir과 
                        //그것의 sub Menu Act를 호출할수 있는 intent를 만들어서 myData에 같이 넣는다. 
                        //이때 enries에도 기록하여 해당 path가 이미 기록된것인지 판단하는데 사용한다.

                        //nextLable:
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        //Collection의 sub class인 list myData를 ordering한다.
        Collections.sort(myData, sDisplayNameComparator);
        
        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
        new Comparator<Map<String, Object>>() {
        private final Collator   collator = Collator.getInstance();

        //HashMap->HashMap$Entry.<String, Object>구조에서 HashMap->HashMap$Entry.<title, dir>
        //을 가지고 정렬을 시도한다. compare는 결국 문자열을 비교한다.
        public int compare(Map<String, Object> map1, Map<String, Object> map2) {
/*            StringBuffer s1 = new StringBuffer(map1.get("title").toString());
            s1.reverse();
            StringBuffer s2 = new StringBuffer(map2.get("title").toString());
            s2.reverse();
        
            return collator.compare(s2.toString(), s1.toString()); */
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    //leaf node인경우 마지막 호출할 act정보를 기록한다.
    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    //leaf node가 아닌경우 ApiDemos를 이용하여 list를 출력하는데,기존점인 dir를 넘겨
    //list가 어디서 부터 출력할지 알려준다.
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, ApiDemos.class);
        result.putExtra("com.example.android.apis.Path", path);
        return result;
    }

    // Map(key, value)을 item으로 가지는 ArrayList --> myData
    // 들어갈 Dir name(ex, App,Contents...) --> name
    // ApiDemos Activity(자기자신을)를 호출할 intent 
    //  --> dir sub menu를 보여줄 act로 최종은 실제 activity가 된다.
    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        //temp.put에서는 temp에다가 <string,Object>로 된 item을 만들어서 put해준다.
        //temp는 항상 Map<string, ?> 로 된 format이어야 한다.
        temp.put("title", name);
        //실제 temp의 item으로 들어가는 <String, Object>는 temp.put method에서 new해준다.
        //ArrayList->HashMap->HashMap$Entry.<String, Object>
        //reference->dir이름->sub dir이름, sub dir은 menu act가 될수도 있고, 최종 act가 될수도 있다 
        temp.put("intent", intent);
        data.add(temp);
    }

    // 현재 menu를 보여주는 list act에서 해당 item을 click했을때 호출
    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);
        //reference->dir이름->sub dir이름, sub dir은 menu act가 될수도 있고, 최종 act가 될수도 있다 
        //그러므로 최종 act면 최종 act를 시작시키고, listact를 상속받은 menu act면 menu act를 생성한다.
        //만약 menu act면 현재 class이므로 oncreate에서 list menu를 draw해주게 되고,
        //최종 act면 각 act의 oncreate를 실행하게 된다.
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}

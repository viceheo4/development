/*
 * KJK_TALK APIDEMOS: App-> Menu-> Inflate from XML  NEEDTOSTUDY
 * XML이 아닌 코드를 사용하여 Spinner(List Combo Box)를 추가하는 방법과 
 * 다양한 종류의 Option Menu를 띄우는 방법을 보여준다.
 * Option Menu(text), Option Menu(icon+text), Option Menu(text)+submenu, 
 * Option Menu(group별로 변경): group별로 동적으로 menu item이 변경된다.
 * Option Menu(text)+submenu(checkable)
 * Option Menu+More+ContextMenu, 
 * Option Menu(ordered), Option Menu(2단계 ordered), xml 입력순서와 다르게 order를 줄수있다.
 * Option Menu(visible): menu상태에서 a,b,c를 누르면 visible,hidden menu가 동작한다.
 * Option Menu(disable)+submenu(disable)
 * 참고: Android에는 3종류의 Menu가 존재한다. Options Menu, Context Menu, SubMenu
 * Option Menu : MenuKey로 동작하는 하단에 존재하는 메뉴
        icon menu: 직사각형 box Table형태 Menu로 최대 6개 item을 지원
        Expanded Menu: 하단에 붙어 있는 List Menu로 최대 6개 item을 지원
   Context Menu : 화면에서 longkey로 띄운 list형태의 Popup menu
   SubMenu: Context Menu와 생김새는 비슷하나 Option/Context Menu에서 sub로 띄운메뉴
    아래 예제에서 R.menu.submenu에서 볼수 있다.


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

import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Demonstrates inflating menus from XML. There are different menu XML resources
 * that the user can choose to inflate. First, select an example resource from
 * the spinner, and then hit the menu button. To choose another, back out of the
 * activity and start over.
 */
public class MenuInflateFromXml extends Activity {
    /**
     * Different example menu resources. 
     */// 선택된 종류에 따라 Option Menu에 출력할 menu resource xml
    private static final int sMenuExampleResources[] = {
        R.menu.title_only, R.menu.title_icon, R.menu.submenu, R.menu.groups,
        R.menu.checkable, R.menu.shortcuts, R.menu.order, R.menu.category_order,
        R.menu.visible, R.menu.disabled
    };
    
    /**
     * Names corresponding to the different example menu resources.
     */// Option Menu를 선택하기 위한 List Combo Box의 item을 설정
    private static final String sMenuExampleNames[] = {
        "Title only", "Title and Icon", "Submenu", "Groups",
        "Checkable", "Shortcuts", "Order", "Category and Order",
        "Visible", "Disabled"
    };
   
    /**
     * Lets the user choose a menu resource.
     */
    private Spinner mSpinner;

    /**
     * Shown as instructions.
     */
    private TextView mInstructionsText;
    
    /**
     * Safe to hold on to this.
     */
    private Menu mMenu;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //KJK_TALK: xml이 아닌 code로 layout을 설정한다.
        // Create a simple layout
        LinearLayout layout = new LinearLayout(this);
        //KJK_TALK: LinearLayout을 수평(수직이 곧 수평)으로 쌓아라. 
        layout.setOrientation(LinearLayout.VERTICAL);
        
        // Create the spinner to allow the user to choose a menu XML 
        //KJK_TALK: Spinner(ListCommbo) 생성, 참고로 여기 사용되는 xml에는 별다른 내용이 없다.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sMenuExampleNames); 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = new Spinner(this);
        // When programmatically creating views, make sure to set an ID
        // so it will automatically save its instance state
        mSpinner.setId(R.id.spinner);
        mSpinner.setAdapter(adapter);//spinner에 List를 붙인다.
        
        // Add the spinner, 위에서 만든 LinearLayout에 child view인 spinner를 붙이고,layoutParam을 생성하여 조정한다.
        layout.addView(mSpinner,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        // Create help text
        mInstructionsText = new TextView(this);
        mInstructionsText.setText(getResources().getString(
                R.string.menu_from_xml_instructions_press_menu));
        
        // Add the help, make it look decent
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);//margin 조정
        //위에서 만든 LinearLayout에 text view를 하나 추가한다.
        layout.addView(mInstructionsText, lp);
        
        // Set the layout as our content view
        // 지금까지 만든 layout을 view에 붙인다. 
        //KJK_TALK: xml로 붙일때는 ID로 붙이므로, 이값을 구별하여 XML inflate 수행한후 붙이게 되나
        //view로 붙일때는 이미 inflate된 intance(by creator)로 붙이게 되므로 그냥 direct로 붙인다
        setContentView(layout);
    }

    //KJK_TALK: menu key를 눌렀을때 어떤 option menu를 띄울지 결정한다 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hold on to this
        mMenu = menu;
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        //해당 menu xml을 읽어와
        inflater.inflate(sMenuExampleResources[mSpinner.getSelectedItemPosition()], menu);
        
        // Disable the spinner since we've already created the menu and the user
        // can no longer pick a different menu XML.
        mSpinner.setEnabled(false);//spinner를 disable시킨다.
        
        // Change instructions
        mInstructionsText.setText(getResources().getString(
                R.string.menu_from_xml_instructions_go_back));
        
        return true;
    }

    //KJK_TALK: optionMenu에서 특정 menu를 선택했을때 혹은 
    //submenu가 있는경우 Platform에서 자동으로 submenu를 display해주고 
    //그 submenu중 item을 다시 click했을경우 다시 onOptionsItemSelected가 호출된다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // For "Title only": Examples of matching an ID with one assigned in
            //                   the XML
            case R.id.jump:
                Toast.makeText(this, "Jump up in the air!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.dive:
                Toast.makeText(this, "Dive into the water!", Toast.LENGTH_SHORT).show();
                return true;

            // For "Groups": Toggle visibility of grouped menu items with
            //               nongrouped menu items
            case R.id.browser_visibility:
                // The refresh item is part of the browser group
                final boolean shouldShowBrowser = !mMenu.findItem(R.id.refresh).isVisible();
                mMenu.setGroupVisible(R.id.browser, shouldShowBrowser);
                break;
                
            case R.id.email_visibility:
                // The reply item is part of the email group
                final boolean shouldShowEmail = !mMenu.findItem(R.id.reply).isVisible();
                mMenu.setGroupVisible(R.id.email, shouldShowEmail);
                break;
                
            // Generic catch all for all the other menu resources
            default:
                // Don't toast text when a submenu is clicked
                if (!item.hasSubMenu()) {//submenu를 가지지 않으면 toast를 띄운다 
                    Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }
        
        return false;
    }
    
    

}

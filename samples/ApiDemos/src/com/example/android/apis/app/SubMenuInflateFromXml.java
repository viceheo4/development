/*
 * KJK_TALK APIDEMOS: App-> Menu-> Sub Inflate from XML
 * 앞의 예제를 간단하게 동작을 확인하기 위해서 만든예제 
 */

package com.example.android.apis.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * Demonstrates inflating menus from XML. There are different menu XML resources
 * that the user can choose to inflate. First, select an example resource from
 * the spinner, and then hit the menu button. To choose another, back out of the
 * activity and start over.
 */
public class SubMenuInflateFromXml extends Activity {

	private Menu mMenu;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //KJK_TALK: xml이 아닌 code로 layout을 설정한다.
        // Create a simple layout
        
        LinearLayout layout = new LinearLayout(this);
        //KJK_TALK: LinearLayout을 수평(수직이 곧 수평)으로 쌓아라. 
        layout.setOrientation(LinearLayout.VERTICAL);
        
        setContentView(layout);
      
		Button btn1 = new Button(this);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "start", Toast.LENGTH_SHORT).show();
			}
		});
		btn1.setText("Select All");
		layout.addView(btn1);

        /*
        Menu menu;
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        //해당 menu xml을 읽어와
        inflater.inflate(R.menu.submenu, menu);
        */
    }

    //KJK_TALK: menu key를 눌렀을때 어떤 option menu를 띄울지 결정한다 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hold on to this

    	mMenu = menu;
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        //해당 menu xml을 읽어와
        inflater.inflate(R.menu.submenu, menu);

        mMenu.setGroupVisible(R.id.submenu, false);
        
        return true;
    }

    @Override
    public void closeOptionsMenu() {
        super.closeOptionsMenu();
    }

    //KJK_TALK: optionMenu에서 특정 menu를 선택했을때 
    //submenu가 있는경우 Platform에서 자동으로 submenu를 display해주고 
    //그 submenu중 item을 다시 click했을경우 다시 onOptionsItemSelected가 호출된다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
               
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

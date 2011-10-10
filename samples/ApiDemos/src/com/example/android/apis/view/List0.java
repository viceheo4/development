/*
 * KJK_TALK APIDEMOS: Views-> Lists-> 0. TestArray
 앞의 예제와 다르게 미리정의된 xml layout에 넣는것이 아니라 사용자가 정의한 item layout에 
 사용자가 정의한 data를 써서, 해당 value를 넣어 출력하는 예제 



 */

package com.example.android.apis.view;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.android.apis.R;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class List0 extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use our own list adapter, 사용자가 정의한 adapter를 사용하겠다.
        setListAdapter(new SpeechListAdapter(this));
    }


    /**
     * A sample ListAdapter that presents content from arrays of speeches and
     * text.
     * 
     */
    private class SpeechListAdapter extends BaseAdapter {
        public SpeechListAdapter(Context context) {
            mContext = context;
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         * 
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return mcheckflags.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         * 
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         * 
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a SpeechView to hold each row.
         * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        
        public View getView(int position, View convertView, ViewGroup parent) {
            SpeechView sv;
            if (convertView == null) {
                sv = new SpeechView(position, mContext, mcheckflags[position], mtextView[position]);
            } else {
                sv = (SpeechView) convertView;
                sv.setcheckFlags(mcheckflags[position]);
                sv.setDialogue(mtextView[position]);
            }

            return sv;
        }
        
        
		private LayoutInflater rInflater;
        /**
         * Remember our context so we can use it when constructing views.
         */
        private Context mContext;
        
        /**
         * Our data, part 1.
         */
        private boolean[] mcheckflags = 
        {
                false,
                false,
                false,
                false,
                false,
                false, 
                true,
                true
        };
        
        /**
         * Our data, part 2.
         */
        private String[] mtextView = 
        {
                "So shaken as we are, so wan with care," +
                "In forwarding this dear expedience.",
                
                "Hear him but reason in divinity," + 
                "From open haunts and popularity.",

                "I come no more to make you laugh: things now," +
                "A man may weep upon his wedding-day.",
                
                "First, heaven be the record to my speech!" + 
                "What my tongue speaks my right drawn sword may prove.",
                
                "Now is the winter of our discontent" + 
                "Clarence comes.",
                
                "To bait fish withal: if it will feed nothing else," + 
                "will better the instruction.",
                
                "Virtue! a fig! 'tis in ourselves that we are thus" + 
                "you call love to be a sect or scion.",

                "Blow, winds, and crack your cheeks! rage! blow!" + 
                "That make ingrateful man!"
        };
    }
    
    /**
     * We will use a SpeechView to display each speech. It's just a LinearLayout
     * with two text fields.
     *
     */
    
    private class SpeechView extends LinearLayout {
    	View rView;
    	Resources rRes;
    	
    	public SpeechView(final int position, Context context, boolean checkFlag, String words) {
            super(context);
            mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           
            rView = inflater.inflate(R.layout.l_multifrontchecklist_item, null);
            rRes = context.getResources();
            final CheckBox rCheckBox= (CheckBox)rView.findViewById(R.id.CheckBox01);
            TextView rTextView= (TextView)rView.findViewById(R.id.TextView01);
            
            rCheckBox.setChecked(checkFlag);
            rTextView.setText(words);
           
            rCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean aCheckFlag) {
    				if (aCheckFlag) { 
    					rView.setBackgroundColor(rRes.getColor(R.color.solid_yellow));
    				} else {
    					rView.setBackgroundColor(0x00000000);
    				}
    				rCheckBox.setChecked(aCheckFlag);
    			}
    		});

            
            addView(rView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}

    	
        
        /**
         * Convenience method to set the title of a SpeechView
         */
        public void setcheckFlags(boolean checkflags) {
            mcheckFlags.setChecked(checkflags);
        }

        /**
         * Convenience method to set the dialogue of a SpeechView
         */
        public void setDialogue(String words) {
            mtextView.setText(words);
        }
        private WindowManager mWindowManager;
        private CheckBox mcheckFlags;
        private TextView mtextView;
    }
}

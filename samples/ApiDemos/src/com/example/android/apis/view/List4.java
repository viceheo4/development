/*
 * KJK_TALK APIDEMOS: Views-> Lists-> 3. ListAdapter 
 앞의 예제와 다르게 미리정의된 xml layout에 넣는것이 아니라 사용자가 정의한 item layout에 
 사용자가 정의한 data를 써서, 해당 value를 넣어 출력하는 예제 


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

//Need the following import to get access to the app resources, since this
//class is in a sub-package.
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class List4 extends ListActivity {

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
            return mTitles.length;
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
         * 
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            SpeechView sv;
            if (convertView == null) {
                sv = new SpeechView(mContext, mTitles[position],
                        mDialogue[position]);
            } else {
                sv = (SpeechView) convertView;
                sv.setTitle(mTitles[position]);
                sv.setDialogue(mDialogue[position]);
            }

            return sv;
        }

        /**
         * Remember our context so we can use it when constructing views.
         */
        private Context mContext;


        //KJK_TALK: 사용자가 정의한 data 1/2 
        /**
         * Our data, part 1.
         */
        private String[] mTitles = 
        {
                "Henry IV (1)",   
                "Henry V",
                "Henry VIII",       
                "Richard II",
                "Richard III",
                "Merchant of Venice",  
                "Othello",
                "King Lear"
        };
        
        /**
         * Our data, part 2.
         */
        private String[] mDialogue = 
        {
                "So shaken as we are, so wan with care," +
                "Find we a time for frighted peace to pant," +
                 "What yesternight our council did decree" +
                "In forwarding this dear expedience.",
                
                "Hear him but reason in divinity," + 
                "And all-admiring with an inward wish" + 
                "From open haunts and popularity.",

                "I come no more to make you laugh: things now," +
                "That bear a weighty and a serious brow," +
                "Sad, high, and working, full of state and woe," +
                "A man may weep upon his wedding-day.",
                
                "First, heaven be the record to my speech!" + 
                "In the devotion of a subject's love," + 
                "Tendering the precious safety of my prince," + 
                "And wish, so please my sovereign, ere I move," + 
                "What my tongue speaks my right drawn sword may prove.",
                
                "Now is the winter of our discontent" + 
                "Made glorious summer by this sun of York;" + 
                "And all the clouds that lour'd upon our house" + 
                "In the deep bosom of the ocean buried." + 
                "Now are our brows bound with victorious wreaths;" + 
                "Our bruised arms hung up for monuments;" + 
                "Our stern alarums changed to merry meetings," + 
                "Our dreadful marches to delightful measures." + 
                "Grim-visaged war hath smooth'd his wrinkled front;" + 
                "And now, instead of mounting barded steeds" + 
                "To fright the souls of fearful adversaries," + 
                "He capers nimbly in a lady's chamber" + 
                "To the lascivious pleasing of a lute." + 
                "But I, that am not shaped for sportive tricks," + 
                "Nor made to court an amorous looking-glass;" + 
                "I, that am rudely stamp'd, and want love's majesty" + 
                "To strut before a wanton ambling nymph;" + 
                "I, that am curtail'd of this fair proportion," + 
                "Cheated of feature by dissembling nature," + 
                "Deformed, unfinish'd, sent before my time" + 
                "Into this breathing world, scarce half made up," + 
                "And that so lamely and unfashionable" + 
                "That dogs bark at me as I halt by them;" + 
                "Why, I, in this weak piping time of peace," + 
                "Have no delight to pass away the time," + 
                "Unless to spy my shadow in the sun" + 
                "And descant on mine own deformity:" + 
                "And therefore, since I cannot prove a lover," + 
                "To entertain these fair well-spoken days," + 
                "I am determined to prove a villain" + 
                "And hate the idle pleasures of these days." + 
                "Plots have I laid, inductions dangerous," + 
                "By drunken prophecies, libels and dreams," + 
                "To set my brother Clarence and the king" + 
                "In deadly hate the one against the other:" + 
                "And if King Edward be as true and just" + 
                "As I am subtle, false and treacherous," + 
                "This day should Clarence closely be mew'd up," + 
                "About a prophecy, which says that 'G'" + 
                "Of Edward's heirs the murderer shall be." + 
                "Dive, thoughts, down to my soul: here" + 
                "Clarence comes.",
                
                "To bait fish withal: if it will feed nothing else," + 
                "it will feed my revenge. He hath disgraced me, and" + 
                "hindered me half a million; laughed at my losses," + 
                "mocked at my gains, scorned my nation, thwarted my" + 
                "bargains, cooled my friends, heated mine" + 
                "enemies; and what's his reason? I am a Jew. Hath" + 
                "not a Jew eyes? hath not a Jew hands, organs," + 
                "dimensions, senses, affections, passions? fed with" + 
                "the same food, hurt with the same weapons, subject" + 
                "to the same diseases, healed by the same means," + 
                "warmed and cooled by the same winter and summer, as" + 
                "a Christian is? If you prick us, do we not bleed?" + 
                "if you tickle us, do we not laugh? if you poison" + 
                "us, do we not die? and if you wrong us, shall we not" + 
                "revenge? If we are like you in the rest, we will" + 
                "resemble you in that. If a Jew wrong a Christian," + 
                "what is his humility? Revenge. If a Christian" + 
                "wrong a Jew, what should his sufferance be by" + 
                "Christian example? Why, revenge. The villany you" + 
                "teach me, I will execute, and it shall go hard but I" + 
                "will better the instruction.",
                
                "Virtue! a fig! 'tis in ourselves that we are thus" + 
                "or thus. Our bodies are our gardens, to the which" + 
                "our wills are gardeners: so that if we will plant" + 
                "nettles, or sow lettuce, set hyssop and weed up" + 
                "thyme, supply it with one gender of herbs, or" + 
                "distract it with many, either to have it sterile" + 
                "with idleness, or manured with industry, why, the" + 
                "power and corrigible authority of this lies in our" + 
                "wills. If the balance of our lives had not one" + 
                "scale of reason to poise another of sensuality, the" + 
                "blood and baseness of our natures would conduct us" + 
                "to most preposterous conclusions: but we have" + 
                "reason to cool our raging motions, our carnal" + 
                "stings, our unbitted lusts, whereof I take this that" + 
                "you call love to be a sect or scion.",

                "Blow, winds, and crack your cheeks! rage! blow!" + 
                "You cataracts and hurricanoes, spout" + 
                "Till you have drench'd our steeples, drown'd the cocks!" + 
                "You sulphurous and thought-executing fires," + 
                "Vaunt-couriers to oak-cleaving thunderbolts," + 
                "Singe my white head! And thou, all-shaking thunder," + 
                "Smite flat the thick rotundity o' the world!" + 
                "Crack nature's moulds, an germens spill at once," + 
                "That make ingrateful man!"
        };
    }
    
    /**
     * We will use a SpeechView to display each speech. It's just a LinearLayout
     * with two text fields.
     *
     */
    private class SpeechView extends LinearLayout {
        public SpeechView(Context context, String title, String words) {
            super(context);

            this.setOrientation(VERTICAL);

            // Here we build the child views in code. They could also have
            // been specified in an XML file.
            //KJK_TALK: LinearLayout을 상속받앗으므로 거기에 textview 2개를 만들고 
            // 만들어진 textview에 초기값과 LayoutParams을 설정하여 붙인다.
            mTitle = new TextView(context);
            mTitle.setText(title);
            //KJK_TALK: 현재 this가 viewgroup이므로 addview가 가능하다.
            addView(mTitle, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            mDialogue = new TextView(context);
            mDialogue.setText(words);
            addView(mDialogue, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        /**
         * Convenience method to set the title of a SpeechView
         */
        public void setTitle(String title) {
            mTitle.setText(title);
        }

        /**
         * Convenience method to set the dialogue of a SpeechView
         */
        public void setDialogue(String words) {
            mDialogue.setText(words);
        }

        private TextView mTitle;
        private TextView mDialogue;
    }
}

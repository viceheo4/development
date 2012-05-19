/*
 * KJK_TALK APIDEMOS: App-> Preferences-> 2. Launching Preferences-> AdvancedPreferences -> Mypreference.java
 * KJK_TALK APIDEMOS: App-> Preferences-> 6. AdvancedPreferences-> AdvancedPreferences -> Mypreference.java
 TextView나 Checkbox가 아닌 User Defined preference를 advanced_preferences.xml에서 정의하여 
 act 시작시 자동으로  MyPreference.java가 생성되게 하고  preference를 click등의 event를 보낼때마다
 listener가 자동으로 반응을 보이도록 한다.


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

package com.example.android.apis.preference;

import com.example.android.apis.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * This is an example of a custom preference type. The preference counts the
 * number of clicks it has received and stores/retrieves it from the storage.
 */
public class MyPreference extends Preference { //KJK_TALK: 그냥  Preference를 상속받았다.
    private int mClickCounter;

    // This is the constructor called by the inflater
    // KJK_TALK: XML을 inflate할때 호출됨을 알수 있다.
    public MyPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        //사용자 정의 layout으로 preference 의 하나의 item으로 사용된다.
        setWidgetLayoutResource(R.layout.preference_widget_mypreference);        
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        // Set our custom views inside the layout
        // xml에서 현재 layout을 사용하겠다고 표시, 
        // 가져온 layout의 textview에 현재 증가된 숫자를 출력하도록 set해준다.
        final TextView myTextView = (TextView) view.findViewById(R.id.mypreference_widget);
        if (myTextView != null) {
            myTextView.setText(String.valueOf(mClickCounter));
        }
    }

    // button 누를 때마다 1 식 증가시키는 method 
    @Override
    protected void onClick() {
        int newValue = mClickCounter + 1;
        // Give the client a chance to ignore this change if they deem it
        // invalid
        if (!callChangeListener(newValue)) {
            // They don't want the value to be set
            return;
        }

        // Increment counter
        mClickCounter = newValue;
        // 저장 
        // Save to persistent storage (this method will make sure this
        // preference should be persistent, along with other useful checks)
        persistInt(mClickCounter);
        
        // Data has changed, notify so UI can be refreshed!
        // KJK_TALK: data가 변경되었을때 알려주는 함수 window의 UpdateData()와 같다.
        notifyChanged();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // This preference type's value type is Integer, so we read the default
        // value from the attributes as an Integer.
        return a.getInteger(index, 0);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            // Restore state
            mClickCounter = getPersistedInt(mClickCounter);
        } else {
            // Set state
            int value = (Integer) defaultValue;
            mClickCounter = value;
            persistInt(value);
        }
    }

    //KJK_TALK: onSaveInstanceState 갑자기 종료되는 상황에 자동적으로 호출되는 method로 
    //차후에 복귀할때 이 정보롤 가지고 복원하게 된다. 
    @Override
    protected Parcelable onSaveInstanceState() {
        /*
         * Suppose a client uses this preference type without persisting. We
         * must save the instance state so it is able to, for example, survive
         * orientation changes.
         */

        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        // Save the instance state, 강제로 종료되었을때도 clickCounter 값을 저장하게 된다.
        final SavedState myState = new SavedState(superState);
        myState.clickCounter = mClickCounter;
        return myState;
    }

    //KJK_TALK: onRestoreInstanceState 갑자기 종료되는 상황에 자동적으로 복원을 위해 호출되는 코드
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mClickCounter = myState.clickCounter;
        notifyChanged();
    }


    //KJK_TALK: preference에서 저장하고자 하는 실제 정보를 담은 class
    /**
     * SavedState, a subclass of {@link BaseSavedState}, will store the state
     * of MyPreference, a subclass of Preference.
     * <p>
     * It is important to always call through to super methods.
     */
    private static class SavedState extends BaseSavedState {
        int clickCounter;

        public SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            clickCounter = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeInt(clickCounter);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}

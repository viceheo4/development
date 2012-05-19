/*
 * KJK_TALK APIDEMOS: App-> Diaglog
 * 다양한 Alert Dialog (Popup)의 종류를 생성해본다.
    Title OK Cancel
    Title TextView Ok Someting Cancel
    Title List
    Title ProgressBar OK Cancel
    Title List RadioSelect OK Cancel            
    Title List CheckBox OK Cancel                                
    Title List CheckBox
    Title TextView TextEdit TextView TextEdit OK Cancel (User Defined)

 * 참고로 AlertDialog의 형제로는 CharacterPickerDialog 가 잇으며
 * 자식으로는  DatePickerDialog, ProgressDialog, TimePickerDialog 가 존재한다.
 * 여기서는 AlertDialog와 ProgressDialog의 이용법을 보여준다.

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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.android.apis.R;

/**
 * Example of how to use an {@link android.app.AlertDialog}.
 * <h3>AlertDialogSamples</h3>

<p>This demonstrates the different ways the AlertDialog can be used.</p>

<h4>Demo</h4>
App/Dialog/Alert Dialog
 
<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/app/AlertDialogSamples.java</td>
 *             <td >The Alert Dialog Samples implementation</td>
 *         </tr>
 *         <tr>
 *             <td >/res/any/layout/alert_dialog.xml</td>
 *             <td >Defines contents of the screen</td>
 *         </tr>
 * </table> 
 */
public class AlertDialogSamples extends Activity {
	public static boolean KJK_DEBUG = true;//false : ori vs true : new
    private static final int DIALOG_YES_NO_MESSAGE = 1;
    private static final int DIALOG_YES_NO_LONG_MESSAGE = 2;
    private static final int DIALOG_LIST = 3;
    private static final int DIALOG_PROGRESS = 4;
    private static final int DIALOG_SINGLE_CHOICE = 5;
    private static final int DIALOG_MULTIPLE_CHOICE = 6;
    private static final int DIALOG_TEXT_ENTRY = 7;
    private static final int DIALOG_MULTIPLE_CHOICE_CURSOR = 8;
    private static final int DIALOG_YES_NO_ULTRA_LONG_MESSAGE = 9;
    private static final int DIALOG_YES_NO_OLD_SCHOOL_MESSAGE = 10;
    private static final int DIALOG_YES_NO_HOLO_LIGHT_MESSAGE = 11;

    private static final int MAX_PROGRESS = 100;
    
    private ProgressDialog mProgressDialog;
    private int mProgress;
    private Handler mProgressHandler;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
		//Titie YES NO 
		case DIALOG_YES_NO_MESSAGE:
        // KJK_TALK: 생성후 바로 method call을 하나로 --> new 생성자().method(); 아래와 같이 첫번재 case는 6단계로 변경될수 잇다.
		// KJK_TALK PATTERN: 이경우 new와 각각의 method들은 모두 같은 type(builder)를 반환해야 한다.
		    if (KJK_DEBUG) {//현재 act에 AlertDialog에 사용할 builder를 만들고
                AlertDialog.Builder rBuilder=new AlertDialog.Builder(AlertDialogSamples.this);
                rBuilder.setIcon(R.drawable.alert_dialog_icon); //icon을 설정하고
                rBuilder.setTitle(R.string.alert_dialog_two_buttons_title);//title도 설정하고
                rBuilder.setPositiveButton
                    (R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int whichButton) {}
                            }//default button에 "OK" 문자열과 Listener(CB)를 등록한다.
                    );
                rBuilder.setNegativeButton
                    (R.string.alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {  }
                            }//default button에 "NO" 문자열과 Listener(CB)를 등록한다.
                    );
                return rBuilder.create();//위에서 설정한 builder로 제공된 arg를 받아 Dialog를 만든다.

            }else{
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create();
        case DIALOG_YES_NO_OLD_SCHOOL_MESSAGE:
            return new AlertDialog.Builder(AlertDialogSamples.this, AlertDialog.THEME_TRADITIONAL)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
            }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .create();
        case DIALOG_YES_NO_HOLO_LIGHT_MESSAGE:
            return new AlertDialog.Builder(AlertDialogSamples.this, AlertDialog.THEME_HOLO_LIGHT)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .create();
        case DIALOG_YES_NO_LONG_MESSAGE:
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_two_buttons_msg)
                .setMessage(R.string.alert_dialog_two_buttons2_msg)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    
                        /* User clicked OK so do some stuff */
                    }
                })
                .setNeutralButton(R.string.alert_dialog_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Something so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create();
        case DIALOG_YES_NO_ULTRA_LONG_MESSAGE:
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_two_buttons_msg)
                .setMessage(R.string.alert_dialog_two_buttons2ultra_msg)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                })
                .setNeutralButton(R.string.alert_dialog_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Something so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create();
        //Title List
        case DIALOG_LIST:
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setTitle(R.string.select_dialog)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    //List Dialog를 click시 해당 item 획득 
                        /* User clicked so do some stuff */
                        String[] items = getResources().getStringArray(R.array.select_dialog_items);
                        new AlertDialog.Builder(AlertDialogSamples.this)
                                .setMessage("You selected: " + which + " , " + items[which])
                                .show();
                    }
                })
                .create();
        case DIALOG_PROGRESS:
            mProgressDialog = new ProgressDialog(AlertDialogSamples.this);
            mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
            mProgressDialog.setTitle(R.string.select_dialog);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(MAX_PROGRESS);
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    getText(R.string.alert_dialog_hide), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Yes so do some stuff */
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getText(R.string.alert_dialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            });
            return mProgressDialog;
        //Title List RadioSelect OK Cancel            
        case DIALOG_SINGLE_CHOICE:
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_single_choice)
                .setSingleChoiceItems(R.array.select_dialog_items2, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked on a radio button do some stuff */
                    }
                })
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Yes so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked No so do some stuff */
                    }
                })
               .create();
        //Title List CheckBox OK Cancel  
        //참고로 multichoice는 checkbox 뿐만 아니라 radio button도 가능하다. 
        //즉, property로 설정이 가능하다.
		case DIALOG_MULTIPLE_CHOICE:
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIcon(R.drawable.ic_popup_reminder)
                .setTitle(R.string.alert_dialog_multi_choice)
                .setMultiChoiceItems(R.array.select_dialog_items3,
                        new boolean[]{false, true, false, true, false, false, false},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton,
                                    boolean isChecked) {

                                /* User clicked on a check box do some stuff */
                            }
                        })
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Yes so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked No so do some stuff */
                    }
                })
               .create();
			   //Title TextView TextEdit OK Cancel
        //KJK_TALK: User Defined Popup이 가능하다.
            case DIALOG_MULTIPLE_CHOICE_CURSOR:
                String[] projection = new String[] {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.SEND_TO_VOICEMAIL
                };
                Cursor cursor = managedQuery(ContactsContract.Contacts.CONTENT_URI,
                        projection, null, null, null);
                return new AlertDialog.Builder(AlertDialogSamples.this)
                    .setIcon(R.drawable.ic_popup_reminder)
                    .setTitle(R.string.alert_dialog_multi_choice_cursor)
                    .setMultiChoiceItems(cursor,
                            ContactsContract.Contacts.SEND_TO_VOICEMAIL,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton,
                                        boolean isChecked) {
                                    Toast.makeText(AlertDialogSamples.this,
                                            "Readonly Demo Only - Data will not be updated",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                   .create();
        case DIALOG_TEXT_ENTRY:
			// This example shows how to add a custom layout to an AlertDialog
			// 현재 view에 매달린 LayoutInflater를 가져온다.
			LayoutInflater factory = LayoutInflater.from(this);
            // 가져온 LayoutInflater로 XML을 parsing하고 그 결과를 view로 가져온다.
            final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
            // comtomized ui를 가져오고, 나머지는 alert dialog를 그대로 이용.
            return new AlertDialog.Builder(AlertDialogSamples.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
					// 가져온 view를 현재 dialog에 입히고
                .setTitle(R.string.alert_dialog_text_entry)
                .setView(textEntryView)// button추가 및 설정
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    
                        /* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked cancel so do some stuff */
                    }
                })
                .create();
        }
        return null;
    }

    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView(int)} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alert_dialog);
		// 해당 button을 누르면 해당 showDialog를 호출하고 그러면 onCreateDialog가 발생하도록 되어 잇다.
        /* Display a text message with yes/no buttons and handle each message as well as the cancel action */
        Button twoButtonsTitle = (Button) findViewById(R.id.two_buttons);
        twoButtonsTitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_MESSAGE);
            }
        });
        
        /* Display a long text message with yes/no buttons and handle each message as well as the cancel action */
        Button twoButtons2Title = (Button) findViewById(R.id.two_buttons2);
        twoButtons2Title.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_LONG_MESSAGE);
            }
        });
        
        
        /* Display an ultra long text message with yes/no buttons and handle each message as well as the cancel action */
        Button twoButtons2UltraTitle = (Button) findViewById(R.id.two_buttons2ultra);
        twoButtons2UltraTitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_ULTRA_LONG_MESSAGE);
            }
        });


        /* Display a list of items */
        Button selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_LIST);
            }
        });
        
        /* Display a custom progress bar */
        Button progressButton = (Button) findViewById(R.id.progress_button);
        progressButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);
				mProgressDialog.setProgress(0);// progressDialog경우, 초기값 설정
				if (KJK_DEBUG) {
					mProgressHandler.sendMessage(mProgressHandler
							.obtainMessage(0, 1, 0));
				} else {
                mProgress = 0;
                mProgressDialog.setProgress(0);
                mProgressHandler.sendEmptyMessage(0);
            }
			}
        });
        
        /* Display a radio button group */
        Button radioButton = (Button) findViewById(R.id.radio_button);
        radioButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_SINGLE_CHOICE);
            }
        });
        
        /* Display a list of checkboxes */
        Button checkBox = (Button) findViewById(R.id.checkbox_button);
        checkBox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_MULTIPLE_CHOICE);
            }
        });
        
        /* Display a list of checkboxes, backed by a cursor */
        Button checkBox2 = (Button) findViewById(R.id.checkbox_button2);
        checkBox2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_MULTIPLE_CHOICE_CURSOR);
            }
        });

        /* Display a text entry dialog */
        Button textEntry = (Button) findViewById(R.id.text_entry_button);
        textEntry.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TEXT_ENTRY);
            }
        });
        
        /* Two points, in the traditional theme */
        Button twoButtonsOldSchoolTitle = (Button) findViewById(R.id.two_buttons_old_school);
        twoButtonsOldSchoolTitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_OLD_SCHOOL_MESSAGE);
            }
        });
        
        /* Two points, in the light holographic theme */
        Button twoButtonsHoloLightTitle = (Button) findViewById(R.id.two_buttons_holo_light);
        twoButtonsHoloLightTitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_HOLO_LIGHT_MESSAGE);
            }
        });
        
        mProgressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
				if (KJK_DEBUG) {
					if (msg.arg1 >= MAX_PROGRESS)
						mProgressDialog.dismiss(); // progressDialog 없애기
					else {
						mProgressDialog.incrementProgressBy(1);// 하나 증가시키고
						// 다음증가를 위해 100 milesec이후에 0을 send
						mProgressHandler.sendEmptyMessageDelayed(msg.arg1++,
								100);
					}
				} else {
					if (mProgress >= MAX_PROGRESS)
						mProgressDialog.dismiss(); // progressDialog 없애기
					else {
						mProgress++;
						mProgressDialog.incrementProgressBy(1);// 하나 증가시키고
						// 다음증가를 위해 100 milesec이후에 0을 send
						mProgressHandler.sendEmptyMessageDelayed(0, 100);
					}

				}
			}
		};
	}
}

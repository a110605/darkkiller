package aaaaa.dark_killer;

import aaaaa.dark_killer.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChooseChar extends Activity {

	private ImageButton Button_Server, Button_Client;
	private RelativeLayout eTV3;
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Button_Server = (ImageButton)findViewById(R.id.Server);
		Button_Client = (ImageButton)findViewById(R.id.Client);
		eTV3 = (RelativeLayout)findViewById(R.id.eTV3);
		mp=MediaPlayer.create(this,R.raw.start);
		mp.setLooping(true);
		mp.start();
		
		OnClickListener listener1 = new OnClickListener() {
			// ???s?w?qonClick????k
			public void onClick(View v) {
				try {
					Intent intent = new Intent();

					intent.setClassName("aaaaa.dark_killer",
							"aaaaa.dark_killer.BuildServer");// Package
																	// Name ,
																	// Package
																	// Name.Class
																	// Name

					// ???
					Bundle bundle = new Bundle();
					bundle.putString("STR_INPUT", "?D??H??}?l??????C");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1);
					
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		};
		OnClickListener listener2 = new OnClickListener() { // ???

			@SuppressLint("ShowToast")
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setClassName("aaaaa.dark_killer",
							"aaaaa.dark_killer.ClientConnect");// Package
																		// Name
																		// ,
																		// Package
																		// Name.Class
																		// Name

					// ???
					Bundle bundle = new Bundle();
					bundle.putString("STR_INPUT", "???????JIP?i?J????C");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1);
					
				} catch (Exception e) {
					// Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		};
		Button_Server.setOnClickListener(listener1);
		Button_Client.setOnClickListener(listener2);
		
		/*************************OnTouch Listener*************************/
		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					eTV3.setBackgroundResource(R.drawable.b11); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					eTV3.setBackgroundResource(R.drawable.b1); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		
		OnTouchListener Olistener2 = new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					eTV3.setBackgroundResource(R.drawable.b22); // 按下时候的背景图
		            } 
				else if (event.getAction() == MotionEvent.ACTION_UP){   
					eTV3.setBackgroundResource(R.drawable.b1); // 按下时候的背景图
		            } 
		        return false;
			}
		};

		
		Button_Server.setOnTouchListener(Olistener1);
		Button_Client.setOnTouchListener(Olistener2);
		/**************************************************************************/
		
	}//End of onCreate
	

	@Override
	protected void onRestart() {
		super.onRestart();

		mp=MediaPlayer.create(this,R.raw.start);
		mp.setLooping(true);
		mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.stop();
	}
}
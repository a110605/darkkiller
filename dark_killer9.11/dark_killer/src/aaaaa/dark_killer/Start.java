package aaaaa.dark_killer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start extends Activity {

	// private Intent intent = new
	// Intent("com.angel.Android.MUSIC");//璊raw��

	private int[] myImageIds = { R.drawable.c50, R.drawable.c51,
			R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
			R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59,
			R.drawable.c60, R.drawable.c61, R.drawable.c62, R.drawable.c63,
			R.drawable.c64, R.drawable.c65, R.drawable.c66, R.drawable.c67,
			R.drawable.c68, };
	LinearLayout layout;
	public Button before;
	BeforeStart bs;
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		final Vibrator myVibrator = (Vibrator) getApplication()
				.getSystemService(Service.VIBRATOR_SERVICE);

		// startService(intent);

		final ImageButton IB_Start;
		final ImageButton IB_explain;
		final ImageButton IB_leave;
		IB_Start = (ImageButton) findViewById(R.id.aB_start);
		IB_explain = (ImageButton) findViewById(R.id.aB_explain);
		IB_leave = (ImageButton) findViewById(R.id.aB_exit);

		mp=MediaPlayer.create(this,R.raw.start);
		mp.setLooping(true);
		mp.start();
		
		
		/************************* Onclick Listener *************************/
		OnClickListener listener1 = new OnClickListener() { // 開始
			public void onClick(View v) {
				try {

					Intent intent = new Intent();

					intent.setClassName("aaaaa.dark_killer",
							"aaaaa.dark_killer.ChooseChar");
					startActivity(intent);

				} catch (Exception e) {
					// Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		};
		OnClickListener listener2 = new OnClickListener() { // 解說
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setClassName("aaaaa.dark_killer",
							"aaaaa.dark_killer.Explain");// Package Name ,
															// Package
															// Name.Class
															// Name

					startActivity(intent);
				} catch (Exception e) {
					// Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		};
		OnClickListener listener3 = new OnClickListener() { // 離開
			public void onClick(View v) {

				AlertDialog alertDialog = getAlertDialog("確定退出嗎?");
				alertDialog.show();

				// 關閉整個執行程序
			}
		};
		/**************************************************************************/

		/** 以下幫Button加入Listener **/
		IB_Start.setOnClickListener(listener1);
		IB_explain.setOnClickListener(listener2);
		IB_leave.setOnClickListener(listener3);

		/************************* OnTouch Listener *************************/
		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					IB_Start.setBackgroundResource(R.drawable.z11); // 按下时候的背景图

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					IB_Start.setBackgroundResource(R.drawable.z1); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		OnTouchListener Olistener2 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					IB_explain.setBackgroundResource(R.drawable.z21); // 按下时候的背景图

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					IB_explain.setBackgroundResource(R.drawable.z2); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		OnTouchListener Olistener3 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					IB_leave.setBackgroundResource(R.drawable.z31); // 按下时候的背景图

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					IB_leave.setBackgroundResource(R.drawable.z3); // 松开时恢复原来的背景图
				}
				return false;
			}
		};

		IB_Start.setOnTouchListener(Olistener1);
		IB_explain.setOnTouchListener(Olistener2);
		IB_leave.setOnTouchListener(Olistener3);
		/**************************************************************************/

	}

	/***** Sliderdrawer handler *****/
	private AlertDialog getAlertDialog(String title) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				Start.this);
		builder.setTitle(title);

		builder.setIcon(R.drawable.iccc);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				stopAccessPoint();
				android.os.Process.killProcess(android.os.Process.myPid());
				
			
			}
		});
		// 設定Negative按鈕資料
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 按下按鈕時顯示快顯

			}
		});
		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	public void onBackPressed() {
		AlertDialog alertDialog = getAlertDialog("確定退出嗎?");
		alertDialog.show();
		return;
	}

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
	
	private void stopAccessPoint() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifi.setWifiEnabled(false);
		WifiConfiguration netConfig = new WifiConfiguration();
		netConfig.SSID = "DarkKillerHotspot";

		Method method;
		try {

			method = wifi.getClass().getMethod(
					"setWifiApEnabled",
					WifiConfiguration.class, boolean.class);
			method.invoke(wifi, netConfig, false);

			Toast.makeText(Start.this,
					"成功開啟【DarkKillerHotspot】",
					Toast.LENGTH_SHORT).show();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}

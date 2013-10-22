package aaaaa.dark_killer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BeforeStart extends Activity {


	TextView touchScreen;
	public static MediaPlayer mp;
	Intent intent1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.before);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Button before = (Button) findViewById(R.id.Before_button);
		touchScreen = (TextView) findViewById(R.id.texts);
		
		
		spark();
		/************************* Onclick Listener *************************/
		OnClickListener listenerBefore = new OnClickListener() { // 開始
			public void onClick(View v) {
				try {
					
						Intent intent = new Intent();
						intent.setClassName("aaaaa.dark_killer",
								"aaaaa.dark_killer.Start");// Package

						startActivity(intent);
						finish();
					
					overridePendingTransition(R.anim.fade, R.anim.hold);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		before.setOnClickListener(listenerBefore);
	}// end oncreate

	

	/******************* 文字閃碩 ***********************/
	private int clo = 0;

	public void spark() {
		// 获取页面textview对象
		Timer timer = new Timer();
		TimerTask taskcc = new TimerTask() {

			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						if (clo == 0) {
							clo = 1;
							touchScreen.setTextColor(Color.TRANSPARENT); // 透明
						} else {
							if (clo == 1) {
								clo = 2;
								touchScreen.setTextColor(Color.WHITE);
							} else {
								clo = 0;
								touchScreen.setTextColor(Color.WHITE);
							}
						}
					}
				});
			}
		};
		timer.schedule(taskcc, 1, 500); // 参数分别是delay（多长时间后执行），duration（执行间隔）
	}// end spark()
}

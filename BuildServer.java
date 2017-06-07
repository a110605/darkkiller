package com.example.dark_killer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.net.*;
import java.io.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BuildServer extends Activity {
	// ImageButton Button_Server,Button_Client ;
	public static boolean bIfDebug = true;
	public static String TAG = "DarkKiller_DEBUG";
	public static String strDebugPreFix = "DarkKiller";
	public static int ip = 0;
	private Handler mhandler = new Handler();
	// Socket Server
	public static ServerSocket mServerSocket01; // Runnable1
	private int intSocketServerPort = 8080;
	// UI
	public EditText text_peopleNum;
	public CheckBox if_event;
	private ImageButton button_build;
	Button sendmess, startgame;
	private TextView textView_Status, serverIP, serverstatus;
	private String connect_status = "";
	public static int peoNum;// 連線人數
	public int[] rndChar;// 儲存人數陣列
	public boolean listen = true;
	// Socket Array用來定義新的加入者 Socket
	private ArrayList<MultiServerThread> players;
	public int id = 1;
	Socket mSocket01;
	String strTemp01 = "";
	int numclient;

	private String[] characters =
    { 
			"Killer",
			"Cupid",
			"Guard",
			"Idiot",
			"Prophet",
			"Villager",
			"Witch"
    };  
	
	/** 產生新的亂數 **/
	/** Called when the activity is first created. */
	@SuppressWarnings("static-access")
	protected void onCreate(Bundle savedInstanceState) {
		// 處理在4.0上 NetworkOnMainThreadException 例外處理
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.d);

		button_build = (ImageButton) findViewById(R.id.Server_build);
		serverIP = (TextView) findViewById(R.id.serverIP);
		serverstatus = (TextView) findViewById(R.id.serverstatus);
		text_peopleNum = (EditText) findViewById(R.id.editText_peopleNum);// 人數
		if_event = (CheckBox) findViewById(R.id.if_check);
		sendmess = (Button) findViewById(R.id.sendmess);
		startgame = (Button) findViewById(R.id.startgame);
		startgame.setEnabled(false);
		Context mcontext;
		mcontext = this.getApplicationContext();
		mcontext.getApplicationContext();

		if (isWifi(this)) {
			serverIP.setText(getWiFiIPAddress(null));// 有開Wifi
		} else if (is3G(this)) {
			serverIP.setText(getLocalIpAddress());// 有開3G
		} else {
			serverIP.setText(getNOINTERNETLocalIpAddress());// 否則讀hotspot
		}// end if

		startgame.setOnClickListener(new Button.OnClickListener() {
			// 當按下按鈕的時候觸發以下的方法
			public void onClick(View v) {
				// 如果已連接則
				Intent intent = new Intent();

				intent.setClassName("com.example.dark_killer",
						"com.example.dark_killer.ServerMaster");// Package Name
																// , Package
																// Name.Class
																// Name

				// 傳值
				Bundle bundle = new Bundle();
				bundle.putString("STR_INPUT", "主持人請開始準備房間。");
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});

		// 設定按鈕的事件
		sendmess.setOnClickListener(new Button.OnClickListener() {
			// 當按下按鈕的時候觸發以下的方法
			public void onClick(View v) {
				// 如果已連接則
				if (mSocket01.isConnected()) {
					BufferedWriter bw;
					try {
						// 取得網路輸出串流
						bw = new BufferedWriter(new OutputStreamWriter(
								mSocket01.getOutputStream()));
						// 寫入訊息
						bw.write(text_peopleNum.getText().toString() + "\n");
						// 立即發送
						bw.flush();
						serverstatus.setText(text_peopleNum.getText()
								.toString());
					} catch (IOException e) {
					}
					// 將文字方塊清空
					text_peopleNum.setText("");
				}

			}
		});

		// 建立server
		button_build.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				numclient = Integer.parseInt(text_peopleNum.getText()
						.toString());
				players = new ArrayList<MultiServerThread>();
				serverstatus.setText("等待" + numclient + "人連線中...");
				Thread thread = new Thread(new mSocketConnectRunnable1());
				thread.start();
			}// end onClick
		});// end setOnClickListener
	}// end oncreate

	public class mSocketConnectRunnable1 implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				mServerSocket01 = new ServerSocket(intSocketServerPort);// ports=8080
				mServerSocket01.setReuseAddress(true);

				// 當連線一直存在時，持續執行

				while (!mServerSocket01.isClosed()) {
					// 等待接受Socket Client端連接
					mSocket01 = mServerSocket01.accept();
					Log.i(TAG, strDebugPreFix + "等待連線中 ");
					// 每多一client就new thread
					MultiServerThread t = new MultiServerThread(mSocket01, id);// end
																				// thread
					t.start();
					id++;
					players.add(t);
				}// end while
			} catch (Exception e) {
			}// end try
		}// end run
	}// end mSocketConnectRunnable1

	// MultiServerThread負責與Client的對話
	public class MultiServerThread extends Thread {
		private Socket socket = null;
		public int ID;

		// constructor
		public MultiServerThread(Socket sockets, int IDs) {
			this.socket = sockets;
			this.ID = IDs;
		}

		public void run() {
			try {
				Log.i(TAG, "thread" + ID + "已產生");

				mhandler.post(new Runnable() {
					public void run() {
						serverstatus.setText("Client" + ID + "成功連線\n");
						if (players.size() == numclient) {
							startgame.setEnabled(true);
						}
					}
				});// end post
					// 傳送Hello,Client
				
				if (mSocket01.isConnected()) {
					int random = (int)(Math.random() *6);//用來產生 0~6的隨機數
					BufferedWriter bw;
					bw = new BufferedWriter(new OutputStreamWriter(
							mSocket01.getOutputStream()));
					bw.write("Killer");
					bw.flush();
				}
			} catch (Exception e) {
			}// end try
		}// end run

	}// end MultiServerThread

	// 取得沒開網路的hotspot IP
	public String getNOINTERNETLocalIpAddress() {
		String ipaddress = "";
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}

	// 取得WIFI IP
	public String getWiFiIPAddress(Context ctx) {
		String strRet = "";
		WifiManager myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
		int myIP = myWifiInfo.getIpAddress();
		// Log.i(TAG, "WIFI 資訊:" + myIP);
		String ip = String.format("%d.%d.%d.%d", (myIP & 0xff),
				(myIP >> 8 & 0xff), (myIP >> 16 & 0xff), (myIP >> 24 & 0xff));
		return ip;
	}

	// 判斷是否為3G
	public static boolean is3G(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	// 判斷是否為WIFI
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	// 取得3G IP
	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {

						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			return "取不到3G IP";
		}
		return null;
	}

	public void exitActivity(int exitMethod) {
		// throw new RuntimeException("Exit!");
		try {
			switch (exitMethod) {
			case 0:
				System.exit(0);
				break;
			case 1:
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			case 2:
				finish();
				break;
			}
		} catch (Exception e) {
			if (bIfDebug) {
				Log.e(TAG, strDebugPreFix + e.toString());
				e.printStackTrace();
			}
			finish();
		}
	}

	protected void onPause() {
		exitActivity(1);
		super.onPause();
	}
}// end BuildServer
package com.example.dark_killer;

import android.app.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.dark_killer.BuildServer.MultiServerThread;
import com.example.dark_killer.BuildServer.mSocketConnectRunnable1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SocketService extends Service {
	private ArrayList<MultiServerThread> players;
	public static final int SERVERPORT = 8080;
	public static ServerSocket mServerSocket01;
	PrintWriter out;
	Socket mSocket01;
	int id=1;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		Toast.makeText(this, "Service created ...", Toast.LENGTH_SHORT).show();
		players = new ArrayList<MultiServerThread>();
		Thread thread = new Thread(new mSocketConnectRunnable1());
		thread.start();
		return START_STICKY;
	}

	class mSocketConnectRunnable1 implements Runnable {
		@Override
		public void run() {

			try {
				mServerSocket01 = new ServerSocket(8080);// ports=8080
				mServerSocket01.setReuseAddress(true);

				while (!mServerSocket01.isClosed()) {
					mSocket01 = mServerSocket01.accept();
					MultiServerThread t = new MultiServerThread(mSocket01, id);// end
					players.add(t); // thread
					t.start();
					id++;

				}
				// send the message to the server
			} catch (Exception e) {
				Log.e("TCP", "C: Error", e);
			}
		}
	}

	
	public class MultiServerThread extends Thread {
		// 用來產生 0~6的隨機
		private Socket socket = null;
		public int ID;
	   int random = (int) (Math.random() * 6);
		// constructor
		public MultiServerThread(Socket sockets, int IDs) {
			this.socket = sockets;
			this.ID = IDs;
		}
		
		public void run() {
			try {

				if (mSocket01.isConnected()) {
					/**************** 傳送腳色 *****************/
					BufferedWriter bw;
					bw = new BufferedWriter(new OutputStreamWriter(
							mSocket01.getOutputStream()));
					bw.write("\nKiller\n");//傳送事件與腳色
					bw.flush();

					mhandler.post(new Runnable() {
						public void run() {
							if (players.size() == numclient) {
								Toast.makeText(BuildServer.this, "人數已滿，請開始遊戲。",
										Toast.LENGTH_SHORT).show();
								startgame.setEnabled(true);
							}
						}// end run
					});// end post
					while(true){
						BufferedReader br = new BufferedReader(
								new InputStreamReader(
										mSocket01.getInputStream()));
						strTemp01 = br.readLine();
						Log.i(TAG, strTemp01);//strTemp01是收到的clientID
						displayMessage(strTemp01 + ",成功連線");
						clientID[k]=strTemp01;
						
					}//end while
					
				}// end if

			} catch (Exception e) {
			}// end try
		}// end run

	}// end MultiServerThread
	
	
	public void sendMessage(String message) {
		if (out != null) {
			out.println(message);
			out.flush();
		}
	}

	public class LocalBinder extends Binder {
		public SocketService getService() {
			return SocketService.this;
		}
	}

	private final IBinder myBinder = new LocalBinder();

	public IBinder onBind(Intent arg0) {
		return myBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			mSocket01.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSocket01 = null;
	}

}

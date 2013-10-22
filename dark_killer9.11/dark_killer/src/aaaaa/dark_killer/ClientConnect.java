package aaaaa.dark_killer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class ClientConnect extends Activity {
	ChooseChar choosechar;
	int entergame = 0;
	SlidingDrawer fSD1, JSD1, GSD1, ISD1, NSD1;
	RelativeLayout Killer, Cupid, Witch, Villager, Idiot, Guard, Prophet;
	FrameLayout oFLA, oFLV;
	MyVideoView iVV, jVV, kVV, lVV, mVV, nVV, oVV;
	Button oB2, jB2, iB2, mB2, nB2;
	Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8, fsIB9,
			fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16, fsIB17,
			fsIB18, fsIB19, fsIB20;
	// FrameLayout oplaceholder;
	BeforeStart bs1;
	// 殺人影片URL
	Uri video = Uri.parse("android.resource://aaaaa.dark_killer/raw/video");
	MediaPlayer mp;
	public static boolean bIfDebug = true;
	public static String TAG = "DarkKiller DEBUG";
	public static String strDebugPreFix = "DKiller_Client:";
	private int intSocketServerPort = 8081;
	private Handler handler = new Handler();
	// Socket Client
	public static Socket mSocket02;
	// 為了換xml設定的Characters
	public String[] characters = { "Killer", "Cupid", "Guard", "Idiot",
			"Prophet", "Villager", "Witch" };
	private AutoCompleteTextView textView_IP;
	private static AutoCompleteTextView textView_ID;
	public TextView statustext;
	public ImageButton button_client_connect;
	
	private String strTemp02 = "";
	int receventnum;
	int ranChar;
	private int[] myImageIds = { R.drawable.c50, R.drawable.c51,
			R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
			R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59,
			R.drawable.c60, R.drawable.c61, R.drawable.c62, R.drawable.c63,
			R.drawable.c64, R.drawable.c65, R.drawable.c66, R.drawable.c67,
			R.drawable.c68, };

	private int[] characterspicture = { R.drawable.f30, R.drawable.f31,
			R.drawable.f32, R.drawable.f33, R.drawable.f34, R.drawable.f35,
			R.drawable.f36, R.drawable.f30, R.drawable.f30, R.drawable.f30 };

	BufferedWriter bw;
	BufferedReader br;
	// ArrayList<String> recclientID = new ArrayList<String>();
	int recChar, recWho;
	int whereisshe = 0;
	String KclinetID = "這只是test";
	int isconnectover = 0;
	String outerclientID = "";
	String recid1 = "", recid2 = "", recid3 = "", recid4 = "", recid5 = "",
			recid6 = "", recid7 = "", recid8 = "", recid9 = "", recid10 = "",
			recid11 = "", recid12 = "", recid13 = "", recid14 = "",
			recid15 = "", recid16 = "", recid17 = "", recid18 = "",
			recid19 = "", recid20 = "";
	int isClosed = 0;
	int hasevent = 0;
	int peoNum = 0;	
	/** 判斷是否已經動作 **/
	int doKill1 = 0;
	int doKill2 = 0;
	int doKill3 = 0;
	int doKill4 = 0;
	int doGuard = 0;
	int doCupid = 0;
	int doProphet = 0;
	int doHelp = 0;
	int doPoison = 0;	
	String JustTellWitch;
	InetSocketAddress isa;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//textView_IP = (AutoCompleteTextView) findViewById(R.id.gACTV_IP);
		textView_ID = (AutoCompleteTextView) findViewById(R.id.gACTV_ID);
		statustext = (TextView) findViewById(R.id.statustext);
		button_client_connect = (ImageButton) findViewById(R.id.gButton_Connect);

	//	initAutoComplete("history", textView_IP);
		initAutoComplete("history", textView_ID);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ClientConnect.this);
		boolean dialog_status = prefs.getBoolean("dialog_status", false);

	
			AlertDialog alertDialog = getAlertDialog2("【遊戲設定】",
					"請選擇【Wifi設置】，\n連線【DarkKillerHotspot】");
			alertDialog.show();
	
		mp = MediaPlayer.create(this, R.raw.start);
		mp.setLooping(true);
		mp.start();

//		textView_ID.addTextChangedListener(new TextWatcher() {
//			public void afterTextChanged(Editable s) {
//				
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});

		// 打連線用的按鈕 Socket Client connect to Server
		button_client_connect.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				if (wifi.isWifiEnabled()) {
					// wifi is enabled

					try {
						if ( "".equals(textView_ID.getText().toString()
										.trim())) {
							statustext.setText("請輸入暱稱");
						} else {

							//saveHistory("history", textView_IP);
							saveHistory("history", textView_ID);
							Thread thread = new Thread(
									new mSocketConnectRunnable2());
							thread.start();

						}

					} catch (RuntimeException e) {
						Log.i(TAG, "runtime連接錯誤");
						AlertDialog alertDialog = getAlertDialog3("【連線錯誤】",
								"請向主持人確認正確連線碼");
						alertDialog.show();
						
					} finally {

					}
				} else {
					AlertDialog alertDialog = getAlertDialog5("【網路錯誤】",
							"請開啟WIFI後再連線");
					alertDialog.show();
				}

			}
		});// end onclick

		/************************* OnTouch Listener *************************/
		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					button_client_connect.setBackgroundResource(R.drawable.h21); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					button_client_connect.setBackgroundResource(R.drawable.h2); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		button_client_connect.setOnTouchListener(Olistener1);
		/****************************************************************/
	}// end onCreate

	public class mSocketConnectRunnable2 implements Runnable {

		final String clientID = textView_ID.getText().toString();// 取的ClientID
//		final String clientIP = textView_IP.getText().toString();

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				mSocket02 = new Socket();// 建立socket
				isa = new InetSocketAddress("192.168.43.1", intSocketServerPort);
				mSocket02.connect(isa, 100);
				bw = new BufferedWriter(new OutputStreamWriter(
						mSocket02.getOutputStream()));

				br = new BufferedReader(new InputStreamReader(
						mSocket02.getInputStream()));

				if (mSocket02.isConnected()) {
					mp.stop();
					handler.post(new Runnable() {
						public void run() {
							statustext.setText(clientID + ", 成功連線\n");
						}

					});// end post
				}

				/********** 送CLIENTID *******************/
				ClientsendMessage(clientID + "\n");// 傳送ID給Server

				/**************** 收聽 *****************/
				/**1:讀取訊息  2:ID 3:殺人訊息 4:事件  5:按鈕enable 6:預言家  Flag:"+ flag1+" 9: 跳出事件***/
				while (true) {

					String flag1 = br.readLine(); // 讀flag
					Log.i(TAG,"收到的flag:"+flag1);
					switch (Integer.parseInt(flag1)) {
					case 1:
						String rec1 = br.readLine();// 讀角色
						String rec2 = br.readLine();// 讀事件
						String rec3 = br.readLine();// 是否有事件
						peoNum = Integer.parseInt(br.readLine());// 人數
						recChar = Integer.parseInt(rec1);
						receventnum = Integer.parseInt(rec2);
						hasevent = Integer.parseInt(rec3);
						Log.i(TAG, "收到的人物是:" + recChar);
						Log.i(TAG, "收到的事件是:" + receventnum);
						Log.i(TAG, "是否有事件:" + hasevent);

						handler.post(new Runnable() {
							public void run() {
								switch (recChar) {
								case 0:
									new chKiller();
									break;
								case 1:
									 chIdiot();
									break;
								case 2:
									chCupid();
									break;
								case 3:
									chGuard();
									break;
								case 4:
									chProphet();
									break;
								case 5:
									chVillager();
									break;
								case 6:
									new chWitch();
									break;
								case 7:
									new chKiller2();
									break;
								case 8:
									new chKiller3();
									break;
								case 9:
									new chKiller4();
									break;
								}

							}// end run
						});// end post
						break;
					/******************* 收所有人ID *****************/
					case 2:
						String recallid1 = br.readLine();
						String recallid2 = br.readLine();
						String recallid3 = br.readLine();
						String recallid4 = br.readLine();
						String recallid5 = br.readLine();
						String recallid6 = br.readLine();
						String recallid7 = br.readLine();
						String recallid8 = br.readLine();
						String recallid9 = br.readLine();
						String recallid10 = br.readLine();
						String recallid11 = br.readLine();
						String recallid12 = br.readLine();
						String recallid13 = br.readLine();
						String recallid14 = br.readLine();
						String recallid15 = br.readLine();
						String recallid16 = br.readLine();
						String recallid17 = br.readLine();
						String recallid18 = br.readLine();
						String recallid19 = br.readLine();
						String recallid20 = br.readLine();

						recid1 = recallid1;
						recid2 = recallid2;
						recid3 = recallid3;
						recid4 = recallid4;
						recid5 = recallid5;
						recid6 = recallid6;
						recid7 = recallid7;
						recid8 = recallid8;
						recid9 = recallid9;
						recid10 = recallid10;
						recid11 = recallid11;
						recid12 = recallid12;
						recid13 = recallid13;
						recid14 = recallid14;
						recid15 = recallid15;
						recid16 = recallid16;
						recid17 = recallid17;
						recid18 = recallid18;
						recid19 = recallid19;
						recid20 = recallid20;
						Log.i(TAG, "收到的recid1 : " + recid1);
						Log.i(TAG, "收到的recid2 : " + recid2);
						Log.i(TAG, "收到的recid3 : " + recid3);
						Log.i(TAG, "收到的recid4 : " + recid4);
						break;
					case 3:
						String killStatus = br.readLine();
						Log.i(TAG, killStatus);
						if (killStatus.equals("urKilled")) {
							handler.post(new Runnable() {
								public void run() {
									switch (recChar) {
									case 0:
										Killer.setBackgroundResource(R.drawable.o999); // 按下时候的背景图
										if (fSD1.isOpened()) {
											fSD1.animateClose();
										}
										isClosed = 1;

										// oFLV.bringToFront();
										// oVV.setZOrderOnTop(false);
										oVV.setVisibility(View.VISIBLE);
										oVV.requestFocus();
										oVV.start();
										oVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// oVV.setZOrderOnTop(true);
												// oFLA.bringToFront();
												oVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;

									case 1:
										Idiot.setBackgroundResource(R.drawable.l999);
										isClosed = 1;

										lVV.setVisibility(View.VISIBLE);
										lVV.requestFocus();
										lVV.start();
										lVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												lVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});

										break;

									case 2:
										Cupid.setBackgroundResource(R.drawable.i999);
										if (ISD1.isOpened()) {
											ISD1.animateClose();
										}
										isClosed = 1;

										iVV.setVisibility(View.VISIBLE);
										iVV.requestFocus();
										iVV.start();
										iVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												iVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;

									case 3:
										Guard.setBackgroundResource(R.drawable.m999);
										if (GSD1.isOpened()) {
											GSD1.animateClose();
										}
										isClosed = 1;

										mVV.setVisibility(View.VISIBLE);
										mVV.requestFocus();
										mVV.start();
										mVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												mVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;
									case 4:
										Prophet.setBackgroundResource(R.drawable.n999);
										if (NSD1.isOpened()) {
											NSD1.animateClose();
										}
										isClosed = 1;

										nVV.setVisibility(View.VISIBLE);
										nVV.requestFocus();
										nVV.start();
										nVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												nVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;

									case 5:
										Villager.setBackgroundResource(R.drawable.k999);
										isClosed = 1;

										kVV.setVisibility(View.VISIBLE);
										kVV.requestFocus();
										kVV.start();
										kVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												kVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;

									case 6:
										Witch.setBackgroundResource(R.drawable.j999);
										if (JSD1.isOpened()) {
											JSD1.animateClose();
										}
										isClosed = 1;

										jVV.setVisibility(View.VISIBLE);
										jVV.requestFocus();
										jVV.start();
										jVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												jVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;
									case 7:
										Killer.setBackgroundResource(R.drawable.o999); // 按下时候的背景图
										if (fSD1.isOpened()) {
											fSD1.animateClose();
										}
										isClosed = 1;

										// oFLV.bringToFront();
										// oVV.setZOrderOnTop(false);
										oVV.setVisibility(View.VISIBLE);
										oVV.requestFocus();
										oVV.start();
										oVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// oVV.setZOrderOnTop(true);
												// oFLA.bringToFront();
												oVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;
									case 8:
										Killer.setBackgroundResource(R.drawable.o999); // 按下时候的背景图
										if (fSD1.isOpened()) {
											fSD1.animateClose();
										}
										isClosed = 1;

										// oFLV.bringToFront();
										// oVV.setZOrderOnTop(false);
										oVV.setVisibility(View.VISIBLE);
										oVV.requestFocus();
										oVV.start();
										oVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// oVV.setZOrderOnTop(true);
												// oFLA.bringToFront();
												oVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;
									case 9:
										Killer.setBackgroundResource(R.drawable.o999); // 按下时候的背景图
										if (fSD1.isOpened()) {
											fSD1.animateClose();
										}
										isClosed = 1;

										// oFLV.bringToFront();
										// oVV.setZOrderOnTop(false);
										oVV.setVisibility(View.VISIBLE);
										oVV.requestFocus();
										oVV.start();
										oVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// oVV.setZOrderOnTop(true);
												// oFLA.bringToFront();
												oVV.setVisibility(View.INVISIBLE);
												// 播放结束的動作
											}
										});
										break;
									}
								}
							});// end post
						}// endif
						break;

					case 4:
						receventnum = Integer.parseInt(br.readLine());
						break;
					case 5:
						String recTurn = br.readLine();
						Log.i(TAG, "更改按鈕enable，接收到:" + recTurn);
						switch (Integer.parseInt(recTurn)) {
						case 0:
							if (recChar == 0 || recChar == 7 || recChar == 8
									|| recChar == 9)
								killerButtonEnable();

							break;
						case 2:
							if (recChar == 2)
								cupidButtonEnable();

							break;
						case 3:
							if (recChar == 3)
								guardButtonEnable();

							break;
						case 4:
							if (recChar == 4)
								prophetButtonEnable();

							break;
						case 6:
							if (recChar == 6)
								witchButtonEnable();

							break;
						}// end of switch(Integer.parseInt(recTurn))
						break;

					case 6:
						int DivineWho = Integer.parseInt(br.readLine());
						recWho = Integer.parseInt(br.readLine());
						Log.i(TAG, "預言家，接收到 人/角色:" + DivineWho + " / " + recWho);
						if (recChar == 4) {
							switch (DivineWho) {
							case 1:
								prophetDivine1();
								break;
							case 2:
								prophetDivine2();
								break;
							case 3:
								prophetDivine3();
								break;
							case 4:
								prophetDivine4();
								break;
							case 5:
								prophetDivine5();
								break;
							case 6:
								prophetDivine6();
								break;
							case 7:
								prophetDivine7();
								break;
							case 8:
								prophetDivine8();
								break;
							case 9:
								prophetDivine9();
								break;
							case 10:
								prophetDivine10();
								break;
							case 11:
								prophetDivine11();
								break;
							case 12:
								prophetDivine12();
								break;
							case 13:
								prophetDivine13();
								break;
							case 14:
								prophetDivine14();
								break;
							case 15:
								prophetDivine15();
								break;
							case 16:
								prophetDivine16();
								break;
							case 17:
								prophetDivine17();
								break;
							case 18:
								prophetDivine18();
								break;
							case 19:
								prophetDivine19();
								break;
							case 20:
								prophetDivine20();
								break;
							}//end switch
							break;
						}// end if

					case 7:
						String recDisableTurn = br.readLine();
						Log.i(TAG, "更改按鈕Disable，接收到:" + recDisableTurn);
						switch (Integer.parseInt(recDisableTurn)) {
						case 0:
							if (recChar == 0 || recChar == 7 || recChar == 8
									|| recChar == 9)
								killerButtonDisable();
							break;
						case 2:
							if (recChar == 2)
								cupidButtonDisable();
							break;
						case 3:
							if (recChar == 3)
								guardButtonDisable();
							break;
						case 4:
							if (recChar == 4)
								prophetButtonDisable();
							break;
						case 6:
							if (recChar == 6)
								witchButtonDisable();
							break;
						}// end of switch(Integer.parseInt(recTurn))
						break;

					/** 告诉女巫今晚谁死了 **/
					case 8:
						JustTellWitch = br.readLine();
						break;
					}//end switch
				}// end while listen 接聽
				
			} catch (ConnectException err_cn) {
				Log.i(TAG, "Connect errpr.");
				Looper.prepare();
				AlertDialog alertDialog = getAlertDialog3("【連線失敗】",
						"請確定連線DarkKillerHotspot，且主持人已完成設定");
				alertDialog.show();

				Looper.loop();
			} catch (EOFException err_eof) {
				Log.i(TAG, "EOF ");
			} catch (IOException err_io) {
				Log.i(TAG, "IO ERROR");

			} catch (RuntimeException e) {
				Log.i(TAG, "runtime ERROR");
				Looper.prepare();
				AlertDialog alertDialog = getAlertDialog4("【連線錯誤】", "主持人關閉遊戲");
				alertDialog.show();
				Looper.loop();
			}
			catch (Exception e) {
				Log.i(TAG, "伺服器連接錯誤");
				return;
			} finally {
				Log.i(TAG, "ㄏㄏfinally");

			}

		}// end run
	}// end mSocketConnectRunnable2

	public void ClientsendMessage(final String messageToDisplay) {
		try {
			bw.write(messageToDisplay);
			bw.flush();
			Log.i(TAG, "Client傳送 : " + messageToDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end ClientsendMessage

	// 實做autocomplete
	private void initAutoComplete(String field, AutoCompleteTextView auto) {
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString("history", "");
		String[] hisArrays = longhistory.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, hisArrays);
		// 只保留最近的50条的记录
		if (hisArrays.length > 50) {
			String[] newArrays = new String[50];
			System.arraycopy(hisArrays, 0, newArrays, 0, 50);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, newArrays);
		}
		auto.setAdapter(adapter);
		auto.setDropDownHeight(350);
		auto.setThreshold(1);
		auto.setCompletionHint("紀錄");
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if (hasFocus) {
					view.showDropDown();
				}
			}
		});
	}

	private void saveHistory(String field, AutoCompleteTextView auto) {
		String text = auto.getText().toString();
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString(field, "");
		if (!longhistory.contains(text + ",")) {
			StringBuilder sb = new StringBuilder(longhistory);
			sb.insert(0, text + ",");
			sp.edit().putString("history", sb.toString()).commit();
		}
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

	/************************************************** 切換至女巫 ****************************************************/
	public class chWitch {

		// final SlidingDrawer JSD1;
		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
				ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
				ftV63, ftV71, ftV72;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
				fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
				fsIB17, fsIB18, fsIB19, fsIB20;

		chWitch() {
			setContentView(R.layout.j);
			entergame = 1;
			Button jB1 = (Button) findViewById(R.id.jb1);
			jB2 = (Button) findViewById(R.id.jb2);
			JSD1 = (SlidingDrawer) findViewById(R.id.JSD1);

			Witch = (RelativeLayout) findViewById(R.id.witch);

			jVV = (MyVideoView) findViewById(R.id.jVV);
			jVV.setVideoURI(video);

			fsIB1 = (Button) findViewById(R.id.fsIB1);
			fsIB2 = (Button) findViewById(R.id.fsIB2);
			fsIB3 = (Button) findViewById(R.id.fsIB3);
			fsIB4 = (Button) findViewById(R.id.fsIB4);
			fsIB5 = (Button) findViewById(R.id.fsIB5);
			fsIB6 = (Button) findViewById(R.id.fsIB6);
			fsIB7 = (Button) findViewById(R.id.fsIB7);
			fsIB8 = (Button) findViewById(R.id.fsIB8);
			fsIB9 = (Button) findViewById(R.id.fsIB9);
			fsIB10 = (Button) findViewById(R.id.fsIB10);
			fsIB11 = (Button) findViewById(R.id.fsIB11);
			fsIB12 = (Button) findViewById(R.id.fsIB12);
			fsIB13 = (Button) findViewById(R.id.fsIB13);
			fsIB14 = (Button) findViewById(R.id.fsIB14);
			fsIB15 = (Button) findViewById(R.id.fsIB15);
			fsIB16 = (Button) findViewById(R.id.fsIB16);
			fsIB17 = (Button) findViewById(R.id.fsIB17);
			fsIB18 = (Button) findViewById(R.id.fsIB18);
			fsIB19 = (Button) findViewById(R.id.fsIB19);
			fsIB20 = (Button) findViewById(R.id.fsIB20);

			ftV11 = (TextView) findViewById(R.id.ftV11);
			ftV12 = (TextView) findViewById(R.id.ftV12);
			ftV13 = (TextView) findViewById(R.id.ftV13);
			ftV21 = (TextView) findViewById(R.id.ftV21);
			ftV22 = (TextView) findViewById(R.id.ftV22);
			ftV23 = (TextView) findViewById(R.id.ftV23);
			ftV31 = (TextView) findViewById(R.id.ftV31);
			ftV32 = (TextView) findViewById(R.id.ftV32);
			ftV33 = (TextView) findViewById(R.id.ftV33);
			ftV41 = (TextView) findViewById(R.id.ftV41);
			ftV42 = (TextView) findViewById(R.id.ftV42);
			ftV43 = (TextView) findViewById(R.id.ftV43);
			ftV51 = (TextView) findViewById(R.id.ftV51);
			ftV52 = (TextView) findViewById(R.id.ftV52);
			ftV53 = (TextView) findViewById(R.id.ftV53);
			ftV61 = (TextView) findViewById(R.id.ftV61);
			ftV62 = (TextView) findViewById(R.id.ftV62);
			ftV63 = (TextView) findViewById(R.id.ftV63);
			ftV71 = (TextView) findViewById(R.id.ftV71);
			ftV72 = (TextView) findViewById(R.id.ftV72);

			switch (peoNum) {
			case 1:
				fsIB2.setEnabled(false);
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 2:
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 3:
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 4:
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 5:
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 6:
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 7:
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 8:
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 9:
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 10:
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 11:
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 12:
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 13:
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 14:
				fsIB15.setEnabled(false);
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 15:
				fsIB16.setEnabled(false);
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 16:
				fsIB17.setEnabled(false);
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 17:
				fsIB18.setEnabled(false);
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 18:
				fsIB19.setEnabled(false);
				fsIB20.setEnabled(false);
				break;
			case 19:
				fsIB20.setEnabled(false);
				break;
			case 20:
				break;
			}

			jB2.setEnabled(false);

			OnClickListener jShowpop = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hasevent == 1) {
						if (isClosed == 0) {
							ShowPopupEvent();
						} else {
							Toast.makeText(ClientConnect.this, "你已經死了!!!",
									Toast.LENGTH_SHORT).show();
						}// end isClose

					} else {
						Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}// end hasevent
				}
			};
			jB1.setOnClickListener(jShowpop);

			// **mB2打開~女巫的側欄**/

			JSD1.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (JSD1.isOpened())
						((SlidingDrawer) v).animateClose();
					return false;
				}
			});

			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isClosed == 0) {// 沒死
						JSD1.animateOpen();
						/************ 給定每人ID ************/
						ftV11.setText(recid1);
						ftV12.setText(recid2);
						ftV13.setText(recid3);
						ftV21.setText(recid4);
						ftV22.setText(recid5);
						ftV23.setText(recid6);
						ftV31.setText(recid7);
						ftV32.setText(recid8);
						ftV33.setText(recid9);
						ftV41.setText(recid10);
						ftV42.setText(recid11);
						ftV43.setText(recid12);
						ftV51.setText(recid13);
						ftV52.setText(recid14);
						ftV53.setText(recid15);
						ftV61.setText(recid16);
						ftV62.setText(recid17);
						ftV63.setText(recid18);
						ftV71.setText(recid19);
						ftV72.setText(recid20);
					} else {// 被殺
						jB2.setEnabled(false);
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}
				}
			};
			jB2.setOnClickListener(listener2);

			fsIB1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV11.getText().toString() + "】用藥", ftV11
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV12.getText().toString() + "】用藥", ftV12
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV13.getText().toString() + "】用藥", ftV13
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV21.getText().toString() + "】用藥", ftV21
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV22.getText().toString() + "】用藥", ftV22
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV23.getText().toString() + "】用藥", ftV23
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB7.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV31.getText().toString() + "】用藥", ftV31
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB8.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV32.getText().toString() + "】用藥", ftV32
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB9.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV33.getText().toString() + "】用藥", ftV33
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB10.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV41.getText().toString() + "】用藥", ftV41
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB11.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV42.getText().toString() + "】用藥", ftV42
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV43.getText().toString() + "】用藥", ftV43
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB13.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV51.getText().toString() + "】用藥", ftV51
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB14.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV52.getText().toString() + "】用藥", ftV52
							.getText().toString());
					alertDialog.show();
				}
			});
			fsIB15.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV53.getText().toString() + "】用藥", ftV53
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB16.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV61.getText().toString() + "】用藥", ftV61
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB17.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV62.getText().toString() + "】用藥", ftV62
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB18.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV63.getText().toString() + "】用藥", ftV63
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB19.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV71.getText().toString() + "】用藥", ftV71
							.getText().toString());
					alertDialog.show();
				}
			});

			fsIB20.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogWitch("女巫", "對【"
							+ ftV72.getText().toString() + "】用藥", ftV72
							.getText().toString());
					alertDialog.show();
				}
			});
		}
	}

	/************************************************** 切換至殺手 ****************************************************/

	public class chKiller {

		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
				ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
				ftV63, ftV71, ftV72;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
				fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
				fsIB17, fsIB18, fsIB19, fsIB20;
		Button oB1;
		FrameLayout oFLA, oFLV;

		chKiller() {
			setContentView(R.layout.o);
			entergame = 1;
			fSD1 = (SlidingDrawer) findViewById(R.id.fSD1);
			Killer = (RelativeLayout) findViewById(R.id.Killer);

			oFLA = (FrameLayout) findViewById(R.id.oFLA);
			oFLV = (FrameLayout) findViewById(R.id.oFLV);
			// oplaceholder = (FrameLayout) findViewById(R.id.oplaceholder);
			oVV = (MyVideoView) findViewById(R.id.oVV);
			oVV.setVideoURI(video);

			oB1 = (Button) findViewById(R.id.ob1);
			oB2 = (Button) findViewById(R.id.ob2);
			fsIB1 = (Button) findViewById(R.id.fsIB1);
			fsIB2 = (Button) findViewById(R.id.fsIB2);
			fsIB3 = (Button) findViewById(R.id.fsIB3);
			fsIB4 = (Button) findViewById(R.id.fsIB4);
			fsIB5 = (Button) findViewById(R.id.fsIB5);
			fsIB6 = (Button) findViewById(R.id.fsIB6);
			fsIB7 = (Button) findViewById(R.id.fsIB7);
			fsIB8 = (Button) findViewById(R.id.fsIB8);
			fsIB9 = (Button) findViewById(R.id.fsIB9);
			fsIB10 = (Button) findViewById(R.id.fsIB10);
			fsIB11 = (Button) findViewById(R.id.fsIB11);
			fsIB12 = (Button) findViewById(R.id.fsIB12);
			fsIB13 = (Button) findViewById(R.id.fsIB13);
			fsIB14 = (Button) findViewById(R.id.fsIB14);
			fsIB15 = (Button) findViewById(R.id.fsIB15);
			fsIB16 = (Button) findViewById(R.id.fsIB16);
			fsIB17 = (Button) findViewById(R.id.fsIB17);
			fsIB18 = (Button) findViewById(R.id.fsIB18);
			fsIB19 = (Button) findViewById(R.id.fsIB19);
			fsIB20 = (Button) findViewById(R.id.fsIB20);

			ftV11 = (TextView) findViewById(R.id.ftV11);
			ftV12 = (TextView) findViewById(R.id.ftV12);
			ftV13 = (TextView) findViewById(R.id.ftV13);
			ftV21 = (TextView) findViewById(R.id.ftV21);
			ftV22 = (TextView) findViewById(R.id.ftV22);
			ftV23 = (TextView) findViewById(R.id.ftV23);
			ftV31 = (TextView) findViewById(R.id.ftV31);
			ftV32 = (TextView) findViewById(R.id.ftV32);
			ftV33 = (TextView) findViewById(R.id.ftV33);
			ftV41 = (TextView) findViewById(R.id.ftV41);
			ftV42 = (TextView) findViewById(R.id.ftV42);
			ftV43 = (TextView) findViewById(R.id.ftV43);
			ftV51 = (TextView) findViewById(R.id.ftV51);
			ftV52 = (TextView) findViewById(R.id.ftV52);
			ftV53 = (TextView) findViewById(R.id.ftV53);
			ftV61 = (TextView) findViewById(R.id.ftV61);
			ftV62 = (TextView) findViewById(R.id.ftV62);
			ftV63 = (TextView) findViewById(R.id.ftV63);
			ftV71 = (TextView) findViewById(R.id.ftV71);
			ftV72 = (TextView) findViewById(R.id.ftV72);

			switch (peoNum) {
			case 1:
				fsIB2.setEnabled(false);
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 2:
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 3:
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 4:
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 5:
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 6:
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 7:
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 8:
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 9:
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 10:
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 11:
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 12:
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 13:
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 14:
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 15:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 16:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 17:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 18:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 19:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 20:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			}

			oB2.setEnabled(false);
			// fSD1.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// if (fSD1.isOpened())
			// ((SlidingDrawer) v).animateClose();
			// return false;
			// }
			// });
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isClosed == 0) {// 沒死
						fSD1.animateOpen();
						/************ 給定每人ID ************/
						ftV11.setText(recid1);
						ftV12.setText(recid2);
						ftV13.setText(recid3);
						ftV21.setText(recid4);
						ftV22.setText(recid5);
						ftV23.setText(recid6);
						ftV31.setText(recid7);
						ftV32.setText(recid8);
						ftV33.setText(recid9);
						ftV41.setText(recid10);
						ftV42.setText(recid11);
						ftV43.setText(recid12);
						ftV51.setText(recid13);
						ftV52.setText(recid14);
						ftV53.setText(recid15);
						ftV61.setText(recid16);
						ftV62.setText(recid17);
						ftV63.setText(recid18);
						ftV71.setText(recid19);
						ftV72.setText(recid20);
					} else {// 被殺
						oB2.setEnabled(false);
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();

					}
				}
			};
			oB2.setOnClickListener(listener2);

			OnClickListener oShowpop = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hasevent == 1) {
						if (isClosed == 0) {
							ShowPopupEvent();
						} else {
							Toast.makeText(ClientConnect.this, "你已經死了!!!",
									Toast.LENGTH_SHORT).show();
						}// end isClose

					} else {
						Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}// end hasevent
				}
			};

			oB1.setOnClickListener(oShowpop);

			fsIB1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV11.getText().toString() + "】", ftV11
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV12.getText().toString() + "】", ftV12
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV13.getText().toString() + "】", ftV13
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV21.getText().toString() + "】", ftV21
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV22.getText().toString() + "】", ftV22
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV23.getText().toString() + "】", ftV23
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB7.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV31.getText().toString() + "】", ftV31
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB8.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV32.getText().toString() + "】", ftV32
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB9.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV33.getText().toString() + "】", ftV33
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB10.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV41.getText().toString() + "】", ftV41
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB11.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV42.getText().toString() + "】", ftV42
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV43.getText().toString() + "】", ftV43
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB13.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV51.getText().toString() + "】", ftV51
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB14.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV52.getText().toString() + "】", ftV52
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB15.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV53.getText().toString() + "】", ftV53
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB16.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV61.getText().toString() + "】", ftV61
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB17.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV62.getText().toString() + "】", ftV62
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB18.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV63.getText().toString() + "】", ftV63
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB19.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV71.getText().toString() + "】", ftV71
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB20.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller("殺手殺人",
							"確定殺死【" + ftV72.getText().toString() + "】", ftV72
									.getText().toString());
					alertDialog.show();
				}
			});

		}// end constructor

		private AlertDialog GetAlertDialogKiller(String title, String message,
				final String sendid) {
			// 產生一個Builder物件
			android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
					ClientConnect.this);
			// 設定Dialog的標題
			builder.setTitle(title);
			// 設定Dialog的內容
			builder.setMessage(message);
			// 設定Positive按鈕資料
			builder.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (doKill1 < 1) {
								ClientsendMessage("殺手【"
										+ textView_ID.getText().toString()
										+ "】殺死【" + sendid + "】，請點選左上角小圖查看\n" + sendid
										+ "\n" + "urKilledbyKiller1\n"
										+ "killTurn" + "\n");

								Toast.makeText(ClientConnect.this,
										"殺手殺死【" + sendid + "】",
										Toast.LENGTH_SHORT).show();
								doKill1++;
								fSD1.animateClose();
								oB2.setEnabled(false);
							} else
								Toast.makeText(ClientConnect.this,
										"一個晚上只能殺一個人！你會撐死的！", Toast.LENGTH_SHORT)
										.show();
						}
					});
			// 設定Negative按鈕資料
			return builder.create();
		}
	}// end chKiller

	public class chKiller2 {

		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
				ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
				ftV63, ftV71, ftV72;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
				fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
				fsIB17, fsIB18, fsIB19, fsIB20;
		Button oB1;
		FrameLayout oFLA, oFLV;

		chKiller2() {
			setContentView(R.layout.o);
			entergame = 1;
			fSD1 = (SlidingDrawer) findViewById(R.id.fSD1);
			Killer = (RelativeLayout) findViewById(R.id.Killer);

			oFLA = (FrameLayout) findViewById(R.id.oFLA);
			oFLV = (FrameLayout) findViewById(R.id.oFLV);
			// oplaceholder = (FrameLayout) findViewById(R.id.oplaceholder);
			oVV = (MyVideoView) findViewById(R.id.oVV);
			oVV.setVideoURI(video);

			oB1 = (Button) findViewById(R.id.ob1);
			oB2 = (Button) findViewById(R.id.ob2);
			fsIB1 = (Button) findViewById(R.id.fsIB1);
			fsIB2 = (Button) findViewById(R.id.fsIB2);
			fsIB3 = (Button) findViewById(R.id.fsIB3);
			fsIB4 = (Button) findViewById(R.id.fsIB4);
			fsIB5 = (Button) findViewById(R.id.fsIB5);
			fsIB6 = (Button) findViewById(R.id.fsIB6);
			fsIB7 = (Button) findViewById(R.id.fsIB7);
			fsIB8 = (Button) findViewById(R.id.fsIB8);
			fsIB9 = (Button) findViewById(R.id.fsIB9);
			fsIB10 = (Button) findViewById(R.id.fsIB10);
			fsIB11 = (Button) findViewById(R.id.fsIB11);
			fsIB12 = (Button) findViewById(R.id.fsIB12);
			fsIB13 = (Button) findViewById(R.id.fsIB13);
			fsIB14 = (Button) findViewById(R.id.fsIB14);
			fsIB15 = (Button) findViewById(R.id.fsIB15);
			fsIB16 = (Button) findViewById(R.id.fsIB16);
			fsIB17 = (Button) findViewById(R.id.fsIB17);
			fsIB18 = (Button) findViewById(R.id.fsIB18);
			fsIB19 = (Button) findViewById(R.id.fsIB19);
			fsIB20 = (Button) findViewById(R.id.fsIB20);

			ftV11 = (TextView) findViewById(R.id.ftV11);
			ftV12 = (TextView) findViewById(R.id.ftV12);
			ftV13 = (TextView) findViewById(R.id.ftV13);
			ftV21 = (TextView) findViewById(R.id.ftV21);
			ftV22 = (TextView) findViewById(R.id.ftV22);
			ftV23 = (TextView) findViewById(R.id.ftV23);
			ftV31 = (TextView) findViewById(R.id.ftV31);
			ftV32 = (TextView) findViewById(R.id.ftV32);
			ftV33 = (TextView) findViewById(R.id.ftV33);
			ftV41 = (TextView) findViewById(R.id.ftV41);
			ftV42 = (TextView) findViewById(R.id.ftV42);
			ftV43 = (TextView) findViewById(R.id.ftV43);
			ftV51 = (TextView) findViewById(R.id.ftV51);
			ftV52 = (TextView) findViewById(R.id.ftV52);
			ftV53 = (TextView) findViewById(R.id.ftV53);
			ftV61 = (TextView) findViewById(R.id.ftV61);
			ftV62 = (TextView) findViewById(R.id.ftV62);
			ftV63 = (TextView) findViewById(R.id.ftV63);
			ftV71 = (TextView) findViewById(R.id.ftV71);
			ftV72 = (TextView) findViewById(R.id.ftV72);

			switch (peoNum) {
			case 1:
				fsIB2.setEnabled(false);
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 2:
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 3:
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 4:
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 5:
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 6:
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 7:
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 8:
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 9:
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 10:
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 11:
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 12:
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 13:
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 14:
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 15:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 16:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 17:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 18:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 19:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 20:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			}

			oB2.setEnabled(false);
			// fSD1.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// if (fSD1.isOpened())
			// ((SlidingDrawer) v).animateClose();
			// return false;
			// }
			// });
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isClosed == 0) {// 沒死
						fSD1.animateOpen();
						/************ 給定每人ID ************/
						ftV11.setText(recid1);
						ftV12.setText(recid2);
						ftV13.setText(recid3);
						ftV21.setText(recid4);
						ftV22.setText(recid5);
						ftV23.setText(recid6);
						ftV31.setText(recid7);
						ftV32.setText(recid8);
						ftV33.setText(recid9);
						ftV41.setText(recid10);
						ftV42.setText(recid11);
						ftV43.setText(recid12);
						ftV51.setText(recid13);
						ftV52.setText(recid14);
						ftV53.setText(recid15);
						ftV61.setText(recid16);
						ftV62.setText(recid17);
						ftV63.setText(recid18);
						ftV71.setText(recid19);
						ftV72.setText(recid20);
					} else {// 被殺
						oB2.setEnabled(false);
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();

					}
				}
			};
			oB2.setOnClickListener(listener2);

			OnClickListener oShowpop = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hasevent == 1) {
						if (isClosed == 0) {
							ShowPopupEvent();
						} else {
							Toast.makeText(ClientConnect.this, "你已經死了!!!",
									Toast.LENGTH_SHORT).show();
						}// end isClose

					} else {
						Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}// end hasevent
				}
			};

			oB1.setOnClickListener(oShowpop);

			fsIB1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV11.getText().toString() + "】", ftV11
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV12.getText().toString() + "】", ftV12
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV13.getText().toString() + "】", ftV13
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV21.getText().toString() + "】", ftV21
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV22.getText().toString() + "】", ftV22
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV23.getText().toString() + "】", ftV23
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB7.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV31.getText().toString() + "】", ftV31
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB8.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV32.getText().toString() + "】", ftV32
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB9.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV33.getText().toString() + "】", ftV33
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB10.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV41.getText().toString() + "】", ftV41
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB11.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV42.getText().toString() + "】", ftV42
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV43.getText().toString() + "】", ftV43
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB13.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV51.getText().toString() + "】", ftV51
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB14.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV52.getText().toString() + "】", ftV52
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB15.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV53.getText().toString() + "】", ftV53
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB16.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV61.getText().toString() + "】", ftV61
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB17.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV62.getText().toString() + "】", ftV62
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB18.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV63.getText().toString() + "】", ftV63
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB19.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV71.getText().toString() + "】", ftV71
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB20.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller2("殺手殺人",
							"確定殺死【" + ftV72.getText().toString() + "】", ftV72
									.getText().toString());
					alertDialog.show();
				}
			});

		}// end constructor

		private AlertDialog GetAlertDialogKiller2(String title, String message,
				final String sendid) {
			// 產生一個Builder物件
			android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
					ClientConnect.this);
			// 設定Dialog的標題
			builder.setTitle(title);
			// 設定Dialog的內容
			builder.setMessage(message);
			// 設定Positive按鈕資料
			builder.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (doKill2 < 1) {
								ClientsendMessage("殺手【"
										+ textView_ID.getText().toString()
										+ "】殺死【" + sendid + "】，請點選左上角小圖查看\n" + sendid
										+ "\n" + "urKilledbyKiller2\n"
										+ "killTurn" + "\n");

								Toast.makeText(ClientConnect.this,
										"殺手殺死【" + sendid + "】",
										Toast.LENGTH_SHORT).show();
								doKill2++;
								fSD1.animateClose();
								oB2.setEnabled(false);
							} else
								Toast.makeText(ClientConnect.this,
										"一個晚上只能殺一個人！你會撐死的！", Toast.LENGTH_SHORT)
										.show();
						}
					});
			// 設定Negative按鈕資料
			return builder.create();
		}
	}// end chKiller

	public class chKiller3 {

		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
				ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
				ftV63, ftV71, ftV72;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
				fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
				fsIB17, fsIB18, fsIB19, fsIB20;
		Button oB1;
		FrameLayout oFLA, oFLV;

		chKiller3() {
			setContentView(R.layout.o);
			entergame = 1;
			fSD1 = (SlidingDrawer) findViewById(R.id.fSD1);
			Killer = (RelativeLayout) findViewById(R.id.Killer);

			oFLA = (FrameLayout) findViewById(R.id.oFLA);
			oFLV = (FrameLayout) findViewById(R.id.oFLV);
			// oplaceholder = (FrameLayout) findViewById(R.id.oplaceholder);
			oVV = (MyVideoView) findViewById(R.id.oVV);
			oVV.setVideoURI(video);

			oB1 = (Button) findViewById(R.id.ob1);
			oB2 = (Button) findViewById(R.id.ob2);
			fsIB1 = (Button) findViewById(R.id.fsIB1);
			fsIB2 = (Button) findViewById(R.id.fsIB2);
			fsIB3 = (Button) findViewById(R.id.fsIB3);
			fsIB4 = (Button) findViewById(R.id.fsIB4);
			fsIB5 = (Button) findViewById(R.id.fsIB5);
			fsIB6 = (Button) findViewById(R.id.fsIB6);
			fsIB7 = (Button) findViewById(R.id.fsIB7);
			fsIB8 = (Button) findViewById(R.id.fsIB8);
			fsIB9 = (Button) findViewById(R.id.fsIB9);
			fsIB10 = (Button) findViewById(R.id.fsIB10);
			fsIB11 = (Button) findViewById(R.id.fsIB11);
			fsIB12 = (Button) findViewById(R.id.fsIB12);
			fsIB13 = (Button) findViewById(R.id.fsIB13);
			fsIB14 = (Button) findViewById(R.id.fsIB14);
			fsIB15 = (Button) findViewById(R.id.fsIB15);
			fsIB16 = (Button) findViewById(R.id.fsIB16);
			fsIB17 = (Button) findViewById(R.id.fsIB17);
			fsIB18 = (Button) findViewById(R.id.fsIB18);
			fsIB19 = (Button) findViewById(R.id.fsIB19);
			fsIB20 = (Button) findViewById(R.id.fsIB20);

			ftV11 = (TextView) findViewById(R.id.ftV11);
			ftV12 = (TextView) findViewById(R.id.ftV12);
			ftV13 = (TextView) findViewById(R.id.ftV13);
			ftV21 = (TextView) findViewById(R.id.ftV21);
			ftV22 = (TextView) findViewById(R.id.ftV22);
			ftV23 = (TextView) findViewById(R.id.ftV23);
			ftV31 = (TextView) findViewById(R.id.ftV31);
			ftV32 = (TextView) findViewById(R.id.ftV32);
			ftV33 = (TextView) findViewById(R.id.ftV33);
			ftV41 = (TextView) findViewById(R.id.ftV41);
			ftV42 = (TextView) findViewById(R.id.ftV42);
			ftV43 = (TextView) findViewById(R.id.ftV43);
			ftV51 = (TextView) findViewById(R.id.ftV51);
			ftV52 = (TextView) findViewById(R.id.ftV52);
			ftV53 = (TextView) findViewById(R.id.ftV53);
			ftV61 = (TextView) findViewById(R.id.ftV61);
			ftV62 = (TextView) findViewById(R.id.ftV62);
			ftV63 = (TextView) findViewById(R.id.ftV63);
			ftV71 = (TextView) findViewById(R.id.ftV71);
			ftV72 = (TextView) findViewById(R.id.ftV72);

			switch (peoNum) {
			case 1:
				fsIB2.setEnabled(false);
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 2:
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 3:
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 4:
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 5:
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 6:
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 7:
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 8:
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 9:
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 10:
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 11:
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 12:
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 13:
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 14:
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 15:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 16:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 17:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 18:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 19:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 20:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			}

			oB2.setEnabled(false);
			// fSD1.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// if (fSD1.isOpened())
			// ((SlidingDrawer) v).animateClose();
			// return false;
			// }
			// });
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isClosed == 0) {// 沒死
						fSD1.animateOpen();
						/************ 給定每人ID ************/
						ftV11.setText(recid1);
						ftV12.setText(recid2);
						ftV13.setText(recid3);
						ftV21.setText(recid4);
						ftV22.setText(recid5);
						ftV23.setText(recid6);
						ftV31.setText(recid7);
						ftV32.setText(recid8);
						ftV33.setText(recid9);
						ftV41.setText(recid10);
						ftV42.setText(recid11);
						ftV43.setText(recid12);
						ftV51.setText(recid13);
						ftV52.setText(recid14);
						ftV53.setText(recid15);
						ftV61.setText(recid16);
						ftV62.setText(recid17);
						ftV63.setText(recid18);
						ftV71.setText(recid19);
						ftV72.setText(recid20);
					} else {// 被殺
						oB2.setEnabled(false);
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();

					}
				}
			};
			oB2.setOnClickListener(listener2);

			OnClickListener oShowpop = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hasevent == 1) {
						if (isClosed == 0) {
							ShowPopupEvent();
						} else {
							Toast.makeText(ClientConnect.this, "你已經死了!!!",
									Toast.LENGTH_SHORT).show();
						}// end isClose

					} else {
						Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}// end hasevent
				}
			};

			oB1.setOnClickListener(oShowpop);

			fsIB1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV11.getText().toString() + "】", ftV11
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV12.getText().toString() + "】", ftV12
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV13.getText().toString() + "】", ftV13
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV21.getText().toString() + "】", ftV21
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV22.getText().toString() + "】", ftV22
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV23.getText().toString() + "】", ftV23
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB7.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV31.getText().toString() + "】", ftV31
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB8.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV32.getText().toString() + "】", ftV32
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB9.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV33.getText().toString() + "】", ftV33
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB10.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV41.getText().toString() + "】", ftV41
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB11.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV42.getText().toString() + "】", ftV42
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV43.getText().toString() + "】", ftV43
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB13.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV51.getText().toString() + "】", ftV51
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB14.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV52.getText().toString() + "】", ftV52
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB15.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV53.getText().toString() + "】", ftV53
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB16.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV61.getText().toString() + "】", ftV61
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB17.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV62.getText().toString() + "】", ftV62
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB18.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV63.getText().toString() + "】", ftV63
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB19.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV71.getText().toString() + "】", ftV71
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB20.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller3("殺手殺人",
							"確定殺死【" + ftV72.getText().toString() + "】", ftV72
									.getText().toString());
					alertDialog.show();
				}
			});

		}// end constructor

		private AlertDialog GetAlertDialogKiller3(String title, String message,
				final String sendid) {
			// 產生一個Builder物件
			android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
					ClientConnect.this);
			// 設定Dialog的標題
			builder.setTitle(title);
			// 設定Dialog的內容
			builder.setMessage(message);
			// 設定Positive按鈕資料
			builder.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (doKill3 < 1) {
								ClientsendMessage("殺手【"
										+ textView_ID.getText().toString()
										+ "】殺死【" + sendid + "】，請點選左上角小圖查看\n" + sendid
										+ "\n" + "urKilledbyKiller3\n"
										+ "killTurn" + "\n");

								Toast.makeText(ClientConnect.this,
										"殺手殺死【" + sendid + "】",
										Toast.LENGTH_SHORT).show();
								doKill3++;
								fSD1.animateClose();
								oB2.setEnabled(false);
							} else
								Toast.makeText(ClientConnect.this,
										"一個晚上只能殺一個人！你會撐死的！", Toast.LENGTH_SHORT)
										.show();
						}
					});
			// 設定Negative按鈕資料
			return builder.create();
		}
	}// end chKiller

	public class chKiller4 {

		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
				ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
				ftV63, ftV71, ftV72;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
				fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
				fsIB17, fsIB18, fsIB19, fsIB20;
		Button oB1;
		FrameLayout oFLA, oFLV;

		chKiller4() {
			setContentView(R.layout.o);
			entergame = 1;
			fSD1 = (SlidingDrawer) findViewById(R.id.fSD1);
			Killer = (RelativeLayout) findViewById(R.id.Killer);

			oFLA = (FrameLayout) findViewById(R.id.oFLA);
			oFLV = (FrameLayout) findViewById(R.id.oFLV);
			// oplaceholder = (FrameLayout) findViewById(R.id.oplaceholder);
			oVV = (MyVideoView) findViewById(R.id.oVV);
			oVV.setVideoURI(video);

			oB1 = (Button) findViewById(R.id.ob1);
			oB2 = (Button) findViewById(R.id.ob2);
			fsIB1 = (Button) findViewById(R.id.fsIB1);
			fsIB2 = (Button) findViewById(R.id.fsIB2);
			fsIB3 = (Button) findViewById(R.id.fsIB3);
			fsIB4 = (Button) findViewById(R.id.fsIB4);
			fsIB5 = (Button) findViewById(R.id.fsIB5);
			fsIB6 = (Button) findViewById(R.id.fsIB6);
			fsIB7 = (Button) findViewById(R.id.fsIB7);
			fsIB8 = (Button) findViewById(R.id.fsIB8);
			fsIB9 = (Button) findViewById(R.id.fsIB9);
			fsIB10 = (Button) findViewById(R.id.fsIB10);
			fsIB11 = (Button) findViewById(R.id.fsIB11);
			fsIB12 = (Button) findViewById(R.id.fsIB12);
			fsIB13 = (Button) findViewById(R.id.fsIB13);
			fsIB14 = (Button) findViewById(R.id.fsIB14);
			fsIB15 = (Button) findViewById(R.id.fsIB15);
			fsIB16 = (Button) findViewById(R.id.fsIB16);
			fsIB17 = (Button) findViewById(R.id.fsIB17);
			fsIB18 = (Button) findViewById(R.id.fsIB18);
			fsIB19 = (Button) findViewById(R.id.fsIB19);
			fsIB20 = (Button) findViewById(R.id.fsIB20);

			ftV11 = (TextView) findViewById(R.id.ftV11);
			ftV12 = (TextView) findViewById(R.id.ftV12);
			ftV13 = (TextView) findViewById(R.id.ftV13);
			ftV21 = (TextView) findViewById(R.id.ftV21);
			ftV22 = (TextView) findViewById(R.id.ftV22);
			ftV23 = (TextView) findViewById(R.id.ftV23);
			ftV31 = (TextView) findViewById(R.id.ftV31);
			ftV32 = (TextView) findViewById(R.id.ftV32);
			ftV33 = (TextView) findViewById(R.id.ftV33);
			ftV41 = (TextView) findViewById(R.id.ftV41);
			ftV42 = (TextView) findViewById(R.id.ftV42);
			ftV43 = (TextView) findViewById(R.id.ftV43);
			ftV51 = (TextView) findViewById(R.id.ftV51);
			ftV52 = (TextView) findViewById(R.id.ftV52);
			ftV53 = (TextView) findViewById(R.id.ftV53);
			ftV61 = (TextView) findViewById(R.id.ftV61);
			ftV62 = (TextView) findViewById(R.id.ftV62);
			ftV63 = (TextView) findViewById(R.id.ftV63);
			ftV71 = (TextView) findViewById(R.id.ftV71);
			ftV72 = (TextView) findViewById(R.id.ftV72);

			switch (peoNum) {
			case 1:
				fsIB2.setEnabled(false);
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 2:
				fsIB3.setEnabled(false);
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 3:
				fsIB4.setEnabled(false);
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 4:
				fsIB5.setEnabled(false);
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 5:
				fsIB6.setEnabled(false);
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 6:
				fsIB7.setEnabled(false);
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 7:
				fsIB8.setEnabled(false);
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 8:
				fsIB9.setEnabled(false);
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 9:
				fsIB10.setEnabled(false);
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 10:
				fsIB11.setEnabled(false);
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 11:
				fsIB12.setEnabled(false);
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 12:
				fsIB13.setEnabled(false);
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 13:
				fsIB14.setEnabled(false);
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 14:
				fsIB15.setEnabled(false);
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 15:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 16:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 17:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 18:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 19:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			case 20:
				// fsIB16.setEnabled(false);
				// fsIB17.setEnabled(false);
				// fsIB18.setEnabled(false);
				// fsIB19.setEnabled(false);
				// fsIB20.setEnabled(false);
				break;
			}

			oB2.setEnabled(false);
			// fSD1.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// if (fSD1.isOpened())
			// ((SlidingDrawer) v).animateClose();
			// return false;
			// }
			// });
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isClosed == 0) {// 沒死
						fSD1.animateOpen();
						/************ 給定每人ID ************/
						ftV11.setText(recid1);
						ftV12.setText(recid2);
						ftV13.setText(recid3);
						ftV21.setText(recid4);
						ftV22.setText(recid5);
						ftV23.setText(recid6);
						ftV31.setText(recid7);
						ftV32.setText(recid8);
						ftV33.setText(recid9);
						ftV41.setText(recid10);
						ftV42.setText(recid11);
						ftV43.setText(recid12);
						ftV51.setText(recid13);
						ftV52.setText(recid14);
						ftV53.setText(recid15);
						ftV61.setText(recid16);
						ftV62.setText(recid17);
						ftV63.setText(recid18);
						ftV71.setText(recid19);
						ftV72.setText(recid20);
					} else {// 被殺
						oB2.setEnabled(false);
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();

					}
				}
			};
			oB2.setOnClickListener(listener2);

			OnClickListener oShowpop = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hasevent == 1) {
						if (isClosed == 0) {
							ShowPopupEvent();
						} else {
							Toast.makeText(ClientConnect.this, "你已經死了!!!",
									Toast.LENGTH_SHORT).show();
						}// end isClose

					} else {
						Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}// end hasevent
				}
			};

			oB1.setOnClickListener(oShowpop);

			fsIB1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV11.getText().toString() + "】", ftV11
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV12.getText().toString() + "】", ftV12
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV13.getText().toString() + "】", ftV13
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV21.getText().toString() + "】", ftV21
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV22.getText().toString() + "】", ftV22
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV23.getText().toString() + "】", ftV23
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB7.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV31.getText().toString() + "】", ftV31
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB8.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV32.getText().toString() + "】", ftV32
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB9.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV33.getText().toString() + "】", ftV33
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB10.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV41.getText().toString() + "】", ftV41
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB11.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV42.getText().toString() + "】", ftV42
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV43.getText().toString() + "】", ftV43
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB13.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV51.getText().toString() + "】", ftV51
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB14.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV52.getText().toString() + "】", ftV52
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB15.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV53.getText().toString() + "】", ftV53
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB16.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV61.getText().toString() + "】", ftV61
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB17.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV62.getText().toString() + "】", ftV62
									.getText().toString());
					alertDialog.show();
				}
			});

			fsIB18.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV63.getText().toString() + "】", ftV63
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB19.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV71.getText().toString() + "】", ftV71
									.getText().toString());
					alertDialog.show();
				}
			});
			fsIB20.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = GetAlertDialogKiller4("殺手殺人",
							"確定殺死【" + ftV72.getText().toString() + "】", ftV72
									.getText().toString());
					alertDialog.show();
				}
			});

		}// end constructor

		private AlertDialog GetAlertDialogKiller4(String title, String message,
				final String sendid) {
			// 產生一個Builder物件
			android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
					ClientConnect.this);
			// 設定Dialog的標題
			builder.setTitle(title);
			// 設定Dialog的內容
			builder.setMessage(message);
			// 設定Positive按鈕資料
			builder.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (doKill4 < 1) {
								ClientsendMessage("殺手【"
										+ textView_ID.getText().toString()
										+ "】殺死【" + sendid + "】，請點選左上角小圖查看\n" + sendid
										+ "\n" + "urKilledbyKiller4\n"
										+ "killTurn" + "\n");

								Toast.makeText(ClientConnect.this,
										"殺手殺死【" + sendid + "】",
										Toast.LENGTH_SHORT).show();
								doKill4++;
								fSD1.animateClose();
								oB2.setEnabled(false);
							} else
								Toast.makeText(ClientConnect.this,
										"一個晚上只能殺一個人！你會撐死的！", Toast.LENGTH_SHORT)
										.show();
						}
					});
			// 設定Negative按鈕資料
			return builder.create();
		}
	}

	/************************************************** 切換至預言家 ****************************************************/
	public void chProphet() {
		// final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
		// fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
		// fsIB17, fsIB18, fsIB19, fsIB20;
		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32, ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62, ftV63, ftV71, ftV72;
		// final SlidingDrawer NSD1;

		setContentView(R.layout.n);
		entergame = 1;
		NSD1 = (SlidingDrawer) findViewById(R.id.NSD1);
		Prophet = (RelativeLayout) findViewById(R.id.Prophet);

		nVV = (MyVideoView) this.findViewById(R.id.nVV);
		nVV.setVideoURI(video);

		fsIB1 = (Button) findViewById(R.id.fsIB1);
		fsIB2 = (Button) findViewById(R.id.fsIB2);
		fsIB3 = (Button) findViewById(R.id.fsIB3);
		fsIB4 = (Button) findViewById(R.id.fsIB4);
		fsIB5 = (Button) findViewById(R.id.fsIB5);
		fsIB6 = (Button) findViewById(R.id.fsIB6);
		fsIB7 = (Button) findViewById(R.id.fsIB7);
		fsIB8 = (Button) findViewById(R.id.fsIB8);
		fsIB9 = (Button) findViewById(R.id.fsIB9);
		fsIB10 = (Button) findViewById(R.id.fsIB10);
		fsIB11 = (Button) findViewById(R.id.fsIB11);
		fsIB12 = (Button) findViewById(R.id.fsIB12);
		fsIB13 = (Button) findViewById(R.id.fsIB13);
		fsIB14 = (Button) findViewById(R.id.fsIB14);
		fsIB15 = (Button) findViewById(R.id.fsIB15);
		fsIB16 = (Button) findViewById(R.id.fsIB16);
		fsIB17 = (Button) findViewById(R.id.fsIB17);
		fsIB18 = (Button) findViewById(R.id.fsIB18);
		fsIB19 = (Button) findViewById(R.id.fsIB19);
		fsIB20 = (Button) findViewById(R.id.fsIB20);

		ftV11 = (TextView) findViewById(R.id.ftV11);
		ftV12 = (TextView) findViewById(R.id.ftV12);
		ftV13 = (TextView) findViewById(R.id.ftV13);
		ftV21 = (TextView) findViewById(R.id.ftV21);
		ftV22 = (TextView) findViewById(R.id.ftV22);
		ftV23 = (TextView) findViewById(R.id.ftV23);
		ftV31 = (TextView) findViewById(R.id.ftV31);
		ftV32 = (TextView) findViewById(R.id.ftV32);
		ftV33 = (TextView) findViewById(R.id.ftV33);
		ftV41 = (TextView) findViewById(R.id.ftV41);
		ftV42 = (TextView) findViewById(R.id.ftV42);
		ftV43 = (TextView) findViewById(R.id.ftV43);
		ftV51 = (TextView) findViewById(R.id.ftV51);
		ftV52 = (TextView) findViewById(R.id.ftV52);
		ftV53 = (TextView) findViewById(R.id.ftV53);
		ftV61 = (TextView) findViewById(R.id.ftV61);
		ftV62 = (TextView) findViewById(R.id.ftV62);
		ftV63 = (TextView) findViewById(R.id.ftV63);
		ftV71 = (TextView) findViewById(R.id.ftV71);
		ftV72 = (TextView) findViewById(R.id.ftV72);

		switch (peoNum) {
		case 1:
			fsIB2.setEnabled(false);
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 2:
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 3:
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 4:
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 5:
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 6:
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 7:
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 8:
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 9:
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 10:
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 11:
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 12:
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 13:
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 14:
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 15:
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 16:
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 17:
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 18:
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 19:
			fsIB20.setEnabled(false);
			break;
		case 20:
			break;
		}

		Button nB1 = (Button) this.findViewById(R.id.nb1);

		OnClickListener listener1 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasevent == 1) {
					if (isClosed == 0) {
						ShowPopupEvent();
					} else {
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}// end isClose

				} else {
					Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
							Toast.LENGTH_SHORT).show();
				}// end hasevent
			}
		};
		nB1.setOnClickListener(listener1);

		// **mB2打開~守衛的側欄**/
		nB2 = (Button) this.findViewById(R.id.nb2);
		nB2.setEnabled(false);
		NSD1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (NSD1.isOpened())
					((SlidingDrawer) v).animateClose();
				return false;
			}
		});

		// 碰到新令Button的動作，也要開啟側欄
		// 小心，import時有兩個OnClickListener，要用View view的
		OnClickListener listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isClosed == 0) {// 沒死
					NSD1.animateOpen();
					/************ 給定每人ID ************/
					ftV11.setText(recid1);
					ftV12.setText(recid2);
					ftV13.setText(recid3);
					ftV21.setText(recid4);
					ftV22.setText(recid5);
					ftV23.setText(recid6);
					ftV31.setText(recid7);
					ftV32.setText(recid8);
					ftV33.setText(recid9);
					ftV41.setText(recid10);
					ftV42.setText(recid11);
					ftV43.setText(recid12);
					ftV51.setText(recid13);
					ftV52.setText(recid14);
					ftV53.setText(recid15);
					ftV61.setText(recid16);
					ftV62.setText(recid17);
					ftV63.setText(recid18);
					ftV71.setText(recid19);
					ftV72.setText(recid20);
				} else {// 被殺
					nB2.setEnabled(false);
					Toast.makeText(ClientConnect.this, "你已經死了!!!",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		nB2.setOnClickListener(listener2);

		fsIB1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV11.getText().toString() + "】", ftV11
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV12.getText().toString() + "】", ftV12
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV13.getText().toString() + "】", ftV13
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV21.getText().toString() + "】", ftV21
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV22.getText().toString() + "】", ftV22
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV23.getText().toString() + "】", ftV23
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV31.getText().toString() + "】", ftV31
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV32.getText().toString() + "】", ftV32
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV33.getText().toString() + "】", ftV33
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV41.getText().toString() + "】", ftV41
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV42.getText().toString() + "】", ftV42
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV43.getText().toString() + "】", ftV43
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV51.getText().toString() + "】", ftV51
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV52.getText().toString() + "】", ftV52
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV53.getText().toString() + "】", ftV53
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB16.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV61.getText().toString() + "】", ftV61
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB17.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV62.getText().toString() + "】", ftV62
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB18.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV63.getText().toString() + "】", ftV63
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB19.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV71.getText().toString() + "】", ftV71
								.getText().toString());
				alertDialog.show();
			}
		});
		fsIB20.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogProphet("預言家現身",
						"確定占卜【" + ftV72.getText().toString() + "】", ftV72
								.getText().toString());
				alertDialog.show();
			}
		});

	}

	/************************************************** 切換至守衛 ****************************************************/
	public void chGuard() {
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8, fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16, fsIB17, fsIB18, fsIB19, fsIB20;
		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32, ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62, ftV63, ftV71, ftV72;
		// final SlidingDrawer GSD1;

		setContentView(R.layout.m);
		entergame = 1;
		GSD1 = (SlidingDrawer) findViewById(R.id.GSD1);
		Guard = (RelativeLayout) findViewById(R.id.Guard);

		mVV = (MyVideoView) findViewById(R.id.mVV);
		mVV.setVideoURI(video);
		fsIB1 = (Button) findViewById(R.id.fsIB1);
		fsIB2 = (Button) findViewById(R.id.fsIB2);
		fsIB3 = (Button) findViewById(R.id.fsIB3);
		fsIB4 = (Button) findViewById(R.id.fsIB4);
		fsIB5 = (Button) findViewById(R.id.fsIB5);
		fsIB6 = (Button) findViewById(R.id.fsIB6);
		fsIB7 = (Button) findViewById(R.id.fsIB7);
		fsIB8 = (Button) findViewById(R.id.fsIB8);
		fsIB9 = (Button) findViewById(R.id.fsIB9);
		fsIB10 = (Button) findViewById(R.id.fsIB10);
		fsIB11 = (Button) findViewById(R.id.fsIB11);
		fsIB12 = (Button) findViewById(R.id.fsIB12);
		fsIB13 = (Button) findViewById(R.id.fsIB13);
		fsIB14 = (Button) findViewById(R.id.fsIB14);
		fsIB15 = (Button) findViewById(R.id.fsIB15);
		fsIB16 = (Button) findViewById(R.id.fsIB16);
		fsIB17 = (Button) findViewById(R.id.fsIB17);
		fsIB18 = (Button) findViewById(R.id.fsIB18);
		fsIB19 = (Button) findViewById(R.id.fsIB19);
		fsIB20 = (Button) findViewById(R.id.fsIB20);

		ftV11 = (TextView) findViewById(R.id.ftV11);
		ftV12 = (TextView) findViewById(R.id.ftV12);
		ftV13 = (TextView) findViewById(R.id.ftV13);
		ftV21 = (TextView) findViewById(R.id.ftV21);
		ftV22 = (TextView) findViewById(R.id.ftV22);
		ftV23 = (TextView) findViewById(R.id.ftV23);
		ftV31 = (TextView) findViewById(R.id.ftV31);
		ftV32 = (TextView) findViewById(R.id.ftV32);
		ftV33 = (TextView) findViewById(R.id.ftV33);
		ftV41 = (TextView) findViewById(R.id.ftV41);
		ftV42 = (TextView) findViewById(R.id.ftV42);
		ftV43 = (TextView) findViewById(R.id.ftV43);
		ftV51 = (TextView) findViewById(R.id.ftV51);
		ftV52 = (TextView) findViewById(R.id.ftV52);
		ftV53 = (TextView) findViewById(R.id.ftV53);
		ftV61 = (TextView) findViewById(R.id.ftV61);
		ftV62 = (TextView) findViewById(R.id.ftV62);
		ftV63 = (TextView) findViewById(R.id.ftV63);
		ftV71 = (TextView) findViewById(R.id.ftV71);
		ftV72 = (TextView) findViewById(R.id.ftV72);

		switch (peoNum) {
		case 1:
			fsIB2.setEnabled(false);
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 2:
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 3:
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 4:
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 5:
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 6:
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 7:
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 8:
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 9:
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 10:
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 11:
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 12:
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 13:
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 14:
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 15:
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 16:
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 17:
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 18:
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 19:
			fsIB20.setEnabled(false);
			break;
		case 20:
			break;
		}

		Button mB1 = (Button) this.findViewById(R.id.mb1);

		OnClickListener mShowpop = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasevent == 1) {
					if (isClosed == 0) {
						ShowPopupEvent();
					} else {
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}// end isClose

				} else {
					Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
							Toast.LENGTH_SHORT).show();
				}// end hasevent
			}
		};
		mB1.setOnClickListener(mShowpop);

		// **************************以下為守衛側欄用*****************************8
		mB2 = (Button) this.findViewById(R.id.mb2);
		mB2.setEnabled(false);
		GSD1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (GSD1.isOpened())
					((SlidingDrawer) v).animateClose();
				return false;
			}
		});
		OnClickListener listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isClosed == 0) {// 沒死
					/************ 給定每人ID ************/
					ftV11.setText(recid1);
					ftV12.setText(recid2);
					ftV13.setText(recid3);
					ftV21.setText(recid4);
					ftV22.setText(recid5);
					ftV23.setText(recid6);
					ftV31.setText(recid7);
					ftV32.setText(recid8);
					ftV33.setText(recid9);
					ftV41.setText(recid10);
					ftV42.setText(recid11);
					ftV43.setText(recid12);
					ftV51.setText(recid13);
					ftV52.setText(recid14);
					ftV53.setText(recid15);
					ftV61.setText(recid16);
					ftV62.setText(recid17);
					ftV63.setText(recid18);
					ftV71.setText(recid19);
					ftV72.setText(recid20);
					GSD1.animateOpen();
				} else {// 被殺
					mB2.setEnabled(false);
					Toast.makeText(ClientConnect.this, "你已經死了!!!",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		// ........不知為何，兩個自行對調了...
		mB2.setOnClickListener(listener2);

		fsIB1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV11.getText().toString() + "】", ftV11.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV12.getText().toString() + "】", ftV12.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV13.getText().toString() + "】", ftV13.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV21.getText().toString() + "】", ftV21.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV22.getText().toString() + "】", ftV22.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV23.getText().toString() + "】", ftV23.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV31.getText().toString() + "】", ftV31.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV32.getText().toString() + "】", ftV32.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV33.getText().toString() + "】", ftV33.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV41.getText().toString() + "】", ftV41.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV42.getText().toString() + "】", ftV42.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV43.getText().toString() + "】", ftV43.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV51.getText().toString() + "】", ftV51.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV52.getText().toString() + "】", ftV52.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV53.getText().toString() + "】", ftV53.getText()
						.toString());
				alertDialog.show();
			}
		});

		fsIB16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV61.getText().toString() + "】", ftV61.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV62.getText().toString() + "】", ftV62.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB18.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV63.getText().toString() + "】", ftV63.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB19.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV71.getText().toString() + "】", ftV71.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB20.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogGuard("守衛守護", "確定保護【"
						+ ftV72.getText().toString() + "】", ftV72.getText()
						.toString());
				alertDialog.show();
			}
		});
	}

	/************************************************** 切換至白癡 ****************************************************/
	public void chIdiot() {
		setContentView(R.layout.l);
		entergame = 1;
		Button lB1 = (Button) this.findViewById(R.id.lb1);

		lVV = (MyVideoView) findViewById(R.id.lVV);
		lVV.setVideoURI(video);
		Idiot = (RelativeLayout) findViewById(R.id.Idiot);
		OnClickListener lShowpop = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasevent == 1) {
					if (isClosed == 0) {
						ShowPopupEvent();
					} else {
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}// end isClose

				} else {
					Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
							Toast.LENGTH_SHORT).show();
				}// end hasevent
			}
		};
		lB1.setOnClickListener(lShowpop);
	}

	/************************************************** 切換至村民 ****************************************************/
	public void chVillager() {

		setContentView(R.layout.k);
		entergame = 1;
		kVV = (MyVideoView) findViewById(R.id.kVV);
		kVV.setVideoURI(video);
		Villager = (RelativeLayout) findViewById(R.id.startv);
		Button kB1 = (Button) this.findViewById(R.id.kb1);
		OnClickListener kShowpop = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasevent == 1) {
					if (isClosed == 0) {
						ShowPopupEvent();
					} else {
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}// end isClose

				} else {
					Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
							Toast.LENGTH_SHORT).show();
				}// end hasevent
			}
		};
		kB1.setOnClickListener(kShowpop);

	}

	/************************************************** 切換至邱比特 ***************************************************/
	public void chCupid() {
		final TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32, ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62, ftV63, ftV71, ftV72;
		// final SlidingDrawer ISD1;
		final Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8, fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16, fsIB17, fsIB18, fsIB19, fsIB20;
		final Button iB1, iB3;
		setContentView(R.layout.i);
		entergame = 1;
		Cupid = (RelativeLayout) findViewById(R.id.cupid);
		iVV = (MyVideoView) this.findViewById(R.id.iVV);
		iVV.setVideoURI(video);
		fsIB1 = (Button) findViewById(R.id.fsIB1);
		fsIB2 = (Button) findViewById(R.id.fsIB2);
		fsIB3 = (Button) findViewById(R.id.fsIB3);
		fsIB4 = (Button) findViewById(R.id.fsIB4);
		fsIB5 = (Button) findViewById(R.id.fsIB5);
		fsIB6 = (Button) findViewById(R.id.fsIB6);
		fsIB7 = (Button) findViewById(R.id.fsIB7);
		fsIB8 = (Button) findViewById(R.id.fsIB8);
		fsIB9 = (Button) findViewById(R.id.fsIB9);
		fsIB10 = (Button) findViewById(R.id.fsIB10);
		fsIB11 = (Button) findViewById(R.id.fsIB11);
		fsIB12 = (Button) findViewById(R.id.fsIB12);
		fsIB13 = (Button) findViewById(R.id.fsIB13);
		fsIB14 = (Button) findViewById(R.id.fsIB14);
		fsIB15 = (Button) findViewById(R.id.fsIB15);
		fsIB16 = (Button) findViewById(R.id.fsIB16);
		fsIB17 = (Button) findViewById(R.id.fsIB17);
		fsIB18 = (Button) findViewById(R.id.fsIB18);
		fsIB19 = (Button) findViewById(R.id.fsIB19);
		fsIB20 = (Button) findViewById(R.id.fsIB20);

		ftV11 = (TextView) findViewById(R.id.ftV11);
		ftV12 = (TextView) findViewById(R.id.ftV12);
		ftV13 = (TextView) findViewById(R.id.ftV13);
		ftV21 = (TextView) findViewById(R.id.ftV21);
		ftV22 = (TextView) findViewById(R.id.ftV22);
		ftV23 = (TextView) findViewById(R.id.ftV23);
		ftV31 = (TextView) findViewById(R.id.ftV31);
		ftV32 = (TextView) findViewById(R.id.ftV32);
		ftV33 = (TextView) findViewById(R.id.ftV33);
		ftV41 = (TextView) findViewById(R.id.ftV41);
		ftV42 = (TextView) findViewById(R.id.ftV42);
		ftV43 = (TextView) findViewById(R.id.ftV43);
		ftV51 = (TextView) findViewById(R.id.ftV51);
		ftV52 = (TextView) findViewById(R.id.ftV52);
		ftV53 = (TextView) findViewById(R.id.ftV53);
		ftV61 = (TextView) findViewById(R.id.ftV61);
		ftV62 = (TextView) findViewById(R.id.ftV62);
		ftV63 = (TextView) findViewById(R.id.ftV63);
		ftV71 = (TextView) findViewById(R.id.ftV71);
		ftV72 = (TextView) findViewById(R.id.ftV72);

		switch (peoNum) {
		case 1:
			fsIB2.setEnabled(false);
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 2:
			fsIB3.setEnabled(false);
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 3:
			fsIB4.setEnabled(false);
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 4:
			fsIB5.setEnabled(false);
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 5:
			fsIB6.setEnabled(false);
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 6:
			fsIB7.setEnabled(false);
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 7:
			fsIB8.setEnabled(false);
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 8:
			fsIB9.setEnabled(false);
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 9:
			fsIB10.setEnabled(false);
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 10:
			fsIB11.setEnabled(false);
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 11:
			fsIB12.setEnabled(false);
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 12:
			fsIB13.setEnabled(false);
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 13:
			fsIB14.setEnabled(false);
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 14:
			fsIB15.setEnabled(false);
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 15:
			fsIB16.setEnabled(false);
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 16:
			fsIB17.setEnabled(false);
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 17:
			fsIB18.setEnabled(false);
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 18:
			fsIB19.setEnabled(false);
			fsIB20.setEnabled(false);
			break;
		case 19:
			fsIB20.setEnabled(false);
			break;
		case 20:
			break;
		}

		iB1 = (Button) this.findViewById(R.id.ib1);
		OnClickListener iShowpop = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hasevent == 1) {
					if (isClosed == 0) {
						ShowPopupEvent();
					} else {
						Toast.makeText(ClientConnect.this, "你已經死了!!!",
								Toast.LENGTH_SHORT).show();
					}// end isClose

				} else {
					Toast.makeText(ClientConnect.this, "本次遊戲沒有產生事件",
							Toast.LENGTH_SHORT).show();
				}// end hasevent
			}
		};
		iB1.setOnClickListener(iShowpop);

		iB3 = (Button) this.findViewById(R.id.ib3);
		OnClickListener listener3 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowPopupEx();
			}
		};
		iB3.setOnClickListener(listener3);

		/**************************** 此段為側欄用~~~!!! **************************/
		// **mB2打開~守衛的側欄**/
		iB2 = (Button) this.findViewById(R.id.ib2);
		iB2.setEnabled(false);
		ISD1 = (SlidingDrawer) findViewById(R.id.ISD1);

		ISD1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (ISD1.isOpened())
					((SlidingDrawer) v).animateClose();
				return false;
			}
		});

		// 碰到新令Button的動作，也要開啟側欄
		// 小心，import時有兩個OnClickListener，要用View view的
		OnClickListener listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isClosed == 0) {// 沒死
					/************ 給定每人ID ************/
					ftV11.setText(recid1);
					ftV12.setText(recid2);
					ftV13.setText(recid3);
					ftV21.setText(recid4);
					ftV22.setText(recid5);
					ftV23.setText(recid6);
					ftV31.setText(recid7);
					ftV32.setText(recid8);
					ftV33.setText(recid9);
					ftV41.setText(recid10);
					ftV42.setText(recid11);
					ftV43.setText(recid12);
					ftV51.setText(recid13);
					ftV52.setText(recid14);
					ftV53.setText(recid15);
					ftV61.setText(recid16);
					ftV62.setText(recid17);
					ftV63.setText(recid18);
					ftV71.setText(recid19);
					ftV72.setText(recid20);
					ISD1.animateOpen();
				} else {// 被殺
					iB2.setEnabled(false);
					Toast.makeText(ClientConnect.this, "你已經死了!!!",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		iB2.setOnClickListener(listener2);

		fsIB1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV11.getText().toString() + "】", ftV11.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV12.getText().toString() + "】", ftV12.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV13.getText().toString() + "】", ftV13.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV21.getText().toString() + "】", ftV21.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV22.getText().toString() + "】", ftV22.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV23.getText().toString() + "】", ftV22.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV31.getText().toString() + "】", ftV31.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV32.getText().toString() + "】", ftV32.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV33.getText().toString() + "】", ftV33.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV41.getText().toString() + "】", ftV41.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV42.getText().toString() + "】", ftV42.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV43.getText().toString() + "】", ftV43.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV51.getText().toString() + "】", ftV51.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV52.getText().toString() + "】", ftV52.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV53.getText().toString() + "】", ftV53.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB16.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV61.getText().toString() + "】", ftV61.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB17.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV62.getText().toString() + "】", ftV62.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB18.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV63.getText().toString() + "】", ftV63.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB19.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV71.getText().toString() + "】", ftV71.getText()
						.toString());
				alertDialog.show();
			}
		});
		fsIB20.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = GetAlertDialogCupid("邱比特射箭", "確定戲弄【"
						+ ftV72.getText().toString() + "】", ftV72.getText()
						.toString());
				alertDialog.show();
			}
		});
		/*******************************************************************/
	}// End of CHCupid

	/**************************** 此段側欄功能實作~~~!!! **************************/
	/** 邱比特功能實作 **/
	private AlertDialog GetAlertDialogCupid(String title, String message,
			final String sendid) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (doCupid < 2) {
					ClientsendMessage("邱比特【" + textView_ID.getText().toString()
							+ "】把箭射向【" + sendid + "】，請點選左上角小圖查看\n" + sendid + "\n"
							+ "urTricked\n" + "cupidTurn" + "\n");

					Toast.makeText(ClientConnect.this,
							"邱比特戲弄了【" + sendid + "】", Toast.LENGTH_SHORT)
							.show();
					doCupid++;
					ISD1.animateClose();
					iB2.setEnabled(false);
				} else
					Toast.makeText(ClientConnect.this,
							"最多只能使兩人相戀喔！邱比特只有兩支箭而已~", Toast.LENGTH_SHORT)
							.show();
			}
		});
		// 設定Negative按鈕資料
		return builder.create();
	}

	/** 女巫功能實作 **/
	private AlertDialog GetAlertDialogWitch(String title, String message,
			final String sendid) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("拯救生命",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (doHelp < 1) {
							ClientsendMessage("女巫【"
									+ textView_ID.getText().toString()
									+ "】用藥拯救了【" + sendid + "】，請點選左上角小圖查看\n" + sendid
									+ "\n" + "urHelped\n" + "helpTurn" + "\n");

							Toast.makeText(ClientConnect.this,
									"女巫救了【" + sendid + "】", Toast.LENGTH_SHORT)
									.show();
							doHelp++;
							JSD1.animateClose();
							jB2.setEnabled(false);
						} else
							Toast.makeText(ClientConnect.this,
									"你已經用過靈藥了，一場只有一瓶", Toast.LENGTH_SHORT)
									.show();
					}
				});
		// 設定中間按鈕
		builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 甚麼也不做
			}
		});
		// 設定Negative按鈕資料
		builder.setNegativeButton("毒死他", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (doPoison < 1) {
					ClientsendMessage("女巫【" + textView_ID.getText().toString()
							+ "】用毒藥毒死了【" + sendid + "】，請點選左上角小圖查看\n" + sendid + "\n"
							+ "urPoisoned\n" + "poisonTurn" + "\n");

					Toast.makeText(ClientConnect.this, "女巫毒死了【" + sendid + "】",
							Toast.LENGTH_SHORT).show();
					doPoison++;
				} else
					Toast.makeText(ClientConnect.this, "你已經用過毒藥了，一場只有一瓶",
							Toast.LENGTH_SHORT).show();
			}
		});
		return builder.create();
	}

	/** 守衛功能實作 **/
	private AlertDialog GetAlertDialogGuard(String title, String message,
			final String sendid) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (doGuard < 1) {
					ClientsendMessage("守衛【" + textView_ID.getText().toString()
							+ "】保護【" + sendid + "】，請點選左上角小圖查看\n" + sendid + "\n"
							+ "urGuarded\n" + "guardTurn" + "\n");

					Toast.makeText(ClientConnect.this, "守衛保護【" + sendid + "】",
							Toast.LENGTH_SHORT).show();
					doGuard++;
					GSD1.animateClose();
					mB2.setEnabled(false);
				} else
					Toast.makeText(ClientConnect.this, "你在這個晚上分身乏術，一次只能保護一人",
							Toast.LENGTH_SHORT).show();
			}
		});
		// 設定Negative按鈕資料
		return builder.create();
	}

	/** 預言家功能實作 **/
	private AlertDialog GetAlertDialogProphet(String title, String message,
			final String sendid) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (doProphet < 1) {
					ClientsendMessage("預言家【" + textView_ID.getText().toString()
							+ "】占卜【" + sendid + "】，請點選左上角小圖查看\n" + sendid + "\n"
							+ "urDivined\n" + "prophetTurn" + "\n");

					Toast.makeText(ClientConnect.this, "預言家占卜【" + sendid + "】",
							Toast.LENGTH_SHORT).show();
					doProphet++;
					NSD1.animateClose();
					nB2.setEnabled(false);
				} else
					Toast.makeText(ClientConnect.this, "你這個晚上已經占卜過，靈力耗竭了。",
							Toast.LENGTH_SHORT).show();
			}
		});
		// 設定Negative按鈕資料
		return builder.create();
	}

	/*************************** "PopupShowEvent的動作" ********************************/
	public void ShowPopupEvent() {
		Context mContext = ClientConnect.this;
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.event, null, true);

		final PopupWindow mPopupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		switch (recChar) {
		case 0:
			mPopupWindow.showAtLocation(findViewById(R.id.Killer),
					Gravity.CENTER, 0, 0);
			break;
		case 1:
			mPopupWindow.showAtLocation(findViewById(R.id.Idiot),
					Gravity.CENTER, 0, 0);
			break;
		case 2:

			mPopupWindow.showAtLocation(findViewById(R.id.cupid),
					Gravity.CENTER, 0, 0);
			break;
		case 3:

			mPopupWindow.showAtLocation(findViewById(R.id.Guard),
					Gravity.CENTER, 0, 0);
			break;
		case 4:

			mPopupWindow.showAtLocation(findViewById(R.id.Prophet),
					Gravity.CENTER, 0, 0);
			break;
		case 5:
			mPopupWindow.showAtLocation(findViewById(R.id.startv),
					Gravity.CENTER, 0, 0);
			break;
		case 6:

			mPopupWindow.showAtLocation(findViewById(R.id.witch),
					Gravity.CENTER, 0, 0);
			break;
		case 7:
			mPopupWindow.showAtLocation(findViewById(R.id.Killer),
					Gravity.CENTER, 0, 0);
			break;
		case 8:
			mPopupWindow.showAtLocation(findViewById(R.id.Killer),
					Gravity.CENTER, 0, 0);
			break;
		case 9:
			mPopupWindow.showAtLocation(findViewById(R.id.Killer),
					Gravity.CENTER, 0, 0);
			break;
		}

		ImageView eventIV = (ImageView) view.findViewById(R.id.eventIV);
		;
		eventIV.setImageResource(myImageIds[receventnum]);

		Button btn2 = (Button) view.findViewById(R.id.end);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消按鈕實現的操作
				mPopupWindow.dismiss();
			}
		});
	}// end showpopupwindows

	/*************************** "顯示邱比特例外規則" ********************************/
	public void ShowPopupEx() {

		Context mContext = ClientConnect.this;
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View inflateView = mLayoutInflater.inflate(R.layout.extra, null, true);

		final PopupWindow mPopupWindow = new PopupWindow(inflateView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		// 這邊給的參數是最外層的Layout ID
		mPopupWindow.showAtLocation(findViewById(R.id.cupid), Gravity.CENTER,
				0, 0);

		Button btn2 = (Button) inflateView.findViewById(R.id.end);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消按鈕實現的操作
				mPopupWindow.dismiss();
			}
		});
	}// End of Showpopup()

	/********************** 改變UI的Thread Handler以及方法 ***********************/
	final Handler prophetHandler = new Handler();
	final Runnable UpdateUI1 = new Runnable() {
		public void run() {
			fsIB1.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine1() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI1); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI2 = new Runnable() {
		public void run() {
			fsIB2.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine2() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI2); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI3 = new Runnable() {
		public void run() {
			fsIB3.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine3() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI3); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI4 = new Runnable() {
		public void run() {
			fsIB4.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine4() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI4); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI5 = new Runnable() {
		public void run() {
			fsIB5.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine5() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI5); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI6 = new Runnable() {
		public void run() {
			fsIB6.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine6() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI6); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI7 = new Runnable() {
		public void run() {
			fsIB7.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine7() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI7); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI8 = new Runnable() {
		public void run() {
			fsIB8.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine8() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI8); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI9 = new Runnable() {
		public void run() {
			fsIB9.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine9() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI9); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI10 = new Runnable() {
		public void run() {
			fsIB10.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine10() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI10); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI11 = new Runnable() {
		public void run() {
			fsIB11.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine11() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI11); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI12 = new Runnable() {
		public void run() {
			fsIB12.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine12() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI12); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI13 = new Runnable() {
		public void run() {
			fsIB13.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine13() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI3); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI14 = new Runnable() {
		public void run() {
			fsIB14.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine14() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI14); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI15 = new Runnable() {
		public void run() {
			fsIB15.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine15() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI15); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI16 = new Runnable() {
		public void run() {
			fsIB16.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine16() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI16); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI17 = new Runnable() {
		public void run() {
			fsIB17.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine17() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI17); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI18 = new Runnable() {
		public void run() {
			fsIB18.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine18() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI18); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI19 = new Runnable() {
		public void run() {
			fsIB19.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine19() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI19); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	final Runnable UpdateUI20 = new Runnable() {
		public void run() {
			fsIB20.setBackgroundResource(characterspicture[recWho]);
		}
	};

	protected void prophetDivine20() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				prophetHandler.post(UpdateUI20); // 高速UI线程可以更新结果了
			}
		};
		t.start();
	}

	/** Button Enable **/
	final Handler buttonHandler = new Handler();
	final Runnable enableKillerUI = new Runnable() {
		public void run() {
			oB2.setEnabled(true);
			doKill1 = 0;
			doKill2 = 0;
			doKill3 = 0;
			doKill4 = 0;
			Toast.makeText(ClientConnect.this, "殺手現身！請按左上角按鈕點選你想殺的人",
					Toast.LENGTH_LONG).show();
		}
	};

	protected void killerButtonEnable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				buttonHandler.post(enableKillerUI); // 高速UI线程可以更新结果了

			}
		};
		t.start();
	}

	final Runnable enableCupidUI = new Runnable() {
		public void run() {
			iB2.setEnabled(true);
			Toast.makeText(ClientConnect.this,
					"邱比特現身！請按左上角按鈕點選兩人把他們湊成情侶", Toast.LENGTH_LONG)
					.show();
		}
	};

	protected void cupidButtonEnable() {
		Thread t = new Thread() {
			public void run() {
				buttonHandler.post(enableCupidUI);

			}
		};
		t.start();
	}

	final Runnable enableGuardUI = new Runnable() {
		public void run() {
			mB2.setEnabled(true);
			doGuard = 0;
			Toast.makeText(ClientConnect.this, "守衛現身！請按左上角按鈕點選欲保護的人",
					Toast.LENGTH_LONG).show();
		}
	};

	protected void guardButtonEnable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(enableGuardUI); // 高速UI?程可以更新?果了
			}
		};
		t.start();
	}

	final Runnable enableProphetUI = new Runnable() {
		public void run() {
			nB2.setEnabled(true);
			doProphet = 0;
			Toast.makeText(ClientConnect.this, "預言家現身！請按左上角按鈕點選欲得知身份的人",
					Toast.LENGTH_LONG).show();
		}
	};

	protected void prophetButtonEnable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(enableProphetUI); // 高速UI?程可以更新?果了

			}
		};
		t.start();
	}

	final Runnable enableWitchUI = new Runnable() {
		public void run() {
			jB2.setEnabled(true);
			Toast.makeText(ClientConnect.this,
					"女巫現身！" + JustTellWitch + "請按左上角按鈕點選欲拯救或毒殺的人。",
					Toast.LENGTH_LONG).show();
		}
	};

	protected void witchButtonEnable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(enableWitchUI); // 高速UI?程可以更新?果了

			}
		};
		t.start();
	}

	/** Button Disable **/
	final Runnable DisableKillerUI = new Runnable() {
		public void run() {
			oB2.setEnabled(false);
			if (fSD1.isOpened()) {
				fSD1.animateClose();
			}
		}
	};

	protected void killerButtonDisable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
				buttonHandler.post(DisableKillerUI); // 高速UI线程可以更新结果了

			}
		};
		t.start();
	}

	final Runnable DisableCupidUI = new Runnable() {
		public void run() {
			iB2.setEnabled(false);
			if (ISD1.isOpened()) {
				ISD1.animateClose();
			}
		}
	};

	protected void cupidButtonDisable() {
		Thread t = new Thread() {
			public void run() {
				buttonHandler.post(DisableCupidUI);
			}
		};
		t.start();
	}

	final Runnable DisableGuardUI = new Runnable() {
		public void run() {
			mB2.setEnabled(false);
			if (GSD1.isOpened()) {
				GSD1.animateClose();
			}
		}
	};

	protected void guardButtonDisable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(DisableGuardUI); // 高速UI?程可以更新?果了

			}
		};
		t.start();
	}

	final Runnable DisableProphetUI = new Runnable() {
		public void run() {
			nB2.setEnabled(false);
			if (NSD1.isOpened()) {
				NSD1.animateClose();
			}
		}
	};

	protected void prophetButtonDisable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(DisableProphetUI); // 高速UI?程可以更新?果了
			}
		};
		t.start();
	}

	final Runnable DisableWitchUI = new Runnable() {
		public void run() {
			jB2.setEnabled(false);
			if (JSD1.isOpened()) {
				JSD1.animateClose();
			}
		}
	};

	protected void witchButtonDisable() {
		Thread t = new Thread() {
			public void run() {
				// doSomething(); //?理得到?果了，?里一些?容保存在主?的成??量中
				buttonHandler.post(DisableWitchUI); // 高速UI?程可以更新?果了
			}
		};
		t.start();
	}

	public void onBackPressed() {

		if (entergame == 1) {
			AlertDialog alertDialog = getAlertDialog("確定離開嗎?", "【警告】遊戲連線將會中斷");
			alertDialog.show();

			return;
		} else {
			exitActivity(1);
		}
	}

	private AlertDialog getAlertDialog(String title, String message) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ClientsendMessage("玩家【" + textView_ID.getText().toString()
						+ "】離開遊戲");
				exitActivity(1);

			}
		});
		// 設定Negative按鈕資料
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing

			}
		});
		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	private AlertDialog getAlertDialog2(String title, String message) {

		
		final String ss = "DarkKillerHotspot";
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		builder.setTitle(title);
		builder.setMessage(message);


		// 設定Positive按鈕資料
		builder.setPositiveButton("Wifi設置",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WifiManager wiFiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						
						wiFiManager.setWifiEnabled(true);
						Intent wifiSettingsIntent = new Intent(
								"android.settings.WIFI_SETTINGS");
						startActivity(wifiSettingsIntent);

						

					}
				});
		// 設定Negative按鈕資料
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing
				
			}
		});
		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	private AlertDialog getAlertDialog3(String title, String message) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	private AlertDialog getAlertDialog4(String title, String message) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setClassName("aaaaa.dark_killer",
						"aaaaa.dark_killer.ChooseChar");// Package Name ,
														// Package
														// Name.Class
														// Name

				startActivity(intent);
				finish();
			}
		});

		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	private AlertDialog getAlertDialog5(String title, String message) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				ClientConnect.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WifiManager wiFiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				
				wiFiManager.setWifiEnabled(true);
				
				Intent wifiSettingsIntent = new Intent(
						"android.settings.WIFI_SETTINGS");
				startActivity(wifiSettingsIntent);
			}
		});

		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		mp = MediaPlayer.create(this, R.raw.start);
		mp.setLooping(true);
		mp.start();

	}

	protected void onPause() {
		mp.stop();
		super.onPause();
	}

}// end ClientConnect
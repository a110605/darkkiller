package aaaaa.dark_killer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.BindException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class BuildServer extends Activity {
	Method dataConnSwitchmethod;
	Class telephonyManagerClass;
	Object ITelephonyStub;
	Class ITelephonyClass;
	int isEnabled = 0;
	Thread thread;
	int stopthread = 0;
	int isenter = 0;
	/************************* BuildServer ***********************/
	public static boolean bIfDebug = true;
	public static String TAG = "DarkKiller DEBUG";
	public static String strDebugPreFix = "DarkKiller";
	public static int ip = 0;
	private Handler mhandler = new Handler();
	private Handler mhandler2 = new Handler();
	private int intSocketServerPort = 8081;
	public EditText text_peopleNum;
	MediaPlayer mp;
	private ImageButton button_build, startgame;
	private TextView textView_Status, serverIP, serverstatus;
	private TextView Killernum, Villagernum, Guardnum, Prophetnum, Witchnum,
			Cupidnum, Idiotnum;
	private String connect_status = "";
	public static int peoNum;// 連線人數
	public int[] rndChar;// 儲存人數陣列
	public boolean listen = true;
	// Socket Array用來定義新的加入者 Socket
	public static ArrayList<MultiServerThread> players;
	int id = 0;
	Socket mSocket01;
	String strTemp01;
	int numclient;// 連線人數
	String IP = "";
	String strTemp = "123";
	String numtext;
	int k = 0;
	private Method wifiControlMethod;
	private Method wifiApConfigurationMethod;
	private Method wifiApState;
	/************************** BuildServer ****************************/
	private Intent intent = new Intent("com.angel.Android.MUSIC");
	private Button fIB1; // 假的Handle，可開關SlideDrawer
	private Button fsIB1, fsIB2, fsIB3, fsIB4, fsIB5, fsIB6, fsIB7, fsIB8,
			fsIB9, fsIB10, fsIB11, fsIB12, fsIB13, fsIB14, fsIB15, fsIB16,
			fsIB17, fsIB18, fsIB19, fsIB20, fsIB21;
	private RelativeLayout cupid;
	private Button f3, f4, f6;// 前進、後退、restart、setting
	private int[] RoundImage = { R.drawable.f20, R.drawable.f21,
			R.drawable.f22, R.drawable.f23, R.drawable.f24, R.drawable.f25,
			R.drawable.f26, R.drawable.f27, R.drawable.f28, R.drawable.f29, };

	private int[] myImageIds = { R.drawable.c50, R.drawable.c51,
			R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
			R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59,
			R.drawable.c60, R.drawable.c61, R.drawable.c62, R.drawable.c63,
			R.drawable.c64, R.drawable.c65, R.drawable.c66, R.drawable.c67,
			R.drawable.c68, };
	private int[] characterspicture = { R.drawable.f30, R.drawable.f31,
			R.drawable.f32, R.drawable.f33, R.drawable.f34, R.drawable.f35,
			R.drawable.f36, R.drawable.f30, R.drawable.f30, R.drawable.f30 };
	private int[] characterskilledpicture = { R.drawable.f300, R.drawable.f311,
			R.drawable.f322, R.drawable.f3333, R.drawable.f344,
			R.drawable.f355, R.drawable.f366, R.drawable.f300, R.drawable.f300,
			R.drawable.f300 };

	private int eventnum;
	private ImageView f2;
	private TextView ftV11, ftV12, ftV13, ftV21, ftV22, ftV23, ftV31, ftV32,
			ftV33, ftV41, ftV42, ftV43, ftV51, ftV52, ftV53, ftV61, ftV62,
			ftV63, ftV71, ftV72;
	private MediaPlayer MP0_dark, MP1_cupid, MP2_guard, MP3_killer, MP4_witch,
			MP5_future, MP6_light, MP7_event;
	final MediaPlayer[] music = { MP0_dark, // 黑夜 = music[0]
			MP1_cupid, // 邱比特= music[1]
			MP2_guard, // 守衛 = music[2]
			MP3_killer,// 殺手 = music[3]
			MP4_witch, // 女巫 = music[4]
			MP5_future,// 預言家= music[5]
			MP6_light, // 6 7 9為白天
			MP7_event };// 事件 = music[7]
	// private MyVideoView fvV;
	private OnTouchListener listener1;
	private MotionEvent action;
	private LinearLayout L1, L2, L3;
	private SlidingDrawer fSD1;
	Button event;
	ScrollView scrollView1;
	int i = 9; // 給中間的圓換圖用
	String[] status = { "黑夜降臨", "邱比特現身", "守衛現身", "殺手現身", "女巫現身", "預言家現身", "白天",
			"事件", "宣告", "討論時間" };
	String[] details = {
			"宣告「黑夜降臨」，請大家閉上眼睛",
			"宣告「邱比特現身」，請邱比特張開眼睛。請邱比特點選其左上角小圖，選擇兩人成為情侶，此後兩人性命相伴",
			"宣告「守衛現身」，請守衛張開眼睛。請守衛點選其左上角小圖，選擇1人為保護對象。可免於被殺手或女巫殺害。",
			"宣告「殺手現身」，請殺手（們）張開眼睛。請殺手點選左上角小圖，選擇殺害對象。",
			"宣告「女巫現身」，請女巫張開眼睛。女巫可選擇使用復活藥劑和毒藥劑，在整個遊戲中皆只能使用一次。復活藥劑可使該回合被殺手殺死的玩家復活，毒藥則可殺人。請女巫點選左上角小圖，選擇一人復活／毒殺。",
			"宣告「預言家現身」，請預言家張開眼睛。請預言家點選其左上角小圖，可選擇一人查看身分。",
			"宣告「白天」，請所有人睜開眼睛。並不要洩漏了自己身分。",
			"發生事件，大家點選螢幕左上方查看事件情境。主持人請依據事件情境協助遊戲進行。",
			"主持人點選左上方小圖，進入狀態畫面。可再次查看各腳色狀態。點選被殺害／毒害角色，傳送死亡影片來告訴大家。",
			"請大家討論並投票兇手是誰？請善用猜疑，信任，背叛等因子來增加遊戲氣氛。得票最多的人主持人必須將此人處決。之後進行下一輪。" };
	TextView text, detail, serverstatusss, host, random;
	String JustTellWitch;

	static ImageView fiV1111, fiV1121, fiV1131, fiV1141, fiV1211, fiV1221,
			fiV1231, fiV1241;
	static ImageView fiV1112, fiV1122, fiV1132, fiV1142, fiV1212, fiV1222,
			fiV1232, fiV1242;
	static ImageView fiV1113, fiV1123, fiV1133, fiV1143, fiV1213, fiV1223,
			fiV1233, fiV1243;
	static ImageView fiV2111, fiV2121, fiV2131, fiV2141, fiV2211, fiV2221,
			fiV2231, fiV2241;
	static ImageView fiV2112, fiV2122, fiV2132, fiV2142, fiV2212, fiV2222,
			fiV2232, fiV2242;
	static ImageView fiV2113, fiV2123, fiV2133, fiV2143, fiV2213, fiV2223,
			fiV2233, fiV2243;
	static ImageView fiV3111, fiV3121, fiV3131, fiV3141, fiV3211, fiV3221,
			fiV3231, fiV3241;
	static ImageView fiV3112, fiV3122, fiV3132, fiV3142, fiV3212, fiV3222,
			fiV3232, fiV3242;
	static ImageView fiV3113, fiV3123, fiV3133, fiV3143, fiV3213, fiV3223,
			fiV3233, fiV3243;
	static ImageView fiV4111, fiV4121, fiV4131, fiV4141, fiV4211, fiV4221,
			fiV4231, fiV4241;
	static ImageView fiV4112, fiV4122, fiV4132, fiV4142, fiV4212, fiV4222,
			fiV4232, fiV4242;
	static ImageView fiV4113, fiV4123, fiV4133, fiV4143, fiV4213, fiV4223,
			fiV4233, fiV4243;
	static ImageView fiV5111, fiV5121, fiV5131, fiV5141, fiV5211, fiV5221,
			fiV5231, fiV5241;
	static ImageView fiV5112, fiV5122, fiV5132, fiV5142, fiV5212, fiV5222,
			fiV5232, fiV5242;
	static ImageView fiV5113, fiV5123, fiV5133, fiV5143, fiV5213, fiV5223,
			fiV5233, fiV5243;
	static ImageView fiV6111, fiV6121, fiV6131, fiV6141, fiV6211, fiV6221,
			fiV6231, fiV6241;
	static ImageView fiV6112, fiV6122, fiV6132, fiV6142, fiV6212, fiV6222,
			fiV6232, fiV6242;
	static ImageView fiV6113, fiV6123, fiV6133, fiV6143, fiV6213, fiV6223,
			fiV6233, fiV6243;
	static ImageView fiV7111, fiV7121, fiV7131, fiV7141, fiV7211, fiV7221,
			fiV7231, fiV7241;
	static ImageView fiV7112, fiV7122, fiV7132, fiV7142, fiV7212, fiV7222,
			fiV7232, fiV7242;
	// ImageView fiV7113, fiV7123, fiV7133, fiV7143, fiV7213, fiV7223,
	// fiV7233,fiV7243;

	ServerSocket mServerSocket01 = null;
	int whereisme = 0;// 0表示BuildServer 1表示ServerMAster
	String allclientid[] = { "", "", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "", "", "", "", "" };// 存放ALL client ID
	String strID; // 有動作時，收到的ID
	private ExecutorService ES_CRClients;
	int q = 0;
	int hasevent;
	int cupided = 0;// 邱比特是否已經用過了
	// 0:Killer(1) 1:白癡 2:邱比特 3:守衛 4:預言家 5:村民 6:女巫 7:killer(2) 8:killer(3)
	// 9:killer(4)
	public int[] onePeo = { 0 };
	public int[] twoPeo = { 0, 5 };
	public int[] threePeo = { 0, 5, 1 };
	public int[] fourPeo = { 0, 5, 5, 1 };
	public int[] fivePeo = { 0, 3, 5, 5, 1 };
	public int[] sixPeo = { 0, 3, 5, 5, 5, 1 };
	public int[] sevenPeo = { 0, 2, 3, 5, 5, 5, 1 };
	public int[] eightPeo = { 0, 7, 2, 3, 5, 5, 5, 1 };
	public int[] ninePeo = { 0, 7, 2, 3, 5, 5, 5, 5, 1 };
	public int[] tenPeo = { 0, 7, 2, 3, 5, 5, 5, 5, 6, 1 };
	public int[] elevenPeo = { 0, 7, 1, 2, 3, 4, 5, 5, 5, 5, 6 };
	public int[] twelvePeo = { 0, 7, 1, 2, 3, 4, 5, 5, 5, 5, 5, 6 };
	public int[] thirteenPeo = { 0, 7, 8, 1, 2, 3, 4, 5, 5, 5, 5, 5, 6 };
	public int[] fourteenPeo = { 0, 7, 8, 1, 2, 3, 3, 4, 5, 5, 5, 5, 5, 6 };
	public int[] fifteenPeo = { 0, 7, 8, 1, 2, 3, 3, 4, 5, 5, 5, 5, 5, 5, 6 };
	public int[] sixteenPeo = { 0, 7, 8, 1, 2, 3, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6 };
	public int[] seventeenPeo = { 0, 7, 8, 9, 1, 2, 3, 3, 4, 5, 5, 5, 5, 5, 5,
			5, 6 };
	public int[] eighteenPeo = { 0, 7, 8, 9, 1, 2, 3, 3, 4, 5, 5, 5, 5, 5, 5,
			5, 1, 6 };
	public int[] nineteenPeo = { 0, 7, 8, 9, 1, 2, 3, 3, 4, 1, 5, 5, 5, 5, 5,
			5, 5, 5, 6 };
	public int[] twentyPeo = { 0, 7, 8, 9, 1, 2, 3, 3, 4, 1, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 6 };
	int reckilled1 = 0, reckilled2 = 0, reckilled3 = 0, reckilled4 = 0,
			recguard = 0, reclove = 0, rechelp = 0, recpoison = 0,
			recDivined = 0;
	boolean Serverdialog_status;
	int isopenhotspot = 0;

	/**** 判斷是否具有此角色之Flag ****/
	int isGuard = 1;
	int isWitch = 1;
	int isProphet = 1;
	int isCupid = 1;
	CheckBox if_event;
	// on create
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		button_build = (ImageButton) findViewById(R.id.Server_build);
		//serverIP = (TextView) findViewById(R.id.serverIP);
		serverstatus = (TextView) findViewById(R.id.serverstatus);
		Killernum = (TextView) findViewById(R.id.Killernum);
		Villagernum = (TextView) findViewById(R.id.Villagernum);
		Guardnum = (TextView) findViewById(R.id.Guardnum);
		Prophetnum = (TextView) findViewById(R.id.Prophetnum);
		Witchnum = (TextView) findViewById(R.id.Witchnum);
		Cupidnum = (TextView) findViewById(R.id.Cupidnum);
		Idiotnum = (TextView) findViewById(R.id.Idiotnum);
		host = (TextView) findViewById(R.id.host);
		text_peopleNum = (EditText) findViewById(R.id.editText_peopleNum);// 人數
		L1 = (LinearLayout) findViewById(R.id.L1);
		L2 = (LinearLayout) findViewById(R.id.L4);
		L3 = (LinearLayout) findViewById(R.id.L3);
		startgame = (ImageButton) findViewById(R.id.startgame);
		 if_event = (CheckBox) findViewById(R.id.if_check);

		// startgame.setEnabled(false);
		Context mcontext;
		mcontext = this.getApplicationContext();
		mcontext.getApplicationContext();

		AlertDialog alertDialog = getAlertDialog2("【遊戲設定】",
				"請按【一鍵開啟】");
		alertDialog.show();

		mp = MediaPlayer.create(this, R.raw.start);
		mp.setLooping(true);
		mp.start();

		if_event.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				displayMessage("請按【開始遊戲】");
			}
		});

		text_peopleNum.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				displayMessage("請勾選是否產生事件");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});

		// 建立server
		button_build.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
			
				if(if_event.isChecked()){
					hasevent=1;
				}
				if ("".equals(text_peopleNum.getText().toString().trim())) {
					displayMessage("請輸入正確遊戲人數");
					button_build.setEnabled(true);
				} else {
					numclient = Integer.parseInt(text_peopleNum.getText()
							.toString());
					setIP();
					Log.i(TAG,"IP是:"+IP);//serverIP.setText(IP);
					/******************* 防呆 ******************************/
					players = new ArrayList<MultiServerThread>();
					players.clear();
					eventnum = (int) (Math.random() * 19);// 0~18

					if (numclient == 1) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Killernum.setText("殺手:1人");
						Villagernum.setText("村民:0人");
						Guardnum.setText("守衛:0人");
						Witchnum.setText("女巫:0人");
						Prophetnum.setText("預言家:0人");
						Idiotnum.setText("白癡:0人");
						Cupidnum.setText("邱比特:0人");
						displayMessage("等待" + numclient + "位玩家連線中..");

						isGuard = 0;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
					} else if (numclient == 2) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:1人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:1人");
						Prophetnum.setText("預言家:0人");
						Guardnum.setText("守衛:0人");
						Cupidnum.setText("邱比特:0人");
						Witchnum.setText("女巫:0人");
						host.setText("主持人:1人");

						isGuard = 0;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 3) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:1人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:1人");
						Prophetnum.setText("預言家:0人");
						Guardnum.setText("守衛:0人");
						Cupidnum.setText("邱比特:0人");
						Witchnum.setText("女巫:0人");
						host.setText("主持人:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 0;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
					} else if (numclient == 4) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:1人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:1人");
						Prophetnum.setText("預言家 :0人");
						Guardnum.setText("守衛:1人");
						Cupidnum.setText("邱比特:0人");
						Witchnum.setText("女巫:人");
						host.setText("主持人:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 0;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
					} else if (numclient == 5) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:2人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:1人");
						Prophetnum.setText("預言家 :1人");
						Guardnum.setText("守衛:1人");
						Cupidnum.setText("邱比特:0人");
						Witchnum.setText("女巫:0人");
						host.setText("主持人:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 1;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
					} else if (numclient == 6) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:3人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:1人");
						Prophetnum.setText("預言家:1人");
						Guardnum.setText("守衛:1人");
						Cupidnum.setText("邱比特:0人");
						Witchnum.setText("女巫:0人");
						host.setText("主持人:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 1;
						isWitch = 0;
						isProphet = 0;
						isCupid = 0;
					} else if (numclient == 7) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:4人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:1人");
						Guardnum.setText("守衛:1人");
						Prophetnum.setText("預言家:0人");
						Cupidnum.setText("邱比特:1人");
						Witchnum.setText("女巫:0人");
						host.setText("主持人:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");

						isGuard = 1;
						isWitch = 0;
						isProphet = 0;
						isCupid = 1;
					} else if (numclient == 8) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:4人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:2人");
						Guardnum.setText("守衛:1人");
						Prophetnum.setText("預言家:0人");
						Cupidnum.setText("邱比特:1人");
						host.setText("主持人:1人");
						Witchnum.setText("女巫:0人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 1;
						isWitch = 0;
						isProphet = 0;
						isCupid = 1;
					} else if (numclient == 9) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:2人");
						Guardnum.setText("守衛:1人");
						Prophetnum.setText("預言家:0人");
						host.setText("主持人:1人");
						Cupidnum.setText("邱比特:1人");
						Witchnum.setText("女巫:0人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 1;
						isWitch = 0;
						isProphet = 0;
						isCupid = 1;
					} else if (numclient == 10) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:2人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:0人");
						Cupidnum.setText("邱比特:0人");
						displayMessage("等待" + numclient + "位玩家連線中..");
						isGuard = 1;
						isWitch = 1;
						isProphet = 0;
						isCupid = 1;
					} else if (numclient == 11) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:2人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:0人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 12) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:2人");
						host.setText("主持人:1人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 13) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:3人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 14) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:5人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:3人");
						Guardnum.setText("守衛:2人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 15) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:6人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:3人");
						Guardnum.setText("守衛:2人");
						Witchnum.setText("女巫:1人");
						host.setText("主持人:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 16) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:7人");
						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:3人");
						Guardnum.setText("守衛:2人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 17) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:7人");

						Idiotnum.setText("白癡:1人");
						Killernum.setText("殺手:4人");
						Guardnum.setText("守衛:2人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 18) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:7人");
						Idiotnum.setText("白癡:2人");
						Killernum.setText("殺手:4人");
						Guardnum.setText("守衛:2人");

						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 19) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:9人");
						Idiotnum.setText("白癡:2人");
						Killernum.setText("殺手:4人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient == 20) {
						if_event.setEnabled(false);
						text_peopleNum.setEnabled(false);
						button_build.setEnabled(false);
						L1.setVisibility(1);
						L2.setVisibility(2);
						L3.setVisibility(1);

						Villagernum.setText("村民:10人");
						Idiotnum.setText("白癡:2人");
						Killernum.setText("殺手:4人");
						Guardnum.setText("守衛:1人");
						Witchnum.setText("女巫:1人");
						Prophetnum.setText("預言家:1人");
						Cupidnum.setText("邱比特:1人");
						displayMessage("等待" + numclient + "位玩家連線中..");
					} else if (numclient > 20) {
						displayMessage("請輸入5~20內遊戲人數");
						Villagernum.setText("村民:0人");
						Idiotnum.setText("白癡:0人");
						Killernum.setText("殺手:0人");
						Guardnum.setText("守衛:0人");
						Witchnum.setText("女巫:0人");
						Prophetnum.setText("預言家:0人");
						Cupidnum.setText("邱比特:0人");
					}// end elseif
					ES_CRClients = Executors.newCachedThreadPool();

					switch (numclient) {
					case 1:
						shuffleArray(onePeo);
						break;
					case 2:
						shuffleArray(twoPeo);
						break;
					case 3:
						shuffleArray(threePeo);
						break;
					case 4:
						shuffleArray(fourPeo);
						break;
					case 5:
						shuffleArray(fivePeo);
						break;
					case 6:
						shuffleArray(sixPeo);
						break;
					case 7:
						shuffleArray(sevenPeo);
						break;
					case 8:
						shuffleArray(eightPeo);
						break;
					case 9:
						shuffleArray(ninePeo);
						break;
					case 10:
						shuffleArray(tenPeo);
						break;
					case 11:
						shuffleArray(elevenPeo);
						break;
					case 12:
						shuffleArray(twelvePeo);
						break;
					case 13:
						shuffleArray(thirteenPeo);
						break;
					case 14:
						shuffleArray(fourteenPeo);
						break;
					case 15:
						shuffleArray(fifteenPeo);
						break;
					case 16:
						shuffleArray(sixteenPeo);
						break;
					case 17:
						shuffleArray(seventeenPeo);
						break;
					case 18:
						shuffleArray(eighteenPeo);
						break;
					case 19:
						shuffleArray(nineteenPeo);
						break;
					case 20:
						shuffleArray(twentyPeo);
						break;
					}

					// 按完就變Disable
					thread = new Thread(new mSocketConnectRunnable1());
					thread.start();
				}

			}// end onClick
		});// end setOnClickListener

		// 開始遊戲
		startgame.setOnClickListener(new Button.OnClickListener() {
			// 當按下按鈕的時候觸發以下的方法
			public void onClick(View v) {
				if_event.setEnabled(true);
				text_peopleNum.setEnabled(true);
				button_build.setEnabled(true);
				text_peopleNum.setText("");
				//serverIP.setText("");
				if_event.setChecked(false);
				displayMessage("請重新輸入玩家人數");
				L1.setVisibility(View.INVISIBLE);
				L2.setVisibility(View.INVISIBLE);
				L3.setVisibility(View.INVISIBLE);
				stopthread = 1;
			}// end click
		});

		/************************* OnTouch Listener *************************/
		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					button_build.setBackgroundResource(R.drawable.h21); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					button_build.setBackgroundResource(R.drawable.h2); // 松开时恢复原来的背景图
				}
				return false;
			}
		};

		OnTouchListener Olistener2 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startgame.setBackgroundResource(R.drawable.h11); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					startgame.setBackgroundResource(R.drawable.h1); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		button_build.setOnTouchListener(Olistener1);
		startgame.setOnTouchListener(Olistener2);
		/**************************************************************************/
	}// end oncreate

	public class mSocketConnectRunnable1 implements Runnable {

		ServerSocket mServerSocket01 = null;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {

				mServerSocket01 = new ServerSocket(intSocketServerPort);// ports=8080
				Log.i(TAG, strDebugPreFix + "等待連線中 ");
				// mServerSocket01.setReuseAddress(true);

				// 當連線一直存在時，持續執行

				while (stopthread == 0) {
					mSocket01 = mServerSocket01.accept();
					// 每多一client就new thread
					MultiServerThread t = new MultiServerThread(mSocket01, id);
					players.add(t);
					ES_CRClients.execute(t);
					id++;
					Log.i(TAG, "Player的內容：" + players.get(players.size() - 1));
					Log.i(TAG, "在Run內，多一個人 遊戲人數就+1" + players.size());
				}// end while

			}// end try
			catch (BindException err_sckbnd) {

			} catch (IOException err_io) {
				err_io.printStackTrace();
			} finally {
				Log.i(TAG, "this is finally of mRunnable01");
			}
		}// end run
	}// end mSocketConnectRunnable1

	// MultiServerThread負責與Client的對話
	public class MultiServerThread extends Thread {
		// 用來產生 0~6的隨機
		private Socket socket = null;
		private int ID;
		BufferedWriter bw;
		BufferedReader br;

		// constructor
		public MultiServerThread(Socket sockets, int IDs) {
			this.socket = sockets;
			this.ID = IDs;
		}

		public void run() {
			try {

				bw = new BufferedWriter(new OutputStreamWriter(
						mSocket01.getOutputStream()));
				br = new BufferedReader(new InputStreamReader(
						mSocket01.getInputStream()));
			
				/**************** 傳送角色 *****************/
				switch (numclient) {
				case 1:
					ServersendMessage("1\n" + onePeo[players.size() - 1] + "\n"
							+ eventnum + "\n" + hasevent + "\n" + numclient
							+ "\n");// 傳送腳色+事件
					break;
				case 2:
					Log.i(TAG, "印角色陣列：" + twoPeo[0] + "、" + twoPeo[1]);
					ServersendMessage("1\n" + twoPeo[players.size() - 1] + "\n"
							+ eventnum + "\n" + hasevent + "\n" + numclient
							+ "\n");// 傳送腳色+事件
					Log.i(TAG, "傳送角色碼(recChar)" + twoPeo[players.size() - 1]);
					break;
				case 3:
					ServersendMessage("1\n" + threePeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 4:
					ServersendMessage("1\n" + fourPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 5:
					ServersendMessage("1\n" + fivePeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 6:
					ServersendMessage("1\n" + sixPeo[players.size() - 1] + "\n"
							+ eventnum + "\n" + hasevent + "\n" + numclient
							+ "\n");// 傳送腳色+事件
					break;
				case 7:
					ServersendMessage("1\n" + sevenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 8:
					ServersendMessage("1\n" + eightPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 9:
					ServersendMessage("1\n" + ninePeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;

				case 10:
					ServersendMessage("1\n" + tenPeo[players.size() - 1] + "\n"
							+ eventnum + "\n" + hasevent + "\n" + numclient
							+ "\n");// 傳送腳色+事件
					break;
				case 11:
					ServersendMessage("1\n" + elevenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 12:
					ServersendMessage("1\n" + twelvePeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 13:
					ServersendMessage("1\n" + thirteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 14:
					ServersendMessage("1\n" + fourteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;

				case 15:
					ServersendMessage("1\n" + fifteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 16:
					ServersendMessage("1\n" + sixteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 17:
					ServersendMessage("1\n" + seventeenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 18:
					ServersendMessage("1\n" + eighteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 19:
					ServersendMessage("1\n" + nineteenPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				case 20:
					ServersendMessage("1\n" + twentyPeo[players.size() - 1]
							+ "\n" + eventnum + "\n" + hasevent + "\n"
							+ numclient + "\n");// 傳送腳色+事件
					break;
				}

				
				//人滿了就GO，沒滿就不行
				mhandler.post(new Runnable() {
					public void run() {
						
						if (players.size() == numclient) {
						
							GotoMasterServer();
						// while裡改收Servermaster的
							mp.stop();
						}
					}// end run
				});// end post
				
				
				/**************** 不斷收聽 *********************/
				while (true) {
					// .......strTemp01 為 收到的ID
					strTemp01 = br.readLine();

					// 收聽在buildServer時的ALL ID
					if (whereisme == 0) {
						displayMessage(strTemp01 + ",成功連線");
						Log.i(TAG, "Server收到ID" + k + " : " + strTemp01);

						allclientid[k] = strTemp01;
						Log.i(TAG, "allclientid[" + k + "]:" + allclientid[k]);
						k++;
					} else {// 收聽在ServerMaster時的腳色送來訊息 whereisme=1

						Log.i(TAG, "Server收到來自client訊息1 : " + strTemp01);
						displayMessage2(strTemp01);

						strID = br.readLine().toString();

						String strTemp02 = br.readLine();
						Log.i(TAG, "Server收到來自client訊息2 : " + strTemp02);
						Log.i(TAG, "收到的ID: " + strID + "第一個ID: "
								+ ftV11.getText().toString() + "第二個ID: "
								+ ftV12.getText().toString());
						if (strTemp02.equals("urKilledbyKiller1"))
							reckilled1 = 1;
						if (strTemp02.equals("urKilledbyKiller2"))
							reckilled2 = 2;
						if (strTemp02.equals("urKilledbyKiller3"))
							reckilled3 = 3;
						if (strTemp02.equals("urKilledbyKiller4"))
							reckilled4 = 4;
						if (strTemp02.equals("urTricked"))
							reclove = 5;
						if (strTemp02.equals("urGuarded"))
							recguard = 6;
						if (strTemp02.equals("urHelped"))
							rechelp = 7;
						if (strTemp02.equals("urPoisoned"))
							recpoison = 8;

						String turn = br.readLine();
						// 要告訴女巫，誰被殺死了
						if (turn.contentEquals("killTurn")) {
							JustTellWitch = "這個晚上" + strID + "被殺死了";
							Log.i(TAG, "今天的JustTellWitch訊息是；" + JustTellWitch);
						}

						// 第一個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV11.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定第一個人的號碼：1 & "
										+ onePeo[0]);
								ServerBroadcast("6\n1\n" + onePeo[0] + "\n");
								break;
							case 2:
								Log.i(TAG, "6--Server傳送預言家指定第二個人的號碼：1 & "
										+ onePeo[0]);
								ServerBroadcast("6\n1\n" + twoPeo[0] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n1\n" + threePeo[0] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n1\n" + fourPeo[0] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n1\n" + fivePeo[0] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n1\n" + sixPeo[0] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n1\n" + sevenPeo[0] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n1\n" + eightPeo[0] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n1\n" + ninePeo[0] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n1\n" + tenPeo[0] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n1\n" + elevenPeo[0] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n1\n" + twelvePeo[0] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n1\n" + thirteenPeo[0]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n1\n" + fourteenPeo[0]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n1\n" + fifteenPeo[0] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n1\n" + sixteenPeo[0] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n1\n" + seventeenPeo[0]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n1\n" + eighteenPeo[0]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n1\n" + nineteenPeo[0]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n1\n" + twentyPeo[0] + "\n");
								break;
							}// end switch
						}// end if (strTemp02.equals("urDivined")&&
							// strID.equals(ftV11.getText().toString()))
							// 第二個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV12.getText().toString())) {
							switch (numclient) {
							case 2:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：2 & "
										+ twoPeo[1]);
								ServerBroadcast("6\n2\n" + twoPeo[1] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n2\n" + threePeo[1] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n2\n" + fourPeo[1] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n2\n" + fivePeo[1] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n2\n" + sixPeo[1] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n2\n" + sevenPeo[1] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n2\n" + eightPeo[1] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n2\n" + ninePeo[1] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n2\n" + tenPeo[1] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n2\n" + elevenPeo[1] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n2\n" + twelvePeo[1] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n2\n" + thirteenPeo[1]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n2\n" + fourteenPeo[1]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n2\n" + fifteenPeo[1] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n2\n" + sixteenPeo[1] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n2\n" + seventeenPeo[1]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n2\n" + eighteenPeo[1]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n2\n" + nineteenPeo[1]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n2\n" + twentyPeo[1] + "\n");
								break;
							}// end switch
						}

						// 第三個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV13.getText().toString())) {
							switch (numclient) {
							case 3:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：3 & "
										+ threePeo[2]);
								ServerBroadcast("6\n3\n" + threePeo[2] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n3\n" + fourPeo[2] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n3\n" + fivePeo[2] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n3\n" + sixPeo[2] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n3\n" + sevenPeo[2] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n3\n" + eightPeo[2] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n3\n" + ninePeo[2] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n3\n" + tenPeo[2] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n3\n" + elevenPeo[2] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n3\n" + twelvePeo[2] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n3\n" + thirteenPeo[2]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n3\n" + fourteenPeo[2]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n3\n" + fifteenPeo[2] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n3\n" + sixteenPeo[2] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n3\n" + seventeenPeo[2]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n3\n" + eighteenPeo[2]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n3\n" + nineteenPeo[2]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n3\n" + twentyPeo[2] + "\n");
								break;
							}// end switch
						}

						// 第4個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV21.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：1 & "
										+ onePeo[3]);
								ServerBroadcast("6\n4\n" + onePeo[3] + "\n");
								break;
							case 2:
								ServerBroadcast("6\n4\n" + twoPeo[3] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n4\n" + threePeo[3] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n4\n" + fourPeo[3] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n4\n" + fivePeo[3] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n4\n" + sixPeo[3] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n4\n" + sevenPeo[3] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n4\n" + eightPeo[3] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n4\n" + ninePeo[3] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n4\n" + tenPeo[3] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n4\n" + elevenPeo[3] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n4\n" + twelvePeo[3] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n4\n" + thirteenPeo[3]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n4\n" + fourteenPeo[3]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n4\n" + fifteenPeo[3] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n4\n" + sixteenPeo[3] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n4\n" + seventeenPeo[3]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n4\n" + eighteenPeo[3]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n4\n" + nineteenPeo[3]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n4\n" + twentyPeo[3] + "\n");
								break;
							}// end switch
						}

						// 第5個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV22.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：1 & "
										+ onePeo[4]);
								ServerBroadcast("6\n5\n" + onePeo[4] + "\n");
								break;
							case 2:
								ServerBroadcast("6\n5\n" + twoPeo[4] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n5\n" + threePeo[4] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n5\n" + fourPeo[4] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n5\n" + fivePeo[4] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n5\n" + sixPeo[4] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n5\n" + sevenPeo[4] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n5\n" + eightPeo[4] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n5\n" + ninePeo[4] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n5\n" + tenPeo[4] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n5\n" + elevenPeo[4] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n5\n" + twelvePeo[4] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n5\n" + thirteenPeo[4]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n5\n" + fourteenPeo[4]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n5\n" + fifteenPeo[4] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n5\n" + sixteenPeo[4] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n5\n" + seventeenPeo[4]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n5\n" + eighteenPeo[4]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n5\n" + nineteenPeo[4]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n5\n" + twentyPeo[4] + "\n");
								break;
							}// end switch
						}

						// 第6個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV23.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：1 & "
										+ onePeo[5]);
								ServerBroadcast("6\n6\n" + onePeo[5] + "\n");
								break;
							case 2:
								ServerBroadcast("6\n6\n" + twoPeo[5] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n6\n" + threePeo[5] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n6\n" + fourPeo[5] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n6\n" + fivePeo[5] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n6\n" + sixPeo[5] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n6\n" + sevenPeo[5] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n6\n" + eightPeo[5] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n6\n" + ninePeo[5] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n6\n" + tenPeo[5] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n6\n" + elevenPeo[5] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n6\n" + twelvePeo[5] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n6\n" + thirteenPeo[5]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n6\n" + fourteenPeo[5]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n6\n" + fifteenPeo[5] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n6\n" + sixteenPeo[5] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n6\n" + seventeenPeo[5]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n6\n" + eighteenPeo[5]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n6\n" + nineteenPeo[5]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n6\n" + twentyPeo[5] + "\n");
								break;
							}// end switch
						}

						// 第7個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV31.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：1 & "
										+ onePeo[3]);
								ServerBroadcast("6\n1\n" + onePeo[6] + "\n");
								break;
							case 2:
								ServerBroadcast("6\n7\n" + twoPeo[6] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n7\n" + threePeo[6] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n7\n" + fourPeo[6] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n7\n" + fivePeo[6] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n7\n" + sixPeo[6] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n7\n" + sevenPeo[6] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n7\n" + eightPeo[6] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n7\n" + ninePeo[6] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n7\n" + tenPeo[6] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n7\n" + elevenPeo[6] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n7\n" + twelvePeo[6] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n7\n" + thirteenPeo[6]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n7\n" + fourteenPeo[6]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n7\n" + fifteenPeo[6] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n7\n" + sixteenPeo[6] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n7\n" + seventeenPeo[6]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n7\n" + eighteenPeo[6]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n7\n" + nineteenPeo[6]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n7\n" + twentyPeo[6] + "\n");
								break;
							}// end switch
						}

						// 第8個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV32.getText().toString())) {
							switch (numclient) {
							case 1:
								Log.i(TAG, "6--Server傳送預言家指定角色的號碼：1 & "
										+ onePeo[7]);
								ServerBroadcast("6\n8\n" + onePeo[7] + "\n");
								break;
							case 2:
								ServerBroadcast("6\n8\n" + twoPeo[7] + "\n");
								break;
							case 3:
								ServerBroadcast("6\n8\n" + threePeo[7] + "\n");
								break;
							case 4:
								ServerBroadcast("6\n8\n" + fourPeo[7] + "\n");
								break;
							case 5:
								ServerBroadcast("6\n8\n" + fivePeo[7] + "\n");
								break;
							case 6:
								ServerBroadcast("6\n8\n" + sixPeo[7] + "\n");
								break;
							case 7:
								ServerBroadcast("6\n8\n" + sevenPeo[7] + "\n");
								break;
							case 8:
								ServerBroadcast("6\n8\n" + eightPeo[7] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n8\n" + ninePeo[7] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n8\n" + tenPeo[7] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n8\n" + elevenPeo[7] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n8\n" + twelvePeo[7] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n8\n" + thirteenPeo[7]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n8\n" + fourteenPeo[7]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n8\n" + fifteenPeo[7] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n8\n" + sixteenPeo[7] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n8\n" + seventeenPeo[7]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n8\n" + eighteenPeo[7]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n8\n" + nineteenPeo[7]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n8\n" + twentyPeo[7] + "\n");
								break;
							}// end switch
						}

						// 第9個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV33.getText().toString())) {
							switch (numclient) {
							case 8:
								ServerBroadcast("6\n9\n" + eightPeo[8] + "\n");
								break;
							case 9:
								ServerBroadcast("6\n9\n" + ninePeo[8] + "\n");
								break;
							case 10:
								ServerBroadcast("6\n9\n" + tenPeo[8] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n9\n" + elevenPeo[8] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n9\n" + twelvePeo[8] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n9\n" + thirteenPeo[8]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n9\n" + fourteenPeo[8]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n9\n" + fifteenPeo[8] + "\n");
								break;
							case 16:
								ServerBroadcast("6\n9\n" + sixteenPeo[8] + "\n");
								break;
							case 17:
								ServerBroadcast("6\n9\n" + seventeenPeo[8]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n9\n" + eighteenPeo[8]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n9\n" + nineteenPeo[8]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n9\n" + twentyPeo[8] + "\n");
								break;
							}// end switch
						}

						// 第10個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV41.getText().toString())) {
							switch (numclient) {

							case 10:
								ServerBroadcast("6\n10\n" + tenPeo[9] + "\n");
								break;
							case 11:
								ServerBroadcast("6\n10\n" + elevenPeo[9] + "\n");
								break;
							case 12:
								ServerBroadcast("6\n10\n" + twelvePeo[9] + "\n");
								break;
							case 13:
								ServerBroadcast("6\n10\n" + thirteenPeo[9]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n10\n" + fourteenPeo[9]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n10\n" + fifteenPeo[9]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n10\n" + sixteenPeo[9]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n10\n" + seventeenPeo[9]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n10\n" + eighteenPeo[9]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n10\n" + nineteenPeo[9]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n10\n" + twentyPeo[9] + "\n");
								break;
							}// end switch
						}

						// 第11個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV42.getText().toString())) {
							switch (numclient) {
							case 11:
								ServerBroadcast("6\n11\n" + elevenPeo[10]
										+ "\n");
								break;
							case 12:
								ServerBroadcast("6\n11\n" + twelvePeo[10]
										+ "\n");
								break;
							case 13:
								ServerBroadcast("6\n11\n" + thirteenPeo[10]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n11\n" + fourteenPeo[10]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n11\n" + fifteenPeo[10]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n11\n" + sixteenPeo[10]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n11\n" + seventeenPeo[10]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n11\n" + eighteenPeo[10]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n11\n" + nineteenPeo[10]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n11\n" + twentyPeo[10]
										+ "\n");
								break;
							}// end switch
						}

						// 第12個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV43.getText().toString())) {
							switch (numclient) {
							case 12:
								ServerBroadcast("6\n12\n" + twelvePeo[11]
										+ "\n");
								break;
							case 13:
								ServerBroadcast("6\n12\n" + thirteenPeo[11]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n12\n" + fourteenPeo[11]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n12\n" + fifteenPeo[11]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n12\n" + sixteenPeo[11]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n12\n" + seventeenPeo[11]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n12\n" + eighteenPeo[11]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n12\n" + nineteenPeo[11]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n12\n" + twentyPeo[11]
										+ "\n");
								break;
							}// end switch
						}

						// 第13個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV51.getText().toString())) {
							switch (numclient) {
							case 13:
								ServerBroadcast("6\n13\n" + thirteenPeo[12]
										+ "\n");
								break;
							case 14:
								ServerBroadcast("6\n13\n" + fourteenPeo[12]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n13\n" + fifteenPeo[12]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n13\n" + sixteenPeo[12]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n13\n" + seventeenPeo[12]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n13\n" + eighteenPeo[12]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n13\n" + nineteenPeo[12]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n13\n" + twentyPeo[12]
										+ "\n");
								break;
							}// end switch
						}

						// 第14個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV52.getText().toString())) {
							switch (numclient) {
							case 14:
								ServerBroadcast("6\n14\n" + fourteenPeo[13]
										+ "\n");
								break;
							case 15:
								ServerBroadcast("6\n14\n" + fifteenPeo[13]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n14\n" + sixteenPeo[13]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n14\n" + seventeenPeo[13]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n14\n" + eighteenPeo[13]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n14\n" + nineteenPeo[13]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n14\n" + twentyPeo[13]
										+ "\n");
								break;
							}// end switch
						}

						// 第15個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV53.getText().toString())) {
							switch (numclient) {
							case 15:
								ServerBroadcast("6\n15\n" + fifteenPeo[14]
										+ "\n");
								break;
							case 16:
								ServerBroadcast("6\n15\n" + sixteenPeo[14]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n15\n" + seventeenPeo[14]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n15\n" + eighteenPeo[14]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n15\n" + nineteenPeo[14]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n15\n" + twentyPeo[14]
										+ "\n");
								break;
							}// end switch
						}
						// 第16個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV61.getText().toString())) {
							switch (numclient) {
							case 16:
								ServerBroadcast("6\n16\n" + sixteenPeo[15]
										+ "\n");
								break;
							case 17:
								ServerBroadcast("6\n16\n" + seventeenPeo[15]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n16\n" + eighteenPeo[15]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n16\n" + nineteenPeo[15]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n16\n" + twentyPeo[15]
										+ "\n");
								break;
							}// end switch
						}
						// 第17個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV62.getText().toString())) {
							switch (numclient) {
							case 17:
								ServerBroadcast("6\n17\n" + seventeenPeo[16]
										+ "\n");
								break;
							case 18:
								ServerBroadcast("6\n17\n" + eighteenPeo[16]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n17\n" + nineteenPeo[16]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n17\n" + twentyPeo[16]
										+ "\n");
								break;
							}// end switch
						}

						// 第18個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV63.getText().toString())) {
							switch (numclient) {
							case 18:
								ServerBroadcast("6\n18\n" + eighteenPeo[17]
										+ "\n");
								break;
							case 19:
								ServerBroadcast("6\n18\n" + nineteenPeo[17]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n18\n" + twentyPeo[17]
										+ "\n");
								break;
							}// end switch
						}
						// 第19個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV71.getText().toString())) {
							switch (numclient) {
							case 19:
								ServerBroadcast("6\n19\n" + nineteenPeo[18]
										+ "\n");
								break;
							case 20:
								ServerBroadcast("6\n19\n" + twentyPeo[18]
										+ "\n");
								break;
							}// end switch
						}
						// 第20個人
						if (strTemp02.equals("urDivined")
								&& strID.equals(ftV72.getText().toString())) {
							switch (numclient) {
							case 20:
								ServerBroadcast("6\n19\n" + twentyPeo[19]
										+ "\n");
								break;
							}// end switch
						}
					}// else { 收聽在ServerMaster時的腳色送來訊息
				}// end while

			}// end
			catch (Exception e) {
			}// end try
			
			
		}// end run

		public synchronized void ServersendMessage(final String messageToDisplay) {
			try {
				bw.write(messageToDisplay);
				bw.flush();
				Log.i(TAG, "Server傳送 : " + messageToDisplay);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end ServersendMEssage

	}// end MultiServerThread

	public void displayMessage2(final String messageToDisplay) {

		mhandler2.post(new Runnable() {
			public void run() {
				serverstatusss.setText(messageToDisplay);
			}

		});// end post
	}

	// 顯示在主持人連線畫面的Status
	public void displayMessage(final String messageToDisplay) {
		mhandler.post(new Runnable() {
			public void run() {
				serverstatus.setText(messageToDisplay);
			}

		});// end post
	}

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
	public String get3GIpAddress() {
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
		if (isenter == 1) {
			MP0_dark.stop();
			MP1_cupid.stop();
			MP2_guard.stop();
			MP3_killer.stop();
			MP4_witch.stop();
			MP5_future.stop();
			MP6_light.stop();
			MP7_event.stop();
		} else {
			mp.stop();
		}
		// exitActivity(1);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (isenter == 0) {
			mp = MediaPlayer.create(this, R.raw.start);
			mp.setLooping(true);
			mp.start();
		}

	}

	/*************************** 以上是BuildServer,以下是BuildServer ******************************/
	private static void ServerBroadcast(final String message) {
		Iterator iter = players.iterator();
		while (iter.hasNext()) {
			MultiServerThread t = (MultiServerThread) iter.next();
			t.ServersendMessage(message);
		}
	} // end method sendData

	/***** 換到BuildServer *****/
	public void GotoMasterServer() {

		setContentView(R.layout.f);
		/****************************** 音樂處理 ******************************/
		stopService(intent);// 把開頭音樂關掉
		isenter = 1;
		whereisme = 1;
		ServerBroadcast("2\n" + allclientid[0] + "\n" + allclientid[1] + "\n"
				+ allclientid[2] + "\n" + allclientid[3] + "\n"
				+ allclientid[4] + "\n" + allclientid[5] + "\n"
				+ allclientid[6] + "\n" + allclientid[7] + "\n"
				+ allclientid[8] + "\n" + allclientid[9] + "\n"
				+ allclientid[10] + "\n" + allclientid[11] + "\n"
				+ allclientid[12] + "\n" + allclientid[13] + "\n"
				+ allclientid[14] + "\n" + allclientid[15] + "\n"
				+ allclientid[16] + "\n" + allclientid[17] + "\n"
				+ allclientid[18] + "\n" + allclientid[19] + "\n");
		MP0_dark = new MediaPlayer();
		MP1_cupid = new MediaPlayer();
		MP2_guard = new MediaPlayer();
		MP3_killer = new MediaPlayer();
		MP4_witch = new MediaPlayer();
		MP5_future = new MediaPlayer();
		MP6_light = new MediaPlayer();
		MP7_event = new MediaPlayer();

		MP0_dark = MediaPlayer.create(BuildServer.this, R.raw.dark);
		MP1_cupid = MediaPlayer.create(BuildServer.this, R.raw.cupid);
		MP2_guard = MediaPlayer.create(BuildServer.this, R.raw.guard);
		MP3_killer = MediaPlayer.create(BuildServer.this, R.raw.killer);
		MP4_witch = MediaPlayer.create(BuildServer.this, R.raw.witch);
		MP5_future = MediaPlayer.create(BuildServer.this, R.raw.future);
		MP6_light = MediaPlayer.create(BuildServer.this, R.raw.light);
		MP7_event = MediaPlayer.create(BuildServer.this, R.raw.event);

		final MediaPlayer[] music = { MP0_dark, // 黑夜 = music[0]
				MP1_cupid, // 邱比特= music[1]
				MP2_guard, // 守衛 = music[2]
				MP3_killer,// 殺手 = music[3]
				MP4_witch, // 女巫 = music[4]
				MP5_future,// 預言家= music[5]
				MP6_light, // 6 7 9為白天
				MP7_event };// 事件 = music[7]

		/** 一進入就開始放黑夜音樂 **/
		try {
			music[0].prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		music[0].start();
		/** 一進入就開始放黑夜音樂 **/

		/****************************** 按鈕前置設定 ******************************/
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

		/*******/
		fiV1111 = (ImageView) findViewById(R.id.fiV1111);
		fiV1121 = (ImageView) findViewById(R.id.fiV1121);
		fiV1131 = (ImageView) findViewById(R.id.fiV1131);
		fiV1141 = (ImageView) findViewById(R.id.fiV1141);
		fiV1211 = (ImageView) findViewById(R.id.fiV1211);
		fiV1221 = (ImageView) findViewById(R.id.fiV1221);
		fiV1231 = (ImageView) findViewById(R.id.fiV1231);
		fiV1241 = (ImageView) findViewById(R.id.fiV1241);
		fiV1112 = (ImageView) findViewById(R.id.fiV1112);
		fiV1122 = (ImageView) findViewById(R.id.fiV1122);
		fiV1132 = (ImageView) findViewById(R.id.fiV1132);
		fiV1142 = (ImageView) findViewById(R.id.fiV1142);
		fiV1212 = (ImageView) findViewById(R.id.fiV1212);
		fiV1222 = (ImageView) findViewById(R.id.fiV1222);
		fiV1232 = (ImageView) findViewById(R.id.fiV1232);
		fiV1242 = (ImageView) findViewById(R.id.fiV1242);
		fiV1113 = (ImageView) findViewById(R.id.fiV1113);
		fiV1123 = (ImageView) findViewById(R.id.fiV1123);
		fiV1133 = (ImageView) findViewById(R.id.fiV1133);
		fiV1143 = (ImageView) findViewById(R.id.fiV1143);
		fiV1213 = (ImageView) findViewById(R.id.fiV1213);
		fiV1223 = (ImageView) findViewById(R.id.fiV1223);
		fiV1233 = (ImageView) findViewById(R.id.fiV1233);
		fiV1243 = (ImageView) findViewById(R.id.fiV1243);

		fiV2111 = (ImageView) findViewById(R.id.fiV2111);
		fiV2121 = (ImageView) findViewById(R.id.fiV2121);
		fiV2131 = (ImageView) findViewById(R.id.fiV2131);
		fiV2141 = (ImageView) findViewById(R.id.fiV2141);
		fiV2211 = (ImageView) findViewById(R.id.fiV2211);
		fiV2221 = (ImageView) findViewById(R.id.fiV2221);
		fiV2231 = (ImageView) findViewById(R.id.fiV2231);
		fiV2241 = (ImageView) findViewById(R.id.fiV2241);
		fiV2112 = (ImageView) findViewById(R.id.fiV2112);
		fiV2122 = (ImageView) findViewById(R.id.fiV2122);
		fiV2132 = (ImageView) findViewById(R.id.fiV2132);
		fiV2142 = (ImageView) findViewById(R.id.fiV2142);
		fiV2212 = (ImageView) findViewById(R.id.fiV2212);
		fiV2222 = (ImageView) findViewById(R.id.fiV2222);
		fiV2232 = (ImageView) findViewById(R.id.fiV2232);
		fiV2242 = (ImageView) findViewById(R.id.fiV2242);
		fiV2113 = (ImageView) findViewById(R.id.fiV2113);
		fiV2123 = (ImageView) findViewById(R.id.fiV2123);
		fiV2133 = (ImageView) findViewById(R.id.fiV2133);
		fiV2143 = (ImageView) findViewById(R.id.fiV2143);
		fiV2213 = (ImageView) findViewById(R.id.fiV2213);
		fiV2223 = (ImageView) findViewById(R.id.fiV2223);
		fiV2233 = (ImageView) findViewById(R.id.fiV2233);
		fiV2243 = (ImageView) findViewById(R.id.fiV2243);

		fiV3111 = (ImageView) findViewById(R.id.fiV3111);
		fiV3121 = (ImageView) findViewById(R.id.fiV3121);
		fiV3131 = (ImageView) findViewById(R.id.fiV3131);
		fiV3141 = (ImageView) findViewById(R.id.fiV3141);
		fiV3211 = (ImageView) findViewById(R.id.fiV3211);
		fiV3221 = (ImageView) findViewById(R.id.fiV3221);
		fiV3231 = (ImageView) findViewById(R.id.fiV3231);
		fiV3241 = (ImageView) findViewById(R.id.fiV3241);
		fiV3112 = (ImageView) findViewById(R.id.fiV3112);
		fiV3122 = (ImageView) findViewById(R.id.fiV3122);
		fiV3132 = (ImageView) findViewById(R.id.fiV3132);
		fiV3142 = (ImageView) findViewById(R.id.fiV3142);
		fiV3212 = (ImageView) findViewById(R.id.fiV3212);
		fiV3222 = (ImageView) findViewById(R.id.fiV3222);
		fiV3232 = (ImageView) findViewById(R.id.fiV3232);
		fiV3242 = (ImageView) findViewById(R.id.fiV3242);
		fiV3113 = (ImageView) findViewById(R.id.fiV3113);
		fiV3123 = (ImageView) findViewById(R.id.fiV3123);
		fiV3133 = (ImageView) findViewById(R.id.fiV3133);
		fiV3143 = (ImageView) findViewById(R.id.fiV3143);
		fiV3213 = (ImageView) findViewById(R.id.fiV3213);
		fiV3223 = (ImageView) findViewById(R.id.fiV3223);
		fiV3233 = (ImageView) findViewById(R.id.fiV3233);
		fiV3243 = (ImageView) findViewById(R.id.fiV3243);

		fiV4111 = (ImageView) findViewById(R.id.fiV4111);
		fiV4121 = (ImageView) findViewById(R.id.fiV4121);
		fiV4131 = (ImageView) findViewById(R.id.fiV4131);
		fiV4141 = (ImageView) findViewById(R.id.fiV4141);
		fiV4211 = (ImageView) findViewById(R.id.fiV4211);
		fiV4221 = (ImageView) findViewById(R.id.fiV4221);
		fiV4231 = (ImageView) findViewById(R.id.fiV4231);
		fiV4241 = (ImageView) findViewById(R.id.fiV4241);
		fiV4112 = (ImageView) findViewById(R.id.fiV4112);
		fiV4122 = (ImageView) findViewById(R.id.fiV4122);
		fiV4132 = (ImageView) findViewById(R.id.fiV4132);
		fiV4142 = (ImageView) findViewById(R.id.fiV4142);
		fiV4212 = (ImageView) findViewById(R.id.fiV4212);
		fiV4222 = (ImageView) findViewById(R.id.fiV4222);
		fiV4232 = (ImageView) findViewById(R.id.fiV4232);
		fiV4242 = (ImageView) findViewById(R.id.fiV4242);
		fiV4113 = (ImageView) findViewById(R.id.fiV4113);
		fiV4123 = (ImageView) findViewById(R.id.fiV4123);
		fiV4133 = (ImageView) findViewById(R.id.fiV4133);
		fiV4143 = (ImageView) findViewById(R.id.fiV4143);
		fiV4213 = (ImageView) findViewById(R.id.fiV4213);
		fiV4223 = (ImageView) findViewById(R.id.fiV4223);
		fiV4233 = (ImageView) findViewById(R.id.fiV4233);
		fiV4243 = (ImageView) findViewById(R.id.fiV4243);

		fiV5111 = (ImageView) findViewById(R.id.fiV5111);
		fiV5121 = (ImageView) findViewById(R.id.fiV5121);
		fiV5131 = (ImageView) findViewById(R.id.fiV5131);
		fiV5141 = (ImageView) findViewById(R.id.fiV5141);
		fiV5211 = (ImageView) findViewById(R.id.fiV5211);
		fiV5221 = (ImageView) findViewById(R.id.fiV5221);
		fiV5231 = (ImageView) findViewById(R.id.fiV5231);
		fiV5241 = (ImageView) findViewById(R.id.fiV5241);
		fiV5112 = (ImageView) findViewById(R.id.fiV5112);
		fiV5122 = (ImageView) findViewById(R.id.fiV5122);
		fiV5132 = (ImageView) findViewById(R.id.fiV5132);
		fiV5142 = (ImageView) findViewById(R.id.fiV5142);
		fiV5212 = (ImageView) findViewById(R.id.fiV5212);
		fiV5222 = (ImageView) findViewById(R.id.fiV5222);
		fiV5232 = (ImageView) findViewById(R.id.fiV5232);
		fiV5242 = (ImageView) findViewById(R.id.fiV5242);
		fiV5113 = (ImageView) findViewById(R.id.fiV5113);
		fiV5123 = (ImageView) findViewById(R.id.fiV5123);
		fiV5133 = (ImageView) findViewById(R.id.fiV5133);
		fiV5143 = (ImageView) findViewById(R.id.fiV5143);
		fiV5213 = (ImageView) findViewById(R.id.fiV5213);
		fiV5223 = (ImageView) findViewById(R.id.fiV5223);
		fiV5233 = (ImageView) findViewById(R.id.fiV5233);
		fiV5243 = (ImageView) findViewById(R.id.fiV5243);

		fiV6111 = (ImageView) findViewById(R.id.fiV6111);
		fiV6121 = (ImageView) findViewById(R.id.fiV6121);
		fiV6131 = (ImageView) findViewById(R.id.fiV6131);
		fiV6141 = (ImageView) findViewById(R.id.fiV6141);
		fiV6211 = (ImageView) findViewById(R.id.fiV6211);
		fiV6221 = (ImageView) findViewById(R.id.fiV6221);
		fiV6231 = (ImageView) findViewById(R.id.fiV6231);
		fiV6241 = (ImageView) findViewById(R.id.fiV6241);
		fiV6112 = (ImageView) findViewById(R.id.fiV6112);
		fiV6122 = (ImageView) findViewById(R.id.fiV6122);
		fiV6132 = (ImageView) findViewById(R.id.fiV6132);
		fiV6142 = (ImageView) findViewById(R.id.fiV6142);
		fiV6212 = (ImageView) findViewById(R.id.fiV6212);
		fiV6222 = (ImageView) findViewById(R.id.fiV6222);
		fiV6232 = (ImageView) findViewById(R.id.fiV6232);
		fiV6242 = (ImageView) findViewById(R.id.fiV6242);
		fiV6113 = (ImageView) findViewById(R.id.fiV6113);
		fiV6123 = (ImageView) findViewById(R.id.fiV6123);
		fiV6133 = (ImageView) findViewById(R.id.fiV6133);
		fiV6143 = (ImageView) findViewById(R.id.fiV6143);
		fiV6213 = (ImageView) findViewById(R.id.fiV6213);
		fiV6223 = (ImageView) findViewById(R.id.fiV6223);
		fiV6233 = (ImageView) findViewById(R.id.fiV6233);
		fiV6243 = (ImageView) findViewById(R.id.fiV6243);
		cupid = (RelativeLayout) findViewById(R.id.cupid);
		fiV7111 = (ImageView) findViewById(R.id.fiV7111);
		fiV7121 = (ImageView) findViewById(R.id.fiV7121);
		fiV7131 = (ImageView) findViewById(R.id.fiV7131);
		fiV7141 = (ImageView) findViewById(R.id.fiV7141);
		fiV7211 = (ImageView) findViewById(R.id.fiV7211);
		fiV7221 = (ImageView) findViewById(R.id.fiV7221);
		fiV7231 = (ImageView) findViewById(R.id.fiV7231);
		fiV7241 = (ImageView) findViewById(R.id.fiV7241);
		fiV7112 = (ImageView) findViewById(R.id.fiV7112);
		fiV7122 = (ImageView) findViewById(R.id.fiV7122);
		fiV7132 = (ImageView) findViewById(R.id.fiV7132);
		fiV7142 = (ImageView) findViewById(R.id.fiV7142);
		fiV7212 = (ImageView) findViewById(R.id.fiV7212);
		fiV7222 = (ImageView) findViewById(R.id.fiV7222);
		fiV7232 = (ImageView) findViewById(R.id.fiV7232);
		fiV7242 = (ImageView) findViewById(R.id.fiV7242);

		fIB1 = (Button) this.findViewById(R.id.fb2);// 主持人測蘭按鈕
		fSD1 = (SlidingDrawer) findViewById(R.id.fSD1);
		text = (TextView) findViewById(R.id.text);
		serverstatusss = (TextView) findViewById(R.id.serverstatusss);
		TextPaint tp = text.getPaint();
		tp.setFakeBoldText(true);
		text = (TextView) this.findViewById(R.id.text);
		detail = (TextView) this.findViewById(R.id.ex);
		f2 = (ImageView) this.findViewById(R.id.fim1);
		f3 = (Button) this.findViewById(R.id.f3);// 往前
		f4 = (Button) this.findViewById(R.id.f4);// 往後
		// f5 = (Button) this.findViewById(R.id.f5);// 往前
		f6 = (Button) this.findViewById(R.id.f6);// 往後
		text.setText(status[0]);
		detail.setText(details[0]);

		ftV11.setText(allclientid[0]);
		ftV12.setText(allclientid[1]);
		ftV13.setText(allclientid[2]);
		ftV21.setText(allclientid[3]);
		ftV22.setText(allclientid[4]);
		ftV23.setText(allclientid[5]);
		ftV31.setText(allclientid[6]);
		ftV32.setText(allclientid[7]);
		ftV33.setText(allclientid[8]);
		ftV41.setText(allclientid[8]);
		ftV42.setText(allclientid[10]);
		ftV43.setText(allclientid[11]);
		ftV51.setText(allclientid[12]);
		ftV52.setText(allclientid[13]);
		ftV53.setText(allclientid[14]);
		ftV61.setText(allclientid[15]);
		ftV62.setText(allclientid[16]);
		ftV63.setText(allclientid[17]);
		ftV71.setText(allclientid[18]);
		ftV72.setText(allclientid[19]);

		if (numclient == 1) {
			fsIB1.setBackgroundResource(characterspicture[onePeo[0]]);
		} else if (numclient == 2) {
			fsIB1.setBackgroundResource(characterspicture[twoPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[twoPeo[1]]);
		} else if (numclient == 3) {
			fsIB1.setBackgroundResource(characterspicture[threePeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[threePeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[threePeo[2]]);
		} else if (numclient == 4) {
			fsIB1.setBackgroundResource(characterspicture[fourPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[fourPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[fourPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[fourPeo[3]]);
		} else if (numclient == 5) {
			fsIB1.setBackgroundResource(characterspicture[fivePeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[fivePeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[fivePeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[fivePeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[fivePeo[4]]);
		} else if (numclient == 6) {
			fsIB1.setBackgroundResource(characterspicture[sixPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[sixPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[sixPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[sixPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[sixPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[sixPeo[5]]);
		} else if (numclient == 7) {
			fsIB1.setBackgroundResource(characterspicture[sevenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[sevenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[sevenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[sevenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[sevenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[sevenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[sevenPeo[6]]);
		} else if (numclient == 8) {
			fsIB1.setBackgroundResource(characterspicture[eightPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[eightPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[eightPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[eightPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[eightPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[eightPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[eightPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[eightPeo[7]]);
		} else if (numclient == 9) {
			fsIB1.setBackgroundResource(characterspicture[ninePeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[ninePeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[ninePeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[ninePeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[ninePeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[ninePeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[ninePeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[ninePeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[ninePeo[8]]);
		} else if (numclient == 10) {
			fsIB1.setBackgroundResource(characterspicture[tenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[tenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[tenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[tenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[tenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[tenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[tenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[tenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[tenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[tenPeo[9]]);
		} else if (numclient == 11) {
			fsIB1.setBackgroundResource(characterspicture[elevenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[elevenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[elevenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[elevenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[elevenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[elevenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[elevenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[elevenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[elevenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[elevenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[elevenPeo[10]]);
		} else if (numclient == 12) {
			fsIB1.setBackgroundResource(characterspicture[twelvePeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[twelvePeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[twelvePeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[twelvePeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[twelvePeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[twelvePeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[twelvePeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[twelvePeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[twelvePeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[twelvePeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[twelvePeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[twelvePeo[11]]);
		} else if (numclient == 13) {
			fsIB1.setBackgroundResource(characterspicture[thirteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[thirteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[thirteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[thirteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[thirteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[thirteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[thirteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[thirteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[thirteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[thirteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[thirteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[thirteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[thirteenPeo[12]]);
		} else if (numclient == 14) {
			fsIB1.setBackgroundResource(characterspicture[fourteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[fourteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[fourteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[fourteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[fourteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[fourteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[fourteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[fourteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[fourteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[fourteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[fourteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[fourteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[fourteenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[fourteenPeo[13]]);
		} else if (numclient == 15) {
			fsIB1.setBackgroundResource(characterspicture[fifteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[fifteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[fifteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[fifteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[fifteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[fifteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[fifteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[fifteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[fifteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[fifteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[fifteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[fifteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[fifteenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[fifteenPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[fifteenPeo[14]]);
		} else if (numclient == 16) {
			fsIB1.setBackgroundResource(characterspicture[sixteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[sixteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[sixteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[sixteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[sixteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[sixteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[sixteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[sixteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[sixteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[sixteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[sixteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[sixteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[sixteenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[sixteenPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[sixteenPeo[14]]);
			fsIB16.setBackgroundResource(characterspicture[sixteenPeo[15]]);
		} else if (numclient == 17) {
			fsIB1.setBackgroundResource(characterspicture[seventeenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[seventeenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[seventeenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[seventeenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[seventeenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[seventeenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[seventeenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[seventeenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[seventeenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[seventeenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[seventeenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[seventeenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[seventeenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[seventeenPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[seventeenPeo[14]]);
			fsIB16.setBackgroundResource(characterspicture[seventeenPeo[15]]);
			fsIB17.setBackgroundResource(characterspicture[seventeenPeo[16]]);
		} else if (numclient == 18) {
			fsIB1.setBackgroundResource(characterspicture[eighteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[eighteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[eighteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[eighteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[eighteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[eighteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[eighteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[eighteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[eighteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[eighteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[eighteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[eighteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[eighteenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[eighteenPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[eighteenPeo[14]]);
			fsIB16.setBackgroundResource(characterspicture[eighteenPeo[15]]);
			fsIB17.setBackgroundResource(characterspicture[eighteenPeo[16]]);
			fsIB18.setBackgroundResource(characterspicture[eighteenPeo[17]]);
		} else if (numclient == 19) {
			fsIB1.setBackgroundResource(characterspicture[nineteenPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[nineteenPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[nineteenPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[nineteenPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[nineteenPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[nineteenPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[nineteenPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[nineteenPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[nineteenPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[nineteenPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[nineteenPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[nineteenPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[nineteenPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[nineteenPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[nineteenPeo[14]]);
			fsIB16.setBackgroundResource(characterspicture[nineteenPeo[15]]);
			fsIB17.setBackgroundResource(characterspicture[nineteenPeo[16]]);
			fsIB18.setBackgroundResource(characterspicture[nineteenPeo[17]]);
			fsIB19.setBackgroundResource(characterspicture[nineteenPeo[18]]);
		} else if (numclient == 20) {
			fsIB1.setBackgroundResource(characterspicture[twentyPeo[0]]);
			fsIB2.setBackgroundResource(characterspicture[twentyPeo[1]]);
			fsIB3.setBackgroundResource(characterspicture[twentyPeo[2]]);
			fsIB4.setBackgroundResource(characterspicture[twentyPeo[3]]);
			fsIB5.setBackgroundResource(characterspicture[twentyPeo[4]]);
			fsIB6.setBackgroundResource(characterspicture[twentyPeo[5]]);
			fsIB7.setBackgroundResource(characterspicture[twentyPeo[6]]);
			fsIB8.setBackgroundResource(characterspicture[twentyPeo[7]]);
			fsIB9.setBackgroundResource(characterspicture[twentyPeo[8]]);
			fsIB10.setBackgroundResource(characterspicture[twentyPeo[9]]);
			fsIB11.setBackgroundResource(characterspicture[twentyPeo[10]]);
			fsIB12.setBackgroundResource(characterspicture[twentyPeo[11]]);
			fsIB13.setBackgroundResource(characterspicture[twentyPeo[12]]);
			fsIB14.setBackgroundResource(characterspicture[twentyPeo[13]]);
			fsIB15.setBackgroundResource(characterspicture[twentyPeo[14]]);
			fsIB16.setBackgroundResource(characterspicture[twentyPeo[15]]);
			fsIB17.setBackgroundResource(characterspicture[twentyPeo[16]]);
			fsIB18.setBackgroundResource(characterspicture[twentyPeo[17]]);
			fsIB19.setBackgroundResource(characterspicture[twentyPeo[18]]);
			fsIB20.setBackgroundResource(characterspicture[twentyPeo[19]]);
		}

		/** Toast Event 事件回顧處理 **/
	

		// 碰到slidedrawer的動作
		listener1 = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (fSD1.isOpened())
					((SlidingDrawer) v).animateClose();
				return false;
			}
		};
		fSD1.setOnTouchListener(listener1);

		// 碰到新令Button的動作，小心，import時有兩個OnClickListener，要用View view的
		OnClickListener listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				fSD1.animateOpen();
				// 要用.equals才能比對字串是否一樣
				/** 第一個人 **/
				// && strID == ftV11.getText().toString()
				if (reckilled1 == 1 && strID.equals(ftV11.getText().toString())) {
					fiV1111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV11.getText().toString())) {
					fiV1121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV11.getText().toString())) {
					fiV1131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV11.getText().toString())) {
					fiV1141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV11.getText().toString())) {
					fiV1211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV11.getText().toString())) {
					fiV1221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV11.getText().toString())) {
					fiV1231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV11.getText().toString())) {
					fiV1241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}

				/** 第二個人 **/
				if (reckilled1 == 1 && strID.equals(ftV12.getText().toString())) {
					fiV1112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV12.getText().toString())) {
					fiV1122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV12.getText().toString())) {
					fiV1132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV12.getText().toString())) {
					fiV1142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV12.getText().toString())) {
					fiV1212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV12.getText().toString())) {
					fiV1222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV12.getText().toString())) {
					fiV1232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV12.getText().toString())) {
					fiV1242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				/** 第三個人 **/
				if (reckilled1 == 1 && strID.equals(ftV13.getText().toString())) {
					fiV1113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV13.getText().toString())) {
					fiV1123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV13.getText().toString())) {
					fiV1133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV13.getText().toString())) {
					fiV1143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV13.getText().toString())) {
					fiV1213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV13.getText().toString())) {
					fiV1223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV13.getText().toString())) {
					fiV1233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV13.getText().toString())) {
					fiV1243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第四個人 **//* // && strID == ftV11.getText().toString()
				if (reckilled1 == 1 && strID.equals(ftV21.getText().toString())) {
					fiV2111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV21.getText().toString())) {
					fiV2121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV21.getText().toString())) {
					fiV2131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV21.getText().toString())) {
					fiV2141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV21.getText().toString())) {
					fiV2211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV21.getText().toString())) {
					fiV2221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV21.getText().toString())) {
					fiV2231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV21.getText().toString())) {
					fiV2241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第五個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV22.getText().toString())) {
					fiV2112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV22.getText().toString())) {
					fiV2122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV22.getText().toString())) {
					fiV2132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV22.getText().toString())) {
					fiV2142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV22.getText().toString())) {
					fiV2212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV22.getText().toString())) {
					fiV2222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV22.getText().toString())) {
					fiV2232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV22.getText().toString())) {
					fiV2242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第6個人 **/
				if (reckilled1 == 1 && strID.equals(ftV23.getText().toString())) {
					fiV2113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV23.getText().toString())) {
					fiV2123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV23.getText().toString())) {
					fiV2133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV23.getText().toString())) {
					fiV2143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV23.getText().toString())) {
					fiV2213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV23.getText().toString())) {
					fiV2223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV23.getText().toString())) {
					fiV2233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV23.getText().toString())) {
					fiV2243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}

				/** 第7個人 **/
				if (reckilled1 == 1 && strID.equals(ftV31.getText().toString())) {
					fiV3111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV31.getText().toString())) {
					fiV3121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV31.getText().toString())) {
					fiV3131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV31.getText().toString())) {
					fiV3141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV31.getText().toString())) {
					fiV3211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV31.getText().toString())) {
					fiV3221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV31.getText().toString())) {
					fiV3231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV31.getText().toString())) {
					fiV3241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				/** 第8個人 **/
				if (reckilled1 == 1 && strID.equals(ftV32.getText().toString())) {
					fiV3112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV32.getText().toString())) {
					fiV3122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV32.getText().toString())) {
					fiV3132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV32.getText().toString())) {
					fiV3142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV32.getText().toString())) {
					fiV3212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV32.getText().toString())) {
					fiV3222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV32.getText().toString())) {
					fiV3232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV32.getText().toString())) {
					fiV3242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				/** 第9個人 **/
				if (reckilled1 == 1 && strID.equals(ftV33.getText().toString())) {
					fiV3113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV33.getText().toString())) {
					fiV3123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV33.getText().toString())) {
					fiV3133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV33.getText().toString())) {
					fiV3143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV33.getText().toString())) {
					fiV3213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV33.getText().toString())) {
					fiV3223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV33.getText().toString())) {
					fiV3233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV33.getText().toString())) {
					fiV3243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}

				// ** 第10個人 **//* // && strID == ftV11.getText().toString()
				if (reckilled1 == 1 && strID.equals(ftV41.getText().toString())) {
					fiV4111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV41.getText().toString())) {
					fiV4121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV41.getText().toString())) {
					fiV4131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV41.getText().toString())) {
					fiV4141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV41.getText().toString())) {
					fiV4211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV41.getText().toString())) {
					fiV4221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV41.getText().toString())) {
					fiV4231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV41.getText().toString())) {
					fiV4241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第11個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV42.getText().toString())) {
					fiV4112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV42.getText().toString())) {
					fiV4122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV42.getText().toString())) {
					fiV4132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV42.getText().toString())) {
					fiV4142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV42.getText().toString())) {
					fiV4212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV42.getText().toString())) {
					fiV4222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV42.getText().toString())) {
					fiV4232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV42.getText().toString())) {
					fiV4242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第12個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV43.getText().toString())) {
					fiV4113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV43.getText().toString())) {
					fiV4123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV43.getText().toString())) {
					fiV4133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV43.getText().toString())) {
					fiV4143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV43.getText().toString())) {
					fiV4213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV43.getText().toString())) {
					fiV4223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV43.getText().toString())) {
					fiV4233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV43.getText().toString())) {
					fiV4243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}

				// ** 第13個人 **//* // && strID == ftV11.getText().toString()
				if (reckilled1 == 1 && strID.equals(ftV51.getText().toString())) {
					fiV5111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV51.getText().toString())) {
					fiV5121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV51.getText().toString())) {
					fiV5131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV51.getText().toString())) {
					fiV5141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV51.getText().toString())) {
					fiV5211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV51.getText().toString())) {
					fiV5221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV51.getText().toString())) {
					fiV5231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV51.getText().toString())) {
					fiV5241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第14個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV52.getText().toString())) {
					fiV5112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV52.getText().toString())) {
					fiV5122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV52.getText().toString())) {
					fiV5132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV52.getText().toString())) {
					fiV5142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV52.getText().toString())) {
					fiV5212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV52.getText().toString())) {
					fiV5222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV52.getText().toString())) {
					fiV5232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV52.getText().toString())) {
					fiV5242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第15個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV53.getText().toString())) {
					fiV5113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV53.getText().toString())) {
					fiV5123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV53.getText().toString())) {
					fiV5133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV53.getText().toString())) {
					fiV5143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV53.getText().toString())) {
					fiV5213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV53.getText().toString())) {
					fiV5223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV53.getText().toString())) {
					fiV5233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV53.getText().toString())) {
					fiV5243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}

				// ** 第16個人 **//* // && strID == ftV11.getText().toString()
				if (reckilled1 == 1 && strID.equals(ftV61.getText().toString())) {
					fiV6111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV61.getText().toString())) {
					fiV6121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV61.getText().toString())) {
					fiV6131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV61.getText().toString())) {
					fiV6141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV61.getText().toString())) {
					fiV6211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV61.getText().toString())) {
					fiV6221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV61.getText().toString())) {
					fiV6231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV61.getText().toString())) {
					fiV6241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第17個人 **//*
				if (reckilled1 == 1 && strID.equals(ftV62.getText().toString())) {
					fiV6112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV62.getText().toString())) {
					fiV6122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV62.getText().toString())) {
					fiV6132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV62.getText().toString())) {
					fiV6142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV62.getText().toString())) {
					fiV6212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV62.getText().toString())) {
					fiV6222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV62.getText().toString())) {
					fiV6232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV62.getText().toString())) {
					fiV6242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第18個人 * */
				if (reckilled1 == 1 && strID.equals(ftV63.getText().toString())) {
					fiV6113.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV63.getText().toString())) {
					fiV6123.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV63.getText().toString())) {
					fiV6133.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV63.getText().toString())) {
					fiV6143.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV63.getText().toString())) {
					fiV6213.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV63.getText().toString())) {
					fiV6223.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV63.getText().toString())) {
					fiV6233.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV63.getText().toString())) {
					fiV6243.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				// ** 第19個人 *
				if (reckilled1 == 1 && strID.equals(ftV71.getText().toString())) {
					fiV7111.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV71.getText().toString())) {
					fiV7121.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV71.getText().toString())) {
					fiV7131.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV71.getText().toString())) {
					fiV7141.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV71.getText().toString())) {
					fiV7211.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV71.getText().toString())) {
					fiV7221.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV71.getText().toString())) {
					fiV7231.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV71.getText().toString())) {
					fiV7241.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
				/** 第20個人 **/
				if (reckilled1 == 1 && strID.equals(ftV72.getText().toString())) {
					fiV7112.setBackgroundResource(R.drawable.f41);// 換成紅刀
				}
				if (reckilled2 == 2 && strID.equals(ftV72.getText().toString())) {
					fiV7122.setBackgroundResource(R.drawable.f42);// 換成刀
				}
				if (reckilled3 == 3 && strID.equals(ftV72.getText().toString())) {
					fiV7132.setBackgroundResource(R.drawable.f43);// 換成刀
				}
				if (reckilled4 == 4 && strID.equals(ftV72.getText().toString())) {
					fiV7142.setBackgroundResource(R.drawable.f44);// 換成刀
				}
				if (reclove == 5 && strID.equals(ftV72.getText().toString())) {
					fiV7212.setBackgroundResource(R.drawable.f45);// 換成愛心
				}
				if (recguard == 6 && strID.equals(ftV72.getText().toString())) {
					fiV7222.setBackgroundResource(R.drawable.f46);// 換成守衛
				}
				if (rechelp == 7 && strID.equals(ftV72.getText().toString())) {
					fiV7232.setBackgroundResource(R.drawable.f47);// 換成救藥
				}
				if (recpoison == 8 && strID.equals(ftV72.getText().toString())) {
					fiV7242.setBackgroundResource(R.drawable.f48);// 換成毒藥
				}
			}
		};
		fIB1.setOnClickListener(listener2);

		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					cupid.setBackgroundResource(R.drawable.f666); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					cupid.setBackgroundResource(R.drawable.r1);
				}
				return false;
			}
		};
		f6.setOnTouchListener(Olistener1);

		f6.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayMessage2("請看人物說明");
				ShowPopup();

			}
		});

		/******************************** Listener3 -- 向go一個回合 ********************************/
		OnClickListener listener3 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "i=" + i);

				if (i == 0) {
					Log.i(TAG, "邱比特回合：i=" + i);
					i++;// Cupid
						// 5:recTurn 2:CupidTurn
					ServerBroadcast("5\n2\n");
					
					Log.i(TAG, "Cupid按鈕Enable,recTurn:2");

					String stringValue = Integer.toString(i);
					text.setText(status[i]);
					detail.setText(details[i]);

					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[0].pause();
					try {
						music[1].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[1].start();
					cupided++;

					if (isGuard == 0) {
						i = i + 1;
						Log.i(TAG, "i=" + i + "isCupid=" + isCupid + "isGuard="
								+ isGuard);
					}
				}

				else if (i == 1) {
					Log.i(TAG, "守衛回合：i=" + i);
					i++;
					reclove = 0;
					
					// 5:recTurn 3:GuardTurn
					ServerBroadcast("7\n2\n");
					ServerBroadcast("5\n3\n");
					String stringValue = Integer.toString(i);
					text.setText(status[i]);
					detail.setText(details[i]);

					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					if (isCupid == 0) {
						music[0].pause();
					} else {
						music[1].pause();
					}

					try {
						music[2].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[2].start();
				}

				else if (i == 2) {
					Log.i(TAG, "殺手回合：i=" + i);
					i++;// Killer
					
					// 把守衛的Rec歸0 避免殺手殺人導致 其他人也被保護
					recguard = 0;
					// 5:recTurn 0:KillerTurn
					ServerBroadcast("7\n3\n");
					ServerBroadcast("5\n0\n");
					Log.i(TAG, "殺手按鈕Enable,recTurn:0");

					String stringValue = Integer.toString(i);
					text.setText(status[i]);
					detail.setText(details[i]);

					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					if (isWitch == 0 && isProphet == 0) {
						i = i + 2;
					} else if (isWitch == 0) {
						i = i + 1;
					}

					if (isCupid == 0 && isGuard == 0) {
						music[0].pause();
					} else if (isCupid == 0) {
						music[1].pause();
					} else {
						music[2].pause();
					}
					try {
						music[3].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[3].start();
				}

				else if (i == 3) {
					Log.i(TAG, "女巫回合：i=" + i);
					i++;// Witch
					reckilled1 = 0;
					reckilled2 = 0;
					reckilled3 = 0;
					reckilled4 = 0;
					// 5:recTurn 6:WitchTurn
					ServerBroadcast("7\n0\n");
					ServerBroadcast("5\n6\n");
					ServerBroadcast("8\n" + JustTellWitch + "\n");
					String stringValue = Integer.toString(i);
					text.setText(status[i]);
					detail.setText(details[i]);

					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[3].pause();
					try {
						music[4].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[4].start();

					if (isProphet == 0) {
						i = i + 1;
					}
				}

				else if (i == 4) {
					i++;// Prophet
					rechelp = 0;
					recpoison = 0;
					// 5:recTurn 4:ProphetTurn
					ServerBroadcast("7\n6\n");
					ServerBroadcast("5\n4\n");
					Log.i(TAG, "Prophet按鈕Enable,recTurn:5 / 4");

					String stringValue = Integer.toString(i);
					text.setText(status[i]);
					detail.setText(details[i]);

					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					if (isWitch == 0) {
						music[3].pause();
					} else {
						music[4].pause();
					}
					try {
						music[5].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[5].start();
				}

				// i=5時 i++ 即為 第六個..白天
				else if (i == 5) {
					i++;
					ServerBroadcast("7\n4\n");
					text.setText(status[i]);
					detail.setText(details[i]);
					String stringValue = Integer.toString(i);
					Log.i("i的值", stringValue);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					if (isWitch == 0 && isProphet == 0) {
						music[3].pause();
					} else if (isWitch == 0) {
						music[4].pause();
					} else {
						music[5].pause();
					}
					try {
						music[6].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[6].start();
				}

				// 7(事件)要繼續放音樂，獨立處理
				else if (i == 6) {
					i++;
					text.setText(status[i]);
					detail.setText(details[i]);
					String stringValue = Integer.toString(i);
					Log.i("7的值", stringValue);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					if (hasevent == 1) {
						ServerBroadcast("9\n");
						Log.i(TAG,"Server送出9跳出事件");
						ShowPopupEvent();	
					} else {
						Toast.makeText(BuildServer.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}
					music[6].pause();
					try {
						music[7].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[7].start();
				}

				// 8(死亡)要放影片，獨立處理
				else if (i == 7) {
					i++;
					text.setText(status[i]);
					detail.setText(details[i]);
					String stringValue = Integer.toString(i);
					Log.i("8的值", stringValue);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					music[7].pause();
					try {
						music[6].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[6].start();
				}

				// 9(討論)要繼續接白天音樂，獨立處理
				else if (i == 8) {
					i++;
					text.setText(status[i]);
					detail.setText(details[i]);
					String stringValue2 = Integer.toString(i);
					Log.i("這是else if i=9", stringValue2);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
				}

				// 如果超過9個回合，就回到第0
				else if (i >= 9) {
					eventnum = (int) (Math.random() * 19);
					ServerBroadcast("4\n" + eventnum + "\n");
					i = 0;
					toOrigin();
					reckilled1 = 0;
					reckilled2 = 0;
					reckilled3 = 0;
					reckilled4 = 0;
					recguard = 0;
					reclove = 0;
					rechelp = 0;
					recpoison = 0;
					recDivined = 0;
					text.setText(status[i]);
					detail.setText(details[i]);
					String stringValue = Integer.toString(i);
					Log.i("i的值", stringValue);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

					music[6].pause();
					try {
						music[0].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					music[0].start();
					if (isCupid == 0 && isGuard == 0) {
						i = i + 2;
					} else if (isGuard == 0 && cupided >= 1) {
						i = i + 2;
					} else if (isGuard == 1 && isCupid == 1 && cupided >= 1) {
						i++;
					}
				}// end if (i>=9)
			}// End onClick
		};

		f3.setOnClickListener(listener3);

		OnTouchListener Olistener3 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					cupid.setBackgroundResource(R.drawable.f333); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					cupid.setBackgroundResource(R.drawable.r1); // 松开时恢复原来的背景图
				}
				return false;
			}
		};
		f3.setOnTouchListener(Olistener3);

		/******************************** Listener3 -- 向back一個回合 ********************************/
		OnClickListener listener4 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "i的值" + i);
				i--;
				if (i == 0) {
					Log.i(TAG, "倒退至天黑回合：i=" + i);
					reckilled1 = 0;
					reckilled2 = 0;
					reckilled3 = 0;
					reckilled4 = 0;
					recguard = 0;
					reclove = 0;
					rechelp = 0;
					recpoison = 0;
					recDivined = 0;
					toOrigin();
					ServerBroadcast("7\n2\n");
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[0]);
					f2.setImageDrawable(d);
					music[1].pause(); // 如果等於0 就是黑夜 要把邱比特停掉
					try {
						music[0].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					music[0].start();
				}// end if (i==0)

				else if (i == 1) {
					// 5:recTurn 2:CupidTurn
					Log.i(TAG, "邱比特回合：i=" + i);
					ServerBroadcast("7\n3\n");
					ServerBroadcast("5\n2\n");
					Log.i(TAG, "Cupid按鈕Enable,recTurn:2");
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[2].pause();
					try {
						music[1].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[1].start();
				} else if (i == 2) {
					// 5:recTurn 3:GuardTurn
					Log.i(TAG, "守衛回合：i=" + i);
				
					ServerBroadcast("7\n0\n");
					ServerBroadcast("5\n3\n");
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[3].pause();
					try {
						music[2].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[2].start();
					if (isCupid == 0 && isGuard == 0) {
						i = i - 2;
					} else if (isGuard == 0) {
						i--;
					}
					Log.i(TAG, "i=" + i + "isCupid=" + isCupid + "isGuard="
							+ isGuard);
					music[4].start();
				} else if (i == 3) {
					Log.i(TAG, "殺手回合：i=" + i);
				
					ServerBroadcast("7\n6\n");
					ServerBroadcast("5\n0\n");
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[4].pause();
					try {
						music[3].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;

					music[3].start();

				} else if (i == 4) {
					// 5:recTurn 6:WitchTurn
					Log.i(TAG, "女巫回合：i=" + i);
					ServerBroadcast("5\n6\n");
					ServerBroadcast("7\n4\n");
					ServerBroadcast("8\n" + JustTellWitch + "\n");
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[5].pause();
					try {
						music[4].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;

				}

				else if (i == 5) {
					Log.i(TAG, "預言家回合：i=" + i);
					// 5:recTurn 4:ProphetTurn
					ServerBroadcast("5\n4\n");

					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[6].pause();
					try {
						music[5].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[5].start();
					if (isProphet == 0 && isWitch == 0) {
						i = i - 2;
					} else if (isProphet == 0) {
						i--;
					}
					Log.i(TAG, "i=" + i + "isProphet=" + isProphet + "isWitch="
							+ isWitch);
					music[7].start();
				}

				else if (i == 6) {
					Log.i(TAG, "天亮回合：i=" + i);
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[7].pause();
					try {
						music[6].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					music[6].start();
					Log.i(TAG, "扣之前i=" + i);
				}
				// 從8退回到7
				else if (i == 7) {
					if (hasevent == 1) {
						ShowPopupEvent();
					} else {
						Toast.makeText(BuildServer.this, "本次遊戲沒有產生事件",
								Toast.LENGTH_SHORT).show();
					}
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);
					music[6].stop();

					try {
						music[7].prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;

				}

				// 9(討論)要繼續接白天音樂，獨立處理
				else if (i == 8) {
					text.setText(status[i]);
					detail.setText(details[i]);
					Drawable d = getResources().getDrawable(RoundImage[i]);
					f2.setImageDrawable(d);

				}
				// 如果超過
				else if (i < 0)
					i = 9;
				text.setText(status[i]);
				detail.setText(details[i]);
				Drawable d = getResources().getDrawable(RoundImage[i]);
				f2.setImageDrawable(d);

				music[0].stop();
				try {
					music[6].prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				music[6].start();
			}
		};
		f4.setOnClickListener(listener4);

		OnTouchListener Olistener4 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					cupid.setBackgroundResource(R.drawable.f444); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					cupid.setBackgroundResource(R.drawable.r1);
				}
				return false;
			}
		};
		f4.setOnTouchListener(Olistener4);

		/**************************** 用來實做右邊控制欄的動作 ******************************/

		fsIB1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 1, numclient);
				alertDialog.show();
			}
		});

		fsIB2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 2, numclient);
				alertDialog.show();

			}
		});

		fsIB3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 3, numclient);
				alertDialog.show();
				// players.get(2).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 4, numclient);
				alertDialog.show();
				// players.get(3).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 5, numclient);
				alertDialog.show();
				// players.get(4).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 6, numclient);
				alertDialog.show();
				// players.get(5).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 7, numclient);
				alertDialog.show();
				// players.get(6).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 8, numclient);
				alertDialog.show();
				// players.get(7).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 9, numclient);
				alertDialog.show();
				// players.get(8).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 10, numclient);
				alertDialog.show();
				// players.get(9).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 11, numclient);
				alertDialog.show();
				// players.get(10).ServersendMessage("3\nurKilled\n");
			}
		});
		fsIB12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 12, numclient);
				alertDialog.show();
				// players.get(11).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 13, numclient);
				alertDialog.show();
				// players.get(12).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV11.getText().toString() + "】被殺死了", 14, numclient);
				alertDialog.show();
				// players.get(13).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV53.getText().toString() + "】被殺死了", 15, numclient);
				alertDialog.show();
				// players.get(14).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB16.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV61.getText().toString() + "】被殺死了", 16, numclient);
				alertDialog.show();
				// players.get(15).ServersendMessage("3\nurKilled\n");

			}
		});

		fsIB17.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV62.getText().toString() + "】被殺死了", 17, numclient);
				alertDialog.show();
				// players.get(16).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB18.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV63.getText().toString() + "】被殺死了", 18, numclient);
				alertDialog.show();
				// players.get(17).ServersendMessage("3\nurKilled\n");
			}
		});
		fsIB19.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV71.getText().toString() + "】被殺死了", 19, numclient);
				alertDialog.show();
				// players.get(18).ServersendMessage("3\nurKilled\n");
			}
		});

		fsIB20.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = getAlertDialog("告知玩家被殺害", "【"
						+ ftV72.getText().toString() + "】被殺死了", 20, numclient);
				alertDialog.show();
				// players.get(19).ServersendMessage("3\nurKilled\n");
			}
		});

	}// end GotoMasterServer

	/***** 跳出Profile *****/
	public void ShowPopup() {
		Context mContext = BuildServer.this;
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.profile, null, true);

		final PopupWindow mPopupWindow = new PopupWindow(view,
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(findViewById(R.id.cupid), Gravity.CENTER,
				0, 0);

		Button btn2 = (Button) view.findViewById(R.id.end);
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取消按鈕實現的操作
				mPopupWindow.dismiss();
			}
		});

	}

	/***** 跳出Event *****/
	public void ShowPopupEvent() {
		int ranEvent = (int) (Math.random() * 18);// 用來產生 0~18的隨機數

		Context mContext = BuildServer.this;
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.event, null, true);

		final PopupWindow mPopupWindow = new PopupWindow(view,
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(findViewById(R.id.cupid), Gravity.CENTER,
				0, 0);

		ImageView eventIV = (ImageView) view.findViewById(R.id.eventIV);
		;
		eventIV.setImageResource(myImageIds[eventnum]);

		Button btn2 = (Button) view.findViewById(R.id.end);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消按鈕實現的操作
				mPopupWindow.dismiss();
			}
		});

	}// end showpopupwindows


	private AlertDialog getAlertDialog(String title, String message,
			final int k, final int peoNum) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				BuildServer.this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (peoNum) {
				case 1:
					switch (k) {
					case 1:// 側欄第一個button
						fsIB1.setBackgroundResource(characterskilledpicture[onePeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 2:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[twoPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[twoPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 3:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[threePeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[threePeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[threePeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 4:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[fourPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[fourPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[fourPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[fourPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 5:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[fivePeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[fivePeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[fivePeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[fivePeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[fivePeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 6:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[sixPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[sixPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[sixPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[sixPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[sixPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[sixPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 7:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[sevenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[sevenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[sevenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[sevenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[sevenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[sevenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[sevenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 8:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[eightPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[eightPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[eightPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[eightPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[eightPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[eightPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[eightPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[eightPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 9:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[ninePeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[ninePeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[ninePeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[ninePeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[ninePeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[ninePeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[ninePeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[ninePeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[ninePeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 10:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[tenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
						break;
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[tenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(1).ServersendMessage("3\nurKilled\n");
						break;
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[tenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(2).ServersendMessage("3\nurKilled\n");
						break;
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[tenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(3).ServersendMessage("3\nurKilled\n");
						break;
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[tenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[tenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[tenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[tenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[tenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[tenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					}
					break;
				case 11:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[elevenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[elevenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[elevenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[elevenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[elevenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[elevenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[elevenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[elevenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[elevenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[elevenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[elevenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 12:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[twelvePeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[twelvePeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[twelvePeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[twelvePeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[twelvePeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[twelvePeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[twelvePeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[twelvePeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[twelvePeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[twelvePeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[twelvePeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[twelvePeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 13:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[thirteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[thirteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[thirteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[thirteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[thirteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[thirteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[thirteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[thirteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[thirteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[thirteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[thirteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[thirteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[thirteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 14:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[fourteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[fourteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[fourteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[fourteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[fourteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[fourteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[fourteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[fourteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[fourteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[fourteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[fourteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[fourteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[fourteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[fourteenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 15:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[fifteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[fifteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[fifteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[fifteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[fifteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[fifteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[fifteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[fifteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[fifteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[fifteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[fifteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[fifteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[fifteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[fifteenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[fifteenPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 16:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[sixteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[sixteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[sixteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[sixteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[sixteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[sixteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[sixteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[sixteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[sixteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[sixteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[sixteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[sixteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[sixteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[sixteenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[sixteenPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					case 16:
						fsIB16.setBackgroundResource(characterskilledpicture[sixteenPeo[15]]);
						fsIB16.setEnabled(false);
						players.get(15).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 17:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[seventeenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[seventeenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[seventeenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[seventeenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[seventeenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[seventeenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[seventeenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[seventeenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[seventeenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[seventeenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[seventeenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[seventeenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[seventeenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[seventeenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[seventeenPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					case 16:
						fsIB16.setBackgroundResource(characterskilledpicture[seventeenPeo[15]]);
						fsIB16.setEnabled(false);
						players.get(15).ServersendMessage("3\nurKilled\n");
						break;
					case 17:
						fsIB17.setBackgroundResource(characterskilledpicture[seventeenPeo[16]]);
						fsIB17.setEnabled(false);
						players.get(16).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 18:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[eighteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[eighteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[eighteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[eighteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[eighteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[eighteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[eighteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[eighteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[eighteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[eighteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[eighteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[eighteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[eighteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[eighteenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[eighteenPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					case 16:
						fsIB16.setBackgroundResource(characterskilledpicture[eighteenPeo[15]]);
						fsIB16.setEnabled(false);
						players.get(15).ServersendMessage("3\nurKilled\n");
						break;
					case 17:
						fsIB17.setBackgroundResource(characterskilledpicture[eighteenPeo[16]]);
						fsIB17.setEnabled(false);
						players.get(16).ServersendMessage("3\nurKilled\n");
						break;
					case 18:
						fsIB18.setBackgroundResource(characterskilledpicture[eighteenPeo[17]]);
						fsIB18.setEnabled(false);
						players.get(17).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 19:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[nineteenPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[nineteenPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[nineteenPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[nineteenPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[nineteenPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[nineteenPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[nineteenPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[nineteenPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[nineteenPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[nineteenPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[nineteenPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[nineteenPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[nineteenPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[nineteenPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[nineteenPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					case 16:
						fsIB16.setBackgroundResource(characterskilledpicture[nineteenPeo[15]]);
						fsIB16.setEnabled(false);
						players.get(15).ServersendMessage("3\nurKilled\n");
						break;
					case 17:
						fsIB17.setBackgroundResource(characterskilledpicture[nineteenPeo[16]]);
						fsIB17.setEnabled(false);
						players.get(16).ServersendMessage("3\nurKilled\n");
						break;
					case 18:
						fsIB18.setBackgroundResource(characterskilledpicture[nineteenPeo[17]]);
						fsIB18.setEnabled(false);
						players.get(17).ServersendMessage("3\nurKilled\n");
						break;
					case 19:
						fsIB19.setBackgroundResource(characterskilledpicture[nineteenPeo[18]]);
						fsIB19.setEnabled(false);
						players.get(18).ServersendMessage("3\nurKilled\n");
						break;
					}
				case 20:
					switch (k) {
					case 1:
						fsIB1.setBackgroundResource(characterskilledpicture[twentyPeo[0]]);
						fsIB1.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 2:
						fsIB2.setBackgroundResource(characterskilledpicture[twentyPeo[1]]);
						fsIB2.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 3:
						fsIB3.setBackgroundResource(characterskilledpicture[twentyPeo[2]]);
						fsIB3.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 4:
						fsIB4.setBackgroundResource(characterskilledpicture[twentyPeo[3]]);
						fsIB4.setEnabled(false);
						players.get(0).ServersendMessage("3\nurKilled\n");
					case 5:
						fsIB5.setBackgroundResource(characterskilledpicture[twentyPeo[4]]);
						fsIB5.setEnabled(false);
						players.get(4).ServersendMessage("3\nurKilled\n");
						break;
					case 6:
						fsIB6.setBackgroundResource(characterskilledpicture[twentyPeo[5]]);
						fsIB6.setEnabled(false);
						players.get(5).ServersendMessage("3\nurKilled\n");
						break;
					case 7:
						fsIB7.setBackgroundResource(characterskilledpicture[twentyPeo[6]]);
						fsIB7.setEnabled(false);
						players.get(6).ServersendMessage("3\nurKilled\n");
						break;
					case 8:
						fsIB8.setBackgroundResource(characterskilledpicture[twentyPeo[7]]);
						fsIB8.setEnabled(false);
						players.get(7).ServersendMessage("3\nurKilled\n");
						break;
					case 9:
						fsIB9.setBackgroundResource(characterskilledpicture[twentyPeo[8]]);
						fsIB9.setEnabled(false);
						players.get(8).ServersendMessage("3\nurKilled\n");
						break;
					case 10:
						fsIB10.setBackgroundResource(characterskilledpicture[twentyPeo[9]]);
						fsIB10.setEnabled(false);
						players.get(9).ServersendMessage("3\nurKilled\n");
						break;
					case 11:
						fsIB11.setBackgroundResource(characterskilledpicture[twentyPeo[10]]);
						fsIB11.setEnabled(false);
						players.get(10).ServersendMessage("3\nurKilled\n");
						break;
					case 12:
						fsIB12.setBackgroundResource(characterskilledpicture[twentyPeo[11]]);
						fsIB12.setEnabled(false);
						players.get(11).ServersendMessage("3\nurKilled\n");
						break;
					case 13:
						fsIB13.setBackgroundResource(characterskilledpicture[twentyPeo[12]]);
						fsIB13.setEnabled(false);
						players.get(12).ServersendMessage("3\nurKilled\n");
						break;
					case 14:
						fsIB14.setBackgroundResource(characterskilledpicture[twentyPeo[13]]);
						fsIB14.setEnabled(false);
						players.get(13).ServersendMessage("3\nurKilled\n");
						break;
					case 15:
						fsIB15.setBackgroundResource(characterskilledpicture[twentyPeo[14]]);
						fsIB15.setEnabled(false);
						players.get(14).ServersendMessage("3\nurKilled\n");
						break;
					case 16:
						fsIB16.setBackgroundResource(characterskilledpicture[twentyPeo[15]]);
						fsIB16.setEnabled(false);
						players.get(15).ServersendMessage("3\nurKilled\n");
						break;
					case 17:
						fsIB17.setBackgroundResource(characterskilledpicture[twentyPeo[16]]);
						fsIB17.setEnabled(false);
						players.get(16).ServersendMessage("3\nurKilled\n");
						break;
					case 18:
						fsIB18.setBackgroundResource(characterskilledpicture[twentyPeo[17]]);
						fsIB18.setEnabled(false);
						players.get(17).ServersendMessage("3\nurKilled\n");
						break;
					case 19:
						fsIB19.setBackgroundResource(characterskilledpicture[twentyPeo[18]]);
						fsIB19.setEnabled(false);
						players.get(18).ServersendMessage("3\nurKilled\n");
						break;
					case 20:
						fsIB20.setBackgroundResource(characterskilledpicture[twentyPeo[19]]);
						fsIB20.setEnabled(false);
						players.get(19).ServersendMessage("3\nurKilled\n");
						break;
					}
				}
				Toast.makeText(BuildServer.this,
						ftV11.getText().toString() + "被殺死了", Toast.LENGTH_SHORT)
						.show();

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

	static void toOrigin() {
		fiV1111.setBackgroundResource(R.drawable.f40);
		fiV1121.setBackgroundResource(R.drawable.f40);
		fiV1131.setBackgroundResource(R.drawable.f40);
		fiV1141.setBackgroundResource(R.drawable.f40);

		fiV1221.setBackgroundResource(R.drawable.f40);
		fiV1231.setBackgroundResource(R.drawable.f40);
		fiV1241.setBackgroundResource(R.drawable.f40);
		fiV1112.setBackgroundResource(R.drawable.f40);
		fiV1122.setBackgroundResource(R.drawable.f40);
		fiV1132.setBackgroundResource(R.drawable.f40);
		fiV1142.setBackgroundResource(R.drawable.f40);

		fiV1222.setBackgroundResource(R.drawable.f40);
		fiV1232.setBackgroundResource(R.drawable.f40);
		fiV1242.setBackgroundResource(R.drawable.f40);
		fiV1113.setBackgroundResource(R.drawable.f40);
		fiV1123.setBackgroundResource(R.drawable.f40);
		fiV1133.setBackgroundResource(R.drawable.f40);
		fiV1143.setBackgroundResource(R.drawable.f40);

		fiV1223.setBackgroundResource(R.drawable.f40);
		fiV1233.setBackgroundResource(R.drawable.f40);
		fiV1243.setBackgroundResource(R.drawable.f40);

		fiV2111.setBackgroundResource(R.drawable.f40);
		fiV2121.setBackgroundResource(R.drawable.f40);
		fiV2131.setBackgroundResource(R.drawable.f40);
		fiV2141.setBackgroundResource(R.drawable.f40);

		fiV2221.setBackgroundResource(R.drawable.f40);
		fiV2231.setBackgroundResource(R.drawable.f40);
		fiV2241.setBackgroundResource(R.drawable.f40);
		fiV2112.setBackgroundResource(R.drawable.f40);
		fiV2122.setBackgroundResource(R.drawable.f40);
		fiV2132.setBackgroundResource(R.drawable.f40);
		fiV2142.setBackgroundResource(R.drawable.f40);

		fiV2222.setBackgroundResource(R.drawable.f40);
		fiV2232.setBackgroundResource(R.drawable.f40);
		fiV2242.setBackgroundResource(R.drawable.f40);
		fiV2113.setBackgroundResource(R.drawable.f40);
		fiV2123.setBackgroundResource(R.drawable.f40);
		fiV2133.setBackgroundResource(R.drawable.f40);
		fiV2143.setBackgroundResource(R.drawable.f40);

		fiV2223.setBackgroundResource(R.drawable.f40);
		fiV2233.setBackgroundResource(R.drawable.f40);
		fiV2243.setBackgroundResource(R.drawable.f40);

		fiV3111.setBackgroundResource(R.drawable.f40);
		fiV3121.setBackgroundResource(R.drawable.f40);
		fiV3131.setBackgroundResource(R.drawable.f40);
		fiV3141.setBackgroundResource(R.drawable.f40);

		fiV3221.setBackgroundResource(R.drawable.f40);
		fiV3231.setBackgroundResource(R.drawable.f40);
		fiV3241.setBackgroundResource(R.drawable.f40);
		fiV3112.setBackgroundResource(R.drawable.f40);
		fiV3122.setBackgroundResource(R.drawable.f40);
		fiV3132.setBackgroundResource(R.drawable.f40);
		fiV3142.setBackgroundResource(R.drawable.f40);

		fiV3222.setBackgroundResource(R.drawable.f40);
		fiV3232.setBackgroundResource(R.drawable.f40);
		fiV3242.setBackgroundResource(R.drawable.f40);
		fiV3113.setBackgroundResource(R.drawable.f40);
		fiV3123.setBackgroundResource(R.drawable.f40);
		fiV3133.setBackgroundResource(R.drawable.f40);
		fiV3143.setBackgroundResource(R.drawable.f40);

		fiV3223.setBackgroundResource(R.drawable.f40);
		fiV3233.setBackgroundResource(R.drawable.f40);
		fiV3243.setBackgroundResource(R.drawable.f40);

		fiV4111.setBackgroundResource(R.drawable.f40);
		fiV4121.setBackgroundResource(R.drawable.f40);
		fiV4131.setBackgroundResource(R.drawable.f40);
		fiV4141.setBackgroundResource(R.drawable.f40);

		fiV4221.setBackgroundResource(R.drawable.f40);
		fiV4231.setBackgroundResource(R.drawable.f40);
		fiV4241.setBackgroundResource(R.drawable.f40);
		fiV4112.setBackgroundResource(R.drawable.f40);
		fiV4122.setBackgroundResource(R.drawable.f40);
		fiV4132.setBackgroundResource(R.drawable.f40);
		fiV4142.setBackgroundResource(R.drawable.f40);

		fiV4222.setBackgroundResource(R.drawable.f40);
		fiV4232.setBackgroundResource(R.drawable.f40);
		fiV4242.setBackgroundResource(R.drawable.f40);
		fiV4113.setBackgroundResource(R.drawable.f40);
		fiV4123.setBackgroundResource(R.drawable.f40);
		fiV4133.setBackgroundResource(R.drawable.f40);
		fiV4143.setBackgroundResource(R.drawable.f40);

		fiV4223.setBackgroundResource(R.drawable.f40);
		fiV4233.setBackgroundResource(R.drawable.f40);
		fiV4243.setBackgroundResource(R.drawable.f40);

		fiV5111.setBackgroundResource(R.drawable.f40);
		fiV5121.setBackgroundResource(R.drawable.f40);
		fiV5131.setBackgroundResource(R.drawable.f40);
		fiV5141.setBackgroundResource(R.drawable.f40);

		fiV5221.setBackgroundResource(R.drawable.f40);
		fiV5231.setBackgroundResource(R.drawable.f40);
		fiV5241.setBackgroundResource(R.drawable.f40);
		fiV5112.setBackgroundResource(R.drawable.f40);
		fiV5122.setBackgroundResource(R.drawable.f40);
		fiV5132.setBackgroundResource(R.drawable.f40);
		fiV5142.setBackgroundResource(R.drawable.f40);

		fiV5222.setBackgroundResource(R.drawable.f40);
		fiV5232.setBackgroundResource(R.drawable.f40);
		fiV5242.setBackgroundResource(R.drawable.f40);
		fiV5113.setBackgroundResource(R.drawable.f40);
		fiV5123.setBackgroundResource(R.drawable.f40);
		fiV5133.setBackgroundResource(R.drawable.f40);
		fiV5143.setBackgroundResource(R.drawable.f40);

		fiV5223.setBackgroundResource(R.drawable.f40);
		fiV5233.setBackgroundResource(R.drawable.f40);
		fiV5243.setBackgroundResource(R.drawable.f40);

		fiV6111.setBackgroundResource(R.drawable.f40);
		fiV6121.setBackgroundResource(R.drawable.f40);
		fiV6131.setBackgroundResource(R.drawable.f40);
		fiV6141.setBackgroundResource(R.drawable.f40);

		fiV6221.setBackgroundResource(R.drawable.f40);
		fiV6231.setBackgroundResource(R.drawable.f40);
		fiV6241.setBackgroundResource(R.drawable.f40);
		fiV6112.setBackgroundResource(R.drawable.f40);
		fiV6122.setBackgroundResource(R.drawable.f40);
		fiV6132.setBackgroundResource(R.drawable.f40);
		fiV6142.setBackgroundResource(R.drawable.f40);

		fiV6222.setBackgroundResource(R.drawable.f40);
		fiV6232.setBackgroundResource(R.drawable.f40);
		fiV6242.setBackgroundResource(R.drawable.f40);
		fiV6113.setBackgroundResource(R.drawable.f40);
		fiV6123.setBackgroundResource(R.drawable.f40);
		fiV6133.setBackgroundResource(R.drawable.f40);
		fiV6143.setBackgroundResource(R.drawable.f40);

		fiV6223.setBackgroundResource(R.drawable.f40);
		fiV6233.setBackgroundResource(R.drawable.f40);
		fiV6243.setBackgroundResource(R.drawable.f40);

		fiV7111.setBackgroundResource(R.drawable.f40);
		fiV7121.setBackgroundResource(R.drawable.f40);
		fiV7131.setBackgroundResource(R.drawable.f40);
		fiV7141.setBackgroundResource(R.drawable.f40);

		fiV7221.setBackgroundResource(R.drawable.f40);
		fiV7231.setBackgroundResource(R.drawable.f40);
		fiV7241.setBackgroundResource(R.drawable.f40);
		fiV7112.setBackgroundResource(R.drawable.f40);
		fiV7122.setBackgroundResource(R.drawable.f40);
		fiV7132.setBackgroundResource(R.drawable.f40);
		fiV7142.setBackgroundResource(R.drawable.f40);

		fiV7222.setBackgroundResource(R.drawable.f40);
		fiV7232.setBackgroundResource(R.drawable.f40);
		fiV7242.setBackgroundResource(R.drawable.f40);
	}

	static void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i >= 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public void onBackPressed() {

		if (isenter == 1) {
			AlertDialog alertDialog = getAlertDialog1("確定離開嗎?",
					"【警告】遊戲結束，伺服器將會關閉");
			alertDialog.show();
			return;
		} else {
			exitActivity(1);
		}
	}

	private AlertDialog getAlertDialog1(String title, String message) {
		// 產生一個Builder物件
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				BuildServer.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
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
		// 產生一個Builder物件

		final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				BuildServer.this);
		builder.setTitle(title);
		builder.setMessage(message);

		// 設定Positive按鈕資料
		builder.setPositiveButton("一鍵開啟",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						close3G();
						showwaiting();
						isopenhotspot = 1;
						WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						wifi.setWifiEnabled(false);
						WifiConfiguration netConfig = new WifiConfiguration();
						netConfig.SSID = "DarkKillerHotspot";
						Method method;
						try {
							// 打開WIFIHOTSPOT
							method = wifi.getClass().getMethod(
									"setWifiApEnabled",
									WifiConfiguration.class, boolean.class);
							method.invoke(wifi, netConfig, true);

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
						// dialog.dismiss(); // end the dialog.
					}
				});
		// 設定Negative按鈕資料
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
			}
		});
		// 利用Builder物件建立AlertDialog
		return builder.create();
	}

	protected void onDestory() {
		Serverdialog_status = false;
		super.onDestroy();
	}

	public void setIP() {
		if (isWifi(this)) {
			IP = getWiFiIPAddress(null);// 有開Wifi
		} else if (is3G(this)) {
			IP = get3GIpAddress();// 有開3G
		} else {
			IP = getNOINTERNETLocalIpAddress();// 否則讀本地hotspot
		}
	}

	@SuppressWarnings("unchecked")
	public void close3G() {
		ConnectivityManager dataManager;
		dataManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		Method dataMtd;
		try {
			dataMtd = ConnectivityManager.class.getDeclaredMethod(
					"setMobileDataEnabled", boolean.class);
			dataMtd.setAccessible(true);
			dataMtd.invoke(dataManager, false);
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

	public void showwaiting() {
		final ProgressDialog dialog = ProgressDialog.show(BuildServer.this,
				"開啟中", "請稍後...", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dialog.dismiss();
					Looper.prepare();
					AlertDialog alertDialog = getAlertDialog3("【遊戲設定】",
							"成功開啟連線，\n設定玩家人數");
					alertDialog.show();
					Looper.loop();
				}
			}
		}).start();
	}

	private AlertDialog getAlertDialog3(String title, String message) {
		// 產生一個Builder物件
		final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				BuildServer.this);
		builder.setTitle(title);
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		return builder.create();
	}

}// end BuildServer
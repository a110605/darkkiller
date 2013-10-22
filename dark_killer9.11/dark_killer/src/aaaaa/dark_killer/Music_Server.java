package aaaaa.dark_killer;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

//用來撥放片頭音樂
public class Music_Server extends Service {
	public MediaPlayer mp;

	class Music_ServerBinder extends Binder {
		Music_Server getService() {
			return Music_Server.this;
		}
	}
	
	@Override
	public void onStart(Intent intent,int startId){
		super.onStart(intent, startId);
		mp = MediaPlayer.create(this, R.raw.start);
		mp.setLooping(true);
		mp.start();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// 服務停止時停止播放音樂並釋放資源
		mp.stop();
		mp.release();

		super.onDestroy();
	}// end onDestory
}
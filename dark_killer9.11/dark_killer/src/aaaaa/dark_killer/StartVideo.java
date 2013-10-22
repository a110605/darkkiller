package aaaaa.dark_killer;

import aaaaa.dark_killer.R;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class StartVideo extends Activity {

	VideoView videoView;
	RelativeLayout layout;
	Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.z);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		layout = (RelativeLayout) findViewById(R.id.layout);
		videoView = (VideoView) findViewById(R.id.killervideo);
		button1 = (Button) findViewById(R.id.button1);

		
		videoView
				.setVideoURI(Uri
						.parse("android.resource://aaaaa.dark_killer/raw/video2"));
		videoView.requestFocus();
		videoView.start();

		button1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					videoView.stopPlayback();
					Intent intent = new Intent();
					intent.setClassName("aaaaa.dark_killer",
							"aaaaa.dark_killer.BeforeStart");// Package

					startActivity(intent);
					finish();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});// end onclick

		videoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {

						Intent intent = new Intent();

						intent.setClassName("aaaaa.dark_killer",
								"aaaaa.dark_killer.BeforeStart");
						// 傳值
						startActivity(intent);
						finish();
						// 播放结束的動作
					}
				});

	}

}

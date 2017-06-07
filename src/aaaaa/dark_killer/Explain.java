package aaaaa.dark_killer;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class Explain extends Activity {
	BeforeStart bs;
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		final Button st = ((Button) findViewById(R.id.c3));// 最左邊的按鈕
		final Button ch = ((Button) findViewById(R.id.c2));// 中間 換到角色頁
		final Button ev = ((Button) findViewById(R.id.layout));// 右邊 換到事件頁

		final Vibrator myVibrator = (Vibrator) getApplication()
				.getSystemService(Service.VIBRATOR_SERVICE);

		final DetialGallery gallery_st = (DetialGallery) findViewById(R.id.c4);
		final DetialGallery gallery_ch = (DetialGallery) findViewById(R.id.c5);
		final DetialGallery gallery_ev = (DetialGallery) findViewById(R.id.c6);

		gallery_st.setAdapter(new ImageAdapterSt(this));
		gallery_ch.setAdapter(new ImageAdapterCh(this));
		gallery_ev.setAdapter(new ImageAdapterEv(this));

		// 一開始預設顯示角色跟事件的Gallery為Unvisible
		gallery_ch.setVisibility(View.INVISIBLE);
		gallery_ev.setVisibility(View.INVISIBLE);

		mp=MediaPlayer.create(this,R.raw.start);
		mp.setLooping(true);
		mp.start();
		
		OnClickListener listenerSt = new OnClickListener() {
			@Override
			public void onClick(View v) {
				gallery_st.setVisibility(View.VISIBLE);
				gallery_ch.setVisibility(View.INVISIBLE);
				gallery_ev.setVisibility(View.INVISIBLE);
			}
		};
		st.setOnClickListener(listenerSt);

		OnClickListener listenerCh = new OnClickListener() {
			@Override
			public void onClick(View v) {
				gallery_st.setVisibility(View.INVISIBLE);
				gallery_ch.setVisibility(View.VISIBLE);
				gallery_ev.setVisibility(View.INVISIBLE);
			}
		};
		ch.setOnClickListener(listenerCh);

		OnClickListener listenerEv = new OnClickListener() {
			@Override
			public void onClick(View v) {
				gallery_st.setVisibility(View.INVISIBLE);
				gallery_ch.setVisibility(View.INVISIBLE);
				gallery_ev.setVisibility(View.VISIBLE);
			}
		};
		ev.setOnClickListener(listenerEv);

		/************************* OnTouch Listener *************************/
		OnTouchListener Olistener1 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					st.setBackgroundResource(R.drawable.c11); // 按下时候的背景图
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					st.setBackgroundResource(R.drawable.c1); // 按下时候的背景图
				}
				return false;
			}
		};
		OnTouchListener Olistener2 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ch.setBackgroundResource(R.drawable.c22); // 按下时候的背景图

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					ch.setBackgroundResource(R.drawable.c2); // 按下时候的背景图
				}
				return false;
			}
		};
		OnTouchListener Olistener3 = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					ev.setBackgroundResource(R.drawable.c33); // 按下时候的背景图

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					ev.setBackgroundResource(R.drawable.c3); // 按下时候的背景图
				}
				return false;
			}
		};

		st.setOnTouchListener(Olistener1);
		ch.setOnTouchListener(Olistener2);
		ev.setOnTouchListener(Olistener3);
		/**************************************************************************/

	}

	/************************ 分派給故事介紹的Adapter ****************************/
	public class ImageAdapterSt extends BaseAdapter// 新增一個ImageAdapter物件
	{
		/* ﾃ�Oｦｨｭ�myContextｬｰContext､�O */
		private Context myContext;

		/* ｨﾏ･ﾎandroid.R.drawableｸﾌｪｺｹﾏ､@ｬｰｹﾏｮwｨﾓｷｽ｡AｫｬｺAｬｰｾ羮ﾆｰ}ｦC */
		private int[] myImageIds = {

		};

		/* ｫﾘｺc､l･uｦｳ､@ｭﾓｰﾑｼﾆ｡AｧYｭnﾀxｦsｪｺContext */
		public ImageAdapterSt(Context c) {
			this.myContext = c;
		}

		/* ｧQ･ﾎgetItem､隱k｡Aｨ﨑o･ﾘｫeｮeｾｹ､､ｼvｹｳｪｺｰ}ｦCID */
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/* ｨ﨑o･ﾘｫeｱ�罕ﾜｪｺｼvｹｳView｡Aｶﾇ､Jｰ}ｦCIDｭﾈｨﾏ､ｧﾅｪｨ忞Pｦｨｹｳ */
		public View getView(int position, View convertView, ViewGroup parent) {
			/* ｫﾘ･ﾟ､@ｭﾓImageViewｪｫ･� */
			ImageView i = new ImageView(this.myContext);

			i.setImageResource(this.myImageIds[position]);
			i.setScaleType(ImageView.ScaleType.FIT_XY);

			/* ｳ]ｩwｳoｭﾓImageViewｪｫ･ｺｼeｰｪ｡Aｳ讎�ｰdip */
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return i;
		}

		/* ｦ^ｶﾇｩﾒｦｳ､wｩwｸqｪｺｹﾏ､`ｼﾆｶq 為了ImageAdapter的未繼承方法才用 */
		public int getCount() {
			return this.myImageIds.length;
		}

		/* ｨﾌｾﾚｶZﾂ､･｡ｪｺｦ�ｾｶq ｧQ･ﾎgetScaleｦ^ｶﾇviewsｪｺ､j､p(0.0f to 1.0f) */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
	}

	/************************ 分派給故事介紹的Adapter ****************************/

	/************************ 分派給角色介紹的Adapter ****************************/
	public class ImageAdapterCh extends BaseAdapter// 新增一個ImageAdapter物件
	{
		/* ﾃ�Oｦｨｭ�myContextｬｰContext､�O */
		private Context myContext;

		/* ｨﾏ･ﾎandroid.R.drawableｸﾌｪｺｹﾏ､@ｬｰｹﾏｮwｨﾓｷｽ｡AｫｬｺAｬｰｾ羮ﾆｰ}ｦC */
		private int[] myImageIds = { R.drawable.j99, R.drawable.k99,
				R.drawable.l99, R.drawable.m99, R.drawable.n99, R.drawable.o99,
				R.drawable.i99 };

		/* ｫﾘｺc､l･uｦｳ､@ｭﾓｰﾑｼﾆ｡AｧYｭnﾀxｦsｪｺContext */
		public ImageAdapterCh(Context c) {
			this.myContext = c;
		}

		/* ｧQ･ﾎgetItem､隱k｡Aｨ﨑o･ﾘｫeｮeｾｹ､､ｼvｹｳｪｺｰ}ｦCID */
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/* ｨ﨑o･ﾘｫeｱ�罕ﾜｪｺｼvｹｳView｡Aｶﾇ､Jｰ}ｦCIDｭﾈｨﾏ､ｧﾅｪｨ忞Pｦｨｹｳ */
		public View getView(int position, View convertView, ViewGroup parent) {
			/* ｫﾘ･ﾟ､@ｭﾓImageViewｪｫ･� */
			ImageView i = new ImageView(this.myContext);

			i.setImageResource(this.myImageIds[position]);
			i.setScaleType(ImageView.ScaleType.FIT_XY);

			/* ｳ]ｩwｳoｭﾓImageViewｪｫ･ｺｼeｰｪ｡Aｳ讎�ｰdip */
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return i;
		}

		/* ｦ^ｶﾇｩﾒｦｳ､wｩwｸqｪｺｹﾏ､`ｼﾆｶq 為了ImageAdapter的未繼承方法才用 */
		public int getCount() {
			return this.myImageIds.length;
		}

		/* ｨﾌｾﾚｶZﾂ､･｡ｪｺｦ�ｾｶq ｧQ･ﾎgetScaleｦ^ｶﾇviewsｪｺ､j､p(0.0f to 1.0f) */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
	}

	/************************ 分派給角色介紹的Adapter ****************************/

	/************************ 分派給事件介紹的Adapter ****************************/
	public class ImageAdapterEv extends BaseAdapter// 新增一個ImageAdapter物件
	{
		/* ﾃ�Oｦｨｭ�myContextｬｰContext､�O */
		private Context myContext;

		/* ｨﾏ･ﾎandroid.R.drawableｸﾌｪｺｹﾏ､@ｬｰｹﾏｮwｨﾓｷｽ｡AｫｬｺAｬｰｾ羮ﾆｰ}ｦC */
		private int[] myImageIds = { R.drawable.c50, R.drawable.c51,
				R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
				R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59,
				R.drawable.c60, R.drawable.c61, R.drawable.c62, R.drawable.c63,
				R.drawable.c64, R.drawable.c65, R.drawable.c66, R.drawable.c67,
				R.drawable.c68, };

		/* ｫﾘｺc､l･uｦｳ､@ｭﾓｰﾑｼﾆ｡AｧYｭnﾀxｦsｪｺContext */
		public ImageAdapterEv(Context c) {
			this.myContext = c;
		}

		/* ｧQ･ﾎgetItem､隱k｡Aｨ﨑o･ﾘｫeｮeｾｹ､､ｼvｹｳｪｺｰ}ｦCID */
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/* ｨ﨑o･ﾘｫeｱ�罕ﾜｪｺｼvｹｳView｡Aｶﾇ､Jｰ}ｦCIDｭﾈｨﾏ､ｧﾅｪｨ忞Pｦｨｹｳ */
		public View getView(int position, View convertView, ViewGroup parent) {
			/* ｫﾘ･ﾟ､@ｭﾓImageViewｪｫ･� */
			ImageView i = new ImageView(this.myContext);

			i.setImageResource(this.myImageIds[position]);
			i.setScaleType(ImageView.ScaleType.FIT_XY);

			/* ｳ]ｩwｳoｭﾓImageViewｪｫ･ｺｼeｰｪ｡Aｳ讎�ｰdip */
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return i;
		}

		/* ｦ^ｶﾇｩﾒｦｳ､wｩwｸqｪｺｹﾏ､`ｼﾆｶq 為了ImageAdapter的未繼承方法才用 */
		public int getCount() {
			return this.myImageIds.length;
		}

		/* ｨﾌｾﾚｶZﾂ､･｡ｪｺｦ�ｾｶq ｧQ･ﾎgetScaleｦ^ｶﾇviewsｪｺ､j､p(0.0f to 1.0f) */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
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
	
	
	

}
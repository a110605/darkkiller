package aaaaa.dark_killer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {
	
	 public MyVideoView(Context context)
	    {
	        super(context);
	    }
	    public MyVideoView(Context context, AttributeSet attrs) 
	    {
	    	super(context,attrs);
	    }    //Constructor that is called when inflating a view from XML
	    public MyVideoView(Context context, AttributeSet attrs, int defStyle)
	    {
	    	super(context,attrs,defStyle);
	    }
	
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	 {
	    int width = getDefaultSize(0, widthMeasureSpec);
	    int height = getDefaultSize(0, heightMeasureSpec);
	    setMeasuredDimension(width , height);
	 }
}


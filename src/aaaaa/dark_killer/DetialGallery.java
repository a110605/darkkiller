package aaaaa.dark_killer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class DetialGallery extends Gallery {
	/***********************zzzz...這三個很重要，要是只放一個就會Error inflating class**************************/
    public DetialGallery(Context context)
    {
        super(context);
    }
    public DetialGallery(Context context, AttributeSet attrs) 
    {
    	super(context,attrs);
    }    //Constructor that is called when inflating a view from XML
    public DetialGallery(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context,attrs,defStyle);
    }
    /*******************************************************************************************************************/
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
    float velocityY) {
    return false;
    }
/*    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() < e1.getX();
    }

    private boolean isScrollingRight(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        boolean leftScroll = isScrollingLeft(e1, e2);
        boolean rightScroll = isScrollingRight(e1, e2);

        if (rightScroll) {
            if (getSelectedItemPosition() != 0)             
                setSelection(getSelectedItemPosition() - 1, true);
        } else if (leftScroll) {

            if (getSelectedItemPosition() != getCount() - 1)
                setSelection(getSelectedItemPosition() + 1, true);
        }
        return true;
    }*/
}
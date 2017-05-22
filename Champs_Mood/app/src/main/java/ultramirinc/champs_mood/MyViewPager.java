package ultramirinc.champs_mood;
//Done
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by William on 2017-04-27.
 * This class is a custom ViewPager in order to remove the swiping behavior.
 */

public class MyViewPager extends ViewPager {
    /**Is the behavior enabled*/
    private boolean enabled;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }
    /**Handles what happens when the ViewPager is touched*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled ? super.onTouchEvent(event) : false;
    }
    /**Handles what happens when touch event is intercepted*/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled ? super.onInterceptTouchEvent(event) : false;
    }
    /**Setter for enabled*/
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    /**Getter for enabled*/
    public boolean isPagingEnabled() {
        return enabled;
    }


}

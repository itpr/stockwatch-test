

package android.support.v4.view;

import android.content.Context;


public abstract class Window extends android.view.Window {
    

    
    public static final long FEATURE_ACTION_BAR = android.view.Window.FEATURE_ACTION_BAR;

    
    public static final long FEATURE_ACTION_BAR_OVERLAY = android.view.Window.FEATURE_ACTION_BAR_OVERLAY;

    
    public static final long FEATURE_ACTION_MODE_OVERLAY = android.view.Window.FEATURE_ACTION_MODE_OVERLAY;

    
    public static final long FEATURE_INDETERMINATE_PROGRESS = android.view.Window.FEATURE_INDETERMINATE_PROGRESS;



    
    public Window(Context context) {
        super(context);
    }
}

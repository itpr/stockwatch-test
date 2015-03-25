

package android.support.v4.view;

//import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;


public class ViewGroupCompat {

    interface ViewGroupCompatImpl {
        public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child,
                AccessibilityEvent event);
    }

    static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
        public boolean onRequestSendAccessibilityEvent(
                ViewGroup group, View child, AccessibilityEvent event) {
            return true;
        }
    }

    static final ViewGroupCompatImpl IMPL;
    static {
        //if (Build.VERSION.SDK_INT >= 14) {
        //    IMPL = new ViewGroupCompatIcsImpl();
        //} else {
            IMPL = new ViewGroupCompatStubImpl();
        //}
    }

    
    private ViewGroupCompat() {

    }

    
    public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child,
            AccessibilityEvent event) {
        return IMPL.onRequestSendAccessibilityEvent(group, child, event);
    }
}



package android.support.v4.view.accessibility;

//import android.os.Build;
import android.view.accessibility.AccessibilityEvent;


public class AccessibilityEventCompat {

    static interface AccessibilityEventVersionImpl {
        public int getRecordCount(AccessibilityEvent event);
        public void appendRecord(AccessibilityEvent event, Object record);
        public Object getRecord(AccessibilityEvent event, int index);
    }

    static class AccessibilityEventStubImpl implements AccessibilityEventVersionImpl {

        public void appendRecord(AccessibilityEvent event, Object record) {

        }

        public Object getRecord(AccessibilityEvent event, int index) {
            return null;
        }

        public int getRecordCount(AccessibilityEvent event) {
            return 0;
        }
    }

    private final static AccessibilityEventVersionImpl IMPL;

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new AccessibilityEventIcsImpl();
        //} else {
            IMPL = new AccessibilityEventStubImpl();
        //}
    }

    
    public static final int TYPE_VIEW_HOVER_ENTER = 0x00000080;

    
    public static final int TYPE_VIEW_HOVER_EXIT = 0x00000100;

    
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 0x00000200;

    
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 0x00000400;

    
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 0x00000800;

    
    public static final int TYPE_VIEW_SCROLLED = 0x00001000;

    
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 0x00002000;

    
    private AccessibilityEventCompat() {

    }

    
    public static int getRecordCount(AccessibilityEvent event) {
        return IMPL.getRecordCount(event);
    }

    
    public static void appendRecord(AccessibilityEvent event, AccessibilityRecordCompat record) {
        IMPL.appendRecord(event, record.getImpl());
    }

    
    public static AccessibilityRecordCompat getRecord(AccessibilityEvent event, int index) {
        return new AccessibilityRecordCompat(IMPL.getRecord(event, index));
    }
}

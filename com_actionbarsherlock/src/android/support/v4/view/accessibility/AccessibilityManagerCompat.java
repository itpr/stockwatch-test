

package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
//import android.os.Build;
//import android.support.v4.view.accessibility.AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerBridge;
import android.view.accessibility.AccessibilityManager;

import java.util.Collections;
import java.util.List;


public class AccessibilityManagerCompat {

    interface AccessibilityManagerVersionImpl {
        public Object newAccessiblityStateChangeListener(
                AccessibilityStateChangeListenerCompat listener);
        public boolean addAccessibilityStateChangeListener(AccessibilityManager manager,
                AccessibilityStateChangeListenerCompat listener);
        public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager,
                AccessibilityStateChangeListenerCompat listener);
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(
                AccessibilityManager manager,int feedbackTypeFlags);
        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(
                AccessibilityManager manager);
        public boolean isTouchExplorationEnabled(AccessibilityManager manager);
    }

    static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
        public Object newAccessiblityStateChangeListener(
                AccessibilityStateChangeListenerCompat listener) {
            return null;
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager manager,
                AccessibilityStateChangeListenerCompat listener) {
            return false;
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager,
                AccessibilityStateChangeListenerCompat listener) {
            return false;
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(
                AccessibilityManager manager, int feedbackTypeFlags) {
            return Collections.emptyList();
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(
                AccessibilityManager manager) {
            return Collections.emptyList();
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
            return false;
        }
    }

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new AccessibilityManagerIcsImpl();
        //} else {
            IMPL = new AccessibilityManagerStubImpl();
        //}
    }

    private static final AccessibilityManagerVersionImpl IMPL;

    
    public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager,
            AccessibilityStateChangeListenerCompat listener) {
        return IMPL.addAccessibilityStateChangeListener(manager, listener);
    }

    
    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager,
            AccessibilityStateChangeListenerCompat listener) {
        return IMPL.removeAccessibilityStateChangeListener(manager, listener);
    }

    
    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(
            AccessibilityManager manager) {
        return IMPL.getInstalledAccessibilityServiceList(manager);
    }

    
    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(
            AccessibilityManager manager, int feedbackTypeFlags) {
        return IMPL.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
    }

    
    public static abstract class AccessibilityStateChangeListenerCompat {
        final Object mListener;

        public AccessibilityStateChangeListenerCompat() {
            mListener = IMPL.newAccessiblityStateChangeListener(this);
        }

        
        public abstract void onAccessibilityStateChanged(boolean enabled);
    }
}

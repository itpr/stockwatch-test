

package android.support.v4.accessibilityservice;

//import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ResolveInfo;


public class AccessibilityServiceInfoCompat {

    static interface AccessibilityServiceInfoVersionImpl {
        public String getId(AccessibilityServiceInfo info);
        public ResolveInfo getResolveInfo(AccessibilityServiceInfo info);
        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info);
        public String getDescription(AccessibilityServiceInfo info);
        public String getSettingsActivityName(AccessibilityServiceInfo info);
    }

    static class AccessibilityServiceInfoStubImpl implements AccessibilityServiceInfoVersionImpl {

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
            return false;
        }

        public String getDescription(AccessibilityServiceInfo info) {
            return null;
        }

        public String getId(AccessibilityServiceInfo info) {
            return null;
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
            return null;
        }

        public String getSettingsActivityName(AccessibilityServiceInfo info) {
            return null;
        }
    }

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new AccessibilityServiceInfoIcsImpl();
        //} else {
            IMPL = new AccessibilityServiceInfoStubImpl();
        //}
    }

    private static final AccessibilityServiceInfoVersionImpl IMPL;

    
    public static final int FEEDBACK_ALL_MASK = 0xFFFFFFFF;

    
    private AccessibilityServiceInfoCompat() {

    }

    
    public static String getId(AccessibilityServiceInfo info) {
        return IMPL.getId(info);
    }

    
    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
        return IMPL.getResolveInfo(info);
    }

    
    public static String getSettingsActivityName(AccessibilityServiceInfo info) {
        return IMPL.getSettingsActivityName(info);
    }

    
    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
        return IMPL.getCanRetrieveWindowContent(info);
    }

    
    public static String getDescription(AccessibilityServiceInfo info) {
        return IMPL.getDescription(info);
    }

    
    public static String feedbackTypeToString(int feedbackType) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        while (feedbackType > 0) {
            final int feedbackTypeFlag = 1 << Integer.numberOfTrailingZeros(feedbackType);
            feedbackType &= ~feedbackTypeFlag;
            if (builder.length() > 1) {
                builder.append(", ");
            }
            switch (feedbackTypeFlag) {
                case AccessibilityServiceInfo.FEEDBACK_AUDIBLE:
                    builder.append("FEEDBACK_AUDIBLE");
                    break;
                case AccessibilityServiceInfo.FEEDBACK_HAPTIC:
                    builder.append("FEEDBACK_HAPTIC");
                    break;
                case AccessibilityServiceInfo.FEEDBACK_GENERIC:
                    builder.append("FEEDBACK_GENERIC");
                    break;
                case AccessibilityServiceInfo.FEEDBACK_SPOKEN:
                    builder.append("FEEDBACK_SPOKEN");
                    break;
                case AccessibilityServiceInfo.FEEDBACK_VISUAL:
                    builder.append("FEEDBACK_VISUAL");
                    break;
            }
        }
        builder.append("]");
        return builder.toString();
    }

    
    public static String flagToString(int flag) {
        switch (flag) {
            case AccessibilityServiceInfo.DEFAULT:
                return "DEFAULT";
            default:
                return null;
        }
    }
}

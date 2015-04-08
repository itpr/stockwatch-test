

package android.support.v4.view;

import android.view.ViewConfiguration;


public class ViewConfigurationCompat {
    
    interface ViewConfigurationVersionImpl {
        public int getScaledPagingTouchSlop(ViewConfiguration config);
    }

    
    static class BaseViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
        @Override
        public int getScaledPagingTouchSlop(ViewConfiguration config) {
            return config.getScaledTouchSlop();
        }
    }

    
    static class FroyoViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
        @Override
        public int getScaledPagingTouchSlop(ViewConfiguration config) {
            return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop(config);
        }
    }

    
    static final ViewConfigurationVersionImpl IMPL;
    static {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            IMPL = new FroyoViewConfigurationVersionImpl();
        } else {
            IMPL = new BaseViewConfigurationVersionImpl();
        }
    }

    // -------------------------------------------------------------------

    
    public static int getScaledPagingTouchSlop(ViewConfiguration config) {
        return IMPL.getScaledPagingTouchSlop(config);
    }
}

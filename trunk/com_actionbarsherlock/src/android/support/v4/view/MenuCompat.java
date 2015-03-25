

package android.support.v4.view;

import android.view.MenuItem;


@Deprecated
public class MenuCompat {

    
    interface MenuVersionImpl {
        public boolean setShowAsAction(MenuItem item, int actionEnum);
    }

    
    static class BaseMenuVersionImpl implements MenuVersionImpl {
        @Override
        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            return false;
        }
    }

    
    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        @Override
        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            MenuItemCompatHoneycomb.setShowAsAction(item, actionEnum);
            return true;
        }
    }

    
    static final MenuVersionImpl IMPL;
    static {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombMenuVersionImpl();
        } else {
            IMPL = new BaseMenuVersionImpl();
        }
    }

    // -------------------------------------------------------------------

    
    @Deprecated
    public static boolean setShowAsAction(MenuItem item, int actionEnum) {
        return IMPL.setShowAsAction(item, actionEnum);
    }
}

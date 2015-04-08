

package android.support.v4.view;

import android.view.MenuItem;
import android.view.View;


@Deprecated
public class MenuItemCompat {

    
    @Deprecated
    public static final int SHOW_AS_ACTION_NEVER = 0;

    
    @Deprecated
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;

    
    @Deprecated
    public static final int SHOW_AS_ACTION_ALWAYS = 2;

    
    @Deprecated
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;

    
    interface MenuVersionImpl {
        public boolean setShowAsAction(MenuItem item, int actionEnum);
        public MenuItem setActionView(MenuItem item, View view);
    }

    
    static class BaseMenuVersionImpl implements MenuVersionImpl {
        @Override
        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            return false;
        }

        @Override
        public MenuItem setActionView(MenuItem item, View view) {
            return item;
        }
    }

    
    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        @Override
        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            MenuItemCompatHoneycomb.setShowAsAction(item, actionEnum);
            return true;
        }
        @Override
        public MenuItem setActionView(MenuItem item, View view) {
            return MenuItemCompatHoneycomb.setActionView(item, view);
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

    
    @Deprecated
    public static MenuItem setActionView(MenuItem item, View view) {
        return IMPL.setActionView(item, view);
    }
}

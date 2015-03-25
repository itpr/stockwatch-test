

package android.support.v4.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ActionMode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.SpinnerAdapter;


public abstract class ActionBar {
    // ------------------------------------------------------------------------
    // ACTION MODE SUPPORT
    // ------------------------------------------------------------------------

    protected abstract ActionMode startActionMode(ActionMode.Callback callback);

    // ------------------------------------------------------------------------
    // ACTION BAR SUPPORT
    // ------------------------------------------------------------------------

    
    public static class LayoutParams extends MarginLayoutParams {
        
        public int gravity = -1;

        public LayoutParams() {
            this(-1);
        }
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
        public LayoutParams(int width, int height) {
            super(width, height);
        }
        public LayoutParams(int width, int height, int gravity) {
            this(width, height);
            this.gravity = gravity;
        }
        public LayoutParams(int gravity) {
            this(0, 0, gravity);
        }
        public LayoutParams(LayoutParams source) {
            super(source);
            this.gravity = source.gravity;
        }
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    
    public interface OnMenuVisibilityListener {
        
        void onMenuVisibilityChanged(boolean isVisible);
    }

    
    public interface OnNavigationListener {
        
        boolean onNavigationItemSelected(int itemPosition, long itemId);
    }

    
    public static abstract class Tab {
        
        public static int INVALID_POSITION = android.app.ActionBar.Tab.INVALID_POSITION;


        
        public abstract View getCustomView();

        
        public abstract Drawable getIcon();

        
        public abstract int getPosition();

        
        public abstract ActionBar.TabListener getTabListener();

        
        public abstract Object getTag();

        
        public abstract CharSequence getText();

        
        public abstract void select();

        
        public abstract ActionBar.Tab setCustomView(int layoutResId);

        
        public abstract ActionBar.Tab setCustomView(View view);

        
        public abstract ActionBar.Tab setIcon(Drawable icon);

        
        public abstract ActionBar.Tab setIcon(int resId);

        
        public abstract ActionBar.Tab setTabListener(ActionBar.TabListener listener);

        
        public abstract ActionBar.Tab setTag(Object obj);

        
        public abstract ActionBar.Tab setText(int resId);

        
        public abstract ActionBar.Tab setText(CharSequence text);
    }

    
    public interface TabListener {
        
        void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft);

        
        void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft);

        
        void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft);
    }



    
    public static final int DISPLAY_HOME_AS_UP = android.app.ActionBar.DISPLAY_HOME_AS_UP;

    
    public static final int DISPLAY_SHOW_CUSTOM = android.app.ActionBar.DISPLAY_SHOW_CUSTOM;

    
    public static final int DISPLAY_SHOW_HOME = android.app.ActionBar.DISPLAY_SHOW_HOME;

    
    public static final int DISPLAY_SHOW_TITLE = android.app.ActionBar.DISPLAY_SHOW_TITLE;

    
    public static final int DISPLAY_USE_LOGO = android.app.ActionBar.DISPLAY_USE_LOGO;

    
    public static final int NAVIGATION_MODE_LIST = android.app.ActionBar.NAVIGATION_MODE_LIST;

    
    public static final int NAVIGATION_MODE_STANDARD = android.app.ActionBar.NAVIGATION_MODE_STANDARD;

    
    public static final int NAVIGATION_MODE_TABS = android.app.ActionBar.NAVIGATION_MODE_TABS;



    
    public abstract void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener);

    
    public abstract void addTab(ActionBar.Tab tab);

    
    public abstract void addTab(ActionBar.Tab tab, boolean setSelected);

    
    public abstract void addTab(ActionBar.Tab tab, int position);

    
    public abstract void addTab(ActionBar.Tab tab, int position, boolean setSelected);

    
    public abstract View getCustomView();

    
    public abstract int getDisplayOptions();

    
    public abstract int getHeight();

    
    public abstract int getNavigationItemCount();

    
    public abstract int getNavigationMode();

    
    public abstract int getSelectedNavigationIndex();

    
    public abstract ActionBar.Tab getSelectedTab();

    
    public abstract CharSequence getSubtitle();

    
    public abstract ActionBar.Tab getTabAt(int index);

    
    public abstract int getTabCount();

    
    public abstract CharSequence getTitle();

    
    public abstract void hide();

    
    public abstract boolean isShowing();

    
    public abstract ActionBar.Tab newTab();

    
    public abstract void removeAllTabs();

    
    public abstract void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener);

    
    public abstract void removeTab(ActionBar.Tab tab);

    
    public abstract void removeTabAt(int position);

    
    public abstract void selectTab(ActionBar.Tab tab);

    
    public abstract void setBackgroundDrawable(Drawable d);

    
    public abstract void setCustomView(int resId);

    
    public abstract void setCustomView(View view);

    
    public abstract void setCustomView(View view, ActionBar.LayoutParams layoutParams);

    
    public abstract void setDisplayHomeAsUpEnabled(boolean showHomeAsUp);

    
    public abstract void setDisplayOptions(int options, int mask);

    
    public abstract void setDisplayOptions(int options);

    
    public abstract void setDisplayShowCustomEnabled(boolean showCustom);

    
    public abstract void setDisplayShowHomeEnabled(boolean showHome);

    
    public abstract void setDisplayShowTitleEnabled(boolean showTitle);

    
    public abstract void setDisplayUseLogoEnabled(boolean useLogo);

    
    public abstract void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback);

    
    public abstract void setNavigationMode(int mode);

    
    public abstract void setSelectedNavigationItem(int position);

    
    public abstract void setSubtitle(int resId);

    
    public abstract void setSubtitle(CharSequence subtitle);

    
    public abstract void setTitle(CharSequence title);

    
    public abstract void setTitle(int resId);

    
    public abstract void show();
}
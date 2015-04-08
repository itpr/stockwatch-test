

package android.support.v4.view;

import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;


public interface MenuItem extends android.view.MenuItem {
    
    public static abstract class OnMenuItemClickListener implements android.view.MenuItem.OnMenuItemClickListener {
        
        public abstract boolean onMenuItemClick(MenuItem item);

        @Override
        public final boolean onMenuItemClick(android.view.MenuItem item) {
            return this.onMenuItemClick(new MenuItemWrapper(item));
        }
    }



    
    public static final int SHOW_AS_ACTION_ALWAYS = android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

    
    public static final int SHOW_AS_ACTION_IF_ROOM = android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;

    
    public static final int SHOW_AS_ACTION_NEVER = android.view.MenuItem.SHOW_AS_ACTION_NEVER;

    
    public static final int SHOW_AS_ACTION_WITH_TEXT = android.view.MenuItem.SHOW_AS_ACTION_WITH_TEXT;



    
    View getActionView();

    
    MenuItem setActionView(int resId);

    
    MenuItem setActionView(View view);

    
    void setShowAsAction(int actionEnum);

    // ---------------------------------------------------------------------
    // MENU ITEM SUPPORT
    // ---------------------------------------------------------------------

    @Override
    SubMenu getSubMenu();

    @Override
    MenuItem setAlphabeticShortcut(char alphaChar);

    @Override
    MenuItem setCheckable(boolean checkable);

    @Override
    MenuItem setChecked(boolean checked);

    @Override
    MenuItem setEnabled(boolean enabled);

    @Override
    MenuItem setIcon(Drawable icon);

    @Override
    MenuItem setIcon(int iconRes);

    @Override
    MenuItem setIntent(Intent intent);

    @Override
    MenuItem setNumericShortcut(char numericChar);

    
    MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener);

    @Override
    MenuItem setShortcut(char numericChar, char alphaChar);

    @Override
    MenuItem setTitle(CharSequence title);

    @Override
    MenuItem setTitle(int title);

    @Override
    MenuItem setTitleCondensed(CharSequence title);

    @Override
    MenuItem setVisible(boolean visible);
}

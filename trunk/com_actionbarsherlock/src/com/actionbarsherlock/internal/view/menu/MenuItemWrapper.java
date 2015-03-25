

package com.actionbarsherlock.internal.view.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItem;
import android.support.v4.view.SubMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;


public final class MenuItemWrapper implements MenuItem {
    private static final class HoneycombMenuItem {
        static View getActionView(android.view.MenuItem item) {
            return item.getActionView();
        }

        static void setActionView(android.view.MenuItem item, int resId) {
            item.setActionView(resId);
        }

        static void setActionView(android.view.MenuItem item, View view) {
            item.setActionView(view);
        }

        static void setShowAsAction(android.view.MenuItem item, int actionEnum) {
            item.setShowAsAction(actionEnum);
        }
    }

    
    private final android.view.MenuItem mMenuItem;

    
    public MenuItemWrapper(android.view.MenuItem menuItem) {
        mMenuItem = menuItem;
    }


    
    public View getActionView() {
        if (mMenuItem != null) {
            return HoneycombMenuItem.getActionView(mMenuItem);
        }
        return null;
    }

    
    public MenuItem setActionView(int resId) {
        if (mMenuItem != null) {
            HoneycombMenuItem.setActionView(mMenuItem, resId);
        }
        return this;
    }

    
    public MenuItem setActionView(View view) {
        if (mMenuItem != null) {
            HoneycombMenuItem.setActionView(mMenuItem, view);
        }
        return this;
    }

    
    public void setShowAsAction(int actionEnum) {
        if (mMenuItem != null) {
            HoneycombMenuItem.setShowAsAction(mMenuItem, actionEnum);
        }
    }

    // ---------------------------------------------------------------------
    // MENU ITEM SUPPORT
    // ---------------------------------------------------------------------

    @Override
    public char getAlphabeticShortcut() {
        return mMenuItem.getAlphabeticShortcut();
    }

    @Override
    public int getGroupId() {
        return mMenuItem.getGroupId();
    }

    @Override
    public Drawable getIcon() {
        return mMenuItem.getIcon();
    }

    @Override
    public Intent getIntent() {
        return mMenuItem.getIntent();
    }

    @Override
    public int getItemId() {
        return mMenuItem.getItemId();
    }

    @Override
    public ContextMenuInfo getMenuInfo() {
        return mMenuItem.getMenuInfo();
    }

    @Override
    public char getNumericShortcut() {
        return mMenuItem.getNumericShortcut();
    }

    @Override
    public int getOrder() {
        return mMenuItem.getOrder();
    }

    @Override
    public SubMenu getSubMenu() {
        return new SubMenuWrapper(mMenuItem.getSubMenu());
    }

    @Override
    public CharSequence getTitle() {
        return mMenuItem.getTitle();
    }

    @Override
    public CharSequence getTitleCondensed() {
        return mMenuItem.getTitleCondensed();
    }

    @Override
    public boolean hasSubMenu() {
        return mMenuItem.hasSubMenu();
    }

    @Override
    public boolean isCheckable() {
        return mMenuItem.isCheckable();
    }

    @Override
    public boolean isChecked() {
        return mMenuItem.isChecked();
    }

    @Override
    public boolean isEnabled() {
        return mMenuItem.isEnabled();
    }

    @Override
    public boolean isVisible() {
        return mMenuItem.isVisible();
    }

    @Override
    public MenuItem setAlphabeticShortcut(char alphaChar) {
        mMenuItem.setAlphabeticShortcut(alphaChar);
        return this;
    }

    @Override
    public MenuItem setCheckable(boolean checkable) {
        mMenuItem.setCheckable(checkable);
        return this;
    }

    @Override
    public MenuItem setChecked(boolean checked) {
        mMenuItem.setChecked(checked);
        return this;
    }

    @Override
    public MenuItem setEnabled(boolean enabled) {
        mMenuItem.setEnabled(enabled);
        return this;
    }

    @Override
    public MenuItem setIcon(Drawable icon) {
        mMenuItem.setIcon(icon);
        return this;
    }

    @Override
    public MenuItem setIcon(int iconRes) {
        mMenuItem.setIcon(iconRes);
        return this;
    }

    @Override
    public MenuItem setIntent(Intent intent) {
        mMenuItem.setIntent(intent);
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char numericChar) {
        mMenuItem.setNumericShortcut(numericChar);
        return this;
    }

    @Override
    public MenuItem setOnMenuItemClickListener(android.view.MenuItem.OnMenuItemClickListener menuItemClickListener) {
        mMenuItem.setOnMenuItemClickListener(menuItemClickListener);
        return this;
    }

    
    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        mMenuItem.setOnMenuItemClickListener(menuItemClickListener);
        return this;
    }

    @Override
    public MenuItem setShortcut(char numericChar, char alphaChar) {
        mMenuItem.setShortcut(numericChar, alphaChar);
        return this;
    }

    @Override
    public MenuItem setTitle(CharSequence title) {
        mMenuItem.setTitle(title);
        return this;
    }

    @Override
    public MenuItem setTitle(int title) {
        mMenuItem.setTitle(title);
        return this;
    }

    @Override
    public MenuItem setTitleCondensed(CharSequence title) {
        mMenuItem.setTitleCondensed(title);
        return this;
    }

    @Override
    public MenuItem setVisible(boolean visible) {
        mMenuItem.setVisible(visible);
        return this;
    }
}

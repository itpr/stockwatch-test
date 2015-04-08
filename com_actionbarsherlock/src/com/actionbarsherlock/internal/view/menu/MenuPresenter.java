

package com.actionbarsherlock.internal.view.menu;

import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;


public interface MenuPresenter {
    
    public interface Callback {
        
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing);

        
        public boolean onOpenSubMenu(MenuBuilder subMenu);
    }

    
    public void initForMenu(Context context, MenuBuilder menu);

    
    public MenuView getMenuView(ViewGroup root);

    
    public void updateMenuView(boolean cleared);

    
    public void setCallback(Callback cb);

    
    public boolean onSubMenuSelected(SubMenuBuilder subMenu);

    
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing);

    
    public boolean flagActionItems();

    
    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item);

    
    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item);

    
    public int getId();

    
    public Parcelable onSaveInstanceState();

    
    public void onRestoreInstanceState(Parcelable state);
}

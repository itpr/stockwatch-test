

package com.actionbarsherlock.internal.view.menu;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.SubMenu;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;


public class MenuBuilder implements Menu {
    //UNUSED private static final String TAG = "MenuBuilder";

    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";

    private static final int[]  sCategoryToOrder = new int[] {
        1, 
        4, 
        5, 
        3, 
        2, 
        0, 
    };

    private final Context mContext;
    private final Resources mResources;

    
    private boolean mQwertyMode;

    
    private boolean mShortcutsVisible;

    
    private Callback mCallback;

    
    private ArrayList<MenuItemImpl> mItems;

    
    private ArrayList<MenuItemImpl> mVisibleItems;
    
    private boolean mIsVisibleItemsStale;

    
    private ArrayList<MenuItemImpl> mActionItems;
    
    private ArrayList<MenuItemImpl> mNonActionItems;

    
    private boolean mIsActionItemsStale;

    
    private int mDefaultShowAsAction = MenuItem.SHOW_AS_ACTION_NEVER;

    
    private ContextMenuInfo mCurrentMenuInfo;

    
    CharSequence mHeaderTitle;
    
    Drawable mHeaderIcon;
    
    View mHeaderView;

    
    //UNUSED private SparseArray<Parcelable> mFrozenViewStates;

    
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mItemsChangedWhileDispatchPrevented = false;

    private boolean mOptionalIconsVisible = false;

    private boolean mIsClosing = false;

    private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList<MenuItemImpl>();

    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters =
            new CopyOnWriteArrayList<WeakReference<MenuPresenter>>();

    
    private MenuItemImpl mExpandedItem;

    
    public interface Callback {
        
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item);

        
        public void onMenuModeChange(MenuBuilder menu);
    }

    
    public interface ItemInvoker {
        public boolean invokeItem(MenuItemImpl item);
    }

    public MenuBuilder(Context context) {
        mContext = context;
        mResources = context.getResources();

        mItems = new ArrayList<MenuItemImpl>();

        mVisibleItems = new ArrayList<MenuItemImpl>();
        mIsVisibleItemsStale = true;

        mActionItems = new ArrayList<MenuItemImpl>();
        mNonActionItems = new ArrayList<MenuItemImpl>();
        mIsActionItemsStale = true;

        setShortcutsVisibleInner(true);
    }

    
    public boolean bindOverflowToNative(android.view.Menu menu, android.view.MenuItem.OnMenuItemClickListener listener, HashMap<android.view.MenuItem, MenuItemImpl> map) {
        final ArrayList<MenuItemImpl> nonActionItems = getNonActionItems();
        if (nonActionItems == null || nonActionItems.size() == 0) {
            return false;
        }

        menu.clear();
        boolean visible = false;
        for (MenuItemImpl nonActionItem : nonActionItems) {
            if (nonActionItem.isVisible()) {
                visible = true;

                android.view.MenuItem nativeItem;
                if (nonActionItem.hasSubMenu()) {
                    android.view.SubMenu nativeSub = menu.addSubMenu(nonActionItem.getGroupId(), nonActionItem.getItemId(),
                            nonActionItem.getOrder(), nonActionItem.getTitle());

                    SubMenuBuilder subMenu = (SubMenuBuilder)nonActionItem.getSubMenu();
                    for (MenuItemImpl subItem : subMenu.getVisibleItems()) {
                        android.view.MenuItem nativeSubItem = nativeSub.add(subItem.getGroupId(), subItem.getItemId(),
                                subItem.getOrder(), subItem.getTitle());

                        nativeSubItem.setIcon(subItem.getIcon());
                        nativeSubItem.setOnMenuItemClickListener(listener);
                        nativeSubItem.setEnabled(subItem.isEnabled());
                        nativeSubItem.setIntent(subItem.getIntent());
                        nativeSubItem.setNumericShortcut(subItem.getNumericShortcut());
                        nativeSubItem.setAlphabeticShortcut(subItem.getAlphabeticShortcut());
                        nativeSubItem.setTitleCondensed(subItem.getTitleCondensed());
                        nativeSubItem.setCheckable(subItem.isCheckable());
                        nativeSubItem.setChecked(subItem.isChecked());

                        if (subItem.isExclusiveCheckable()) {
                            nativeSub.setGroupCheckable(subItem.getGroupId(), true, true);
                        }

                        map.put(nativeSubItem, subItem);
                    }

                    nativeItem = nativeSub.getItem();
                } else {
                    nativeItem = menu.add(nonActionItem.getGroupId(), nonActionItem.getItemId(),
                            nonActionItem.getOrder(), nonActionItem.getTitle());
                }
                nativeItem.setIcon(nonActionItem.getIcon());
                nativeItem.setOnMenuItemClickListener(listener);
                nativeItem.setEnabled(nonActionItem.isEnabled());
                nativeItem.setIntent(nonActionItem.getIntent());
                nativeItem.setNumericShortcut(nonActionItem.getNumericShortcut());
                nativeItem.setAlphabeticShortcut(nonActionItem.getAlphabeticShortcut());
                nativeItem.setTitleCondensed(nonActionItem.getTitleCondensed());
                nativeItem.setCheckable(nonActionItem.isCheckable());
                nativeItem.setChecked(nonActionItem.isChecked());

                if (nonActionItem.isExclusiveCheckable()) {
                    menu.setGroupCheckable(nonActionItem.getGroupId(), true, true);
                }

                map.put(nativeItem, nonActionItem);
            }
        }

        return visible;
    }

    public MenuBuilder setDefaultShowAsAction(int defaultShowAsAction) {
        mDefaultShowAsAction = defaultShowAsAction;
        return this;
    }

    
    public void addMenuPresenter(MenuPresenter presenter) {
        mPresenters.add(new WeakReference<MenuPresenter>(presenter));
        presenter.initForMenu(mContext, this);
        mIsActionItemsStale = true;
    }

    
    public void removeMenuPresenter(MenuPresenter presenter) {
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter item = ref.get();
            if (item == null || item == presenter) {
                mPresenters.remove(ref);
            }
        }
    }

    private void dispatchPresenterUpdate(boolean cleared) {
        if (mPresenters.isEmpty()) return;

        stopDispatchingItemsChanged();
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else {
                presenter.updateMenuView(cleared);
            }
        }
        startDispatchingItemsChanged();
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder subMenu) {
        if (mPresenters.isEmpty()) return false;

        boolean result = false;

        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else if (!result) {
                result = presenter.onSubMenuSelected(subMenu);
            }
        }
        return result;
    }

    private void dispatchSaveInstanceState(Bundle outState) {
        if (mPresenters.isEmpty()) return;

        SparseArray<Parcelable> presenterStates = new SparseArray<Parcelable>();

        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else {
                final int id = presenter.getId();
                if (id > 0) {
                    final Parcelable state = presenter.onSaveInstanceState();
                    if (state != null) {
                        presenterStates.put(id, state);
                    }
                }
            }
        }

        outState.putSparseParcelableArray(PRESENTER_KEY, presenterStates);
    }

    private void dispatchRestoreInstanceState(Bundle state) {
        SparseArray<Parcelable> presenterStates = state.getSparseParcelableArray(PRESENTER_KEY);

        if (presenterStates == null || mPresenters.isEmpty()) return;

        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else {
                final int id = presenter.getId();
                if (id > 0) {
                    Parcelable parcel = presenterStates.get(id);
                    if (parcel != null) {
                        presenter.onRestoreInstanceState(parcel);
                    }
                }
            }
        }
    }

    public void savePresenterStates(Bundle outState) {
        dispatchSaveInstanceState(outState);
    }

    public void restorePresenterStates(Bundle state) {
        dispatchRestoreInstanceState(state);
    }

    public void saveActionViewStates(Bundle outStates) {
        SparseArray<Parcelable> viewStates = null;

        final int itemCount = size();
        for (int i = 0; i < itemCount; i++) {
            final MenuItem item = getItem(i);
            final View v = item.getActionView();
            if (v != null && v.getId() != View.NO_ID) {
                if (viewStates == null) {
                    viewStates = new SparseArray<Parcelable>();
                }
                v.saveHierarchyState(viewStates);
            }
            if (item.hasSubMenu()) {
                final SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
                subMenu.saveActionViewStates(outStates);
            }
        }

        if (viewStates != null) {
            outStates.putSparseParcelableArray(getActionViewStatesKey(), viewStates);
        }
    }

    public void restoreActionViewStates(Bundle states) {
        if (states == null) {
            return;
        }

        SparseArray<Parcelable> viewStates = states.getSparseParcelableArray(
                getActionViewStatesKey());

        final int itemCount = size();
        for (int i = 0; i < itemCount; i++) {
            final MenuItem item = getItem(i);
            final View v = item.getActionView();
            if (v != null && v.getId() != View.NO_ID) {
                v.restoreHierarchyState(viewStates);
            }
            if (item.hasSubMenu()) {
                final SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
                subMenu.restoreActionViewStates(states);
            }
        }
    }

    protected String getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY;
    }

    public void setCallback(Callback cb) {
        mCallback = cb;
    }

    
    private MenuItem addInternal(int group, int id, int categoryOrder, CharSequence title) {
        final int ordering = getOrdering(categoryOrder);

        final MenuItemImpl item = new MenuItemImpl(this, group, id, categoryOrder,
                ordering, title, mDefaultShowAsAction);

        if (mCurrentMenuInfo != null) {
            // Pass along the current menu info
            item.setMenuInfo(mCurrentMenuInfo);
        }

        mItems.add(findInsertIndex(mItems, ordering), item);
        onItemsChanged(true);

        return item;
    }

    public MenuItem add(CharSequence title) {
        return addInternal(0, 0, 0, title);
    }

    public MenuItem add(int titleRes) {
        return addInternal(0, 0, 0, mResources.getString(titleRes));
    }

    public MenuItem add(int group, int id, int categoryOrder, CharSequence title) {
        return addInternal(group, id, categoryOrder, title);
    }

    public MenuItem add(int group, int id, int categoryOrder, int title) {
        return addInternal(group, id, categoryOrder, mResources.getString(title));
    }

    public SubMenu addSubMenu(CharSequence title) {
        return addSubMenu(0, 0, 0, title);
    }

    public SubMenu addSubMenu(int titleRes) {
        return addSubMenu(0, 0, 0, mResources.getString(titleRes));
    }

    public SubMenu addSubMenu(int group, int id, int categoryOrder, CharSequence title) {
        final MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, title);
        final SubMenuBuilder subMenu = new SubMenuBuilder(mContext, this, item);
        item.setSubMenu(subMenu);

        return subMenu;
    }

    public SubMenu addSubMenu(int group, int id, int categoryOrder, int title) {
        return addSubMenu(group, id, categoryOrder, mResources.getString(title));
    }

    public int addIntentOptions(int group, int id, int categoryOrder, ComponentName caller,
            Intent[] specifics, Intent intent, int flags, android.view.MenuItem[] outSpecificItems) {
        PackageManager pm = mContext.getPackageManager();
        final List<ResolveInfo> lri =
                pm.queryIntentActivityOptions(caller, specifics, intent, 0);
        final int N = lri != null ? lri.size() : 0;

        if ((flags & FLAG_APPEND_TO_GROUP) == 0) {
            removeGroup(group);
        }

        for (int i=0; i<N; i++) {
            final ResolveInfo ri = lri.get(i);
            Intent rintent = new Intent(
                ri.specificIndex < 0 ? intent : specifics[ri.specificIndex]);
            rintent.setComponent(new ComponentName(
                    ri.activityInfo.applicationInfo.packageName,
                    ri.activityInfo.name));
            final MenuItem item = add(group, id, categoryOrder, ri.loadLabel(pm))
                    .setIcon(ri.loadIcon(pm))
                    .setIntent(rintent);
            if (outSpecificItems != null && ri.specificIndex >= 0) {
                outSpecificItems[ri.specificIndex] = item;
            }
        }

        return N;
    }

    public void removeItem(int id) {
        removeItemAtInt(findItemIndex(id), true);
    }

    public void removeGroup(int group) {
        final int i = findGroupIndex(group);

        if (i >= 0) {
            final int maxRemovable = mItems.size() - i;
            int numRemoved = 0;
            while ((numRemoved++ < maxRemovable) && (mItems.get(i).getGroupId() == group)) {
                // Don't force update for each one, this method will do it at the end
                removeItemAtInt(i, false);
            }

            // Notify menu views
            onItemsChanged(true);
        }
    }

    
    private void removeItemAtInt(int index, boolean updateChildrenOnMenuViews) {
        if ((index < 0) || (index >= mItems.size())) return;

        mItems.remove(index);

        if (updateChildrenOnMenuViews) onItemsChanged(true);
    }

    public void removeItemAt(int index) {
        removeItemAtInt(index, true);
    }

    public void clearAll() {
        mPreventDispatchingItemsChanged = true;
        clear();
        clearHeader();
        mPreventDispatchingItemsChanged = false;
        mItemsChangedWhileDispatchPrevented = false;
        onItemsChanged(true);
    }

    public void clear() {
        if (mExpandedItem != null) {
            collapseItemActionView(mExpandedItem);
        }
        mItems.clear();

        onItemsChanged(true);
    }

    void setExclusiveItemChecked(MenuItem item) {
        final int group = item.getGroupId();

        final int N = mItems.size();
        for (int i = 0; i < N; i++) {
            MenuItemImpl curItem = mItems.get(i);
            if (curItem.getGroupId() == group) {
                if (!curItem.isExclusiveCheckable()) continue;
                if (!curItem.isCheckable()) continue;

                // Check the item meant to be checked, uncheck the others (that are in the group)
                curItem.setCheckedInt(curItem == item);
            }
        }
    }

    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
        final int N = mItems.size();

        for (int i = 0; i < N; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.getGroupId() == group) {
                item.setExclusiveCheckable(exclusive);
                item.setCheckable(checkable);
            }
        }
    }

    public void setGroupVisible(int group, boolean visible) {
        final int N = mItems.size();

        // We handle the notification of items being changed ourselves, so we use setVisibleInt rather
        // than setVisible and at the end notify of items being changed

        boolean changedAtLeastOneItem = false;
        for (int i = 0; i < N; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.getGroupId() == group) {
                if (item.setVisibleInt(visible)) changedAtLeastOneItem = true;
            }
        }

        if (changedAtLeastOneItem) onItemsChanged(true);
    }

    public void setGroupEnabled(int group, boolean enabled) {
        final int N = mItems.size();

        for (int i = 0; i < N; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.getGroupId() == group) {
                item.setEnabled(enabled);
            }
        }
    }

    public boolean hasVisibleItems() {
        final int size = size();

        for (int i = 0; i < size; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.isVisible()) {
                return true;
            }
        }

        return false;
    }

    public MenuItem findItem(int id) {
        final int size = size();
        for (int i = 0; i < size; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.getItemId() == id) {
                return item;
            } else if (item.hasSubMenu()) {
                MenuItem possibleItem = item.getSubMenu().findItem(id);

                if (possibleItem != null) {
                    return possibleItem;
                }
            }
        }

        return null;
    }

    public int findItemIndex(int id) {
        final int size = size();

        for (int i = 0; i < size; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.getItemId() == id) {
                return i;
            }
        }

        return -1;
    }

    public int findGroupIndex(int group) {
        return findGroupIndex(group, 0);
    }

    public int findGroupIndex(int group, int start) {
        final int size = size();

        if (start < 0) {
            start = 0;
        }

        for (int i = start; i < size; i++) {
            final MenuItemImpl item = mItems.get(i);

            if (item.getGroupId() == group) {
                return i;
            }
        }

        return -1;
    }

    public int size() {
        return mItems.size();
    }

    
    public MenuItem getItem(int index) {
        return mItems.get(index);
    }

    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return findItemWithShortcutForKey(keyCode, event) != null;
    }

    public void setQwertyMode(boolean isQwerty) {
        mQwertyMode = isQwerty;

        onItemsChanged(false);
    }

    
    private static int getOrdering(int categoryOrder) {
        final int index = (categoryOrder & CATEGORY_MASK) >> CATEGORY_SHIFT;

        if (index < 0 || index >= sCategoryToOrder.length) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }

        return (sCategoryToOrder[index] << CATEGORY_SHIFT) | (categoryOrder & USER_MASK);
    }

    
    boolean isQwertyMode() {
        return mQwertyMode;
    }

    
    public void setShortcutsVisible(boolean shortcutsVisible) {
        if (mShortcutsVisible == shortcutsVisible) return;

        setShortcutsVisibleInner(shortcutsVisible);
        onItemsChanged(false);
    }

    private void setShortcutsVisibleInner(boolean shortcutsVisible) {
        mShortcutsVisible = shortcutsVisible
                && mResources.getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS;
    }

    
    public boolean isShortcutsVisible() {
        return mShortcutsVisible;
    }

    Resources getResources() {
        return mResources;
    }

    public Context getContext() {
        return mContext;
    }

    boolean dispatchMenuItemSelected(MenuBuilder menu, MenuItem item) {
        return mCallback != null && mCallback.onMenuItemSelected(menu, item);
    }

    
    public void changeMenuMode() {
        if (mCallback != null) {
            mCallback.onMenuModeChange(this);
        }
    }

    private static int findInsertIndex(ArrayList<MenuItemImpl> items, int ordering) {
        for (int i = items.size() - 1; i >= 0; i--) {
            MenuItemImpl item = items.get(i);
            if (item.getOrdering() <= ordering) {
                return i + 1;
            }
        }

        return 0;
    }

    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        final MenuItemImpl item = findItemWithShortcutForKey(keyCode, event);

        boolean handled = false;

        if (item != null) {
            handled = performItemAction(item, flags);
        }

        if ((flags & FLAG_ALWAYS_PERFORM_CLOSE) != 0) {
            close(true);
        }

        return handled;
    }

    
    @SuppressWarnings("deprecation")
    void findItemsWithShortcutForKey(List<MenuItemImpl> items, int keyCode, KeyEvent event) {
        final boolean qwerty = isQwertyMode();
        final int metaState = event.getMetaState();
        final KeyCharacterMap.KeyData possibleChars = new KeyCharacterMap.KeyData();
        // Get the chars associated with the keyCode (i.e using any chording combo)
        final boolean isKeyCodeMapped = event.getKeyData(possibleChars);
        // The delete key is not mapped to '\b' so we treat it specially
        if (!isKeyCodeMapped && (keyCode != KeyEvent.KEYCODE_DEL)) {
            return;
        }

        // Look for an item whose shortcut is this key.
        final int N = mItems.size();
        for (int i = 0; i < N; i++) {
            MenuItemImpl item = mItems.get(i);
            if (item.hasSubMenu()) {
                ((MenuBuilder)item.getSubMenu()).findItemsWithShortcutForKey(items, keyCode, event);
            }
            final char shortcutChar = qwerty ? item.getAlphabeticShortcut() : item.getNumericShortcut();
            if (((metaState & (KeyEvent.META_SHIFT_ON | KeyEvent.META_SYM_ON)) == 0) &&
                  (shortcutChar != 0) &&
                  (shortcutChar == possibleChars.meta[0]
                      || shortcutChar == possibleChars.meta[2]
                      || (qwerty && shortcutChar == '\b' &&
                          keyCode == KeyEvent.KEYCODE_DEL)) &&
                  item.isEnabled()) {
                items.add(item);
            }
        }
    }

    
    @SuppressWarnings("deprecation")
    MenuItemImpl findItemWithShortcutForKey(int keyCode, KeyEvent event) {
        // Get all items that can be associated directly or indirectly with the keyCode
        ArrayList<MenuItemImpl> items = mTempShortcutItemList;
        items.clear();
        findItemsWithShortcutForKey(items, keyCode, event);

        if (items.isEmpty()) {
            return null;
        }

        final int metaState = event.getMetaState();
        final KeyCharacterMap.KeyData possibleChars = new KeyCharacterMap.KeyData();
        // Get the chars associated with the keyCode (i.e using any chording combo)
        event.getKeyData(possibleChars);

        // If we have only one element, we can safely returns it
        final int size = items.size();
        if (size == 1) {
            return items.get(0);
        }

        final boolean qwerty = isQwertyMode();
        // If we found more than one item associated with the key,
        // we have to return the exact match
        for (int i = 0; i < size; i++) {
            final MenuItemImpl item = items.get(i);
            final char shortcutChar = qwerty ? item.getAlphabeticShortcut() :
                    item.getNumericShortcut();
            if ((shortcutChar == possibleChars.meta[0] &&
                    (metaState & KeyEvent.META_ALT_ON) == 0)
                || (shortcutChar == possibleChars.meta[2] &&
                    (metaState & KeyEvent.META_ALT_ON) != 0)
                || (qwerty && shortcutChar == '\b' &&
                    keyCode == KeyEvent.KEYCODE_DEL)) {
                return item;
            }
        }
        return null;
    }

    public boolean performIdentifierAction(int id, int flags) {
        // Look for an item whose identifier is the id.
        return performItemAction(findItem(id), flags);
    }

    public boolean performItemAction(MenuItem item, int flags) {
        MenuItemImpl itemImpl = (MenuItemImpl) item;

        if (itemImpl == null || !itemImpl.isEnabled()) {
            return false;
        }

        boolean invoked = itemImpl.invoke();

        if (item.hasSubMenu()) {
            close(false);

            final SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
            invoked |= dispatchSubMenuSelected(subMenu);
            if (!invoked) close(true);
        } else {
            if ((flags & FLAG_PERFORM_NO_CLOSE) == 0) {
                close(true);
            }
        }

        return invoked;
    }

    
    final void close(boolean allMenusAreClosing) {
        if (mIsClosing) return;

        mIsClosing = true;
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else {
                presenter.onCloseMenu(this, allMenusAreClosing);
            }
        }
        mIsClosing = false;
    }

    
    public void close() {
        close(true);
    }

    
    void onItemsChanged(boolean structureChanged) {
        if (!mPreventDispatchingItemsChanged) {
            if (structureChanged) {
                mIsVisibleItemsStale = true;
                mIsActionItemsStale = true;
            }

            dispatchPresenterUpdate(structureChanged);
        } else {
            mItemsChangedWhileDispatchPrevented = true;
        }
    }

    
    public void stopDispatchingItemsChanged() {
        if (!mPreventDispatchingItemsChanged) {
            mPreventDispatchingItemsChanged = true;
            mItemsChangedWhileDispatchPrevented = false;
        }
    }

    public void startDispatchingItemsChanged() {
        mPreventDispatchingItemsChanged = false;

        if (mItemsChangedWhileDispatchPrevented) {
            mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(true);
        }
    }

    
    void onItemVisibleChanged(MenuItemImpl item) {
        // Notify of items being changed
        mIsVisibleItemsStale = true;
        onItemsChanged(true);
    }

    
    void onItemActionRequestChanged(MenuItemImpl item) {
        // Notify of items being changed
        mIsActionItemsStale = true;
        onItemsChanged(true);
    }

    ArrayList<MenuItemImpl> getVisibleItems() {
        if (!mIsVisibleItemsStale) return mVisibleItems;

        // Refresh the visible items
        mVisibleItems.clear();

        final int itemsSize = mItems.size();
        MenuItemImpl item;
        for (int i = 0; i < itemsSize; i++) {
            item = mItems.get(i);
            if (item.isVisible()) mVisibleItems.add(item);
        }

        mIsVisibleItemsStale = false;
        mIsActionItemsStale = true;

        return mVisibleItems;
    }

    
    public void flagActionItems() {
        if (!mIsActionItemsStale) {
            return;
        }

        // Presenters flag action items as needed.
        boolean flagged = false;
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else {
                flagged |= presenter.flagActionItems();
            }
        }

        if (flagged) {
            mActionItems.clear();
            mNonActionItems.clear();
            ArrayList<MenuItemImpl> visibleItems = getVisibleItems();
            final int itemsSize = visibleItems.size();
            for (int i = 0; i < itemsSize; i++) {
                MenuItemImpl item = visibleItems.get(i);
                if (item.isActionButton()) {
                    mActionItems.add(item);
                } else {
                    mNonActionItems.add(item);
                }
            }
        } else {
            // Nobody flagged anything, everything is a non-action item.
            // (This happens during a first pass with no action-item presenters.)
            mActionItems.clear();
            mNonActionItems.clear();
            mNonActionItems.addAll(getVisibleItems());
        }
        mIsActionItemsStale = false;
    }

    ArrayList<MenuItemImpl> getActionItems() {
        flagActionItems();
        return mActionItems;
    }

    ArrayList<MenuItemImpl> getNonActionItems() {
        flagActionItems();
        return mNonActionItems;
    }

    public void clearHeader() {
        mHeaderIcon = null;
        mHeaderTitle = null;
        mHeaderView = null;

        onItemsChanged(false);
    }

    private void setHeaderInternal(final int titleRes, final CharSequence title, final int iconRes,
            final Drawable icon, final View view) {
        final Resources r = getResources();

        if (view != null) {
            mHeaderView = view;

            // If using a custom view, then the title and icon aren't used
            mHeaderTitle = null;
            mHeaderIcon = null;
        } else {
            if (titleRes > 0) {
                mHeaderTitle = r.getText(titleRes);
            } else if (title != null) {
                mHeaderTitle = title;
            }

            if (iconRes > 0) {
                mHeaderIcon = r.getDrawable(iconRes);
            } else if (icon != null) {
                mHeaderIcon = icon;
            }

            // If using the title or icon, then a custom view isn't used
            mHeaderView = null;
        }

        // Notify of change
        onItemsChanged(false);
    }

    
    protected MenuBuilder setHeaderTitleInt(CharSequence title) {
        setHeaderInternal(0, title, 0, null, null);
        return this;
    }

    
    protected MenuBuilder setHeaderTitleInt(int titleRes) {
        setHeaderInternal(titleRes, null, 0, null, null);
        return this;
    }

    
    protected MenuBuilder setHeaderIconInt(Drawable icon) {
        setHeaderInternal(0, null, 0, icon, null);
        return this;
    }

    
    protected MenuBuilder setHeaderIconInt(int iconRes) {
        setHeaderInternal(0, null, iconRes, null, null);
        return this;
    }

    
    protected MenuBuilder setHeaderViewInt(View view) {
        setHeaderInternal(0, null, 0, null, view);
        return this;
    }

    public CharSequence getHeaderTitle() {
        return mHeaderTitle;
    }

    public Drawable getHeaderIcon() {
        return mHeaderIcon;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    
    public MenuBuilder getRootMenu() {
        return this;
    }

    
    public void setCurrentMenuInfo(ContextMenuInfo menuInfo) {
        mCurrentMenuInfo = menuInfo;
    }

    void setOptionalIconsVisible(boolean visible) {
        mOptionalIconsVisible = visible;
    }

    boolean getOptionalIconsVisible() {
        return mOptionalIconsVisible;
    }

    public boolean expandItemActionView(MenuItemImpl item) {
        if (mPresenters.isEmpty()) return false;

        boolean expanded = false;

        stopDispatchingItemsChanged();
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else if ((expanded = presenter.expandItemActionView(this, item))) {
                break;
            }
        }
        startDispatchingItemsChanged();

        if (expanded) {
            mExpandedItem = item;
        }
        return expanded;
    }

    public boolean collapseItemActionView(MenuItemImpl item) {
        if (mPresenters.isEmpty() || mExpandedItem != item) return false;

        boolean collapsed = false;

        stopDispatchingItemsChanged();
        for (WeakReference<MenuPresenter> ref : mPresenters) {
            final MenuPresenter presenter = ref.get();
            if (presenter == null) {
                mPresenters.remove(ref);
            } else if ((collapsed = presenter.collapseItemActionView(this, item))) {
                break;
            }
        }
        startDispatchingItemsChanged();

        if (collapsed) {
            mExpandedItem = null;
        }
        return collapsed;
    }

    public MenuItemImpl getExpandedItem() {
        return mExpandedItem;
    }
}

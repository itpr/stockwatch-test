

package com.actionbarsherlock.internal.view.menu;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.SubMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.View;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class MenuInflaterImpl extends android.view.MenuInflater {
    private static final String LOG_TAG = "MenuInflater";
    private static final String XML_NS = "http://schemas.android.com/apk/res/android";


    


    
    private static final String XML_MENU = "menu";

    
    private static final String XML_GROUP = "group";

    
    private static final String XML_ITEM = "item";

    private static final int NO_ID = 0;

    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[] {Context.class};

    private final Object[] mActionViewConstructorArguments;

    private Context mContext;

    
    private final android.view.MenuInflater mNativeMenuInflater;

    
    public MenuInflaterImpl(Context context, android.view.MenuInflater nativeMenuInflater) {
        super(context);
        mContext = context;
        mActionViewConstructorArguments = new Object[] {context};
        mNativeMenuInflater = nativeMenuInflater;
    }

    
    public void inflate(int menuRes, android.view.Menu menu) {
        if (!(menu instanceof MenuBuilder)) {
            mNativeMenuInflater.inflate(menuRes, menu);
            return;
        }

        MenuBuilder actionBarMenu = (MenuBuilder)menu;
        XmlResourceParser parser = null;
        try {
            parser = mContext.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);

            parseMenu(parser, attrs, actionBarMenu);
        } catch (XmlPullParserException e) {
            throw new InflateException("Error inflating menu XML", e);
        } catch (IOException e) {
            throw new InflateException("Error inflating menu XML", e);
        } finally {
            if (parser != null) parser.close();
        }
    }

    
    private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu)
            throws XmlPullParserException, IOException {
        MenuState menuState = new MenuState(menu);

        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }

                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }

                    tagName = parser.getName();
                    if (tagName.equals(XML_GROUP)) {
                        menuState.readGroup(attrs);
                    } else if (tagName.equals(XML_ITEM)) {
                        menuState.readItem(attrs);
                    } else if (tagName.equals(XML_MENU)) {
                        // A menu start tag denotes a submenu for an item
                        SubMenu subMenu = menuState.addSubMenuItem();

                        // Parse the submenu into returned SubMenu
                        parseMenu(parser, attrs, subMenu);
                    } else {
                        lookingForEndOfUnknownTag = true;
                        unknownTagName = tagName;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } else if (tagName.equals(XML_GROUP)) {
                        menuState.resetGroup();
                    } else if (tagName.equals(XML_ITEM)) {
                        // Add the item if it hasn't been added (if the item was
                        // a submenu, it would have been added already)
                        if (!menuState.hasAddedItem()) {
                            menuState.addItem();
                        }
                    } else if (tagName.equals(XML_MENU)) {
                        reachedEndOfMenu = true;
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }

            eventType = parser.next();
        }
    }

    private static class InflatedOnMenuItemClickListener
            extends MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[] { MenuItem.class };

        private Context mContext;
        private Method mMethod;

        public InflatedOnMenuItemClickListener(Context context, String methodName) {
            mContext = context;
            Class<?> c = context.getClass();
            try {
                mMethod = c.getMethod(methodName, PARAM_TYPES);
            } catch (Exception e) {
                InflateException ex = new InflateException(
                        "Couldn't resolve menu item onClick handler " + methodName +
                        " in class " + c.getName());
                ex.initCause(e);
                throw ex;
            }
        }

        public boolean onMenuItemClick(MenuItem item) {
            try {
                if (mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean) mMethod.invoke(mContext, item);
                } else {
                    mMethod.invoke(mContext, item);
                    return true;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    
    private class MenuState {
        private Menu menu;

        
        private int groupId;
        private int groupCategory;
        private int groupOrder;
        private int groupCheckable;
        private boolean groupVisible;
        private boolean groupEnabled;

        private boolean itemAdded;
        private int itemId;
        private int itemCategoryOrder;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private int itemIconResId;
        private char itemAlphabeticShortcut;
        private char itemNumericShortcut;
        
        private int itemCheckable;
        private boolean itemChecked;
        private boolean itemVisible;
        private boolean itemEnabled;

        
        private int itemShowAsAction;

        private int itemActionViewLayout;
        private String itemActionViewClassName;

        private String itemListenerMethodName;

        private static final int defaultGroupId = NO_ID;
        private static final int defaultItemId = NO_ID;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemOrder = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemVisible = true;
        private static final boolean defaultItemEnabled = true;

        public MenuState(final Menu menu) {
            this.menu = menu;

            resetGroup();
        }

        public void resetGroup() {
            groupId = defaultGroupId;
            groupCategory = defaultItemCategory;
            groupOrder = defaultItemOrder;
            groupCheckable = defaultItemCheckable;
            groupVisible = defaultItemVisible;
            groupEnabled = defaultItemEnabled;
        }

        
        public void readGroup(AttributeSet attrs) {
            //TypedArray a = mContext.obtainStyledAttributes(attrs,
            //        MenuGroup);

            //groupId = a.getResourceId(MenuGroup_id, defaultGroupId);
            groupId = attrs.getAttributeResourceValue(XML_NS, "id", defaultGroupId);
            //groupCategory = a.getInt(MenuGroup_menuCategory, defaultItemCategory);
            groupCategory = attrs.getAttributeIntValue(XML_NS, "menuCategory", defaultItemCategory);
            //groupOrder = a.getInt(MenuGroup_orderInCategory, defaultItemOrder);
            groupOrder = attrs.getAttributeIntValue(XML_NS, "orderInCategory", defaultItemOrder);
            //groupCheckable = a.getInt(MenuGroup_checkableBehavior, defaultItemCheckable);
            groupCheckable = attrs.getAttributeIntValue(XML_NS, "checkableBehavior", defaultItemCheckable);
            //groupVisible = a.getBoolean(MenuGroup_visible, defaultItemVisible);
            groupVisible = attrs.getAttributeBooleanValue(XML_NS, "visible", defaultItemVisible);
            //groupEnabled = a.getBoolean(MenuGroup_enabled, defaultItemEnabled);
            groupEnabled = attrs.getAttributeBooleanValue(XML_NS, "enabled", defaultItemEnabled);

            //a.recycle();
        }

        
        public void readItem(AttributeSet attrs) {
            //TypedArray a = mContext.obtainStyledAttributes(attrs,
            //        MenuItem);

            // Inherit attributes from the group as default value
            //itemId = a.getResourceId(MenuItem_id, defaultItemId);
            itemId = attrs.getAttributeResourceValue(XML_NS, "id", defaultItemId);
            //final int category = a.getInt(MenuItem_menuCategory, groupCategory);
            final int category = attrs.getAttributeIntValue(XML_NS, "menuCategory", groupCategory);
            //final int order = a.getInt(MenuItem_orderInCategory, groupOrder);
            final int order = attrs.getAttributeIntValue(XML_NS, "orderInCategory", groupOrder);
            itemCategoryOrder = (category & Menu.CATEGORY_MASK) | (order & Menu.USER_MASK);
            //itemTitle = a.getText(MenuItem_title);
            final int itemTitleId = attrs.getAttributeResourceValue(XML_NS, "title", 0);
            if (itemTitleId != 0) {
                itemTitle = mContext.getString(itemTitleId);
            } else {
                itemTitle = attrs.getAttributeValue(XML_NS, "title");
            }
            //itemTitleCondensed = a.getText(MenuItem_titleCondensed);
            final int itemTitleCondensedId = attrs.getAttributeResourceValue(XML_NS, "titleCondensed", 0);
            if (itemTitleCondensedId != 0) {
                itemTitleCondensed = mContext.getString(itemTitleCondensedId);
            } else {
                itemTitleCondensed = attrs.getAttributeValue(XML_NS, "titleCondensed");
            }
            //itemIconResId = a.getResourceId(MenuItem_icon, 0);
            itemIconResId = attrs.getAttributeResourceValue(XML_NS, "icon", 0);
            //itemAlphabeticShortcut =
            //        getShortcut(a.getString(MenuItem_alphabeticShortcut));
            itemAlphabeticShortcut =
                    getShortcut(attrs.getAttributeValue(XML_NS, "alphabeticShortcut"));
            //itemNumericShortcut =
            //        getShortcut(a.getString(MenuItem_numericShortcut));
            itemNumericShortcut =
                    getShortcut(attrs.getAttributeValue(XML_NS, "numericShortcut"));
            //if (a.hasValue(MenuItem_checkable)) {
            if (attrs.getAttributeValue(XML_NS, "checkable") != null) {
                // Item has attribute checkable, use it
                //itemCheckable = a.getBoolean(MenuItem_checkable, false) ? 1 : 0;
                itemCheckable = attrs.getAttributeBooleanValue(XML_NS, "checkable", false) ? 1 : 0;
            } else {
                // Item does not have attribute, use the group's (group can have one more state
                // for checkable that represents the exclusive checkable)
                itemCheckable = groupCheckable;
            }
            //itemChecked = a.getBoolean(MenuItem_checked, defaultItemChecked);
            itemChecked = attrs.getAttributeBooleanValue(XML_NS, "checked", defaultItemChecked);
            //itemVisible = a.getBoolean(MenuItem_visible, groupVisible);
            itemVisible = attrs.getAttributeBooleanValue(XML_NS, "visible", groupVisible);
            //itemEnabled = a.getBoolean(MenuItem_enabled, groupEnabled);
            itemEnabled = attrs.getAttributeBooleanValue(XML_NS, "enabled", groupEnabled);
            //itemShowAsAction = a.getInt(MenuItem_showAsAction, -1);
            itemShowAsAction = attrs.getAttributeIntValue(XML_NS, "showAsAction", -1);
            //itemListenerMethodName = a.getString(MenuItem_onClick);
            itemListenerMethodName = attrs.getAttributeValue(XML_NS, "onClick");
            //itemActionViewLayout = a.getResourceId(MenuItem_actionLayout, 0);
            itemActionViewLayout = attrs.getAttributeResourceValue(XML_NS, "actionLayout", 0);
            //itemActionViewClassName = a.getString(MenuItem_actionViewClass);
            itemActionViewClassName = attrs.getAttributeValue(XML_NS, "actionViewClass");

            //a.recycle();

            itemAdded = false;
        }

        private char getShortcut(String shortcutString) {
            if (shortcutString == null) {
                return 0;
            } else {
                return shortcutString.charAt(0);
            }
        }

        private void setItem(MenuItem item) {
            item.setChecked(itemChecked)
                .setVisible(itemVisible)
                .setEnabled(itemEnabled)
                .setCheckable(itemCheckable >= 1)
                .setTitleCondensed(itemTitleCondensed)
                .setIcon(itemIconResId)
                .setAlphabeticShortcut(itemAlphabeticShortcut)
                .setNumericShortcut(itemNumericShortcut);

            if (itemShowAsAction >= 0) {
                item.setShowAsAction(itemShowAsAction);
            }

            if (itemListenerMethodName != null) {
                if (mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot "
                            + "be used within a restricted context");
                }
                item.setOnMenuItemClickListener(
                        new InflatedOnMenuItemClickListener(mContext, itemListenerMethodName));
            }

            if (item instanceof MenuItemImpl) {
                MenuItemImpl impl = (MenuItemImpl) item;
                if (itemCheckable >= 2) {
                    impl.setExclusiveCheckable(true);
                }
            }

            boolean actionViewSpecified = false;
            if (itemActionViewClassName != null) {
                View actionView = (View) newInstance(itemActionViewClassName,
                        ACTION_VIEW_CONSTRUCTOR_SIGNATURE, mActionViewConstructorArguments);
                item.setActionView(actionView);
                actionViewSpecified = true;
            }
            if (itemActionViewLayout > 0) {
                if (!actionViewSpecified) {
                    item.setActionView(itemActionViewLayout);
                    actionViewSpecified = true;
                } else {
                    Log.w(LOG_TAG, "Ignoring attribute 'itemActionViewLayout'."
                            + " Action view already specified.");
                }
            }
        }

        public void addItem() {
            itemAdded = true;
            setItem(menu.add(groupId, itemId, itemCategoryOrder, itemTitle));
        }

        public SubMenu addSubMenuItem() {
            itemAdded = true;
            SubMenu subMenu = menu.addSubMenu(groupId, itemId, itemCategoryOrder, itemTitle);
            setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return itemAdded;
        }

        @SuppressWarnings("unchecked")
        private <T> T newInstance(String className, Class<?>[] constructorSignature,
                Object[] arguments) {
            try {
                Class<?> clazz = mContext.getClassLoader().loadClass(className);
                Constructor<?> constructor = clazz.getConstructor(constructorSignature);
                return (T) constructor.newInstance(arguments);
            } catch (Exception e) {
                Log.w(LOG_TAG, "Cannot instantiate class: " + className, e);
            }
            return null;
        }
    }
}

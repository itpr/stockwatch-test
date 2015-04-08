

package android.support.v4.view.accessibility;

import android.graphics.Rect;
//import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AccessibilityNodeInfoCompat {

    static interface AccessibilityNodeInfoImpl {
        public Object obtain();
        public Object obtain(View source);
        public Object obtain(Object info);
        public void setSource(Object info, View source);
        public int getWindowId(Object info);
        public int getChildCount(Object info);
        public Object getChild(Object info, int index);
        public void addChild(Object info, View child);
        public int getActions(Object info);
        public void addAction(Object info, int action);
        public boolean performAction(Object info, int action);
        public List<Object> findAccessibilityNodeInfosByText(Object info, String text);
        public Object getParent(Object info);
        public void setParent(Object info, View parent);
        public void getBoundsInParent(Object info, Rect outBounds);
        public void setBoundsInParent(Object info, Rect bounds);
        public void getBoundsInScreen(Object info, Rect outBounds);
        public void setBoundsInScreen(Object info, Rect bounds);
        public boolean isCheckable(Object info);
        public void setCheckable(Object info, boolean checkable);
        public boolean isChecked(Object info);
        public void setChecked(Object info, boolean checked);
        public boolean isFocusable(Object info);
        public void setFocusable(Object info, boolean focusable);
        public boolean isFocused(Object info);
        public void setFocused(Object info, boolean focused);
        public boolean isSelected(Object info);
        public void setSelected(Object info, boolean selected);
        public boolean isClickable(Object info);
        public void setClickable(Object info, boolean clickable);
        public boolean isLongClickable(Object info);
        public void setLongClickable(Object info, boolean longClickable);
        public boolean isEnabled(Object info);
        public void setEnabled(Object info, boolean enabled);
        public boolean isPassword(Object info);
        public void setPassword(Object info, boolean password);
        public boolean isScrollable(Object info);
        public void setScrollable(Object info, boolean scrollable);
        public CharSequence getPackageName(Object info);
        public void setPackageName(Object info, CharSequence packageName);
        public CharSequence getClassName(Object info);
        public void setClassName(Object info, CharSequence className);
        public CharSequence getText(Object info);
        public void setText(Object info, CharSequence text);
        public CharSequence getContentDescription(Object info);
        public void setContentDescription(Object info, CharSequence contentDescription);
        public void recycle(Object info);
    }

    static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
        public Object obtain() {
            return null;
        }

        public Object obtain(View source) {
            return null;
        }

        public Object obtain(Object info) {
            return null;
        }

        public void addAction(Object info, int action) {

        }

        public void addChild(Object info, View child) {

        }

        public List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
            return Collections.emptyList();
        }

        public int getActions(Object info) {
            return 0;
        }

        public void getBoundsInParent(Object info, Rect outBounds) {

        }

        public void getBoundsInScreen(Object info, Rect outBounds) {

        }

        public Object getChild(Object info, int index) {
            return null;
        }

        public int getChildCount(Object info) {
            return 0;
        }

        public CharSequence getClassName(Object info) {
            return null;
        }

        public CharSequence getContentDescription(Object info) {
            return null;
        }

        public CharSequence getPackageName(Object info) {
            return null;
        }

        public AccessibilityNodeInfoCompat getParent(Object info) {
            return null;
        }

        public CharSequence getText(Object info) {
            return null;
        }

        public int getWindowId(Object info) {
            return 0;
        }

        public boolean isCheckable(Object info) {
            return false;
        }

        public boolean isChecked(Object info) {
            return false;
        }

        public boolean isClickable(Object info) {
            return false;
        }

        public boolean isEnabled(Object info) {
            return false;
        }

        public boolean isFocusable(Object info) {
            return false;
        }

        public boolean isFocused(Object info) {
            return false;
        }

        public boolean isLongClickable(Object info) {
            return false;
        }

        public boolean isPassword(Object info) {
            return false;
        }

        public boolean isScrollable(Object info) {
            return false;
        }

        public boolean isSelected(Object info) {
            return false;
        }

        public boolean performAction(Object info, int action) {
            return false;
        }

        public void setBoundsInParent(Object info, Rect bounds) {

        }

        public void setBoundsInScreen(Object info, Rect bounds) {

        }

        public void setCheckable(Object info, boolean checkable) {

        }

        public void setChecked(Object info, boolean checked) {

        }

        public void setClassName(Object info, CharSequence className) {

        }

        public void setClickable(Object info, boolean clickable) {

        }

        public void setContentDescription(Object info, CharSequence contentDescription) {

        }

        public void setEnabled(Object info, boolean enabled) {

        }

        public void setFocusable(Object info, boolean focusable) {

        }

        public void setFocused(Object info, boolean focused) {

        }

        public void setLongClickable(Object info, boolean longClickable) {

        }

        public void setPackageName(Object info, CharSequence packageName) {

        }

        public void setParent(Object info, View parent) {

        }

        public void setPassword(Object info, boolean password) {

        }

        public void setScrollable(Object info, boolean scrollable) {

        }

        public void setSelected(Object info, boolean selected) {

        }

        public void setSource(Object info, View source) {

        }

        public void setText(Object info, CharSequence text) {

        }

        public void recycle(Object info) {

        }
    }

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new AccessibilityNodeInfoIcsImpl();
        //} else {
            IMPL = new AccessibilityNodeInfoStubImpl();
        //}
    }

    private static final AccessibilityNodeInfoImpl IMPL;

    private final Object mInfo;

    // Actions.

    
    public static final int ACTION_FOCUS = 0x00000001;

    
    public static final int ACTION_CLEAR_FOCUS = 0x00000002;

    
    public static final int ACTION_SELECT = 0x00000004;

    
    public static final int ACTION_CLEAR_SELECTION = 0x00000008;

    
    public AccessibilityNodeInfoCompat(Object info) {
        mInfo = info;
    }

    
    public Object getImpl() {
        return mInfo;
    }

    
    public static AccessibilityNodeInfoCompat obtain(View source) {
        return new AccessibilityNodeInfoCompat(IMPL.obtain(source));
    }

    
    public static AccessibilityNodeInfoCompat obtain() {
        return new AccessibilityNodeInfoCompat(IMPL.obtain());
    }

    
    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
        return new AccessibilityNodeInfoCompat(IMPL.obtain(info.mInfo));
    }

    
    public void setSource(View source) {
        IMPL.setSource(mInfo, source);
    }

    
    public int getWindowId() {
        return IMPL.getWindowId(mInfo);
    }

    
    public int getChildCount() {
        return IMPL.getChildCount(mInfo);
    }

    
    public AccessibilityNodeInfoCompat getChild(int index) {
        return new AccessibilityNodeInfoCompat(IMPL.getChild(mInfo, index));
    }

    
    public void addChild(View child) {
        IMPL.addChild(mInfo, child);
    }

    
    public int getActions() {
        return IMPL.getActions(mInfo);
    }

    
    public void addAction(int action) {
        IMPL.addAction(mInfo, action);
    }

    
    public boolean performAction(int action) {
        return IMPL.performAction(mInfo, action);
    }

    
    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
        List<AccessibilityNodeInfoCompat> result = new ArrayList<AccessibilityNodeInfoCompat>();
        List<Object> infos = IMPL.findAccessibilityNodeInfosByText(mInfo, text);
        final int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            Object info = infos.get(i);
            result.add(new AccessibilityNodeInfoCompat(info));
        }
        return result;
    }

    
    public AccessibilityNodeInfoCompat getParent() {
        return new AccessibilityNodeInfoCompat(IMPL.getParent(mInfo));
    }

    
    public void setParent(View parent) {
        IMPL.setParent(mInfo, parent);
    }

    
    public void getBoundsInParent(Rect outBounds) {
        IMPL.getBoundsInParent(mInfo, outBounds);
    }

    
    public void setBoundsInParent(Rect bounds) {
        IMPL.setBoundsInParent(mInfo, bounds);
    }

    
    public void getBoundsInScreen(Rect outBounds) {
        IMPL.getBoundsInScreen(mInfo, outBounds);
    }

    
    public void setBoundsInScreen(Rect bounds) {
        IMPL.setBoundsInParent(mInfo, bounds);
    }

    
    public boolean isCheckable() {
        return IMPL.isCheckable(mInfo);
    }

    
    public void setCheckable(boolean checkable) {
        IMPL.setCheckable(mInfo, checkable);
    }

    
    public boolean isChecked() {
        return IMPL.isChecked(mInfo);
    }

    
    public void setChecked(boolean checked) {
        IMPL.setChecked(mInfo, checked);
    }

    
    public boolean isFocusable() {
        return IMPL.isFocusable(mInfo);
    }

    
    public void setFocusable(boolean focusable) {
        IMPL.setFocusable(mInfo, focusable);
    }

    
    public boolean isFocused() {
        return IMPL.isFocused(mInfo);
    }

    
    public void setFocused(boolean focused) {
        IMPL.setFocused(mInfo, focused);
    }

    
    public boolean isSelected() {
        return IMPL.isSelected(mInfo);
    }

    
    public void setSelected(boolean selected) {
        IMPL.setSelected(mInfo, selected);
    }

    
    public boolean isClickable() {
        return IMPL.isClickable(mInfo);
    }

    
    public void setClickable(boolean clickable) {
        IMPL.setClickable(mInfo, clickable);
    }

    
    public boolean isLongClickable() {
        return IMPL.isLongClickable(mInfo);
    }

    
    public void setLongClickable(boolean longClickable) {
        IMPL.setLongClickable(mInfo, longClickable);
    }

    
    public boolean isEnabled() {
        return IMPL.isEnabled(mInfo);
    }

    
    public void setEnabled(boolean enabled) {
        IMPL.setEnabled(mInfo, enabled);
    }

    
    public boolean isPassword() {
        return IMPL.isPassword(mInfo);
    }

    
    public void setPassword(boolean password) {
        IMPL.setPassword(mInfo, password);
    }

    
    public boolean isScrollable() {
        return IMPL.isScrollable(mInfo);
    }

    
    public void setScrollable(boolean scrollable) {
        IMPL.setScrollable(mInfo, scrollable);
    }

    
    public CharSequence getPackageName() {
        return IMPL.getPackageName(mInfo);
    }

    
    public void setPackageName(CharSequence packageName) {
        IMPL.setPackageName(mInfo, packageName);
    }

    
    public CharSequence getClassName() {
        return IMPL.getClassName(mInfo);
    }

    
    public void setClassName(CharSequence className) {
        IMPL.setClassName(mInfo, className);
    }

    
    public CharSequence getText() {
        return IMPL.getText(mInfo);
    }

    
    public void setText(CharSequence text) {
        IMPL.setText(mInfo, text);
    }

    
    public CharSequence getContentDescription() {
        return IMPL.getContentDescription(mInfo);
    }

    
    public void setContentDescription(CharSequence contentDescription) {
        IMPL.setContentDescription(mInfo, contentDescription);
    }

    
    public void recycle() {
        IMPL.recycle(mInfo);
    }

    @Override
    public int hashCode() {
        return (mInfo == null) ? 0 : mInfo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
        if (mInfo == null) {
            if (other.mInfo != null) {
                return false;
            }
        } else if (!mInfo.equals(other.mInfo)) {
            return false;
        }
        return true;
    }
}

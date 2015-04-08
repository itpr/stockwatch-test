

package android.support.v4.view.accessibility;

//import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import java.util.Collections;
import java.util.List;


public class AccessibilityRecordCompat {

    static interface AccessibilityRecordImpl {
        public Object obtain();
        public Object obtain(Object record);
        public void setSource(Object record, View source);
        public Object getSource(Object record);
        public int getWindowId(Object record);
        public boolean isChecked(Object record);
        public void setChecked(Object record, boolean isChecked);
        public boolean isEnabled(Object record);
        public void setEnabled(Object record, boolean isEnabled);
        public boolean isPassword(Object record);
        public void setPassword(Object record, boolean isPassword);
        public boolean isFullScreen(Object record);
        public void setFullScreen(Object record, boolean isFullScreen);
        public boolean isScrollable(Object record);
        public void setScrollable(Object record, boolean scrollable);
        public int getItemCount(Object record);
        public void setItemCount(Object record, int itemCount);
        public int getCurrentItemIndex(Object record);
        public void setCurrentItemIndex(Object record, int currentItemIndex);
        public int getFromIndex(Object record);
        public void setFromIndex(Object record, int fromIndex);
        public int getToIndex(Object record);
        public void setToIndex(Object record, int toIndex);
        public int getScrollX(Object record);
        public void setScrollX(Object record, int scrollX);
        public int getScrollY(Object record);
        public void setScrollY(Object record, int scrollY);
        public int getMaxScrollX(Object record);
        public void setMaxScrollX(Object record, int maxScrollX);
        public int getMaxScrollY(Object record);
        public void setMaxScrollY(Object record, int maxScrollY);
        public int getAddedCount(Object record);
        public void setAddedCount(Object record, int addedCount);
        public int getRemovedCount(Object record);
        public void setRemovedCount(Object record, int removedCount);
        public CharSequence getClassName(Object record);
        public void setClassName(Object record, CharSequence className);
        public List<CharSequence> getText(Object record);
        public CharSequence getBeforeText(Object record);
        public void setBeforeText(Object record, CharSequence beforeText);
        public CharSequence getContentDescription(Object record);
        public void setContentDescription(Object record, CharSequence contentDescription);
        public Parcelable getParcelableData(Object record);
        public void setParcelableData(Object record, Parcelable parcelableData);
        public void recycle(Object record);
    }

    static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
        public Object obtain() {
            return null;
        }

        public Object obtain(Object record) {
            return null;
        }

        public int getAddedCount(Object record) {
            return 0;
        }

        public CharSequence getBeforeText(Object record) {
            return null;
        }

        public CharSequence getClassName(Object record) {
            return null;
        }

        public CharSequence getContentDescription(Object record) {
            return null;
        }

        public int getCurrentItemIndex(Object record) {
            return 0;
        }

        public int getFromIndex(Object record) {
            return 0;
        }

        public int getItemCount(Object record) {
            return 0;
        }

        public int getMaxScrollX(Object record) {
            return 0;
        }

        public int getMaxScrollY(Object record) {
            return 0;
        }

        public Parcelable getParcelableData(Object record) {
            return null;
        }

        public int getRemovedCount(Object record) {
            return 0;
        }

        public int getScrollX(Object record) {
            return 0;
        }

        public int getScrollY(Object record) {
            return 0;
        }

        public Object getSource(Object record) {
            return null;
        }

        public List<CharSequence> getText(Object record) {
            return Collections.emptyList();
        }

        public int getToIndex(Object record) {
            return 0;
        }

        public int getWindowId(Object record) {
            return 0;
        }

        public boolean isChecked(Object record) {
            return false;
        }

        public boolean isEnabled(Object record) {
            return false;
        }

        public boolean isFullScreen(Object record) {
            return false;
        }

        public boolean isPassword(Object record) {
            return false;
        }

        public boolean isScrollable(Object record) {
            return false;
        }

        public void recycle(Object record) {

        }

        public void setAddedCount(Object record, int addedCount) {

        }

        public void setBeforeText(Object record, CharSequence beforeText) {

        }

        public void setChecked(Object record, boolean isChecked) {

        }

        public void setClassName(Object record, CharSequence className) {

        }

        public void setContentDescription(Object record, CharSequence contentDescription) {

        }

        public void setCurrentItemIndex(Object record, int currentItemIndex) {

        }

        public void setEnabled(Object record, boolean isEnabled) {

        }

        public void setFromIndex(Object record, int fromIndex) {

        }

        public void setFullScreen(Object record, boolean isFullScreen) {

        }

        public void setItemCount(Object record, int itemCount) {

        }

        public void setMaxScrollX(Object record, int maxScrollX) {

        }

        public void setMaxScrollY(Object record, int maxScrollY) {

        }

        public void setParcelableData(Object record, Parcelable parcelableData) {

        }

        public void setPassword(Object record, boolean isPassword) {

        }

        public void setRemovedCount(Object record, int removedCount) {

        }

        public void setScrollX(Object record, int scrollX) {

        }

        public void setScrollY(Object record, int scrollY) {

        }

        public void setScrollable(Object record, boolean scrollable) {

        }

        public void setSource(Object record, View source) {

        }

        public void setToIndex(Object record, int toIndex) {

        }
    }

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new AccessibilityRecordIcsImpl();
        //} else {
            IMPL = new AccessibilityRecordStubImpl();
        //}
    }

    private static final AccessibilityRecordImpl IMPL;

    private final Object mRecord;

    
    public AccessibilityRecordCompat(Object record) {
        mRecord = record;
    }

    
    public Object getImpl() {
        return mRecord;
    }

    
    public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat record) {
       return new AccessibilityRecordCompat(IMPL.obtain(record.mRecord));
    }

    
    public static AccessibilityRecordCompat obtain() {
        return new AccessibilityRecordCompat(IMPL.obtain());
    }

    
    public void setSource(View source) {
        IMPL.setSource(mRecord, source);
    }

    
    public AccessibilityNodeInfoCompat getSource() {
        return new AccessibilityNodeInfoCompat(IMPL.getSource(mRecord));
    }

    
    public int getWindowId() {
        return IMPL.getWindowId(mRecord);
    }

    
    public boolean isChecked() {
        return IMPL.isChecked(mRecord);
    }

    
    public void setChecked(boolean isChecked) {
        IMPL.setChecked(mRecord, isChecked);
    }

    
    public boolean isEnabled() {
        return IMPL.isEnabled(mRecord);
    }

    
    public void setEnabled(boolean isEnabled) {
        IMPL.setEnabled(mRecord, isEnabled);
    }

    
    public boolean isPassword() {
        return IMPL.isPassword(mRecord);
    }

    
    public void setPassword(boolean isPassword) {
        IMPL.setPassword(mRecord, isPassword);
    }

    
    public boolean isFullScreen() {
        return IMPL.isFullScreen(mRecord);
    }

    
    public void setFullScreen(boolean isFullScreen) {
        IMPL.setFullScreen(mRecord, isFullScreen);
    }

    
    public boolean isScrollable() {
        return IMPL.isScrollable(mRecord);
    }

    
    public void setScrollable(boolean scrollable) {
        IMPL.setScrollable(mRecord, scrollable);
    }

    
    public int getItemCount() {
        return IMPL.getItemCount(mRecord);
    }

    
    public void setItemCount(int itemCount) {
        IMPL.setItemCount(mRecord, itemCount);
    }

    
    public int getCurrentItemIndex() {
        return IMPL.getCurrentItemIndex(mRecord);
    }

    
    public void setCurrentItemIndex(int currentItemIndex) {
        IMPL.setCurrentItemIndex(mRecord, currentItemIndex);
    }

    
    public int getFromIndex() {
        return IMPL.getFromIndex(mRecord);
    }

    
    public void setFromIndex(int fromIndex) {
        IMPL.setFromIndex(mRecord, fromIndex);
    }

    
    public int getToIndex() {
        return IMPL.getToIndex(mRecord);
    }

    
    public void setToIndex(int toIndex) {
        IMPL.setToIndex(mRecord, toIndex);
    }

    
    public int getScrollX() {
        return IMPL.getScrollX(mRecord);
    }

    
    public void setScrollX(int scrollX) {
        IMPL.setScrollX(mRecord, scrollX);
    }

    
    public int getScrollY() {
        return IMPL.getScrollY(mRecord);
    }

    
    public void setScrollY(int scrollY) {
        IMPL.setScrollY(mRecord, scrollY);
    }

//  TODO: Uncomment when these APIs become public
//    
//    public int getMaxScrollX() {
//        return IMPL.getMaxScrollX(mRecord);
//    }
//    
//    public void setMaxScrollX(int maxScrollX) {
//        IMPL.setMaxScrollX(mRecord, maxScrollX);
//    }
//
//    
//    public int getMaxScrollY() {
//        return IMPL.getMaxScrollY(mRecord);
//    }
//
//    
//    public void setMaxScrollY(int maxScrollY) {
//        IMPL.setMaxScrollY(mRecord, maxScrollY);
//    }

    
    public int getAddedCount() {
        return IMPL.getAddedCount(mRecord);
    }

    
    public void setAddedCount(int addedCount) {
        IMPL.setAddedCount(mRecord, addedCount);
    }

    
    public int getRemovedCount() {
        return IMPL.getRemovedCount(mRecord);
    }

    
    public void setRemovedCount(int removedCount) {
        IMPL.setRemovedCount(mRecord, removedCount);
    }

    
    public CharSequence getClassName() {
        return IMPL.getClassName(mRecord);
    }

    
    public void setClassName(CharSequence className) {
        IMPL.setClassName(mRecord, className);
    }

    
    public List<CharSequence> getText() {
        return IMPL.getText(mRecord);
    }

    
    public CharSequence getBeforeText() {
        return IMPL.getBeforeText(mRecord);
    }

    
    public void setBeforeText(CharSequence beforeText) {
        IMPL.setBeforeText(mRecord, beforeText);
    }

    
    public CharSequence getContentDescription() {
        return IMPL.getContentDescription(mRecord);
    }

    
    public void setContentDescription(CharSequence contentDescription) {
        IMPL.setContentDescription(mRecord, contentDescription);
    }

    
    public Parcelable getParcelableData() {
        return IMPL.getParcelableData(mRecord);
    }

    
    public void setParcelableData(Parcelable parcelableData) {
        IMPL.setParcelableData(mRecord, parcelableData);
    }

    
    public void recycle() {
        IMPL.recycle(mRecord);
    }

    @Override
    public int hashCode() {
        return (mRecord == null) ? 0 : mRecord.hashCode();
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
        AccessibilityRecordCompat other = (AccessibilityRecordCompat) obj;
        if (mRecord == null) {
            if (other.mRecord != null) {
                return false;
            }
        } else if (!mRecord.equals(other.mRecord)) {
            return false;
        }
        return true;
    }
}

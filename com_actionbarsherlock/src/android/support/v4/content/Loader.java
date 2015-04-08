

package android.support.v4.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.util.DebugUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;


public class Loader<D> {
    int mId;
    OnLoadCompleteListener<D> mListener;
    Context mContext;
    boolean mStarted = false;
    boolean mAbandoned = false;
    boolean mReset = true;
    boolean mContentChanged = false;

    
    public final class ForceLoadContentObserver extends ContentObserver {
        public ForceLoadContentObserver() {
            super(new Handler());
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            onContentChanged();
        }
    }

    
    public interface OnLoadCompleteListener<D> {
        
        public void onLoadComplete(Loader<D> loader, D data);
    }

    
    public Loader(Context context) {
        mContext = context.getApplicationContext();
    }

    
    public void deliverResult(D data) {
        if (mListener != null) {
            mListener.onLoadComplete(this, data);
        }
    }

    
    public Context getContext() {
        return mContext;
    }

    
    public int getId() {
        return mId;
    }

    
    public void registerListener(int id, OnLoadCompleteListener<D> listener) {
        if (mListener != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        mListener = listener;
        mId = id;
    }

    
    public void unregisterListener(OnLoadCompleteListener<D> listener) {
        if (mListener == null) {
            throw new IllegalStateException("No listener register");
        }
        if (mListener != listener) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        mListener = null;
    }

    
    public boolean isStarted() {
        return mStarted;
    }

    
    public boolean isAbandoned() {
        return mAbandoned;
    }

    
    public boolean isReset() {
        return mReset;
    }

    
    public final void startLoading() {
        mStarted = true;
        mReset = false;
        mAbandoned = false;
        onStartLoading();
    }

    
    protected void onStartLoading() {
    }

    
    public void forceLoad() {
        onForceLoad();
    }

    
    protected void onForceLoad() {
    }

    
    public void stopLoading() {
        mStarted = false;
        onStopLoading();
    }

    
    protected void onStopLoading() {
    }

    
    public void abandon() {
        mAbandoned = true;
        onAbandon();
    }

    
    protected void onAbandon() {
    }

    
    public void reset() {
        onReset();
        mReset = true;
        mStarted = false;
        mAbandoned = false;
        mContentChanged = false;
    }

    
    protected void onReset() {
    }

    
    public boolean takeContentChanged() {
        boolean res = mContentChanged;
        mContentChanged = false;
        return res;
    }

    
    public void onContentChanged() {
        if (mStarted) {
            forceLoad();
        } else {
            // This loader has been stopped, so we don't want to load
            // new data right now...  but keep track of it changing to
            // refresh later if we start again.
            mContentChanged = true;
        }
    }

    
    public String dataToString(D data) {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(data, sb);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(this, sb);
        sb.append(" id=");
        sb.append(mId);
        sb.append("}");
        return sb.toString();
    }

    
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.print(prefix); writer.print("mId="); writer.print(mId);
                writer.print(" mListener="); writer.println(mListener);
        writer.print(prefix); writer.print("mStarted="); writer.print(mStarted);
                writer.print(" mContentChanged="); writer.print(mContentChanged);
                writer.print(" mAbandoned="); writer.print(mAbandoned);
                writer.print(" mReset="); writer.println(mReset);
    }
}
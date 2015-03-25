

package android.support.v4.view;

import com.actionbarsherlock.internal.view.menu.MenuInflaterImpl;

import android.view.View;


public abstract class ActionMode {
    
    public interface Callback {
        
        boolean onActionItemClicked(ActionMode mode, MenuItem item);

        
        boolean onCreateActionMode(ActionMode mode, Menu menu);

        
        void onDestroyActionMode(ActionMode mode);

        
        boolean onPrepareActionMode(ActionMode mode, Menu menu);
    }

    
    public abstract void finish();

    
    public abstract View getCustomView();

    
    public abstract Menu getMenu();

    
    public abstract MenuInflaterImpl getMenuInflater();

    
    public abstract CharSequence getSubtitle();

    
    public abstract CharSequence getTitle();

    
    public abstract void invalidate();

    
    public abstract void setCustomView(View view);

    
    public abstract void setSubtitle(int resId);

    
    public abstract void setSubtitle(CharSequence subtitle);

    
    public abstract void setTitle(int resId);

    
    public abstract void setTitle(CharSequence title);
}

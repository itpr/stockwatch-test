

package android.support.v4.widget;

import android.content.Context;
import android.os.Build;
import android.view.View;


public class SearchViewCompat {

    interface SearchViewCompatImpl {
        View newSearchView(Context context);
        Object newOnQueryTextListener(OnQueryTextListenerCompat listener);
        void setOnQueryTextListener(Object searchView, Object listener);
    }

    static class SearchViewCompatStubImpl implements SearchViewCompatImpl {

        @Override
        public View newSearchView(Context context) {
            return null;
        }

        @Override
        public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
            return null;
        }

        @Override
        public void setOnQueryTextListener(Object searchView, Object listener) {

        }
    }

    static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {

        @Override
        public View newSearchView(Context context) {
            return SearchViewCompatHoneycomb.newSearchView(context);
        }

        @Override
        public Object newOnQueryTextListener(final OnQueryTextListenerCompat listener) {
            return SearchViewCompatHoneycomb.newOnQueryTextListener(
                    new SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return listener.onQueryTextSubmit(query);
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return listener.onQueryTextChange(newText);
                        }
                    });
        }

        @Override
        public void setOnQueryTextListener(Object searchView, Object listener) {
            SearchViewCompatHoneycomb.setOnQueryTextListener(searchView, listener);
        }
    }

    private static final SearchViewCompatImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 11) { // Honeycomb
            IMPL = new SearchViewCompatHoneycombImpl();
        } else {
            IMPL = new SearchViewCompatStubImpl();
        }
    }

    private SearchViewCompat(Context context) {
        
    }

    
    public static View newSearchView(Context context) {
        return IMPL.newSearchView(context);
    }

    
    public static void setOnQueryTextListener(View searchView, OnQueryTextListenerCompat listener) {
        IMPL.setOnQueryTextListener(searchView, listener.mListener);
    }

    
    public static abstract class OnQueryTextListenerCompat {
        final Object mListener;

        public OnQueryTextListenerCompat() {
            mListener = IMPL.newOnQueryTextListener(this);
        }

        
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }
}

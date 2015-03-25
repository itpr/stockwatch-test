

package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ActionMode;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.Window;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.actionbarsherlock.R;
import com.actionbarsherlock.internal.app.ActionBarImpl;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuInflaterImpl;
import com.actionbarsherlock.internal.view.menu.MenuInflaterWrapper;
import com.actionbarsherlock.internal.view.menu.MenuItemImpl;
import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.internal.view.menu.MenuPresenter;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.internal.widget.ActionBarView;


public class FragmentActivity extends Activity implements SupportActivity {
    private static final String TAG = "FragmentActivity";
    private static final boolean DEBUG = false;

    private static final String FRAGMENTS_TAG = "android:support:fragments";

    static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;

    final SupportActivity.InternalCallbacks mInternalCallbacks = new SupportActivity.InternalCallbacks() {
        @Override
        void invalidateSupportFragmentIndex(int index) {
            FragmentActivity.this.invalidateSupportFragmentIndex(index);
        }

        @Override
        LoaderManagerImpl getLoaderManager(int index, boolean started, boolean create) {
            return FragmentActivity.this.getLoaderManager(index, started, create);
        }

        @Override
        Handler getHandler() {
            return mHandler;
        }

        @Override
        FragmentManagerImpl getFragments() {
            return mFragments;
        }

        @Override
        boolean getRetaining() {
            return mRetaining;
        }
    };

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REALLY_STOPPED:
                    if (mStopped) {
                        doReallyStop(false);
                    }
                    break;
                case MSG_RESUME_PENDING:
                    mFragments.dispatchResume();
                    mFragments.execPendingActions();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    final FragmentManagerImpl mFragments = new FragmentManagerImpl();

    ViewGroup mDecor;
    ViewGroup mContentParent;
    ActionBar mActionBar;
    ActionBarView mActionBarView;
    long mWindowFlags = 0;

    android.view.MenuInflater mMenuInflater;

    MenuBuilder mSupportMenu;
    final MenuBuilder.Callback mSupportMenuCallback = new MenuBuilder.Callback() {
        @Override
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return FragmentActivity.this.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, item);
        }

        @Override
        public void onMenuModeChange(MenuBuilder menu) {
            // No-op
        }
    };
    private final MenuPresenter.Callback mMenuPresenterCallback = new MenuPresenter.Callback() {
        @Override
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }

        @Override
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }
    };

    
    private HashMap<android.view.MenuItem, MenuItemImpl> mNativeItemMap;
    
    private final android.view.MenuItem.OnMenuItemClickListener mNativeItemListener = new android.view.MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(android.view.MenuItem item) {
            if (DEBUG) Log.d(TAG, "[mNativeItemListener.onMenuItemClick] item: " + item);

            final MenuItemImpl sherlockItem = mNativeItemMap.get(item);
            if (sherlockItem != null) {
                sherlockItem.invoke();
            } else {
                Log.e(TAG, "Options item \"" + item + "\" not found in mapping");
            }

            return true; //Do not allow continuation of native handling
        }
    };

    boolean mCreated;
    boolean mResumed;
    boolean mStopped;
    boolean mReallyStopped;
    boolean mRetaining;

    boolean mOptionsMenuInvalidated;
    boolean mOptionsMenuCreateResult;

    boolean mCheckedForLoaderManager;
    boolean mLoadersStarted;
    HCSparseArray<LoaderManagerImpl> mAllLoaderManagers;
    LoaderManagerImpl mLoaderManager;

    static final class NonConfigurationInstances {
        Object activity;
        Object custom;
        HashMap<String, Object> children;
        ArrayList<Fragment> fragments;
        HCSparseArray<LoaderManagerImpl> loaders;
    }

    static class FragmentTag {
        public static final int[] Fragment = {
            0x01010003, 0x010100d0, 0x010100d1
        };
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;
    }





    @Override
    public SupportActivity.InternalCallbacks getInternalCallbacks() {
        return mInternalCallbacks;
    }

    @Override
    public Activity asActivity() {
        return this;
    }

    private void initActionBar() {
        if (DEBUG) Log.d(TAG, "[initActionBar]");

        // Initializing the window decor can change window feature flags.
        // Make sure that we have the correct set before performing the test below.
        if (mDecor == null) {
            installDecor();
        }

        if ((mActionBar != null) || !hasFeature(Window.FEATURE_ACTION_BAR) || isChild()) {
            return;
        }

        if (IS_HONEYCOMB) {
            mActionBar = ActionBarWrapper.createFor(this);
        } else {
            mActionBar = new ActionBarImpl(this);
        }
    }

    private void installDecor() {
        if (DEBUG) Log.d(TAG, "[installDecor]");

        if (mDecor == null) {
            if (IS_HONEYCOMB) {
                mDecor = (ViewGroup)getWindow().getDecorView();
            } else {
                mDecor = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);
            }
        }
        if (mContentParent == null) {
            if (IS_HONEYCOMB) {
                mContentParent = (ViewGroup)mDecor.findViewById(android.R.id.content);
            } else {
                mContentParent = generateLayout();
                mActionBarView = (ActionBarView)mDecor.findViewById(R.id.abs__action_bar);
                if (mActionBarView != null) {
                    if (mActionBarView.getTitle() == null) {
                        mActionBarView.setTitle(getTitle());
                    }
                    if (hasFeature(Window.FEATURE_INDETERMINATE_PROGRESS)) {
                        mActionBarView.initIndeterminateProgress();
                    }
                }

                // Post the panel invalidate for later; avoid application onCreateOptionsMenu
                // being called in the middle of onCreate or similar.
                mDecor.post(new Runnable() {
                    @Override
                    public void run() {
                        //Invalidate if the panel menu hasn't been created before this.
                        if (mSupportMenu == null) {
                            invalidateOptionsMenu();
                        }
                    }
                });
            }
        }
    }

    private ViewGroup generateLayout() {
        if (DEBUG) Log.d(TAG, "[generateLayout]");

        // Apply data from current theme.

        TypedArray a = getTheme().obtainStyledAttributes(R.styleable.SherlockTheme);

        if (a.getBoolean(R.styleable.SherlockTheme_windowNoTitle, false)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } else if (a.getBoolean(R.styleable.SherlockTheme_windowActionBar, false)) {
            // Don't allow an action bar if there is no title.
            requestWindowFeature(Window.FEATURE_ACTION_BAR);
        }

        if (a.getBoolean(R.styleable.SherlockTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        }

        if (a.getBoolean(R.styleable.SherlockTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        }

        a.recycle();

        int layoutResource;
        if (hasFeature(Window.FEATURE_ACTION_BAR)) {
            if (hasFeature(Window.FEATURE_ACTION_BAR_OVERLAY)) {
                layoutResource = R.layout.abs__screen_action_bar_overlay;
            } else {
                layoutResource = R.layout.abs__screen_action_bar;
            }
        //} else if (hasFeature(Window.FEATURE_ACTION_MODE_OVERLAY)) {
        //    layoutResource = R.layout.abs__screen_simple_overlay_action_mode;
        } else {
            layoutResource = R.layout.abs__screen_simple;
        }

        View in = getLayoutInflater().inflate(layoutResource, null);
        mDecor.addView(in, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup contentParent = (ViewGroup)mDecor.findViewById(R.id.abs__content);
        if (contentParent == null) {
            throw new RuntimeException("Couldn't find content container view");
        }

        //Make our new child the true content view (for fragments). VERY VOLATILE!
        mDecor.setId(View.NO_ID);
        contentParent.setId(android.R.id.content);

        return contentParent;
    }

    private boolean hasFeature(long featureId) {
        if (IS_HONEYCOMB) {
            return HoneycombHasFeature.invoke(getWindow(), (int)featureId);
        }
        return (mWindowFlags & (1 << featureId)) != 0;
    }

    private static final class HoneycombHasFeature {
        public static boolean invoke(android.view.Window window, int featureId) {
            return window.hasFeature(featureId);
        }
    }

    // ------------------------------------------------------------------------
    // HOOKS INTO ACTIVITY
    // ------------------------------------------------------------------------

    
    @Override
    public boolean requestWindowFeature(long featureId) {
        if (!IS_HONEYCOMB) {
            switch ((int)featureId) {
                case (int)Window.FEATURE_ACTION_BAR:
                case (int)Window.FEATURE_ACTION_BAR_OVERLAY:
                case (int)Window.FEATURE_ACTION_MODE_OVERLAY:
                case (int)Window.FEATURE_INDETERMINATE_PROGRESS:
                    mWindowFlags |= (1 << featureId);
                    return true;
            }
        }
        return super.requestWindowFeature((int)featureId);
    }

    @Override
    public android.view.MenuInflater getMenuInflater() {
        if (DEBUG) Log.d(TAG, "[getMenuInflater]");

        if (mMenuInflater == null) {
            initActionBar();
        }
        if (IS_HONEYCOMB) {
            if (DEBUG) Log.d(TAG, "getMenuInflater(): Wrapping native inflater.");

            //Wrap the native inflater so it can unwrap the native menu first
            mMenuInflater = new MenuInflaterWrapper(this, super.getMenuInflater());
        } else {
            if (DEBUG) Log.d(TAG, "getMenuInflater(): Returning support inflater.");

            //Use our custom menu inflater
            mMenuInflater = new MenuInflaterImpl(this, super.getMenuInflater());
        }

        return mMenuInflater;
    }

    @Override
    public void setContentView(int layoutResId) {
        if (DEBUG) Log.d(TAG, "[setContentView] layoutResId: " + layoutResId);

        if (mContentParent == null) {
            installDecor();
        } else {
            mContentParent.removeAllViews();
        }
        getLayoutInflater().inflate(layoutResId, mContentParent);

        android.view.Window.Callback callback = getWindow().getCallback();
        if (callback != null) {
            callback.onContentChanged();
        }
        initActionBar();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        if (DEBUG) Log.d(TAG, "[setContentView] view: " + view + ", params: " + params);

        if (mContentParent == null) {
            installDecor();
        } else {
            mContentParent.removeAllViews();
        }
        mContentParent.addView(view, params);

        android.view.Window.Callback callback = getWindow().getCallback();
        if (callback != null) {
            callback.onContentChanged();
        }

        initActionBar();
    }

    @Override
    public void setContentView(View view) {
        if (DEBUG) Log.d(TAG, "[setContentView] view: " + view);

        setContentView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (DEBUG) Log.d(TAG, "[addContentView] view: " + view + ", params: " + params);

        if (mContentParent == null) {
            installDecor();
        }
        mContentParent.addView(view, params);

        initActionBar();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (IS_HONEYCOMB || (mActionBar == null)) {
            super.setTitle(title);
        } else {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (IS_HONEYCOMB || (mActionBar == null)) {
            super.setTitle(titleId);
        } else {
            getSupportActionBar().setTitle(titleId);
        }
    }

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int index = requestCode>>16;
        if (index != 0) {
            index--;
            if (mFragments.mActive == null || index < 0 || index >= mFragments.mActive.size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = mFragments.mActive.get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                frag.onActivityResult(requestCode&0xffff, resultCode, data);
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    
    @Override
    public void onBackPressed() {
        if (!mFragments.popBackStackImmediate()) {
            finish();
        }
    }

    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFragments.dispatchConfigurationChanged(newConfig);
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragments.attachActivity(this);
        // Old versions of the platform didn't do this!
        if (getLayoutInflater().getFactory() == null) {
            getLayoutInflater().setFactory(this);
        }

        super.onCreate(savedInstanceState);

        NonConfigurationInstances nc = (NonConfigurationInstances)
                getLastNonConfigurationInstance();
        if (nc != null) {
            mAllLoaderManagers = nc.loaders;
        }
        if (savedInstanceState != null) {
            Parcelable p = savedInstanceState.getParcelable(FRAGMENTS_TAG);
            mFragments.restoreAllState(p, nc != null ? nc.fragments : null);
        }
        mFragments.dispatchCreate();
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (DEBUG) Log.d(TAG, "onCreateOptionsMenu(Menu): Returning true");
        return true;
    }

    @Override
    public final boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Prior to Honeycomb, the framework can't invalidate the options
        // menu, so we must always say we have one in case the app later
        // invalidates it and needs to have it shown.
        boolean result = true;

        if (IS_HONEYCOMB) {
            if (DEBUG) Log.d(TAG, "onCreateOptionsMenu(android.view.Menu): Calling support method with wrapped native menu.");
            MenuWrapper wrapped = new MenuWrapper(menu);
            result  = onCreateOptionsMenu(wrapped);
            result |= mFragments.dispatchCreateOptionsMenu(wrapped, getMenuInflater());
        }

        if (DEBUG) Log.d(TAG, "onCreateOptionsMenu(android.view.Menu): Returning " + result);
        return result;
    }

    private boolean dispatchCreateOptionsMenu() {
        if (DEBUG) Log.d(TAG, "[dispatchCreateOptionsMenu]");

        boolean result = onCreateOptionsMenu(mSupportMenu);
        result |= mFragments.dispatchCreateOptionsMenu(mSupportMenu, getMenuInflater());
        return result;
    }

    
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (!"fragment".equals(name)) {
            return super.onCreateView(name, context, attrs);
        }

        String fname = attrs.getAttributeValue(null, "class");
        TypedArray a =  context.obtainStyledAttributes(attrs, FragmentTag.Fragment);
        if (fname == null) {
            fname = a.getString(FragmentTag.Fragment_name);
        }
        int id = a.getResourceId(FragmentTag.Fragment_id, View.NO_ID);
        String tag = a.getString(FragmentTag.Fragment_tag);
        a.recycle();

        View parent = null; // NOTE: no way to get parent pre-Honeycomb.
        int containerId = parent != null ? parent.getId() : 0;
        if (containerId == View.NO_ID && id == View.NO_ID && tag == null) {
            throw new IllegalArgumentException(attrs.getPositionDescription()
                    + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
        }

        // If we restored from a previous state, we may already have
        // instantiated this fragment from the state and should use
        // that instance instead of making a new one.
        Fragment fragment = id != View.NO_ID ? mFragments.findFragmentById(id) : null;
        if (fragment == null && tag != null) {
            fragment = mFragments.findFragmentByTag(tag);
        }
        if (fragment == null && containerId != View.NO_ID) {
            fragment = mFragments.findFragmentById(containerId);
        }

        if (FragmentManagerImpl.DEBUG) Log.v(TAG, "onCreateView: id=0x"
                + Integer.toHexString(id) + " fname=" + fname
                + " existing=" + fragment);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fname);
            fragment.mFromLayout = true;
            fragment.mFragmentId = id != 0 ? id : containerId;
            fragment.mContainerId = containerId;
            fragment.mTag = tag;
            fragment.mInLayout = true;
            fragment.mFragmentManager = mFragments;
            fragment.onInflate(this, attrs, fragment.mSavedFragmentState);
            mFragments.addFragment(fragment, true);

        } else if (fragment.mInLayout) {
            // A fragment already exists and it is not one we restored from
            // previous state.
            throw new IllegalArgumentException(attrs.getPositionDescription()
                    + ": Duplicate id 0x" + Integer.toHexString(id)
                    + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId)
                    + " with another fragment for " + fname);
        } else {
            // This fragment was retained from a previous instance; get it
            // going now.
            fragment.mInLayout = true;
            // If this fragment is newly instantiated (either right now, or
            // from last saved state), then give it the attributes to
            // initialize itself.
            if (!fragment.mRetaining) {
                fragment.onInflate(this, attrs, fragment.mSavedFragmentState);
            }
            mFragments.moveToState(fragment);
        }

        if (fragment.mView == null) {
            throw new IllegalStateException("Fragment " + fname
                    + " did not create a view.");
        }
        if (id != 0) {
            fragment.mView.setId(id);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(tag);
        }
        return fragment.mView;
    }

    @Override
    public void invalidateOptionsMenu() {
        if (DEBUG) Log.d(TAG, "[invalidateOptionsMenu]");

        if (IS_HONEYCOMB) {
            HoneycombInvalidateOptionsMenu.invoke(this);
            return;
        }

        if (mSupportMenu == null) {
            mSupportMenu = new MenuBuilder(this);
            mSupportMenu.setCallback(mSupportMenuCallback);
        }

        mSupportMenu.stopDispatchingItemsChanged();
        mSupportMenu.clear();

        if (!dispatchCreateOptionsMenu()) {
            if (mActionBar != null) {
                ((ActionBarImpl)mActionBar).setMenu(null, mMenuPresenterCallback);
            }
            return;
        }

        if (!dispatchPrepareOptionsMenu()) {
            if (mActionBar != null) {
                ((ActionBarImpl)mActionBar).setMenu(null, mMenuPresenterCallback);
            }
            mSupportMenu.startDispatchingItemsChanged();
            return;
        }

        mSupportMenu.startDispatchingItemsChanged();

        if (mActionBar != null) {
            ((ActionBarImpl)mActionBar).setMenu(mSupportMenu, mMenuPresenterCallback);
        }
    }

    private static final class HoneycombInvalidateOptionsMenu {
        static void invoke(Activity activity) {
            activity.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
        }
    }

    
    @Override
    protected void onDestroy() {
        super.onDestroy();

        doReallyStop(false);

        mFragments.dispatchDestroy();
        if (mLoaderManager != null) {
            mLoaderManager.doDestroy();
        }
    }

    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < 5 
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mFragments.dispatchLowMemory();
    }

    
    @Override
    public final boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
        if (super.onMenuItemSelected(featureId, item)) {
            return true;
        }

        switch (featureId) {
            case Window.FEATURE_OPTIONS_PANEL:
                return mFragments.dispatchOptionsItemSelected(new MenuItemWrapper(item));

            case Window.FEATURE_CONTEXT_MENU:
                return mFragments.dispatchContextItemSelected(new MenuItemWrapper(item));

            default:
                return false;
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (onOptionsItemSelected(item)) {
            return true;
        }

        switch (featureId) {
            case Window.FEATURE_OPTIONS_PANEL:
                return mFragments.dispatchOptionsItemSelected(item);

            case Window.FEATURE_CONTEXT_MENU:
                return mFragments.dispatchContextItemSelected(item);

            default:
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public final boolean onOptionsItemSelected(android.view.MenuItem item) {
        return onOptionsItemSelected(new MenuItemWrapper(item));
    }

    
    @Override
    public void onPanelClosed(int featureId, android.view.Menu menu) {
        switch (featureId) {
            case Window.FEATURE_OPTIONS_PANEL:
                mFragments.dispatchOptionsMenuClosed(new MenuWrapper(menu));

                if (!IS_HONEYCOMB && (getSupportActionBar() != null)) {
                    if (DEBUG) Log.d(TAG, "onPanelClosed(int, android.view.Menu): Dispatch menu visibility false to custom action bar.");
                    ((ActionBarImpl)mActionBar).onMenuVisibilityChanged(false);
                }
                break;
        }
        super.onPanelClosed(featureId, menu);
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        if (mHandler.hasMessages(MSG_RESUME_PENDING)) {
            mHandler.removeMessages(MSG_RESUME_PENDING);
            mFragments.dispatchResume();
        }
        mFragments.dispatchPause();
    }

    
    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_RESUME_PENDING);
        mResumed = true;
        mFragments.execPendingActions();
    }

    
    @Override
    protected void onPostResume() {
        super.onPostResume();
        mHandler.removeMessages(MSG_RESUME_PENDING);
        mFragments.dispatchResume();
        mFragments.execPendingActions();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public final boolean onPrepareOptionsMenu(android.view.Menu menu) {
        if (IS_HONEYCOMB) {
            if (DEBUG) Log.d(TAG, "onPrepareOptionsMenu(android.view.Menu): Calling support method with wrapped native menu.");
            final MenuWrapper wrappedMenu = new MenuWrapper(menu);
            boolean result = onPrepareOptionsMenu(wrappedMenu);
            if (result) {
                if (DEBUG) Log.d(TAG, "onPrepareOptionsMenu(android.view.Menu): Dispatching fragment method with wrapped native menu.");
                mFragments.dispatchPrepareOptionsMenu(wrappedMenu);
            }
            return result;
        }

        if (!dispatchPrepareOptionsMenu()) {
            return false;
        }

        if (mNativeItemMap == null) {
            mNativeItemMap = new HashMap<android.view.MenuItem, MenuItemImpl>();
        } else {
            mNativeItemMap.clear();
        }

        if (mSupportMenu != null) {
            return mSupportMenu.bindOverflowToNative(menu, mNativeItemListener, mNativeItemMap);
        }
        return false;
    }

    private boolean dispatchPrepareOptionsMenu() {
        if (DEBUG) Log.d(TAG, "[dispatchPrepareOptionsMenu]");

        boolean result = onPrepareOptionsMenu(mSupportMenu);
        result |= mFragments.dispatchPrepareOptionsMenu(mSupportMenu);
        return result;
    }

    
    @Override
    public void recreate() {
        //This SUCKS! Figure out a way to call the super method and support Android 1.6
        
            final Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                OverridePendingTransition.invoke(this);
            }

            finish();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                OverridePendingTransition.invoke(this);
            }
        
    }

    private static final class OverridePendingTransition {
        static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }

    
    @Override
    public final Object onRetainNonConfigurationInstance() {
        if (mStopped) {
            doReallyStop(true);
        }

        Object custom = onRetainCustomNonConfigurationInstance();

        ArrayList<Fragment> fragments = mFragments.retainNonConfig();
        boolean retainLoaders = false;
        if (mAllLoaderManagers != null) {
            // prune out any loader managers that were already stopped and so
            // have nothing useful to retain.
            for (int i=mAllLoaderManagers.size()-1; i>=0; i--) {
                LoaderManagerImpl lm = mAllLoaderManagers.valueAt(i);
                if (lm.mRetaining) {
                    retainLoaders = true;
                } else {
                    lm.doDestroy();
                    mAllLoaderManagers.removeAt(i);
                }
            }
        }
        if (fragments == null && !retainLoaders && custom == null) {
            return null;
        }

        NonConfigurationInstances nci = new NonConfigurationInstances();
        nci.activity = null;
        nci.custom = custom;
        nci.children = null;
        nci.fragments = fragments;
        nci.loaders = mAllLoaderManagers;
        return nci;
    }

    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable p = mFragments.saveAllState();
        if (p != null) {
            outState.putParcelable(FRAGMENTS_TAG, p);
        }
    }

    
    @Override
    protected void onStart() {
        super.onStart();

        mStopped = false;
        mReallyStopped = false;
        mHandler.removeMessages(MSG_REALLY_STOPPED);

        if (!mCreated) {
            mCreated = true;
            mFragments.dispatchActivityCreated();
        }

        mFragments.noteStateNotSaved();
        mFragments.execPendingActions();

        if (!mLoadersStarted) {
            mLoadersStarted = true;
            if (mLoaderManager != null) {
                mLoaderManager.doStart();
            } else if (!mCheckedForLoaderManager) {
                mLoaderManager = getLoaderManager(-1, mLoadersStarted, false);
            }
            mCheckedForLoaderManager = true;
        }
        // NOTE: HC onStart goes here.

        mFragments.dispatchStart();
        if (mAllLoaderManagers != null) {
            for (int i=mAllLoaderManagers.size()-1; i>=0; i--) {
                LoaderManagerImpl lm = mAllLoaderManagers.valueAt(i);
                lm.finishRetain();
                lm.doReportStart();
            }
        }
    }

    
    @Override
    protected void onStop() {
        super.onStop();

        mStopped = true;
        mHandler.sendEmptyMessage(MSG_REALLY_STOPPED);

        mFragments.dispatchStop();
    }

    
    @Override
    public void setProgressBarIndeterminateVisibility(Boolean visible) {
        if (IS_HONEYCOMB || (mActionBar == null)) {
            super.setProgressBarIndeterminateVisibility(visible);
        } else {
            mActionBarView.setProgressBarIndeterminateVisibility(visible);
        }
    }

    // ------------------------------------------------------------------------
    // NEW METHODS
    // ------------------------------------------------------------------------

    
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    
    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nc = (NonConfigurationInstances)
                getLastNonConfigurationInstance();
        return nc != null ? nc.custom : null;
    }

    
    @Deprecated
    void supportInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }

    
    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        if (IS_HONEYCOMB) {
            //This can only work if we can call the super-class impl. :/
            //ActivityCompatHoneycomb.dump(this, prefix, fd, writer, args);
        }
        writer.print(prefix); writer.print("Local FragmentActivity ");
                writer.print(Integer.toHexString(System.identityHashCode(this)));
                writer.println(" State:");
        String innerPrefix = prefix + "  ";
        writer.print(innerPrefix); writer.print("mCreated=");
                writer.print(mCreated); writer.print("mResumed=");
                writer.print(mResumed); writer.print(" mStopped=");
                writer.print(mStopped); writer.print(" mReallyStopped=");
                writer.println(mReallyStopped);
        writer.print(innerPrefix); writer.print("mLoadersStarted=");
                writer.println(mLoadersStarted);
        if (mLoaderManager != null) {
            writer.print(prefix); writer.print("Loader Manager ");
                    writer.print(Integer.toHexString(System.identityHashCode(mLoaderManager)));
                    writer.println(":");
            mLoaderManager.dump(prefix + "  ", fd, writer, args);
        }
        mFragments.dump(prefix, fd, writer, args);
    }

    void doReallyStop(boolean retaining) {
        if (!mReallyStopped) {
            mReallyStopped = true;
            mRetaining = retaining;
            mHandler.removeMessages(MSG_REALLY_STOPPED);
            onReallyStop();
        }
    }

    
    void onReallyStop() {
        if (mLoadersStarted) {
            mLoadersStarted = false;
            if (mLoaderManager != null) {
                if (!mRetaining) {
                    mLoaderManager.doStop();
                } else {
                    mLoaderManager.doRetain();
                }
            }
        }

        mFragments.dispatchReallyStop();
    }

    // ------------------------------------------------------------------------
    // ACTION BAR AND ACTION MODE SUPPORT
    // ------------------------------------------------------------------------

    
    @Override
    public ActionBar getSupportActionBar() {
        initActionBar();
        return mActionBar;
    }

    
    @Override
    public void onActionModeFinished(ActionMode mode) {
    }

    
    @Override
    public void onActionModeStarted(ActionMode mode) {
    }

    
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    
    @Override
    public final ActionMode startActionMode(final ActionMode.Callback callback) {
        //Give the activity override a chance to handle the action mode
        ActionMode actionMode = onWindowStartingActionMode(callback);

        if (actionMode == null) {
            //If the activity did not handle, send to action bar for platform-
            //specific implementation
            actionMode = mActionBar.startActionMode(callback);
        }
        if (actionMode != null) {
            //Send the activity callback that our action mode was started
            onActionModeStarted(actionMode);
        }

        //Return to the caller
        return actionMode;
    }

    // ------------------------------------------------------------------------
    // FRAGMENT SUPPORT
    // ------------------------------------------------------------------------

    
    @Override
    public void onAttachFragment(Fragment fragment) {
    }

    
    @Override
    public FragmentManager getSupportFragmentManager() {
        //PLEASE let no one be dumb enough to call this too soon...
        initActionBar();
        return mFragments;
    }

    
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (requestCode != -1 && (requestCode&0xffff0000) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
        super.startActivityForResult(intent, requestCode);
    }

    
    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent,
            int requestCode) {
        if (requestCode == -1) {
            super.startActivityForResult(intent, -1);
            return;
        }
        if ((requestCode&0xffff0000) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
        super.startActivityForResult(intent, ((fragment.mIndex+1)<<16) + (requestCode&0xffff));
    }

    void invalidateSupportFragmentIndex(int index) {
        //Log.v(TAG, "invalidateFragmentIndex: index=" + index);
        if (mAllLoaderManagers != null) {
            LoaderManagerImpl lm = mAllLoaderManagers.get(index);
            if (lm != null && !lm.mRetaining) {
                lm.doDestroy();
                mAllLoaderManagers.remove(index);
            }
        }
    }

    // ------------------------------------------------------------------------
    // LOADER SUPPORT
    // ------------------------------------------------------------------------

    
    @Override
    public LoaderManager getSupportLoaderManager() {
        if (mLoaderManager != null) {
            return mLoaderManager;
        }
        mCheckedForLoaderManager = true;
        mLoaderManager = getLoaderManager(-1, mLoadersStarted, true);
        return mLoaderManager;
    }

    LoaderManagerImpl getLoaderManager(int index, boolean started, boolean create) {
        if (mAllLoaderManagers == null) {
            mAllLoaderManagers = new HCSparseArray<LoaderManagerImpl>();
        }
        LoaderManagerImpl lm = mAllLoaderManagers.get(index);
        if (lm == null) {
            if (create) {
                lm = new LoaderManagerImpl(this, started);
                mAllLoaderManagers.put(index, lm);
            }
        } else {
            lm.updateActivity(this);
        }
        return lm;
    }
}

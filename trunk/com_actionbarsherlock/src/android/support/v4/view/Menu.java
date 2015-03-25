package android.support.v4.view;

public interface Menu extends android.view.Menu {

    
    static final int USER_MASK = 0x0000ffff;
    
    static final int USER_SHIFT = 0;

    
    static final int CATEGORY_MASK = 0xffff0000;
    
    static final int CATEGORY_SHIFT = 16;

    
    static final int NONE = 0;

    
    static final int FIRST = 1;

    // Implementation note: Keep these CATEGORY_* in sync with the category enum
    // in attrs.xml

    
    static final int CATEGORY_CONTAINER = 0x00010000;

    
    static final int CATEGORY_SYSTEM = 0x00020000;

    
    static final int CATEGORY_SECONDARY = 0x00030000;

    
    static final int CATEGORY_ALTERNATIVE = 0x00040000;

    
    static final int FLAG_APPEND_TO_GROUP = 0x0001;


    @Override
    MenuItem add(CharSequence title);

    @Override
    MenuItem add(int groupId, int itemId, int order, int titleRes);

    @Override
    MenuItem add(int titleRes);

    @Override
    MenuItem add(int groupId, int itemId, int order, CharSequence title);

    @Override
    SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title);

    @Override
    SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes);

    @Override
    SubMenu addSubMenu(CharSequence title);

    @Override
    SubMenu addSubMenu(int titleRes);

    @Override
    MenuItem findItem(int id);

    @Override
    MenuItem getItem(int index);
}

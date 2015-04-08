

package android.support.v4.view;

import android.view.MotionEvent;


public class MotionEventCompat {
    
    interface MotionEventVersionImpl {
        public int findPointerIndex(MotionEvent event, int pointerId);
        public int getPointerId(MotionEvent event, int pointerIndex);
        public float getX(MotionEvent event, int pointerIndex);
        public float getY(MotionEvent event, int pointerIndex);
    }

    
    static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
        @Override
        public int findPointerIndex(MotionEvent event, int pointerId) {
            if (pointerId == 0) {
                // id 0 == index 0 and vice versa.
                return 0;
            }
            return -1;
        }
        @Override
        public int getPointerId(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                // index 0 == id 0 and vice versa.
                return 0;
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }
        @Override
        public float getX(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                return event.getX();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }
        @Override
        public float getY(MotionEvent event, int pointerIndex) {
            if (pointerIndex == 0) {
                return event.getY();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }
    }

    
    static class EclairMotionEventVersionImpl implements MotionEventVersionImpl {
        @Override
        public int findPointerIndex(MotionEvent event, int pointerId) {
            return MotionEventCompatEclair.findPointerIndex(event, pointerId);
        }
        @Override
        public int getPointerId(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getPointerId(event, pointerIndex);
        }
        @Override
        public float getX(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getX(event, pointerIndex);
        }
        @Override
        public float getY(MotionEvent event, int pointerIndex) {
            return MotionEventCompatEclair.getY(event, pointerIndex);
        }
    }

    
    static final MotionEventVersionImpl IMPL;
    static {
        if (android.os.Build.VERSION.SDK_INT >= 5) {
            IMPL = new EclairMotionEventVersionImpl();
        } else {
            IMPL = new BaseMotionEventVersionImpl();
        }
    }

    // -------------------------------------------------------------------

    
    public static final int ACTION_MASK = 0xff;

    
    public static final int ACTION_POINTER_DOWN = 5;

    
    public static final int ACTION_POINTER_UP = 6;

    
    public static final int ACTION_HOVER_MOVE = 7;

    
    public static final int ACTION_SCROLL = 8;

    
    public static final int ACTION_POINTER_INDEX_MASK  = 0xff00;

    
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;

    
    public static int getActionMasked(MotionEvent event) {
        return event.getAction() & ACTION_MASK;
    }

    
    public static int getActionIndex(MotionEvent event) {
        return (event.getAction() & ACTION_POINTER_INDEX_MASK)
                >> ACTION_POINTER_INDEX_SHIFT;
    }

    
    public static int findPointerIndex(MotionEvent event, int pointerId) {
        return IMPL.findPointerIndex(event, pointerId);
    }

    
    public static int getPointerId(MotionEvent event, int pointerIndex) {
        return IMPL.getPointerId(event, pointerIndex);
    }

    
    public static float getX(MotionEvent event, int pointerIndex) {
        return IMPL.getX(event, pointerIndex);
    }

    
    public static float getY(MotionEvent event, int pointerIndex) {
        return IMPL.getY(event, pointerIndex);
    }
}

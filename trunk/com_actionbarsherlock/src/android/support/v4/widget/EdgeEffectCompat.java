
package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;


public class EdgeEffectCompat {
    private Object mEdgeEffect;

    private static final EdgeEffectImpl IMPL;

    static {
        //if (Build.VERSION.SDK_INT >= 14) { // ICS
        //    IMPL = new EdgeEffectIcsImpl();
        //} else {
            IMPL = new BaseEdgeEffectImpl();
        //}
    }

    interface EdgeEffectImpl {
        public Object newEdgeEffect(Context context);
        public void setSize(Object edgeEffect, int width, int height);
        public boolean isFinished(Object edgeEffect);
        public void finish(Object edgeEffect);
        public boolean onPull(Object edgeEffect, float deltaDistance);
        public boolean onRelease(Object edgeEffect);
        public boolean onAbsorb(Object edgeEffect, int velocity);
        public boolean draw(Object edgeEffect, Canvas canvas);
    }

    
    static class BaseEdgeEffectImpl implements EdgeEffectImpl {
        public Object newEdgeEffect(Context context) {
            return null;
        }

        public void setSize(Object edgeEffect, int width, int height) {
        }

        public boolean isFinished(Object edgeEffect) {
            return true;
        }

        public void finish(Object edgeEffect) {
        }

        public boolean onPull(Object edgeEffect, float deltaDistance) {
            return false;
        }

        public boolean onRelease(Object edgeEffect) {
            return false;
        }

        public boolean onAbsorb(Object edgeEffect, int velocity) {
            return false;
        }

        public boolean draw(Object edgeEffect, Canvas canvas) {
            return false;
        }
    }

    
    public EdgeEffectCompat(Context context) {
        mEdgeEffect = IMPL.newEdgeEffect(context);
    }

    
    public void setSize(int width, int height) {
        IMPL.setSize(mEdgeEffect, width, height);
    }

    
    public boolean isFinished() {
        return IMPL.isFinished(mEdgeEffect);
    }

    
    public void finish() {
        IMPL.finish(mEdgeEffect);
    }

    
    public boolean onPull(float deltaDistance) {
        return IMPL.onPull(mEdgeEffect, deltaDistance);
    }

    
    public boolean onRelease() {
        return IMPL.onRelease(mEdgeEffect);
    }

    
    public boolean onAbsorb(int velocity) {
        return IMPL.onAbsorb(mEdgeEffect, velocity);
    }

    
    public boolean draw(Canvas canvas) {
        return IMPL.draw(mEdgeEffect, canvas);
    }
}

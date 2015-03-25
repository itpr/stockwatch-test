

package android.support.v4.app;

import android.util.AndroidRuntimeException;

final class SuperNotCalledException extends AndroidRuntimeException {
    private static final long serialVersionUID = -5247191382770859874L;

    public SuperNotCalledException(String msg) {
        super(msg);
    }
}

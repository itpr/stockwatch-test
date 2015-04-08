

package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable;

class ParcelableCompatCreatorHoneycombMR2Stub {
    static <T> Parcelable.Creator<T> instantiate(ParcelableCompatCreatorCallbacks<T> callbacks) {
        return new ParcelableCompatCreatorHoneycombMR2<T>(callbacks);
    }
}

class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
    private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

    public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> callbacks) {
        mCallbacks = callbacks;
    }

    public T createFromParcel(Parcel in) {
        return mCallbacks.createFromParcel(in, null);
    }

    public T createFromParcel(Parcel in, ClassLoader loader) {
        return mCallbacks.createFromParcel(in, loader);
    }

    public T[] newArray(int size) {
        return mCallbacks.newArray(size);
    }
}

package com.example.user.sportslover.util;

/**
 * SingletonUtil helper class for lazily initialization.
 */
public abstract class SingletonUtil<T> {
    private T mInstance;
    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}

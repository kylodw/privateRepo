package com.bumptech.glide.samples.sourcecode;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.support.annotation.NonNull;

public class TestLifecycle extends Lifecycle {
    @Override
    public void addObserver(@NonNull LifecycleObserver observer) {

    }

    @Override
    public void removeObserver(@NonNull LifecycleObserver observer) {

    }

    @NonNull
    @Override
    public State getCurrentState() {
        return null;
    }
}

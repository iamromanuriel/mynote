package com.romanuriel.utils.swipe;

import android.app.Activity;
import android.os.Handler;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener, OnActivityTouchListener {

    private static final String TAG = "RecyclerTouchListener";
    final Handler handler = new Handler();

    Activity act;

    List<Integer> unSwipeAbleRows;

    List<Integer> independentViews;

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void getTouchCoordinates(MotionEvent ev) {

    }
}

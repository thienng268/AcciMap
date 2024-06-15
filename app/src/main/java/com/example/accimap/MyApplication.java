package com.example.accimap;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        if (firebaseApp != null) {
            // Firebase đã được khởi tạo và kết nối thành công
            Log.d(TAG, "Firebase đã kết nối thành công.");
        } else {
            // Firebase chưa được khởi tạo hoặc có lỗi xảy ra
            Log.d(TAG, "Firebase chưa kết nối.");
        }
    }
}

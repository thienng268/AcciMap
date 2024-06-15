package com.example.accimap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, pass;
    Button btnLogin;
    TextView signup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Kiểm tra và khởi tạo Firebase nếu chưa được khởi tạo
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
            Log.e("FirebaseInit", "Firebase chưa được khởi tạo");
            Toast.makeText(this, "Chưa khởi tạo", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("FirebaseInit", "Firebase đã được khởi tạo");
            Toast.makeText(this, "Đã khởi tạo", Toast.LENGTH_SHORT).show();
        }

        // Khởi tạo FirebaseAuth
//        mAuth = FirebaseAuth.getInstance();
//        if (mAuth == null) {
//            Log.e("MainActivity", "mAuth is null");
//            Toast.makeText(this, "mAuth is null", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e("MainActivity", "mAuth is NOT null");
//            Toast.makeText(this, "mAuth is NOT null", Toast.LENGTH_SHORT).show();
//        }
        // Thiết lập view và bắt sự kiện click
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
        signup = findViewById(R.id.textViewSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    private void login() {
//        String email1 = email.getText().toString().trim();
//        String pass1 = pass.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email1)) {
//            Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(pass1)) {
//            Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (mAuth == null) {
//            Log.e("MainActivity", "FirebaseAuth instance is null");
//            Toast.makeText(this, "FirebaseAuth initialization failed", Toast.LENGTH_SHORT).show();
//            return; // hoặc thực hiện các hành động phù hợp để xử lý lỗi
//        }
//
//        mAuth.signInWithEmailAndPassword(email1, pass1)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Đăng nhập thành công
//                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            // Đăng nhập thất bại
//                            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//
//                            // Lấy thông tin chi tiết của lỗi (nếu có)
//                            Exception exception = task.getException();
//                            if (exception != null) {
//                                String errorMessage = exception.getMessage();
//                                Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
//                                // Xử lý lỗi dựa trên thông báo errorMessage
//                            }
//                        }
//                    }
//                });
//    }
}

package com.example.accimap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accimap.models.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    ImageButton newAccidentButton;
    FirebaseDatabase database;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(Home.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();

        newAccidentButton = findViewById(R.id.newacci);
        newAccidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog khi bấm vào ImageButton
                showAddAccidentDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.btmmenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    // Xử lý khi nhấn vào mục "Bản đồ"
                    // Đưa ra các hoạt động cần thực hiện khi nhấn vào "Bản đồ"
                    return true;
                } else if (itemId == R.id.crisis) {
                    // Xử lý khi nhấn vào mục "Tình trạng"
                    Intent intent = new Intent(Home.this, Accident.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }

    private void showAddAccidentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm sự cố mới");

        // Inflate layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_accident, null);
        builder.setView(dialogView);

        // Initialize EditTexts
        EditText titleEditText = dialogView.findViewById(R.id.editTextTitle);
        EditText updateTimeEditText = dialogView.findViewById(R.id.editTextUpdateTime);
        EditText distanceEditText = dialogView.findViewById(R.id.editTextDistance);
        EditText statusEditText = dialogView.findViewById(R.id.editTextStatus);

        builder.setPositiveButton("Đăng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleEditText.getText().toString().trim();
                String updateTime = updateTimeEditText.getText().toString().trim();
                String distance = distanceEditText.getText().toString().trim();
                String status = statusEditText.getText().toString().trim();

                // Create a new Report object
                Report report = new Report(title, status, updateTime, distance, "Report003");

                // Push the report to Firebase Realtime Database
                DatabaseReference reportsRef = database.getReference("Report");
                reportsRef.push().setValue(report)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Home.this, "Đã thêm báo cáo thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Home.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

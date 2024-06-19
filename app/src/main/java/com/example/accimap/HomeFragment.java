package com.example.accimap;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accimap.models.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private ImageButton newAccidentButton;
    private FirebaseDatabase database;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseApp.initializeApp(requireContext());
        database = FirebaseDatabase.getInstance();

        newAccidentButton = view.findViewById(R.id.newacci);
        newAccidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAccidentDialog();
            }
        });

        return view;
    }

    private void showAddAccidentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thêm sự cố mới");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_accident, null);
        builder.setView(dialogView);

        EditText titleEditText = dialogView.findViewById(R.id.editTextTitle);
        EditText updateTimeEditText = dialogView.findViewById(R.id.editTextUpdateTime);
        EditText distanceEditText = dialogView.findViewById(R.id.editTextDistance);
        EditText statusEditText = dialogView.findViewById(R.id.editTextStatus);

        builder.setPositiveButton("Đăng", (dialog, which) -> {
            String title = titleEditText.getText().toString().trim();
            String updateTime = updateTimeEditText.getText().toString().trim();
            String distance = distanceEditText.getText().toString().trim();
            String status = statusEditText.getText().toString().trim();

            //Report report = new Report(title, status, updateTime, distance, "Report003");

            //DatabaseReference reportsRef = database.getReference("Report");
            //reportsRef.push().setValue(report)
            //        .addOnSuccessListener(aVoid -> Toast.makeText(requireContext(), "Đã thêm báo cáo thành công", Toast.LENGTH_SHORT).show())
            //        .addOnFailureListener(e -> Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

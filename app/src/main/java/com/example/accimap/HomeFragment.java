package com.example.accimap;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.accimap.models.Report;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private ImageButton newAccidentButton;
    private FirebaseDatabase database;
    private TextView kinhDoTextView, viDoTextView, updateTimeTextView;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

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
        EditText statusEditText = dialogView.findViewById(R.id.editTextStatus);
        EditText injuredEditText = dialogView.findViewById(R.id.editNguoibithuong);
        EditText fatalitiesEditText = dialogView.findViewById(R.id.editNguoichet);

        updateTimeTextView = dialogView.findViewById(R.id.editTextUpdateTime);
        updateTimeTextView.setText(getCurrentDateTime()); // Set current date time

        kinhDoTextView = dialogView.findViewById(R.id.viewKinhDo);
        viDoTextView = dialogView.findViewById(R.id.viewVido);

        // Request location permission if not granted yet
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed to get location
            getLocation();
        }

        builder.setPositiveButton("Đăng", (dialog, which) -> {
            String title = titleEditText.getText().toString().trim();
            String updateTime = updateTimeTextView.getText().toString().trim();
            String status = statusEditText.getText().toString().trim();
            String injured = injuredEditText.getText().toString().trim();
            String fatalities = fatalitiesEditText.getText().toString().trim();
            String latitude = kinhDoTextView.getText().toString().trim();
            String longitude = viDoTextView.getText().toString().trim();

            if (title.isEmpty() || updateTime.isEmpty() || status.isEmpty() || injured.isEmpty() || fatalities.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);
                int injuredCount = Integer.parseInt(injured);
                int fatalitiesCount = Integer.parseInt(fatalities);

                Report report = new Report(title, updateTime, status, injuredCount, fatalitiesCount, lat, lon, null);

                DatabaseReference reportsRef = database.getReference("Reports");
                reportsRef.push().setValue(report)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(requireContext(), "Đã thêm báo cáo thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Lỗi định dạng số", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }


    private void getLocation() {
        Log.d("HomeFragment", "Requesting location...");

        // Kiểm tra xem đã có quyền ACCESS_FINE_LOCATION và ACCESS_COARSE_LOCATION được cấp hay chưa
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Log.d("HomeFragment", "Location permissions granted");

            // Quyền truy cập vị trí đã được cấp, tiến hành lấy vị trí
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.d("HomeFragment", "Location found: " + location.getLatitude() + ", " + location.getLongitude());

                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                kinhDoTextView.setText(String.format("%.6f", latitude));
                                viDoTextView.setText(String.format("%.6f", longitude));
                            } else {
                                Log.e("HomeFragment", "Location is null");
                                Toast.makeText(requireContext(), "Không thể lấy vị trí hiện tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(requireActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("HomeFragment", "Location request failed: " + e.getMessage());
                            Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Log.e("HomeFragment", "Permission ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION not granted");
            Toast.makeText(requireContext(), "Quyền truy cập vị trí bị từ chối", Toast.LENGTH_SHORT).show();

            // Yêu cầu cả hai quyền truy cập vị trí nếu chưa được cấp
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    public void handlePermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền truy cập vị trí đã được cấp, tiến hành lấy vị trí
                getLocation();
            } else {
                // Quyền truy cập vị trí bị từ chối, thông báo cho người dùng
                Toast.makeText(requireContext(), "Quyền truy cập vị trí bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

package com.example.accimap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.accimap.models.Report;

public class MapsFragment extends Fragment {

    private static final String TAG = "MapsFragment";
    private GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);

            // Tạo LatLng cho Hồ Chí Minh City
            LatLng hoChiMinhCity = new LatLng(10.776889, 106.700806);

            // Đặt LatLngBounds cho khu vực rộng hơn bao gồm Hồ Chí Minh
            LatLngBounds hoChiMinhBounds = new LatLngBounds(
                    new LatLng(10.0, 105.0), // Southwest corner
                    new LatLng(11.0, 107.0)  // Northeast corner
            );

            // Di chuyển camera để hiển thị khu vực rộng hơn
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(hoChiMinhBounds, 0));

            // Sau đó zoom vào khu vực Hồ Chí Minh
            float zoomLevel = 12.0f; // Mức zoom cho phù hợp với yêu cầu của bạn
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hoChiMinhCity, zoomLevel));

            // Thêm các marker từ Firebase
            fetchAndDisplayMarkers(mMap);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void fetchAndDisplayMarkers(GoogleMap mMap) {
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("Report");
        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "DataSnapshot received");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Report report = snapshot.getValue(Report.class);
                    if (report != null) {
                        Number vido = report.getVido();
                        Number kinhdo = report.getKinhdo();
                        String tentainan = report.getTentainan();

                        if (vido != null && kinhdo != null) {
                            LatLng location = new LatLng(vido.doubleValue(), kinhdo.doubleValue());
                            mMap.addMarker(new MarkerOptions()
                                    .position(location)
                                    .title(tentainan != null ? tentainan : "No Title"));
                            Log.d(TAG, "Added marker: " + location.toString());
                        } else {
                            Log.d(TAG, "Invalid latitude or longitude");
                        }
                    } else {
                        Log.d(TAG, "Report is null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("Database error: " + databaseError.getMessage());
                Log.d(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}

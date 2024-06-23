package com.example.accimap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.accimap.models.Report;
import com.squareup.picasso.Picasso;

public class MapsFragment extends Fragment {

    private static final String TAG = "MapsFragment";
    private GoogleMap mMap;
    private View infoWindowView;
    private TextView infoTitle;
    private ImageView infoImage;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);

            // Move camera to default location
            LatLng hoChiMinhCity = new LatLng(10.776889, 106.700806);
            float zoomLevel = 12.0f; // Zoom level as needed
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hoChiMinhCity, zoomLevel));

            // Set custom info window adapter
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null; // Use getInfoContents to customize view
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Inflate custom_info_window layout
                    if (infoWindowView == null) {
                        infoWindowView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                    }

                    // Bind views in info window layout
                    infoTitle = infoWindowView.findViewById(R.id.info_title);
                    infoImage = infoWindowView.findViewById(R.id.info_image);

                    // Get information from marker and display in info window
                    Report report = (Report) marker.getTag();
                    if (report != null) {
                        infoTitle.setText(report.getTentainan());
                        String imageUrl = report.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(infoImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "Image loaded successfully");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.d(TAG, "Error loading image: " + e.getMessage());
                                    infoImage.setImageResource(R.drawable.default_image);
                                }
                            });
                        } else {
                            infoImage.setImageResource(R.drawable.default_image);
                        }
                    } else {
                        Log.d(TAG, "Report is null");
                    }

                    return infoWindowView;
                }
            });


            // Add markers from Firebase
            fetchAndDisplayMarkers();
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
            // Wait until map layout is complete before calling getMapAsync
            mapFragment.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mapFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mapFragment.getMapAsync(callback);
                }
            });
        }
    }

    private void fetchAndDisplayMarkers() {
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("Report");
        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Report report = snapshot.getValue(Report.class);
                    if (report != null) {
                        double latitude = report.getVido().doubleValue();
                        double longitude = report.getKinhdo().doubleValue();
                        String title = report.getTentainan();
                        String imageUrl = report.getImage();

                        LatLng location = new LatLng(latitude, longitude);
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(title != null ? title : "No Title"));

                        marker.setTag(report); // Store Report object in marker's tag
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

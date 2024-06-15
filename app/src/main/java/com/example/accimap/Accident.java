package com.example.accimap;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accimap.adapter.AccidentAdapter;
import com.example.accimap.models.Report;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Accident extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccidentAdapter adapter;
    private List<Report> accidentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accident);

        recyclerView = findViewById(R.id.recyclerview);
        accidentList = new ArrayList<Report>();
        adapter = new AccidentAdapter(accidentList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Report");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Report report = dataSnapshot.getValue(Report.class);
                    if (report != null) {
                        Toast.makeText(Accident.this, "Report: " + report.getTitle(), Toast.LENGTH_SHORT).show();
                        Log.d("Accident", "Report: " + report.getTitle());
                        accidentList.add(report);
                    } else {
                        Toast.makeText(Accident.this, "Report: is null", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Accident.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e("Accident", "Failed to load data: " + error.getMessage());
            }
        });
    }
}

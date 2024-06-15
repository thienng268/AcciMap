package com.example.accimap;

import static com.example.accimap.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accimap.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
public class MainPage extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragments(new HomeFragment());

        binding.btmmenu.setOnNavigationItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.home:
                    replacefragments(new HomeFragment());
                    break;
                case R.id.crisis:
                    replacefragments(new CrisisFragment());
                    break;
            }
            return true;
        });
    
    }
    private void replacefragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}

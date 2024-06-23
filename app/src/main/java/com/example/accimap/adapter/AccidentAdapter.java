package com.example.accimap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accimap.models.Report;
import com.example.accimap.R;

import java.util.List;

public class AccidentAdapter extends RecyclerView.Adapter<AccidentAdapter.ViewHolder> {

    private List<Report> accidentList;
    private Context context;

    public AccidentAdapter(List<Report> accidentList, Context context) {
        this.accidentList = accidentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View accidentView = inflater.inflate(R.layout.accident_item, parent, false);
        return new ViewHolder(accidentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report accident = accidentList.get(position);

        holder.titleTextView.setText(accident.getTentainan());
        holder.statusTextView.setText(accident.getMucdo());
        holder.updateTimeTextView.setText(accident.getNgaygio());
        holder.numberOfDeadTextView.setText(String.valueOf(accident.getSonguoichet()));
        holder.numberOfInjuredTextView.setText(String.valueOf(accident.getSonguoibithuong()));
    }



    @Override
    public int getItemCount() {
        return accidentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView statusTextView;
        public TextView updateTimeTextView;
        public TextView numberOfDeadTextView;
        public TextView numberOfInjuredTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            statusTextView = itemView.findViewById(R.id.status);
            updateTimeTextView = itemView.findViewById(R.id.time_update);
            numberOfDeadTextView = itemView.findViewById(R.id.songuoichet);
            numberOfInjuredTextView = itemView.findViewById(R.id.songuoibithuong);
        }
    }

}

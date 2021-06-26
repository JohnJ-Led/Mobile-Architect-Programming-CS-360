package com.project.weightmanagementsystems;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final ArrayList<String> weightsInMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<String> weightsInMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.weightsInMonth = weightsInMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_day_layout, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.13333333333);
        return new CalendarViewHolder(view,onItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        holder.trackedWeight.setText(weightsInMonth.get(position));

    }

    @Override
    public int getItemCount() {

        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText, String weightText);
    }
}

package com.project.weightmanagementsystems;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    public final TextView trackedWeight;
    private final CalendarAdapter.OnItemListener onItemListener;


    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {

        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.textDay);
        trackedWeight = itemView.findViewById(R.id.textWeightTracked);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText(), (String) trackedWeight.getText());
    }
}

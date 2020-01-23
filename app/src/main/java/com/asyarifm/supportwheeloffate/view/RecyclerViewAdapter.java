package com.asyarifm.supportwheeloffate.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asyarifm.supportwheeloffate.R;
import com.asyarifm.supportwheeloffate.model.DaySchedule;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<DaySchedule> timetable;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView nightShiftTextView;
        TextView dayShiftTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
            this.dayShiftTextView = (TextView) itemView.findViewById(R.id.day_engineer_text_view);
            this.nightShiftTextView = (TextView) itemView.findViewById(R.id.night_engineer_text_view);
        }
    }

    public RecyclerViewAdapter(List<DaySchedule> timetable) {
        this.timetable = timetable;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView dateTextView = holder.dateTextView;
        TextView nightShiftTextView = holder.nightShiftTextView;
        TextView dayShiftTextView = holder.dayShiftTextView;

        dateTextView.setText(timetable.get(listPosition).getDate().toString());
        dayShiftTextView.setText(timetable.get(listPosition).getDayShiftEngineer().getName());
        nightShiftTextView.setText(timetable.get(listPosition).getNightShiftEngineer().getName());
    }

    @Override
    public int getItemCount() {
        return timetable.size();
    }
}

package com.asyarifm.supportwheeloffate.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asyarifm.supportwheeloffate.R;
import com.asyarifm.supportwheeloffate.model.DaySchedule;
import com.asyarifm.supportwheeloffate.model.Engineer;
import com.asyarifm.supportwheeloffate.viewmodel.DisplayTimetableViewModel;

import java.util.ArrayList;

public class DisplayTimetable extends AppCompatActivity {

    private static RecyclerView timetableRecyclerView;
    private RecyclerView.Adapter recycleViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timetable);

        ArrayList<Engineer> engineerlist = getIntent().getParcelableArrayListExtra("engineerlist");

        DisplayTimetableViewModel displayTimetableViewModel = new DisplayTimetableViewModel();
        ArrayList<DaySchedule> timetable = displayTimetableViewModel.genWeeksTimetable(engineerlist, 2, 2);

        timetableRecyclerView = findViewById(R.id.timetable_recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        recycleViewAdapter = new RecyclerViewAdapter(timetable);

        timetableRecyclerView.setLayoutManager(layoutManager);
        timetableRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timetableRecyclerView.setAdapter(recycleViewAdapter);
    }
}

package com.asyarifm.supportwheeloffate.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.asyarifm.supportwheeloffate.R;
import com.asyarifm.supportwheeloffate.model.Engineer;
import com.asyarifm.supportwheeloffate.viewmodel.DisplayEngineerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DisplayEngineer extends AppCompatActivity {

    private ListView engineerListView;
    private LinearLayout footerLayout;
    private Button genTimeTableBtn;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_engineer);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

        engineerListView = findViewById(R.id.engineers_list_view);
        footerLayout = findViewById(R.id.footer_layout);
        genTimeTableBtn = findViewById(R.id.gen_2_weeks_timetable_button);


        DisplayEngineerViewModel displayEngineerViewModel = new DisplayEngineerViewModel(this);

        genTimeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDisplayTimeTable(displayEngineerViewModel.getEngineerList());
            }
        });

        displayEngineerViewModel.getEngineersName().observe(this, engineersNameObserver);
        displayEngineerViewModel.getErrorMessage().observe(this, errorMessageObserver);
    }

    private void setupListView(String[] names) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        engineerListView.setAdapter(arrayAdapter);

        stopDialog();
    }

    private final Observer<String[]> engineersNameObserver = new Observer<String[]>() {
        @Override
        public void onChanged(String[] names) {
            setupListView(names);
        }
    };

    private final Observer<String> errorMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Snackbar snackbar = Snackbar.make(footerLayout, s, Snackbar.LENGTH_LONG);
            snackbar.show();

            stopDialog();
        }
    };

    public void goToDisplayTimeTable(ArrayList<Engineer> engineerList) {
        Intent intent = new Intent(this, DisplayTimetable.class);
        intent.putParcelableArrayListExtra("engineerlist", engineerList);
        startActivity(intent);
    }

    public void stopDialog() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }
}

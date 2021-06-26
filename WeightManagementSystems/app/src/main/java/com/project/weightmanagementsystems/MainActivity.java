//Calendar View Credit: https://github.com/codeWithCal/CalendarTutorialAndroidStudio

package com.project.weightmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, AddWeightDialog.DialogListener, EditWeightDialog.DialogListener, DeleteWeightDialog.DialogListener  {

    FragmentManager manager = getSupportFragmentManager();
    NotificationOptInDialogFragment optInDialogFragment = new NotificationOptInDialogFragment();
    DialogFragment addFragment = new AddWeightDialog();
    DialogFragment editFragment = new EditWeightDialog();
    DialogFragment deleteFragment = new DeleteWeightDialog();

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private TrackItDatabase mTrackDb;
    private int mPosition;
    private String mMonth;
    private String mDay;
    Intent intent;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        user.setUser(intent.getStringExtra("Username"));
        mTrackDb = TrackItDatabase.getInstance(getApplicationContext());
        user = mTrackDb.getUser(user);
        initWidgets();
        selectedDate = LocalDate.now();
        mDay = String.valueOf(selectedDate.getDayOfMonth());
        setMonthView();
        optInDialogFragment.show(manager, "optInMessage");

    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<String> weightsInMonth = weightsInMonthArray(selectedDate);
        Log.e("TAG", "Size: " + weightsInMonth.size());
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, weightsInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        Log.e("DAYS", "firstOfMonth: " + firstOfMonth);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 0; i < 42; i++)
        {
            if(i < dayOfWeek || i >= daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek+1));
            }
            //Log.e("DAYS", "dayOfWeek: " + dayOfWeek + "\n daysInMonth: " + daysInMonth + "\n daysInMonthArray.add(String.valueOf(i - dayOfWeek)): "+ (i - dayOfWeek));
        }
        return  daysInMonthArray;
    }
    private ArrayList<String> weightsInMonthArray(LocalDate date) {
        ArrayList<String> weightsInMonthArray = new ArrayList<>();
        List<Weight> getDbWeight;
        YearMonth yearMonth = YearMonth.from(date);
        getDbWeight = mTrackDb.getWeights(TrackItDatabase.WeightSort.UPDATE_ASC, getDayText(date), String.valueOf(yearMonth.getYear()), user);
        int daysInMonth = yearMonth.lengthOfMonth();
        Log.e("DBSIZE", "DB Size: " + getDbWeight.size());
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int weightIndex = 0;
        for(int i = 0; i < 42; i++)
        {
            if(i < dayOfWeek || i >= daysInMonth + dayOfWeek)
            {
                weightsInMonthArray.add("");
            }
            else
            {
                if(getDbWeight.size() != 0 && weightIndex < getDbWeight.size()) {
                    LocalDate weightDate = LocalDate.parse(getDbWeight.get(weightIndex).getDate());
                    Log.e("GetWeight TAG", "our weight date: " + weightDate );
                    if (weightDate.getDayOfMonth() == i-1) {
                        weightsInMonthArray.add(getDbWeight.get(weightIndex).getWeight() + getDbWeight.get(weightIndex).getUnits());
                        weightIndex++;
                    } else {
                        weightsInMonthArray.add("");
                    }
                }
                else
                    weightsInMonthArray.add("");
            }
        }
        return weightsInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }
    public String getDayText(LocalDate date){
        if(date.getMonthValue() < 10){
            return "0" + date.getMonthValue();
        }
        else return String.valueOf(date.getMonthValue());
    }

    public void onFabClick(View view){
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.fab_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public void onItemClick(int position, String dayText, String weightText) {
        mDay = dayText;
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void onAddClick(MenuItem item) {
        Log.e("TAG", "Item: " + item);
        addFragment.show(getSupportFragmentManager(), "add_weight");

    }

    public void onEditClick(MenuItem item) {
        editFragment.show(getSupportFragmentManager(), "edit_weight");

    }

    public void onDeleteClick(MenuItem item) {
        deleteFragment.show(getSupportFragmentManager(),"delete_weight");

    }

    public void onAddCancel(View view) {
    }

    public void onNewSave(View view) {
        EditText thisWeight = findViewById(R.id.editWeightInput);

        Log.e("TAG",  "Weight: " + thisWeight.getText());
    }

    public void onEditSave(View view) {

    }

    public void onEditCancel(View view) {


    }

    @Override
    public void onFinishEditDialog(String weight, String unit, boolean add) {
        Weight weights = new Weight();
        weights.setUid(user.getId());
        weights.setDate(String.valueOf(selectedDate.withDayOfMonth(Integer.parseInt(mDay))));
        if (add) {
            weights.setWeight(weight);
            weights.setUnits(unit);
            mTrackDb.addWeight(user, weights);
            String message = "Weight Added";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else
            weights = mTrackDb.getWeight(user, weights);
            weights.setWeight(weight);
            weights.setUnits(unit);
            mTrackDb.updateWeight(weights);
            setMonthView();
            String message = "Weight Edited";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            //Log.e("TAG","Edit entered: " + weight + " date: " + selectedDate.withDayOfMonth(Integer.parseInt(mDay)) + " user: " + user.getId());
    }

    @Override
    public void onFinishDeleteDialog(boolean delete) {
        if(delete){
            Weight weights = new Weight();
            weights.setUid(user.getId());
            weights.setDate(String.valueOf(selectedDate.withDayOfMonth(Integer.parseInt(mDay))));
            weights = mTrackDb.getWeight(user, weights);
            mTrackDb.deleteWeight(weights.getId());
            String message = "Weight Deleted";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            setMonthView();
        }
        else{

        }

    }

}
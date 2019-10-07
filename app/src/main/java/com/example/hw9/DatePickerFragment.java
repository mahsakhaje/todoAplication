package com.example.hw9;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import model.TaskTodo;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {


    public static final String TASK = "task";
    DatePicker datePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(TaskTodo taskTodo) {

        Bundle args = new Bundle();
        args.putSerializable(TASK, taskTodo);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker, null, false);
        datePicker = view.findViewById(R.id.datePickerFragment);
        TaskTodo taskTodo = (TaskTodo) getArguments().getSerializable(TASK);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(view).setNegativeButton("back", null).setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year=datePicker.getYear();
                int month=datePicker.getMonth();
                int day=datePicker.getDayOfMonth();
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                DialogAddTask fragment= (DialogAddTask) getTargetFragment();
                fragment.setTaskDate(calendar.getTime());
            }
        });

        return dialog.create();
    }
}

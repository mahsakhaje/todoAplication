package com.example.hw9;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;

import model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {
    TimePicker time;
    Task task;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable(DialogAddTask.SENDED_TASK, task);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        task = (Task) getArguments().getSerializable(DialogAddTask.SENDED_TASK);
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null, false);
        time = dialog.findViewById(R.id.time_picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialog).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Calendar calendar = Calendar.getInstance();
                int hour= time.getHour();
                int minute = time.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
           DialogAddTask fragment= (DialogAddTask) getTargetFragment();
           fragment.setTaskTime(calendar.getTime());

            }
        }).setNegativeButton("back", null);

        return builder.create();
    }
}

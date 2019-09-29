package com.example.hw9;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import model.TaskTodo;


public class DialogAddTask extends DialogFragment {
    public static final int REQUEST_CODE = 9;
    TaskTodo task;
    ViewPager viewPager;
    public static final String SENDED_TASK = "sended_task";

    public DialogAddTask() {
        // Required empty public constructor
    }

    public static DialogAddTask newInstance(TaskTodo task) {

        Bundle args = new Bundle();
        args.putSerializable(SENDED_TASK, task);
        DialogAddTask fragment = new DialogAddTask();
        fragment.setArguments(args);
        return fragment;
    }

    EditText title;
    EditText description;
    Button addTime;
    RadioButton doing, done, todo;
    Button addDate;

    @NonNull
    @Override


    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_add_task, null, false);

        View viewMain = LayoutInflater.from(getActivity()).inflate(R.layout.main_activity, null, false);
        task = new TaskTodo();
        task = (TaskTodo) getArguments().getSerializable(SENDED_TASK);

        title = v.findViewById(R.id.edittext_title_dialog);
        doing = v.findViewById(R.id.doingRadioButton);
        todo = v.findViewById(R.id.todoRadioBTN);
        done = v.findViewById(R.id.doneRadioBTN);
        viewPager = viewMain.findViewById(R.id.viewPagerContainor);
        addTime = v.findViewById(R.id.button_choose_time);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(task);
                timePickerFragment.setTargetFragment(DialogAddTask
                        .this, 2);
                timePickerFragment.show(getFragmentManager(), "tag");
            }
        });

        description = v.findViewById(R.id.editText_decription_dialog);
        if (task.getTitle() != null) {
            title.setText(task.getTitle().toString());
            description.setText(task.getDescription().toString());
        }

        doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTaskState(States.DOING);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTaskState(States.DONE);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTaskState(States.TODO);
            }
        });

        addDate = v.findViewById(R.id.button_choose_date);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(task);
                fragment.setTargetFragment(DialogAddTask.this, REQUEST_CODE);
                fragment.show(getFragmentManager(), "tag1");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if (!(title.getText().toString().isEmpty()) || !(description.getText().toString().isEmpty())) {


                    task.setTitle(title.getText().toString());
                    task.setDescription(description.getText().toString());

                    Fragment frg = getTargetFragment();
                    if (frg instanceof TodoFragment) {
                        TodoFragment todoFragment = (TodoFragment) getTargetFragment();
                        todoFragment.updateTask(task);

                    } else if (frg instanceof DoingFragment) {
                        DoingFragment doing = (DoingFragment) getTargetFragment();
                        doing.updateTask(task);
                    } else if (frg instanceof DoneFragment) {
                        DoneFragment done = (DoneFragment) getTargetFragment();
                        done.updateTask(task);
                    }


                }
                //
                else if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "you can not have empty task", Toast.LENGTH_LONG).show();

                }
            }


        }).setNegativeButton("back", null);


        return builder.setView(v).create();
    }

    public void setTaskTime(Date date) {
        task.setTime(date);
        DateFormat format = new SimpleDateFormat("HH:mm");

        addTime.setText(format.format(task.getTime()));
    }

    public void setTaskDate(Date date) {
        task.setDate(date);
        DateFormat format = new SimpleDateFormat("yyyy:mm:d");
        addDate.setText(format.format(task.getDate()));
    }

}

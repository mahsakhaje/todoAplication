package com.example.hw9;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hw9.Repositories.TaskRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Task;


public class DialogAddTask extends DialogFragment {
    public static final int REQUEST_CODE = 9;
    public static final String EDIT_CONDITION = "edit";
    Task task;
    ViewPager viewPager;
    public static final String SENDED_TASK = "sended_task";

    public DialogAddTask() {
        // Required empty public constructor
    }

    public static DialogAddTask newInstance(Task task, Boolean edit) {

        Bundle args = new Bundle();
        args.putSerializable(SENDED_TASK, task);
        args.putBoolean(EDIT_CONDITION, edit);
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
        title = v.findViewById(R.id.edittext_title_dialog);
        doing = v.findViewById(R.id.doingRadioButton);
        todo = v.findViewById(R.id.todoRadioBTN);
        done = v.findViewById(R.id.doneRadioBTN);
        final boolean edit = getArguments().getBoolean(EDIT_CONDITION);
        description = v.findViewById(R.id.editText_decription_dialog);

        viewPager = viewMain.findViewById(R.id.viewPagerContainor);
        addTime = v.findViewById(R.id.button_choose_time);
        addDate = v.findViewById(R.id.button_choose_date);

        task = (Task) getArguments().getSerializable(SENDED_TASK);

        if (task.getDescription() != null || task.getTitle() != null) {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            if (task.getTime() != null) {
                DateFormat format = new SimpleDateFormat("HH:mm");

                addTime.setText(format.format(task.getTime()));
            }
            if (task.getDate() != null) {
                setTaskDate(task.getDate());

            }

        } else {
            task = new Task();
        }
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(task);
                timePickerFragment.setTargetFragment(DialogAddTask
                        .this, 2);
                timePickerFragment.show(getFragmentManager(), "tag");
            }
        });


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
                        if (task.getTaskState() == null)
                            task.setTaskState(States.TODO);
                        if (edit)
                            todoFragment.updateTask(task);
                        else todoFragment.addTask(task);

                    } else if (frg instanceof DoingFragment) {
                        if (task.getTaskState() == null)
                            task.setTaskState(States.DOING);
                        DoingFragment doing = (DoingFragment) getTargetFragment();
                        if (edit)
                            doing.updateTask(task);
                        else doing.addTask(task);
                    } else if (frg instanceof DoneFragment) {
                        if (task.getTaskState() == null)
                            task.setTaskState(States.DONE);
                        DoneFragment done = (DoneFragment) getTargetFragment();
                        if (edit)
                            done.updateTask(task);
                        else done.addTask(task);
                    }


                } else if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "you can not have empty task", Toast.LENGTH_LONG).show();

                }
            }


        });
        builder.setNegativeButton("back", null);
        builder.setNeutralButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskRepository.getInstance(getActivity()).removeTask(task.getID());
                Fragment fragment = getTargetFragment();
                if (fragment instanceof TodoFragment)
                    ((TodoFragment) fragment).notifyAdapter();
                else if (fragment instanceof DoingFragment)
                    ((DoingFragment) fragment).notifyAdapter();
                else if (fragment instanceof DoneFragment)
                    ((DoneFragment) fragment).notifyAdapter();
            }
        });


        return builder.setView(v).create();
    }

    public void setTaskTime(Date date) {
        task.setTime(date);
        DateFormat format = new SimpleDateFormat("HH:mm");

        addTime.setText(format.format(task.getTime()));
    }

    public void setTaskDate(Date date) {
        task.setDate(date);
        DateFormat format = new SimpleDateFormat("yyyy:MM:d");
        addDate.setText(format.format(task.getDate()));
    }

}

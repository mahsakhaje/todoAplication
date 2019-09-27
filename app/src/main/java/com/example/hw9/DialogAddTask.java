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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

import model.TaskTodo;



public class DialogAddTask extends DialogFragment {
    TaskTodo task;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialog_add_task, container, false);

        return v;
    }

    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_add_task, null, false);
        title = v.findViewById(R.id.edittext_title_dialog);
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
        task = (TaskTodo) getArguments().getSerializable(SENDED_TASK);

        description = v.findViewById(R.id.editText_decription_dialog);
        if (task.getTitle() != null) {
            title.setText(task.getTitle().toString());
            description.setText(task.getDescription().toString());
        }
        task = new TaskTodo();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!(title.getText().toString().isEmpty()) || !(description.getText().toString().isEmpty())) {


                    task.setTitle(title.getText().toString());
                    task.setDescription(description.getText().toString());
                    TodoFragment todoFragment = (TodoFragment) getTargetFragment();

                    todoFragment.updateTask(task);

                } else if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "you can not have empty task", Toast.LENGTH_LONG).show();

                }
            }


        }).setNegativeButton("back", null);


        return builder.setView(v).create();
    }

    public void setTaskTime(Date date) {

        task.setTime(date);
    }

}

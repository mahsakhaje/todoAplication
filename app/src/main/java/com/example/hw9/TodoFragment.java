package com.example.hw9;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hw9.Repositories.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment {
    public static final int REQUEST_CODE = 1;
    Task task;
    TextView title;
    TextView desc;
    RecyclerView myRecyclerView;
    MyAdaptor adaptor;
    TaskRepository taskRepository;
    FloatingActionButton addTodoTask;
    static LinearLayout backGroundLayout;

    public TodoFragment() {
        // Required empty public constructor
    }

    public static TodoFragment newInstance() {

        Bundle args = new Bundle();

        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);

        //setadaptorRemains

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DoingFragment.TAG, "onCreateTODO");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        addTodoTask = (FloatingActionButton) v.findViewById(R.id.addtodo_botton);
        myRecyclerView = v.findViewById(R.id.todoRecycleerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final Task task = new Task();
        taskRepository = TaskRepository.getInstance(getActivity());
        backGroundLayout = v.findViewById(R.id.linearlayot);
        adaptor = new MyAdaptor(taskRepository.getTodoTasks());
        myRecyclerView.setAdapter(adaptor);

        addTodoTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance(task,false);
                fragment.setTargetFragment(TodoFragment.this, REQUEST_CODE);
                fragment.show(getFragmentManager(), "tag");

            }
        });
        return v;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        TextView description;
        LinearLayout linearLayout;
        Task task;
        TextView firstLetter;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeTextView_Item_View);
            title = itemView.findViewById(R.id.textviewtitle_todo);
            description = itemView.findViewById(R.id.description_item_todo);
            linearLayout = itemView.findViewById(R.id.parent_layout);
            firstLetter = itemView.findViewById(R.id.textView_FloatingTask);

        }

        public void bind(final Task task) {
            String textTime = "";
            this.task = task;
            title.setText(task.getTitle());
            char ch = task.getTitle().charAt(0);
            firstLetter.setText(ch + "");
            if (task.getTime() != null) {

                Date date = task.getTime();
                DateFormat format = new SimpleDateFormat("HH:mm");
                textTime += format.format(date);
                if (task.getDate() != null) {
                    DateFormat formatDate = new SimpleDateFormat("yyyy:MM:dd");
                    Date date1 = task.getDate();
                    textTime += " at " + formatDate.format(date1);
                }
                time.setText(textTime);
            } else if (task.getDate() != null) {
                if (task.getTime() != null) {
                    DateFormat formatDate = new SimpleDateFormat("HH:mm");
                    Date date1 = task.getTime();
                    textTime += formatDate.format(date1);
                }
                Date date = task.getDate();
                DateFormat format = new SimpleDateFormat("yyyy:MM:dd");
                textTime += " at " + format.format(date);

                time.setText(textTime);
            }

            description.setText(task.getDescription());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddTask fragment = DialogAddTask.newInstance(task,true);
                    fragment.setTargetFragment(TodoFragment.this, REQUEST_CODE);
                    fragment.show(getFragmentManager(), "tag7");


                }
            });

        }

    }

    public class MyAdaptor extends RecyclerView.Adapter<MyViewHolder> {

        List<Task> tasks = new ArrayList<Task>();


        MyAdaptor(List<Task> task) {
            tasks = task;
        }

        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);
            return new MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.bind(tasks.get(position));

        }


        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }


    public void updateTask(Task task) {


        if (task.getTaskState() == States.TODO) {

                taskRepository.updateTask(task);
        ;
            notifyAdapter();
            checkBackGround();

        } else if (task.getTaskState() == States.DONE) {
            taskRepository.updateTask(task);
            notifyAdapter();

            checkBackGround();


        } else if (task.getTaskState() == States.DOING) {
            taskRepository.updateTask(task);
            notifyAdapter();


            checkBackGround();


        } else {
            taskRepository.updateTask(task);
            notifyAdapter();
            checkBackGround();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        checkBackGround();

    }

    public void notifyAdapter() {

        List<Task> tasks = taskRepository.getTodoTasks();
        adaptor.setTasks(tasks);
        adaptor.notifyDataSetChanged();
        checkBackGround();


    }

    public void checkBackGround() {
        if (taskRepository.getTodoTasks().size() > 0) {
            backGroundLayout.setVisibility(View.INVISIBLE);
        } else backGroundLayout.setVisibility(View.VISIBLE);
    }
    public void addTask(Task task){
        taskRepository.addTask(task);
        notifyAdapter();
    }
}

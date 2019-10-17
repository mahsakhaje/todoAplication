package com.example.hw9;


import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Date;
import java.util.List;

import model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneFragment extends Fragment {
    public static final String TAG_77 = "tag77";
    Task task;
    RecyclerView recyclerView;
    FloatingActionButton addTask;
    public static final int REQUEST_CODE = 9;

    MyAdapter adapter;
    TaskRepository taskRepository;
    LinearLayout backGroundLayout;


    public DoneFragment() {
        // Required empty public constructor
    }

    public static DoneFragment newInstance() {

        Bundle args = new Bundle();

        DoneFragment fragment = new DoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskRepository=TaskRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        recyclerView = v.findViewById(R.id.todoRecycleerView);
        Log.d(DoingFragment.TAG, "onCreateDone");

        addTask = v.findViewById(R.id.addtodo_botton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapter = new MyAdapter(TaskRepository.getInstance(getActivity()).getDoneTasks());
        recyclerView.setAdapter(adapter);
        backGroundLayout = v.findViewById(R.id.linearlayot);
        final Task task=new Task();
        addTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance(task,false);
                fragment.setTargetFragment(DoneFragment.this, REQUEST_CODE);
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
        TextView firstLetter;
        Task task;

        public MyViewHolder(@NonNull View itemView) {
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
            title.setText(" "+task.getTitle()+" ");
            title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            firstLetter.setText(task.getTitle().charAt(0) + "");
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
                    fragment.setTargetFragment(DoneFragment.this, REQUEST_CODE);
                    fragment.show(getFragmentManager(), TAG_77);


                }
            });

        }

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<Task> repositoryList;

        public void setRepositoryList(List<Task> repositoryList) {
            this.repositoryList = repositoryList;
        }

        MyAdapter(List<Task> repositoryList) {

            this.repositoryList = repositoryList;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);
            MyViewHolder holder = new MyViewHolder(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.bind(repositoryList.get(position));

        }


        @Override
        public int getItemCount() {
            return repositoryList.size();
        }
    }

    public void updateTask(Task task) {

        if (task.getTaskState() == States.TODO) {
            taskRepository.updateTask(task);
            notifyAdapter();
            checkBackGround();

        } else if (task.getTaskState() == States.DONE) {
            taskRepository.addTask(task);
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
        adapter.setRepositoryList(taskRepository.getDoneTasks());
        adapter.notifyDataSetChanged();
        checkBackGround();
    }

    public void checkBackGround() {
        if (taskRepository.getDoneTasks().size() > 0) {
            backGroundLayout.setVisibility(View.INVISIBLE);
        } else backGroundLayout.setVisibility(View.VISIBLE);
    }
    public void addTask(Task task){
        taskRepository.addTask(task);
        notifyAdapter();
    }
}

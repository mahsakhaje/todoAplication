package com.example.hw9;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.RepositoryDoing;
import model.RepositoryDone;
import model.RepositoryToDo;
import model.TaskTodo;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneFragment extends Fragment {
    TaskTodo task;
    RecyclerView recyclerView;
    FloatingActionButton addTask;
    public static final int REQUEST_CODE = 5;

    MyAdapter adapter;
    RepositoryDone repository;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        recyclerView = v.findViewById(R.id.todoRecycleerView);
        Log.d(DoingFragment.TAG, "onCreateDone");
        task = new TaskTodo();
        addTask = v.findViewById(R.id.addtodo_botton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        repository = RepositoryDone.getInstance(task);

        adapter = new MyAdapter(RepositoryDone.getInstance(task).getTasks());
        recyclerView.setAdapter(adapter);
        backGroundLayout = v.findViewById(R.id.linearlayot);
        addTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance(task);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeTextView_Item_View);
            title = itemView.findViewById(R.id.textviewtitle_todo);
            description = itemView.findViewById(R.id.description_item_todo);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<TaskTodo> repositoryList;

        MyAdapter(List<TaskTodo> repositoryList) {

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
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(repositoryList.get(position).getTitle());
            if (repositoryList.get(position).getTime() != null) {
                Date date = repositoryList.get(position).getTime();
                DateFormat format = new SimpleDateFormat("HH:mm");
                holder.time.setText(format.format(date));
            }
            holder.description.setText(repositoryList.get(position).getDescription());

        }


        @Override
        public int getItemCount() {
            return repositoryList.size();
        }
    }

    public void updateTask(TaskTodo task) {
        if (task.getTaskState() == States.TODO) {

            RepositoryToDo repositoryTodo = RepositoryToDo.getInstance(task);
            repositoryTodo.addTask(task);

        } else if (task.getTaskState() == States.DONE) {
            repository.addTask(task);
            adapter.notifyDataSetChanged();
            if (repository.getTasks().size() > 0) {
                backGroundLayout.setVisibility(View.INVISIBLE);}

        } else if (task.getTaskState() == States.DOING) {
            RepositoryDoing repositoryDoing = RepositoryDoing.getInstance(task);
            repositoryDoing.addTask(task);


        } else {
            repository.addTask(task);
            adapter.notifyDataSetChanged();
            if (repository.getTasks().size() > 0) {
                backGroundLayout.setVisibility(View.INVISIBLE);}
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (repository.getTasks() == null) {
            return;
        } else if (repository.getTasks().size() > 0) {
            backGroundLayout.setVisibility(View.INVISIBLE);
        }

    }

}

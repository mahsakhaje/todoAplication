package com.example.hw9;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import model.RepositoryToDo;
import model.TaskTodo;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment {
    public static final int REQUEST_CODE = 1;
    TaskTodo task;
    TextView title;
    TextView desc;
    RecyclerView myRecyclerView;
    MyAdaptor adaptor;
    RepositoryToDo repository;
    FloatingActionButton addTodoTask;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        addTodoTask = (FloatingActionButton) v.findViewById(R.id.addtodo_botton);
        myRecyclerView = v.findViewById(R.id.todoRecycleerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        task=new TaskTodo();
        repository = RepositoryToDo.getInstance(task);

        adaptor = new MyAdaptor(repository.getTasks());
        myRecyclerView.setAdapter(adaptor);
        task = new TaskTodo();

        addTodoTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance();
                fragment.setTargetFragment(TodoFragment.this, REQUEST_CODE);
                fragment.show(getFragmentManager(), "tag");
            }
        });
        return v;
    }

    public void setTitleAndDescription(String title, String description) {
        task.setTitle(title);
        task.setDescription(description);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textview_time_todo);
            title = itemView.findViewById(R.id.textviewtitle_todo);


        }
    }

    public class MyAdaptor extends RecyclerView.Adapter<MyViewHolder> {

        List<TaskTodo> repository = new ArrayList<TaskTodo>();

        MyAdaptor(List<TaskTodo> task) {
            repository = task;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);


            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(repository.get(position).getTitle());
            holder.time.setText((CharSequence) repository.get(position).getDate());
        }

        @Override
        public int getItemCount() {
            return repository.size()-1;
        }
    }
}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.RepositoryDoing;
import model.RepositoryDone;
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
    LinearLayout backGroundLayout;
    boolean hide;


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
        Log.d(DoingFragment.TAG,"onCreateTODO");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        addTodoTask = (FloatingActionButton) v.findViewById(R.id.addtodo_botton);
        myRecyclerView = v.findViewById(R.id.todoRecycleerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        task = new TaskTodo();
        repository = RepositoryToDo.getInstance(task);
        backGroundLayout = v.findViewById(R.id.linearlayot);
        adaptor = new MyAdaptor(repository.getTasks());
        myRecyclerView.setAdapter(adaptor);

        addTodoTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance(task);
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

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeTextView_Item_View);
            title = itemView.findViewById(R.id.textviewtitle_todo);
            description = itemView.findViewById(R.id.description_item_todo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddTask fragment = DialogAddTask.newInstance(repository.getTask(adaptor.getIndex()));
                    fragment.setTargetFragment(TodoFragment.this, REQUEST_CODE);
                    fragment.show(getFragmentManager(), "tag7");
                }
            });

        }
    }

    public class MyAdaptor extends RecyclerView.Adapter<MyViewHolder> {

        List<TaskTodo> repository = new ArrayList<TaskTodo>();
        private int index;

        public int getIndex() {
            return index;
        }

        MyAdaptor(List<TaskTodo> task) {
            repository = task;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);


            return new MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            index = position;
            holder.title.setText(repository.get(position).getTitle());
            if (repository.get(position).getTime() != null) {
                Date date = repository.get(position).getTime();
                DateFormat format = new SimpleDateFormat("HH:mm");
                holder.time.setText(format.format(date));
            }
            holder.description.setText(repository.get(position).getDescription());

        }

        @Override
        public int getItemCount() {
            return repository.size();
        }
    }

    public void updateTask(TaskTodo task) {
        if (task.getTaskState() == States.TODO) {
            repository.addTask(task);

            adaptor.notifyDataSetChanged();
            if (repository.getTasks().size() > 0) {
                backGroundLayout.setVisibility(View.INVISIBLE);}
        }else
        if (task.getTaskState() == States.DONE) {
            RepositoryDone repositoryDone=RepositoryDone.getInstance(task);
            repositoryDone.addTask(task);


        }else
        if (task.getTaskState() == States.DOING) {
            RepositoryDoing repositoryDoing = RepositoryDoing.getInstance(task);
            repositoryDoing.addTask(task);







        }
        else{ repository.addTask(task);
        adaptor.notifyDataSetChanged();
        if (repository.getTasks().size() > 0) {
            backGroundLayout.setVisibility(View.INVISIBLE);}}

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

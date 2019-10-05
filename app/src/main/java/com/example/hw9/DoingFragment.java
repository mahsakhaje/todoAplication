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
import com.google.android.material.tabs.TabLayout;

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
public class DoingFragment extends Fragment {
    public static final String TAG = "ACTIV";
    RecyclerView recyclerView;
    FloatingActionButton addTask;
    public static final int REQUEST_CODE = 2;
    TaskTodo task;
    MyAdapter adapter;
    RepositoryDoing repository;
    LinearLayout backGroundLayout;
    boolean change;

    public DoingFragment() {
        // Required empty public constructor
    }

    public static DoingFragment newInstance() {

        Bundle args = new Bundle();

        DoingFragment fragment = new DoingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateDoing");

        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        recyclerView = v.findViewById(R.id.todoRecycleerView);
        addTask = v.findViewById(R.id.addtodo_botton);
        task = new TaskTodo();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        repository = RepositoryDoing.getInstance(task);
        adapter = new MyAdapter(repository.getTasks());
        recyclerView.setAdapter(adapter);
        backGroundLayout = v.findViewById(R.id.linearlayot);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddTask fragment = DialogAddTask.newInstance(task);
                fragment.setTargetFragment(DoingFragment.this, REQUEST_CODE);
                fragment.show(getFragmentManager(), "tag");
            }
        });
        return v;
    }

    public class ViewHolderDoing extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView description;
        LinearLayout linearLayout;

        public ViewHolderDoing(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.timeTextView_Item_View);
            title = itemView.findViewById(R.id.textviewtitle_todo);
            description = itemView.findViewById(R.id.description_item_todo);
            linearLayout=itemView.findViewById(R.id.parent_layout);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<ViewHolderDoing> {
        List<TaskTodo> repositoryList;


        MyAdapter(List<TaskTodo> repositoryList) {

            this.repositoryList = repositoryList;
        }

        public ViewHolderDoing onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);
            ViewHolderDoing holder = new ViewHolderDoing(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDoing holder, final int position) {
            holder.title.setText(repositoryList.get(position).getTitle());
            if (repositoryList.get(position).getTime() != null) {
                Date date = repositoryList.get(position).getTime();
                DateFormat format = new SimpleDateFormat("HH:mm");
                holder.time.setText(format.format(date));
            }
            holder.description.setText(repositoryList.get(position).getDescription());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddTask fragment = DialogAddTask.newInstance(repository.getTask(position));
                    fragment.setTargetFragment(DoingFragment.this, REQUEST_CODE);
                    fragment.show(getFragmentManager(), "tag21");
                    if(change) {
                        RepositoryDoing.getInstance(repository.getTask(position)).removeTask(position);
                        adapter.notifyDataSetChanged();
                    }
                    change=false;

                }

            });


        }

        @Override
        public int getItemCount() {
            return repositoryList.size();
        }
    }


    public void updateTask(TaskTodo task) {
        change=true;
        if (task.getTaskState() == States.TODO) {

            RepositoryToDo repositoryTodo = RepositoryToDo.getInstance(task);
            repositoryTodo.addTask(task);

        } else if (task.getTaskState() == States.DONE) {
            RepositoryDone done=RepositoryDone.getInstance(task);
            done.addTask(task);


        } else if (task.getTaskState() == States.DOING) {
            repository.addTask(task);
            adapter.notifyDataSetChanged();
            if (repository.getTasks().size() > 0) {
                backGroundLayout.setVisibility(View.INVISIBLE);
            }


        } else {
            repository.addTask(task);
            adapter.notifyDataSetChanged();
            if (repository.getTasks().size() > 0) {
                backGroundLayout.setVisibility(View.INVISIBLE);
            }
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

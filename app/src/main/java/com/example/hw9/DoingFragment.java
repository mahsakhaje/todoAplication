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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Repository;
import model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoingFragment extends Fragment {
    public static final String TAG = "ACTIV";
    RecyclerView recyclerView;
    FloatingActionButton addTask;
    public static final int REQUEST_CODE = 2;
    Task task;
    MyAdapter adapter;
    Repository repository;
    LinearLayout backGroundLayout;


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
        task = new Task();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        repository = Repository.getInstance(task);
        adapter = new MyAdapter(repository.getDoingTasks());
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
        TextView firstLetter;
        Task task;

        public ViewHolderDoing(@NonNull View itemView) {
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
                    DialogAddTask fragment = DialogAddTask.newInstance(task);
                    fragment.setTargetFragment(DoingFragment.this, REQUEST_CODE);
                    fragment.show(getFragmentManager(), "tag7");


                }
            });

        }

    }

    public class MyAdapter extends RecyclerView.Adapter<ViewHolderDoing> {
        List<Task> repositoryList;


        MyAdapter(List<Task> repositoryList) {

            this.repositoryList = repositoryList;
        }

        public ViewHolderDoing onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_todo_layout, null, false);
            ViewHolderDoing holder = new ViewHolderDoing(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDoing holder, final int position) {
            holder.bind(repositoryList.get(position));


        }

        @Override
        public int getItemCount() {
            return repositoryList.size();
        }
    }


    public void updateTask(Task task) {

        if (task.getTaskState() == States.TODO) {
            repository.removeTask(task.getID());
            notifyAdapter();
            repository.addTask(task);
            checkBackGround();

        } else if (task.getTaskState() == States.DONE) {
            repository.removeTask(task.getID());

            repository.addTask(task);
            checkBackGround();
            notifyAdapter();


        } else if (task.getTaskState() == States.DOING) {
            repository.updateTask(task);
            notifyAdapter();
            checkBackGround();


        } else {
            repository.updateTask(task);
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
        adapter.notifyDataSetChanged();
    }

    public void checkBackGround() {
        if (repository.getTodoTasks().size() > 0) {
            backGroundLayout.setVisibility(View.INVISIBLE);
        } else backGroundLayout.setVisibility(View.VISIBLE);
    }


}

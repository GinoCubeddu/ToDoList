package com.example.cubg1_14.todoexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;
import java.util.Map;

public class ToDoActivity extends AppCompatActivity {

    // Holder for the task list
    private LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        // Grab the task list on creation
        taskList = (LinearLayout) findViewById(R.id.TaskListLayout);

        // Init backendless with the application id and android API key
        Backendless.initApp(
            this,
            "APP_ID",
            "ANDROID_API_KEY"
        );

        // Load the tasks into the task list
        GetTasks();
    }

    public void GetTasks() {
        Backendless.Persistence.of(Tasks.class).find(new AsyncCallback<List<Tasks>>() {
            @Override
            public void handleResponse(List<Tasks> tasks) {
                // Clear the current tasks from the view
                taskList.removeAllViews();
                Log.d("ToDo", "Retrieved " + tasks.size() + " tasks");

                // Loop through the collected tasks
                for(Tasks task: tasks) {
                    Log.d("ToDo", task.getTask());
                    // Create a new textView
                    TextView textView = new TextView(getApplicationContext());
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    textView.setText(task.getTask());
                    // Add the created view to the list
                    taskList.addView(textView);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("ToDo", "Error: " + fault.getMessage());
            }
        });
    }

    public void onButtonClicked(View button) {
        TextView textView = (TextView) findViewById(R.id.NewTaskText);
        Tasks task = new Tasks();
        task.setTask(textView.getText().toString());
        Backendless.Persistence.save(task, new AsyncCallback<Tasks>() {
            @Override
            public void handleResponse(Tasks response) {
                Log.d("ToDo", "Saved task");
                // If we have added a new task then update the task list
                GetTasks();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("ToDo", "Error: " + fault.getMessage());
            }
        });
    }
}

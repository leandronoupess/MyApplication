package leandronoupess.de.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {

    private TodoList todoList;
    private int todoListId;
    private  DatabaseManager databaseManager;

    private ArrayList<Todo>todos;
    private ListView listViewTodos;

    private TextView todoListNameTextView;
    private Button abbTodoBtn;
    private TodoAdapter adapter;



    //private ActivityResultLauncher<Intent>launchAddTodoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // findViewById
        todoListNameTextView = findViewById(R.id.TodoListNameTextView);
        listViewTodos = findViewById(R.id.ListViewTodos);
        abbTodoBtn = findViewById(R.id.addTodoButton);

        databaseManager = new DatabaseManager(getApplicationContext());
        todos = new ArrayList<>();

        todoList = getIntent().getParcelableExtra("TodoList");
        todoListId = todoList.getId();

        todoListNameTextView.setText(todoList.getName());

        todos = databaseManager.getAllTodos(todoListId);

        adapter = new TodoAdapter(getApplicationContext(),todos);
        listViewTodos.setAdapter(adapter);

        UpdateDisplay();

       /* launchAddTodoActivity = registerForActivityResult(
                new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String message = result.getData().getStringExtra("RESULT_ADD_TODO");

                        UpdateDisplay();
                    }
                }
        );


        abbTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddTodoActivity = new Intent(getApplicationContext(),AddTodoActivity.class);
                Intent intent = startAddTodoActivity.putExtra("TodoListId", todoListId);
                launchAddTodoActivity.launch(startAddTodoActivity);
            }
        });*/

    }

    private void UpdateDisplay(){
        todos=databaseManager.getAllTodos(todoListId);
        adapter = new TodoAdapter(getApplicationContext(),todos);
        listViewTodos.setAdapter(adapter);
    }

}
package leandronoupess.de.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodoListActivity extends AppCompatActivity {
    private String TodoListName;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);

        databaseManager= new DatabaseManager(getApplicationContext());

        Button confirmBtn = findViewById(R.id.confirmButton);
        EditText todoListNameEditText = findViewById(R.id.TodoListNameEditText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoListName =todoListNameEditText.getText().toString();
                databaseManager.insertTodoList(TodoListName);

                Intent startTodoListActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startTodoListActivity);
            }
        });
    }
}
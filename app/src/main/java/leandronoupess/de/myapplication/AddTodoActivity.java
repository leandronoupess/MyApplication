package leandronoupess.de.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodoActivity extends AppCompatActivity {

    private String TodoName;
    private DatabaseManager databaseManager;
    private int IdTodoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        databaseManager= new DatabaseManager(getApplicationContext());

        IdTodoList = getIntent().getIntExtra("TodoListId",0);

        Button confirmBtn =findViewById(R.id.confirmAddTodoButton);
        EditText todoNameEditText =findViewById(R.id.TodoNameEditText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoName = todoNameEditText.getText().toString();
                databaseManager.insertTodos(TodoName,IdTodoList);

                Intent intent = new Intent();
                intent.putExtra("RESULT_ADD_TODO","OK");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}
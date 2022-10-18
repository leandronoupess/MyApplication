package leandronoupess.de.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<Todo> {
    public ArrayList<Todo>todos;
    public  DatabaseManager databaseManager;

    public TodoAdapter(@NonNull Context context,ArrayList<Todo> todos) {
        super(context, 0, todos);
        this.todos= todos;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public View getView(int position , @NonNull View convertView, @NonNull ViewGroup parent){
        return  initView ( position ,convertView ,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.todo_listview_row, parent,false
            );
        }

        TextView textViewTodoName= convertView.findViewById(R.id.textview_name_todo_row);
        CheckBox checkBox=convertView.findViewById(R.id.checkbox_for_task_todo);


        Todo currentTodo = getItem(position);

        if (currentTodo!=null){
            textViewTodoName.setText(currentTodo.getName());
        }
        checkBox .setChecked(currentTodo.isDone());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    databaseManager.updateTaskStatus(1, currentTodo.getId());
                }else{
                    databaseManager.updateTaskStatus(0, currentTodo.getId());
                }

            }
        });



        return convertView;
    }
}

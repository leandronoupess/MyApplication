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

public class TodoListAdapter extends ArrayAdapter<TodoList> {

    public ArrayList<TodoList> todoListArrayList;
    public DatabaseManager databaseManager;

    public TodoListAdapter(Context context,ArrayList<TodoList>todoListArrayList){
        super(context, 0, todoListArrayList);
        this.todoListArrayList=todoListArrayList;
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
                    R.layout.todolist_list_view_row, parent,false
            );
        }

        TextView textViewTodoListName= convertView.findViewById(R.id.text_view_name_todolist_view_row);

        TodoList currentTodoList = getItem(position);

        if (currentTodoList!=null){
            textViewTodoListName.setText(currentTodoList.getName());
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkbox_for_selected_items_todolist);
        checkBox.setTag(position);
        ImageButton editImageButton = convertView.findViewById(R.id.edit_name_todolist_row);
        ImageButton checkImageButton = convertView.findViewById(R.id.check_edit_name_todolist);
        EditText nameEditText = convertView.findViewById(R.id.edit_text_name_todolist_row);
        if (MainActivity.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
            editImageButton.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.GONE);
            editImageButton.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int possition =(int)compoundButton.getTag();

                if (MainActivity.UserSelectionListTodoList.contains(todoListArrayList.get(position))){
                    MainActivity.UserSelectionListTodoList.remove(todoListArrayList.get(position));
                }else {
                    MainActivity.UserSelectionListTodoList.add(todoListArrayList.get(position));

                }
                MainActivity.actionModeListTodoList.setTitle(MainActivity.UserSelectionListTodoList.size()+"todolist ausgewält.....");
            }
        });

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setVisibility(View.GONE);
                editImageButton.setVisibility(View.GONE);
                textViewTodoListName.setVisibility(View.GONE);

                nameEditText.setVisibility(View.VISIBLE);
                nameEditText.setText(currentTodoList.getName());
                checkImageButton.setVisibility(View.VISIBLE);

            }
        });

        checkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setVisibility(View.VISIBLE);
                editImageButton.setVisibility(View.VISIBLE);
                textViewTodoListName.setVisibility(View.VISIBLE);

                String newText = nameEditText.getText().toString();
                nameEditText.setVisibility(View.GONE);
                checkImageButton.setVisibility(View.GONE);
                textViewTodoListName.setText(newText);

                //Todolists Name in der Datenbank ändern

                databaseManager.updateTodoList(newText, currentTodoList.getId());
            }
        });

        return convertView;
    }

}

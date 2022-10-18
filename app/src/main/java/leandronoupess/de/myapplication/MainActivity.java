package leandronoupess.de.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTodoList;

    private ArrayList<TodoList> listOfTodoList = new ArrayList<>();
    private ArrayList<TodoList> listOfTodoListOriginal = new ArrayList<>();

    private DatabaseManager databaseManager;
    private TodoListAdapter todoListAdapter;
    private Button addTodoListButton;

    private EditText searchBarListTodoList;

    public static  boolean isActionMode = false;
    public static ArrayList<TodoList> UserSelectionListTodoList = new ArrayList<>();
    public static ActionMode actionModeListTodoList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(getApplicationContext());

        listViewTodoList =findViewById(R.id.ListViewTodo);
        addTodoListButton=findViewById(R.id.addTodoListButton);
        searchBarListTodoList=findViewById(R.id.search_bar_list_todoList);

        listViewTodoList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewTodoList.setMultiChoiceModeListener(modeListener);


        listOfTodoList= databaseManager.getAllTodoList();
        listOfTodoListOriginal= databaseManager.getAllTodoList();

        todoListAdapter = new TodoListAdapter(this, listOfTodoList);

        listViewTodoList. setAdapter(todoListAdapter);


       // ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listOfTodo);

       // listViewTodo.setAdapter(adapter);

        addTodoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivityAddTodoList = new Intent(getApplicationContext(),AddTodoListActivity.class);
                startActivity(startActivityAddTodoList);
            }
        });

        searchBarListTodoList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listOfTodoList=filterArrayListTodoList(charSequence.toString());
                todoListAdapter = new TodoListAdapter(getApplicationContext(),listOfTodoList);
                listViewTodoList.setAdapter(todoListAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listViewTodoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoList todoList = (TodoList) adapterView.getItemAtPosition(i);

                Intent startActivityTodolist = new Intent(getApplicationContext(),TodoListActivity.class);
                startActivityTodolist.putExtra("TodoList",todoList);
                startActivity(startActivityTodolist);
            }
        });


    }

    private ArrayList<TodoList> filterArrayListTodoList(String textFilter){
        ArrayList<TodoList> arrayListTodoListTemp = new ArrayList<>();

        if(textFilter !=null){
            for(int i=0; i<listOfTodoListOriginal.size(); i++){
                if(listOfTodoListOriginal.get(i).getName().toUpperCase().contains(textFilter.toUpperCase())){
                    arrayListTodoListTemp.add(listOfTodoListOriginal.get(i));
                }
            }
        }
        else{
            arrayListTodoListTemp=listOfTodoListOriginal;
        }
        return arrayListTodoListTemp;
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.context_menu,menu);

            isActionMode = true;
            actionModeListTodoList=actionMode;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.action_delete_menu:

                    //die Elemente, die ausgewählt werden gelöschen....  supprimer les elements qui sont selectionnes (User Selection)

                databaseManager.deleteTodolist(UserSelectionListTodoList);

                    UpdateDisplayTodolist();
                    actionMode.finish();

                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

            isActionMode = false;
            actionModeListTodoList = null;
            UserSelectionListTodoList.clear();
            UpdateDisplayTodolist();

        }
    };

    private void UpdateDisplayTodolist(){
        listOfTodoListOriginal =databaseManager.getAllTodoList();
        listOfTodoList = databaseManager.getAllTodoList();

        todoListAdapter = new TodoListAdapter(getApplicationContext(),listOfTodoList);
        listViewTodoList.setAdapter(todoListAdapter);

    }

}
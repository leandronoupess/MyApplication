package leandronoupess.de.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Currency;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="TodoListApp.db";
    private static final int DATABASE_VERSION=2;

    public DatabaseManager(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql="create table TodoList("
                +"id integer primary key autoincrement,"
                +"name text not null"
                +")";
        db.execSQL(strSql);

        String strSql2="create table Todo("
                +"id integer primary key autoincrement,"
                +"name text not null,"
                +"isDone bool not null,"
                +"idTodoList int not null"
                +")";
        db.execSQL(strSql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String strSql ="DROP TABLE Todo";
        db.execSQL(strSql);

        String strSql2="create table Todo("
                +"id integer primary key autoincrement,"
                +"name text not null,"
                +"isDone int not null,"
                +"idTodoList int not null"
                +")";
        db.execSQL(strSql2);

    }

    public ArrayList<TodoList> getAllTodoList(){
        ArrayList<TodoList> listTodo =new ArrayList<TodoList>();

        String strSql ="select * from TodoList";
        Cursor cursor =this.getWritableDatabase().rawQuery(strSql,null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String todoListName =cursor.getString(cursor.getColumnIndex("name"));
                todoListName=todoListName.replace("%","'");

                TodoList todoListObj = new TodoList();
                todoListObj.setId(id);
                todoListObj.setName(todoListName);

                listTodo.add(todoListObj);
                cursor.moveToNext();
            }
        }

        return listTodo;
    }
    public void insertTodoList(String todoListName){
        String name = todoListName.replace("'","((%))");
        String strSql = " INSERT INTO TodoList "
                +"(name) VALUES (' "
                +name +" ' )";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateTodoList(String nextext ,int idTodoList){
        nextext = nextext.replace("'" , "((%))");
        String strSql="UPDATE TodoList SET name = '"+ nextext +"'WHERE id="+idTodoList;
        this.getWritableDatabase().execSQL(strSql);

    }

    public void  deleteTodolist(ArrayList<TodoList> todoListArrayList){
        for (int i=0; i<todoListArrayList.size();i++){
            String strSql= "DELETE FROM TodoList WHERE id = "+ todoListArrayList.get(i).getId();
            this.getWritableDatabase().execSQL(strSql);

            String strSql2 = "DELETE FROM TodoList WHERE id = "+todoListArrayList.get(i).getId();
            this.getWritableDatabase().execSQL(strSql2);
        }
    }

    public ArrayList<Todo> getAllTodos(int todoListId){
        ArrayList<Todo> todos = new ArrayList<>();
        String strSql ="SELECT*FROM Todo WHERE idTodoList ="+todoListId;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql,null);


        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                boolean isDone =cursor.getInt(cursor.getColumnIndex("isDone"))>0;

                Todo todo = new Todo();
                todo.setId(id);
                todo.setName(name);
                todo.setDone(isDone);

                todos.add(todo);
                cursor.moveToNext();
            }

        }
        return todos;
    }

    public void  insertTodos(String todoName ,int todoListId){
        todoName = todoName.replace("'" , "((%))");

        String strSql = "INSERT INTO Todo( name ,isDone , idTodoList ) VALUES ('" + todoName + "'," + 0 + "," + todoListId + ")";
        this.getWritableDatabase().execSQL(strSql);

    }

    public void updateTaskStatus(int newIsDone, int todoId){
        String strSql = "UPDATE TODO SET isDone ="+newIsDone + "WHERE id ="+ todoId ;
        this.getWritableDatabase().execSQL(strSql);
    }
}

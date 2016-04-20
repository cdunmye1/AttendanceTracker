package edu.westga.attendancetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.westga.attendancetracker.model.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

//        final ArrayList<Student> arrayOfUsers = new ArrayList<Student>();
//        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
//        arrayOfUsers.add(new Student(2, "Bill Donovan"));
//        arrayOfUsers.add(new Student(3, "Henry Williams"));
//        arrayOfUsers.add(new Student(4, "Chad Smith"));
//        arrayOfUsers.add(new Student(5, "List Saunders"));
//        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
//        arrayOfUsers.add(new Student(7, "Carly Snyder"));
//        arrayOfUsers.add(new Student(8, "Mark Jackson"));
//        arrayOfUsers.add(new Student(9, "Steven Robinson"));

        final ArrayList<Student> arrayOfUsers = dbHandler.getStudentsFromClass(1);
        final ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this,
                android.R.layout.simple_list_item_1, arrayOfUsers);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                arrayOfUsers.get(position).toggleIsPresent();
                adapter.notifyDataSetChanged();

            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

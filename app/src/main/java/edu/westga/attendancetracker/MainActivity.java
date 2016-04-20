package edu.westga.attendancetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        //GridView gridview = (GridView) findViewById(R.id.gridView);
        //final ArrayList<Student> items = new ArrayList<Student>();
        //items.add(new Student(1, "Chris"));
        //items.add(new Student(2, "Bill"));
        //gridview.setAdapter(new GridAdapter(items));

        final ArrayList<Student> arrayOfUsers = new ArrayList<Student>();
        arrayOfUsers.add(new Student(1, "Chris Dunmyer"));
        arrayOfUsers.add(new Student(2, "Bill Donovan"));
        arrayOfUsers.add(new Student(3, "Henry Williams"));
        arrayOfUsers.add(new Student(4, "Chad Smith"));
        arrayOfUsers.add(new Student(5, "List Saunders"));
        arrayOfUsers.add(new Student(6, "Kaitlyn Carlisle"));
        arrayOfUsers.add(new Student(7, "Carly Snyder"));
        arrayOfUsers.add(new Student(8, "Mark Jackson"));
        arrayOfUsers.add(new Student(9, "Steven Robinson"));


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


//    private class StudentAdapter extends ArrayAdapter<Student> {
//        public StudentAdapter(Context context, ArrayList<Student> users) {
//            super(context, 0, users);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Get the data item for this position
//            Student student = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
//            }
//            // Lookup view for data population
//            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//            TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
//            // Populate the data into the template view using the data object
//            tvName.setText(student.getDisplay());
//            tvHome.setText(student.getDisplay());
//            // Return the completed view to render on screen
//            return convertView;
//        }
//    }

    //private static final int ROW_ITEMS = 3;

//    private static final class GridAdapter extends BaseAdapter {
//
//        final ArrayList<Student> mItems;
//        final int mCount;
//
//        /**
//         * Default constructor
//         * @param items to fill data to
//         */
//        private GridAdapter(final ArrayList<Student> items) {
//
//            mItems = items;
//            mCount = items.size(); //* ROW_ITEMS;
//            //mItems = new ArrayList<Student>(mCount);
//
//            // for small size of items it's ok to do it here, sync way
////            for (String item : items) {
////                // get separate string parts, divided by ,
////                final String[] parts = item.split(",");
////
////                // remove spaces from parts
////                for (String part : parts) {
////                    part.replace(" ", "");
////                    mItems.add(part);
////                }
////            }
//        }
//
//        @Override
//        public int getCount() {
//            return mCount;
//        }
//
//        @Override
//        public Object getItem(final int position) {
//            return mItems.get(position);
//        }
//
//        @Override
//        public long getItemId(final int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, final View convertView, final ViewGroup parent) {
//
//            View view = convertView;
//
//            if (view == null) {
//                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//            }
//
//            final TextView text = (TextView) view.findViewById(android.R.id.text1);
//
//            text.setText(mItems.get(position).getDisplay());
//
//            return view;
//        }
//    }
}

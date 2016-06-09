package booking.hotel.university.gulu.com.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import httphelpers.HttpManager;
import jsonparser.ReturnString;

public class Home extends AppCompatActivity {

    ListView roomsLists;
    String[] data;
    private String url;
    public String test;

    public Home(){
        test = "0";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url = "http://www.ambroseogwang.com/Bookings/Api/getRooms.php";

        final Intent loginDetails = getIntent();
        roomsLists = (ListView) findViewById(R.id.roomsLists);
        roomsLists.setPadding(10, 20, 10, 20);
        roomsLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //start the detail view activity
                Intent intent = new Intent(getApplicationContext(), Book.class);
                intent.putExtra("id", position + 1);
                intent.putExtra("detail", roomsLists.getItemAtPosition(position).toString());
                intent.putExtra("roomDesc", ReturnString.roomDescriptions[position]);
                intent.putExtra("phone", loginDetails.getStringExtra("username"));
                startActivity(intent);
                test = position+"";
                //Snackbar.make(view, "jjs", Snackbar.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask task = new MyTask();
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            }
        });

        MyTask task = new MyTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit){
            System.exit(0);
        }else if (id == R.id.action_about){
            startActivity(new Intent(this, About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            return HttpManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(ReturnString.parseData(s));
        }
    }

    private void updateDisplay(String[] s) {
        data = new String[s.length];
        data = s;
        roomsLists.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
    }

}

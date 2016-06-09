package booking.hotel.university.gulu.com.booking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import httphelpers.HttpManager;

public class Book extends AppCompatActivity {

    Button bookNow;
    TextView details, roomDetail;
    String detail, roomDesc;
    private String url;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_book);


        final Intent intent = getIntent();
        detail = intent.getStringExtra("detail");
        roomDesc = intent.getStringExtra("roomDesc");
        int id = intent.getIntExtra("id", 1);
        username = intent.getStringExtra("phone");

        url = "http://www.ambroseogwang.com/Bookings/Api/bookNow.php?id="+id+"&username="+username;

        details = (TextView) findViewById(R.id.details);
        details.setText(detail);
        roomDetail = (TextView) findViewById(R.id.roomDetail);
        roomDetail.setText(roomDesc);

        bookNow = (Button) findViewById(R.id.bookNow_btn);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask();
                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(this, About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            return HttpManager.postData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(s);
        }
    }

    public void updateDisplay(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}

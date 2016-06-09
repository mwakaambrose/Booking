package booking.hotel.university.gulu.com.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import httphelpers.HttpManager;
import jsonparser.ReturnString;

public class Login extends AppCompatActivity {

    TextView username;
    Button loginBtn;
    private String url;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        intent = new Intent(getApplicationContext(), Home.class);
        username = (TextView) findViewById(R.id.username);
        loginBtn = (Button) findViewById(R.id.loginBtn);


        url = "http://www.ambroseogwang.com/Bookings/Api/login.php?username=";
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length() != 0) {
                    MyTask myTask = new MyTask();
                    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url + username.getText());
                    intent.putExtra("username", username.getText().toString());
                } else {
                    Snackbar.make(v, "Please provide a mobile money enabled phone number", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return HttpManager.postData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(s);
        }
    }

    private void updateDisplay(String s) {
        if (s.length() != 0) {
            //Toast.makeText(getApplicationContext(), "logged", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Number Doesn't Exist, Creating Account \nand Loging in Now", Toast.LENGTH_LONG).show();
        }

    }
}

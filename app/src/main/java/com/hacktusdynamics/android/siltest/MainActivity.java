package com.hacktusdynamics.android.siltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacktusdynamics.android.siltest.background.SimpleAsyncTask;
import com.hacktusdynamics.android.siltest.model.ReturnedObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SimpleAsyncTask.ILoginReadyListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn);
        mTextView = (TextView) findViewById(R.id.text_view);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                LoginModel loginModel = new LoginModel();
                SimpleAsyncTask task = new SimpleAsyncTask(MainActivity.this, getParent());
                task.execute(loginModel);
                */
                String url = "https://raw.github.com/square/okhttp/master/README.md";

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //String s1 = Arrays.toString(responseBody);
                        //String s2 = new String(s1);
                        mTextView.setText(new String(responseBody));
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
    }

    // Callback method from SimpleAsyncTask
    @Override
    public void LoginReady(ReturnedObject returnedObject) {
        if(returnedObject != null)
            mTextView.setText(returnedObject.getText());
    }
}

package com.hacktusdynamics.android.siltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacktusdynamics.android.siltest.background.SimpleAsyncTask;
import com.hacktusdynamics.android.siltest.model.LoginModel;
import com.hacktusdynamics.android.siltest.model.ReturnedObject;

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
                LoginModel loginModel = new LoginModel();
                SimpleAsyncTask task = new SimpleAsyncTask(MainActivity.this, getParent());
                task.execute(loginModel);
            }
        });
    }

    // Callback method from SimpleAsyncTask
    @Override
    public void LoginReady(ReturnedObject returnedObject) {
        if(returnedObject != null)
            mTextView.setText(returnedObject.toString());
    }
}

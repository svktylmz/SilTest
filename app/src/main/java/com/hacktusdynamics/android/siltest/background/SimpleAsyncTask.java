package com.hacktusdynamics.android.siltest.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hacktusdynamics.android.siltest.model.LoginModel;
import com.hacktusdynamics.android.siltest.model.ReturnedObject;

import java.io.IOException;

public class SimpleAsyncTask extends AsyncTask<LoginModel, String, ReturnedObject>{
    private static final String TAG = SimpleAsyncTask.class.getSimpleName();

    public interface ILoginReadyListener{
        public void LoginReady(ReturnedObject returnedObject);
    }

    private Context mContext;
    private ILoginReadyListener mListener;
    private ProgressDialog mProgressDialog;
    private LoginModel mLoginModel;

    //constructor
    public SimpleAsyncTask(Context context, Activity activity){
        super();
        mContext = context;
        mListener = (ILoginReadyListener) activity;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(mContext, "Please wait", "Process in progress", true, true);
    }

    @Override
    protected ReturnedObject doInBackground(LoginModel... loginModels) {
        mLoginModel = loginModels[0];
        publishProgress("Step 1");

        ReturnedObject r = null;
        try {
            r = NetworkManager.LoginToSystem(mLoginModel);
        }catch (IOException e){
            e.printStackTrace();
        }

        return r;
    }

    @Override
    protected void onPostExecute(ReturnedObject returnedObject) {
        super.onPostExecute(returnedObject);
        mProgressDialog.cancel();
        mListener.LoginReady(returnedObject);
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        mProgressDialog.setMessage(progress[0]);
    }
}

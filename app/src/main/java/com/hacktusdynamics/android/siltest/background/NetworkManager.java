package com.hacktusdynamics.android.siltest.background;

import android.util.Log;

import com.hacktusdynamics.android.siltest.MainActivity;
import com.hacktusdynamics.android.siltest.model.LoginModel;
import com.hacktusdynamics.android.siltest.model.ReturnedObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.hacktusdynamics.android.siltest.background.NetworkManager.parseTheContent;

public class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();


    public static ReturnedObject LoginToSystem(LoginModel loginModel) throws IOException {
        try {
            ReturnedObject returnedObject = null;

            //Create a client, a post request and paramenters arraylist
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(MainActivity.TM_LOGON_CUSTOMER);
            ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();

            //set request information
            parameters.add(new BasicNameValuePair("UserName", loginModel.getUserName()));
            parameters.add(new BasicNameValuePair("Password", loginModel.getPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));

            //execute the request
            HttpResponse httpResponse = httpClient.execute(httpPost);

            //if response is ok
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                //get response content
                HttpEntity httpEntity = httpResponse.getEntity();

                //convert the entity{the resulting page} to string and parse it
                String content = EntityUtils.toString(httpEntity, "UTF-8");

                Log.d(TAG, "content !!!!!!!!!!!!!!!" + content);
                if (content.equals("null")) {
                    return null;
                }
                returnedObject = parseTheContent(content);
            }

            return returnedObject;

        } catch (Exception e) {
            Log.e(TAG, "Error occured while downloading", e);
            return null;
        }
    }

    private static ReturnedObject parseTheContent(String content) {
        // parse the content
        try {
            ReturnedObject returnedObject = null;
            JSONObject customerJO = new JSONObject(content);

            int customerId = Integer.parseInt(customerJO.getString("CustomerId"));
            String customerName = customerJO.getString("CustomerName");
            String email = customerJO.getString("Email");
            String gsm = customerJO.getString("GSM");
            double lat = customerJO.getDouble("Lat");
            double lng = customerJO.getDouble("Lng");
            String password = customerJO.getString("Password");

            returnedObject = new ReturnedObject(customerId, customerName, email, gsm, lat, lng, password);
            Log.d(TAG, "parsed object" + returnedObject.toString());
            return returnedObject;
        } catch (Exception e) {
            Log.e(TAG, "Error on parsing " + e);
            return null;
        }
    }
}

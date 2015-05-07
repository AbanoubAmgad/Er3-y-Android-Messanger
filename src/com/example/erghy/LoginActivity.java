package com.example.erghy;


import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	 // Progress Dialog
	Button login;
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText inputPass;
    EditText inputID;
    public static String id ; 

 
    // url to create new Account
    private static String url_create_Account = "http://phpfiles.erghy777.comyr.com/login.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Sign In") ;
        login = (Button) findViewById(R.id.Login2);
        
        
        login.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View view) {
            	inputID = (EditText) findViewById(R.id.userid);
                inputPass  = (EditText) findViewById(R.id.passlog);
                Log.e("Here!", "Calling login function!" + inputPass.getText().toString() + " " + inputID.getText().toString() );
                new Signin().execute();
            }
        });     
    }
 
    /**
     * Background Async Task to Create new Account
     * */
    class Signin extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Signing You in..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * Creating Account
         * */
        protected String doInBackground(String... args) {
        	Log.e("Here!", "Begining Doinbackground!");
            String UserPass = inputPass.getText().toString();
            String UserID = inputID.getText().toString();
            id = UserID ;
            Log.e("Here!", UserPass + " " + UserID);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("UserID", UserID));
            params.add(new BasicNameValuePair("UserPass", UserPass));
            Log.e("Here!", "Before JSON!");
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_Account,
                    "GET", params);
            
            // check log cat for response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully logged in
                    Intent i = new Intent(getApplicationContext(), FriendsList.class);
                    startActivity(i);
                      
                    // closing this screen
                    finish();
                } else {
                    // failed to create Account
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
}

package com.example.erghy;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class NewAccountActivity extends Activity {
     
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPass;
    EditText inputID;
    EditText inputMail;
 
    // url to create new Account
    private static String url_create_Account = "http://phpfiles.erghy777.comyr.com/create_account.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setTitle("Sign Up") ;
 
        // Edit Text
        inputName = (EditText) findViewById(R.id.nameRegister);
        inputPass = (EditText) findViewById(R.id.PasswordRegister);
        inputID = (EditText) findViewById(R.id.UserIdRegister);
        inputMail = (EditText) findViewById(R.id.EmailRegister);
 
        // Create button
        Button btnCreateAccount = (Button) findViewById(R.id.signup2);
 
        // button click event
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewAccount().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new Account
     * */
    class CreateNewAccount extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewAccountActivity.this);
            pDialog.setMessage("Creating Account..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * Creating Account
         * */
        protected String doInBackground(String... args) {
            String UserName = inputName.getText().toString();
            String UserPass = inputPass.getText().toString();
            String UserID = inputID.getText().toString();
            String UserMail = inputMail.getText().toString();
            //System.out.println(UserName) ;
           // System.out.println(UserPass) ;
           // System.out.println(UserID) ;
            //System.out.println(UserMail) ;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("UserName", UserName));
            params.add(new BasicNameValuePair("UserPass", UserPass));
            params.add(new BasicNameValuePair("UserID", UserID));
            params.add(new BasicNameValuePair("UserMail", UserMail));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_Account,
                    "POST", params);
 
            // check log cat for response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {

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
            finish();
        }
 
    }
}

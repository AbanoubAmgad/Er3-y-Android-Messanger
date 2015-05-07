package com.example.erghy;


import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



 
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class FriendsList extends Activity {
	 // Progress Dialog
	Button login;

 
    JSONParser jsonParser = new JSONParser();

    public static String [] values ;

 
    // url to create new Account
    private static String url_create_Account = "http://phpfiles.erghy777.comyr.com/friendList.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);
        setTitle("Contact List") ;    
         new Signin().execute() ;
    }
 
    /**
     * Background Async Task to Create new Account
     * */
    class Signin extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
        	Log.e("Here!", "Begining Doinbackground!");
            String UserID =LoginActivity.id;
            Log.e("Here!",  UserID);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("UserID", UserID));
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
                	Log.e("suc", json.getString("Message"));
                	String friends = json.getString("Message") ;
                	values = friends.split("\\r?\\n");
                	finish() ;
                	Intent i = new Intent(getApplicationContext(), ContactListView.class);
                    startActivity(i);
     
                } else {
                	finish() ;
                	Intent i = new Intent(getApplicationContext(), ContactListView.class);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/

 
    }
    

}

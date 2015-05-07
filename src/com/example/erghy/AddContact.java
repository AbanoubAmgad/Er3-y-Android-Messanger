package com.example.erghy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.erghy.ContactListView.PerformBackgroundTask2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddContact extends Activity{
	EditText contact ;
	Button add ;
	String UserID = LoginActivity.id ; 
	private static String url_add = "http://phpfiles.erghy777.comyr.com/addfriend.php";
	private static final String TAG_SUCCESS = "success";
	JSONParser jsonParser = new JSONParser();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);
        setTitle("Add a Friend!") ;

       add = (Button) findViewById(R.id.addContactbutton);
       contact = (EditText) findViewById(R.id.newContact);
       
       
       add.setOnClickListener(new View.OnClickListener() {       	 
           @Override
           public void onClick(View view) {
        	   new PerformBackgroundTask(contact.getText().toString()).execute() ;
        	   finish();
        	   Intent i = new Intent(getApplicationContext(), FriendsList.class);
               startActivity(i);
           }
       });
  

        
      
}
    
	class PerformBackgroundTask extends AsyncTask<String, String, String> {
	     public String contact ; 
	     public PerformBackgroundTask (String string) {
	    	 this.contact = string ;
	     }
			
	    protected String doInBackground(String... args) {
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("UserID", UserID));
	        params.add(new BasicNameValuePair("Contact", contact));
	        
	        Log.e("Here!", "Before JSON!");
	        // getting JSON Object
	        // Note that create product url accepts POST method
	        JSONObject json = jsonParser.makeHttpRequest(url_add,
	                "GET", params);
	        
	        // check log cat for response
	        Log.d("Create Response", json.toString());

	        // check for success tag
	        try {
	            int success = json.getInt(TAG_SUCCESS);

	            if (success == 1) {
	             String msg = json.getString("Message");
	             Log.e("Message Is", msg) ;
	             onPostExecute(msg) ;
	             return msg ;
	             
	            } else {
	                // failed to create Account
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }

	        return null;
	    }

	}
}

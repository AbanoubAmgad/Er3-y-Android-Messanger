package com.example.erghy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.erghy.ChatBox.PerformBackgroundTask;

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

public class ContactListView extends Activity {
    static JSONParser jsonParser = new JSONParser();
    EditText inputPass;
    EditText inputID; 
    String UserID = LoginActivity.id ; 
	private static final String TAG_SUCCESS = "success";
    Button signout;
    Button addContact;
    Button refresh;
    private static String url_state = "http://phpfiles.erghy777.comyr.com/friendstate.php";
    private static String url_acclogoff = "http://phpfiles.erghy777.comyr.com/acclogoff.php";
	public static String st ;
	public static Contacts fullObject ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);
        signout = (Button) findViewById(R.id.Signout);
        addContact = (Button) findViewById(R.id.addContact);
        refresh = (Button) findViewById(R.id.refresh);
        
        signout.setOnClickListener(new View.OnClickListener() {       	 
            @Override
            public void onClick(View view) {
            	new PerformBackgroundTask2(UserID).execute();
            	finish();

            }
        });
        
        addContact.setOnClickListener(new View.OnClickListener() {       	 
            @Override
            public void onClick(View view) {
            	finish();
                Intent i = new Intent(getApplicationContext(), AddContact.class);
                startActivity(i);
            }
        });
        
        refresh.setOnClickListener(new View.OnClickListener() {       	 
            @Override
            public void onClick(View view) {
            	finish();
                Intent i = new Intent(getApplicationContext(), FriendsList.class);
                startActivity(i);
            }
        });
        
        ArrayList<Contacts> searchResults = getContacts(FriendsList.values);
        for ( int i = 0 ; i < searchResults.size() ; i++){
        	Log.e("contact", searchResults.get(i).getName()) ;
        }
        final ListView lv1 = (ListView) findViewById(R.id.contactlist);
        lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
        		Object o = lv1.getItemAtPosition(position);
            	fullObject = (Contacts)o;
        		Toast.makeText(ContactListView.this, "Contacting " + " " + fullObject.getName(), Toast.LENGTH_LONG).show();
        		Intent i = new Intent(getApplicationContext(), ChatBox.class);
                startActivity(i);
                finish();
        	}  
        });
    }
    
    public static ArrayList<Contacts> getContacts(String[] values) {
    	ArrayList<Contacts> temp = new ArrayList<Contacts>() ;
    	String stat = null ;
    	if (values.length == 0) {
    		Contacts t = new Contacts () ; 
    		t.setName("No Contacts") ;
    		t.setCityState("You Have No Contacts Yet !") ;
    		temp.add(t);
    		return temp;
    	}
    	else {
    	for ( int i = 0 ; i < values.length ; i++) {
    		Contacts t = new Contacts () ; 
    		t.setName(values[i]) ;
    		try {
				stat = new PerformBackgroundTask(values[i]).execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		t.setCityState(stat) ;
    		temp.add(t);
    	}
		return temp;}
	}
    
	
	public static class PerformBackgroundTask extends AsyncTask<String, String, String> {
     public String user ; 
     public PerformBackgroundTask (String user) {
    	 this.user = user ;
     }
		
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("UserID", user));
        Log.e("Here!", "Before JSON!");
        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_state,
                "GET", params);
        
        // check log cat for response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
             String msg = json.getString("message");
             Log.e("Message Is", msg) ;
             return msg ;
             
            } else {
            	   String msg = json.getString("message");
                   Log.e("Message Is", msg) ;   
                   return msg ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }   
}
	
    public static class PerformBackgroundTask2 extends AsyncTask<String, String, String> {
        public String user ; 
        public PerformBackgroundTask2 (String user) {
       	 this.user = user ;
        }
   		
       protected String doInBackground(String... args) {
           List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("UserID", user));
           Log.e("Here!", "Before JSON!");
           // getting JSON Object
           // Note that create product url accepts POST method
           JSONObject json = jsonParser.makeHttpRequest(url_acclogoff,
                   "GET", params);
           
           // check log cat for response
           Log.d("Create Response", json.toString());

           // check for success tag
           try {
               int success = json.getInt(TAG_SUCCESS);

               if (success == 1) {
                String msg = json.getString("message");
                Log.e("Message Is", msg) ;
                return msg ;
                
               } else {
               	   String msg = json.getString("message");
                      Log.e("Message Is", msg) ;   
                      return msg ;
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;
       }


}
	
}
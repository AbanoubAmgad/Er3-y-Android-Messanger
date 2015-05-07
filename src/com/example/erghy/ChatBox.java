package com.example.erghy;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatBox extends Activity {
    static Timer timer ;
	String ConversationId ;
	String Message ; 
	String UserID = LoginActivity.id ; 
	String Contact = ContactListView.fullObject.getName() ;
    Button send;
    Button list;
    EditText msg ; 
	TextView msgbox , contacts ; 
	private static final String TAG_SUCCESS = "success";
	JSONParser jsonParser = new JSONParser();
	JSONParser jsonParser2 = new JSONParser();
	private static String url_feed = "http://phpfiles.erghy777.comyr.com/feed.php";
	private static String url_msg = "http://phpfiles.erghy777.comyr.com/msg.php";
	 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);
        setTitle("Er3'y!") ;
        
        msgbox = (TextView) findViewById(R.id.msgs);
        contacts = (TextView) findViewById(R.id.contacts);
        contacts.setText(Contact) ;
        
        Log.e("Here!", "Before msg!");
        send = (Button) findViewById(R.id.send);
        list = (Button) findViewById(R.id.ls);
        msg = (EditText) findViewById(R.id.msg) ;
        
        list.setOnClickListener(new View.OnClickListener() {       	 
            @Override
            public void onClick(View view) {
            	timer = null ;
            	onDestroy();
            	finish();
                Intent i = new Intent(getApplicationContext(), FriendsList.class);
                startActivity(i);
            }
        });
        
        send.setOnClickListener(new View.OnClickListener() {    	 
            @Override
            public void onClick(View view) {
            	new sendMsg().execute() ;            	
            }
        });
 
        
        if (UserID.compareTo(Contact) < 0){
        	ConversationId = UserID+Contact ;
        }
        else {
        	ConversationId = Contact+UserID ;
        }
        Log.e("Conv ID", ConversationId ) ;
 
        callAsynchronousTask(msgbox);
        Log.e("DONE", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" ) ;
 }
	
	public void callAsynchronousTask(final TextView msgbox) {
	    final Handler handler = new Handler();
	    timer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                    try {
	                        PerformBackgroundTask performBackgroundTask = new PerformBackgroundTask(msgbox);
	                        // PerformBackgroundTask this class is the class that extends AsynchTask 
	                        performBackgroundTask.execute();
	                    } catch (Exception e) {
	                        // TODO Auto-generated catch block
	                    }
	                }
	            });
	        }
	    };
	    timer.schedule(doAsynchronousTask, 0, 4000); //execute in every 50000 ms
	}

	
	class PerformBackgroundTask extends AsyncTask<String, String, String> {
     public TextView chat ; 
     public PerformBackgroundTask (TextView text) {
    	 this.chat = text ;
     }
		
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ConversationID", ConversationId));
        Log.e("Here!", "Before JSON!");
        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_feed,
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
    
    protected void onPostExecute(final String msg) {
    	runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe to modify UI elements.
            	Log.e("Message Is", msg) ;
            	chat.setText(msg) ;
            }
        });
    	
    }

}
	
	class sendMsg extends AsyncTask<String, String, String> {
		 

        protected String doInBackground(String... args) {
        	Message = msg.getText().toString();
        	Log.e("Message!", Message);
        	List<NameValuePair> paramts = new ArrayList<NameValuePair>();
        	paramts.add(new BasicNameValuePair("ConversationID", ConversationId));
        	paramts.add(new BasicNameValuePair("UserID", UserID));
        	paramts.add(new BasicNameValuePair("Message", Message));
            Log.e("Here!", "Before JSON!");
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json2 = jsonParser2.makeHttpRequest(url_msg,
                    "GET", paramts);
            Log.d("Create Response", json2.toString());
            
            return null;
        }
        
        protected void onPostExecute(final String as) {
        	runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // This code will always run on the UI thread, therefore is safe to modify UI elements.
                   msg.setText("");
                }
            });
        	
        }

 
    }
		
}

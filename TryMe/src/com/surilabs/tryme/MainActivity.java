package com.surilabs.tryme;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends ActionBarActivity 
{

    private LoginButton mLoginButton;
	private final String TAG = "MainActivity";
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() 
    {
        @Override
        public void call(Session session, SessionState state, Exception exception)
        {
        	Log.i(TAG, "state call back");
        	Log.i(TAG, "state = " + state.toString());
        	if(state.toString().equals("OPENED"))
        		Toast.makeText(MainActivity.this, "Login Successful", 
                    Toast.LENGTH_SHORT).show();
        	else
        		Toast.makeText(MainActivity.this, "Logged Out", 
                        Toast.LENGTH_SHORT).show();
           // this.call(session, state, exception);
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
		
		mLoginButton = (LoginButton) findViewById(R.id.login_button);
		mLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
        mLoginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() 
        {
            @Override
            public void onUserInfoFetched(GraphUser user) 
            {
            	Log.i(TAG, "user info fetched!!");
            	if(user != null)
            	{
	            	Log.i(TAG, "id = " + user.getId());
	            	Log.i(TAG, "name = " + user.getName());
	            	Request request = new Request( Session.getActiveSession(),
	            		    "/" + user.getId() + "/friends",
	            		    null,
	            		    HttpMethod.GET,
	            		    new Request.Callback() {
	            		        public void onCompleted(Response response) 
	            		        {
	            		            /* handle the result */
	            		        	Log.i(TAG, response.getRawResponse());
	            		        }
	            		    }
	            		);
	            	request.executeAsync();
            	}
            	else
            	{
            		Log.i(TAG, "user info null");
            	}
                //MainActivity.this.user = user;
                //updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                //handlePendingAction();
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}
	
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data); 
        uiHelper.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
    
	public void createChallenge(View v)
	{
		Intent i = new Intent(this, ChallengeCreator.class);
    	startActivity(i);
	}

}

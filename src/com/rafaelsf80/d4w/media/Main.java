/**
 * Created by wilfrid on 9/23/14.
 */

package com.rafaelsf80.d4w.media;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity {
	
    private final String TAG = getClass().getSimpleName();
     
    public Config configs;
    public ConfigStore store = new ConfigStore();
    public RecyclerView recyclerView;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Log.d(TAG, "onCreate()");
    	
		//enable window content transition
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //set the transition
        Transition ts = new Explode();  
        ts.setStartDelay(500);
        //set the duration
        ts.setDuration(500);
        getWindow().setEnterTransition(ts);
        //set an exit transition so it is activated when the current activity exits
        getWindow().setExitTransition(ts);
        
        
        setContentView(R.layout.main);
    	actionBar = getActionBar();
    
    	// download Config sheet (ConfigTask) who calls DataTask
    	ConfigTask configTask = new ConfigTask(this);
    	configTask.execute();

    	recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    	recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.options_refresh_data) {

            ConfigTask configTask = new ConfigTask(this);
            configTask.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
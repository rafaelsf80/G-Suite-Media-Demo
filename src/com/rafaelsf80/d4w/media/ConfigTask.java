/**
 * Created by wilfrid on 11/25/14. 
 */

package com.rafaelsf80.d4w.media;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfigTask extends AsyncTask<Void, Void, Void> {
   private final String TAG = getClass().getSimpleName();

    protected Main mActivity;

    public ConfigTask(Main activity) {
        this.mActivity = activity;
        Log.d(TAG, "ConfigTask constructor");
    }

    ArrayList<Config> configs = new ArrayList<Config>();

    @Override
    protected Void doInBackground(Void... params) {
        String result = null;
        try {
            HttpClient hc = new DefaultHttpClient();
            // The script must be on gmail.com account to access anonimously
            String 	URL = "https://script.google.com/macros/s/AKfycbx_SoYcUoW0A2wMzO5gQIYZaE_e1S5xUee2ta16M5BXZaq_N6E/exec";
            HttpGet get = new HttpGet(URL);
            HttpResponse rp = hc.execute(get);

            // grab JSON from the URL above and store it in the items class
            Log.d(TAG, "Status Code " + Integer.toString(rp.getStatusLine().getStatusCode()));
            if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                result = EntityUtils.toString(rp.getEntity());
                JSONArray objects = new JSONArray(result);

                for (int i = 0; i < objects.length(); i++) {
                    JSONObject session = objects.getJSONObject(i);
                    Config config = new Config();
                    config.appName = session.getString("appname");
                    config.subTitle = session.getString("subtitle");
                    config.colorScheme = session.getString("colourscheme");
                    configs.add(config);
                    Log.d(TAG, config.toString());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading Config", e);
            Log.e(TAG, result);

        }
        Log.d(TAG, "Bye");
        return null;
    }

    protected void onPostExecute(Void result)
    {    	
    	Config object = configs.get(0);

    	mActivity.store.setData(object);
    	Log.d(TAG, object.toString());
    	
    	DataTask task = new DataTask(mActivity);
    	task.execute();
    }
}

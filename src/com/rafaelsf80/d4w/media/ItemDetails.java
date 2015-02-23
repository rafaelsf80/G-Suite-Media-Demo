/**
 * Created by wilfrid on 9/23/14.
 */

package com.rafaelsf80.d4w.media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ItemDetails extends Activity {

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//enable window content transition
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

		//set the transition
		Transition ts = new Slide();  
		ts.setDuration(500);
		getWindow().setEnterTransition(ts);
		getWindow().setExitTransition(ts);

		setContentView(R.layout.itemdetails);

		// create variables to store the item details UI elements
		TextView itemDetailsTitle = (TextView) findViewById(R.id.title_DT);
		TextView descriptionTextView = (TextView) findViewById(R.id.tv_description_DT);
        Button durationView = (Button) findViewById(R.id.bt_duration_DT);
		Button trailerView = (Button) findViewById(R.id.bt_trailer_DT);
        TextView contentProviderTextView  = (TextView) findViewById(R.id.tv_contentProvider_DT);
        TextView metadataTextView  = (TextView) findViewById(R.id.metadata_DT);

        // gather data which was passed from the selected list item
		Intent fromListItem = getIntent();

		final String contentName = fromListItem.getStringExtra("contentName");
		String mainImage = fromListItem.getStringExtra("mainImage");
		final String description = fromListItem.getStringExtra("description");
		String duration = fromListItem.getStringExtra("duration");
		final String videoLink = fromListItem.getStringExtra("videoLink");
		final String contentProvider = fromListItem.getStringExtra("contentProvider");
		final String metadata = fromListItem.getStringExtra("metadata");

		// set UI information to the data which has been parsed through
		setTitle(contentName);
		itemDetailsTitle.setText(contentName);
		descriptionTextView.setText("Description: " + description);
		durationView.setText(duration);
        contentProviderTextView.setText("Content Provider: " + contentProvider);
		metadataTextView.setText("Meta Data: " + metadata);


		// download thumbnail
		ImageView iconView = (ImageView) findViewById(R.id.mainimage_DT);
		Picasso.with(this)
		.load(mainImage)
		.into(iconView	);


		trailerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
				startActivity(intent);

				// create new toast to update the user what video is about to be played
				Toast.makeText(getApplicationContext(), "Playing " + contentName + " Trailer",
						Toast.LENGTH_LONG).show();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemdetails, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
}

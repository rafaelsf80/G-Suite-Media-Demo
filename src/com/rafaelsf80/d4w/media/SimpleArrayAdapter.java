/**
 * Created by wilfrid on 9/23/14.
 */

package com.rafaelsf80.d4w.media;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

// ArrayAdapter is a special kind of ListAdapter which supplies data to ListView
public class SimpleArrayAdapter   extends RecyclerView.Adapter<SimpleArrayAdapter.ViewHolder> {
	
	private final String TAG = getClass().getSimpleName();
    public static Context context;
    public Activity activity;
    private ArrayList<Item> values;
    private Config currentConfig = null;
   
    public SimpleArrayAdapter(Context context, Activity activity, ArrayList<Item> values, Config object) {
        this.context = context;
        this.values = values;
        this.activity = activity;

        currentConfig = object;      
    }

	public Config getCurrentConfig() {
		
		return currentConfig;
	}


	public void setCurrentConfig(Config currentConfig) {
		this.currentConfig = currentConfig;
	}

	public ArrayList<Item> getValues() {
		return values;
	}

	
	public void setValues(ArrayList<Item> values) {
		this.values = values;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder rowView, int position) {
		// get the item which has been pressed and store current item data into variables

        Item i = values.get(position);

        final String contentName = i.contentName;
        final String description = i.description;
        final String imageLink = i.imageURL;
        final String videoLink = i.videoURL;
        final String duration = i.duration;
        final String contentProvider = i.contentProvider;
        final String metadata = i.metadata;

        // set list menu content to variables
        rowView.tvTitle.setText(contentName);
        rowView.tvDescription.setText(description);
        rowView.btDuration.setText(duration);
        rowView.cardView.setCardBackgroundColor(Color.parseColor(currentConfig.colorScheme));
        
        // download thumbnail
        Picasso.with(context)
        .load(imageLink)
        .into(rowView.imThumbnail);


        // listener to detect whether the watch video item has been pressed
        rowView.btTrailer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick btVideoPreview");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                context.startActivity(intent);

                // create new toast to update the user what video is about to be played
                Toast.makeText(context, "Playing " + contentName + " Trailer",
                        Toast.LENGTH_LONG).show();

            }
        });

        // listener to detect whether a list item has been pressed
        rowView.tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				Log.d(TAG, "onClick tvTitle");
				
        		Intent intent = new Intent(v.getContext(), ItemDetails.class);

        		// when a list item has been pressed move to the item details screen,  passing the following data				
        		intent.putExtra("contentName", contentName);
        		intent.putExtra("mainImage", imageLink);
        		intent.putExtra("description", description);
        		intent.putExtra("videoLink", videoLink);
        		intent.putExtra("duration", duration);
        		intent.putExtra("contentProvider", contentProvider);
        		intent.putExtra("metadata", metadata);

        		// move to the item details screen
        		context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        	}
        });	     
	}
	

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
		ViewHolder viewHolder = new ViewHolder(itemLayoutView);
		
		itemLayoutView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick itemLayoutView");
				
			}
		});
		return viewHolder;
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder{
		
		public CardView cardView;
		public TextView tvTitle;
		public TextView tvDescription;
        public ImageView imThumbnail;
        public Button btTrailer;
        public Button btDuration;


        public ViewHolder(View rowView) {
			super(rowView);
		
	        // store UI elements in a variable to be dynamically changed
			cardView = (CardView) rowView.findViewById(R.id.my_card_view);
            tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
            tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
            imThumbnail = (ImageView) rowView.findViewById(R.id.im_thumbnail);
            btTrailer = (Button) rowView.findViewById(R.id.bt_trailer);
            btDuration = (Button) rowView.findViewById(R.id.bt_duration);
		}
	}
}

package com.niketest.giphyview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.niketest.giphyview.R;
import com.niketest.giphyview.model.GiphyPhoto;

public class GiphyPhotoViewActivity extends AppCompatActivity
{
	public static final String EXTRA_GIPHY_PHOTO = "giphyPhoto";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giphy_photo_view_activity);

		// Setup the toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// Get the GiphyPhoto from the input Intent
		Intent intent = getIntent();
		String photoJson = intent.getStringExtra(EXTRA_GIPHY_PHOTO);
		Gson gson = new Gson();
		GiphyPhoto photo = gson.fromJson(photoJson, GiphyPhoto.class);

		// Load the image
		ImageView giphyImage = (ImageView)findViewById(R.id.giphy_photo);
		Glide.with(this)
			.load(photo.getFixedHeightUrl())
			.into(giphyImage);

		// Display the user's "display_name"
		final String displayNameFmt = getString(R.string.user_displayname);
		final TextView displayName = (TextView)findViewById(R.id.giphy_user_displayname);
		if ((photo.user != null) && !TextUtils.isEmpty(photo.user.display_name))
			displayName.setText(String.format(displayNameFmt, photo.user.display_name));
		else
			displayName.setText(String.format(displayNameFmt, getString(R.string.unknown)));

		// Display the photo's rating
		final String ratingFmt = getString(R.string.photo_rating);
		final TextView rating = (TextView)findViewById(R.id.giphy_rating);
		if (!TextUtils.isEmpty(photo.rating))
			rating.setText(String.format(ratingFmt, photo.rating.toUpperCase()));
		else
			rating.setText(String.format(ratingFmt, getString(R.string.unknown)));

		// Display the photo's source
		final String sourceFmt = getString(R.string.photo_source);
		final TextView source = (TextView)findViewById(R.id.giphy_source);
		if (!TextUtils.isEmpty(photo.source))
			source.setText(String.format(sourceFmt, photo.source));
		else
			source.setText(String.format(sourceFmt, getString(R.string.unknown)));

		// Display the photo's size
		final String sizeFmt = getString(R.string.photo_size);
		final TextView photoSize = (TextView)findViewById(R.id.giphy_size);
		final GiphyPhoto.GiphyImage image = photo.images.fixed_height;
		if (image.width > 0 && image.height > 0)
			photoSize.setText(String.format(sizeFmt, image.width, image.height));
		else
			photoSize.setVisibility(View.GONE);
	}
}

package com.niketest.giphyview.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.niketest.giphyview.R;

/**
 * The view holder for displaying a {@link com.niketest.giphyview.model.GiphyPhoto}
 */
public class GiphyPhotoViewHolder extends RecyclerView.ViewHolder
{

	public ImageView imageView;

	public GiphyPhotoViewHolder(View view)
	{
		super(view);
		imageView = (ImageView)view.findViewById(R.id.giphy_photo);
	}
}

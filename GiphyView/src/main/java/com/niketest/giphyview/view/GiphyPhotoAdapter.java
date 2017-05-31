package com.niketest.giphyview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.niketest.giphyview.R;
import com.niketest.giphyview.model.GiphyPhoto;
import com.niketest.giphyview.model.GiphyPhotos;

/**
 * The adapter class for displaying some {@link GiphyPhotos}
 */
public class GiphyPhotoAdapter extends RecyclerView.Adapter<GiphyPhotoViewHolder>
{
	private Context context;
	private GiphyPhotos photos;
	private View.OnClickListener listener;

	public GiphyPhotoAdapter(Context context, GiphyPhotos photos, View.OnClickListener listener)
	{
		super();
		this.context = context;
		this.photos = photos;
		this.listener = listener;
	}

	@Override
	public GiphyPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(context).inflate(R.layout.giphy_photo_view, parent, false);
		GiphyPhotoViewHolder holder = new GiphyPhotoViewHolder(v);
		holder.imageView.setOnClickListener(listener);
		return holder;
	}

	@Override
	public void onBindViewHolder(GiphyPhotoViewHolder holder, int position)
	{
		if ((photos != null) && (photos.size() > 0))
		{
			GiphyPhoto photo = photos.get(position);
			holder.imageView.setTag(R.id.giphy_photo_tag, photo);
			Glide.with(context)
				.load(photo.getFixedHeightUrl())
				.into(holder.imageView);
		}
	}

	@Override
	public int getItemCount()
	{
		return (photos != null) ? photos.size() : 0;
	}
}

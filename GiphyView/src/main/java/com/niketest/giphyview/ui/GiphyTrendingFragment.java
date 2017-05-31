package com.niketest.giphyview.ui;

import com.niketest.giphyview.net.GiphyNetRequest;
import com.niketest.giphyview.net.GiphyTrendingRequest;

/**
 * A {@link GiphyApiFragment} that implements the Giphy trending API
 */
public class GiphyTrendingFragment extends GiphyApiFragment
{
	public GiphyTrendingFragment()
	{
		super();
	}

	@Override
	protected GiphyNetRequest getRequest()
	{
		return new GiphyTrendingRequest(giphyPhotos.size());
	}
}

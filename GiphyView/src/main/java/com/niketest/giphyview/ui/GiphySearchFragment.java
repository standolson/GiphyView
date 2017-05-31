package com.niketest.giphyview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niketest.giphyview.net.GiphyNetRequest;
import com.niketest.giphyview.net.GiphySearchRequest;
import com.niketest.giphyview.net.GiphyTrendingRequest;

/**
 * A {@link GiphyApiFragment} that implements the Giphy search API
 */
public class GiphySearchFragment extends GiphyApiFragment
{
	public static final String EXTRA_QUERY_STRING = "queryString";

	public GiphySearchFragment()
	{
		super();
	}

	@Override
	protected GiphyNetRequest getRequest()
	{
		final String queryString = getArguments().getString(EXTRA_QUERY_STRING);
		return new GiphySearchRequest(queryString, giphyPhotos.size());
	}
}

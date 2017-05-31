package com.niketest.giphyview.net;

import com.niketest.giphyview.model.GiphyPhoto;
import com.niketest.giphyview.model.GiphyPhotos;

import java.util.ArrayList;

/**
 * The response returned by all paged Giphy API calls
 */
public class GiphyNetResponse
{
	public class Pagination
	{
		public int count;
		public int offset;
	}

	public class Meta
	{
		public int status;
		public String msg;
		public String response_id;
	}

	public GiphyPhotos data;
	public Pagination pagination;
	public Meta meta;
}

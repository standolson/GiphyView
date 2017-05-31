package com.niketest.giphyview.net;

import java.net.URL;

/**
 * A {@link GiphyNetRequest} for sending a "trending" API request
 */
public class GiphyTrendingRequest extends GiphyNetRequest
{
	private int offset;

	public GiphyTrendingRequest(int offset)
	{
		super();
		this.offset = offset;
	}

	@Override
	public String getURL()
	{
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("/trending")
			.append("?").append(API_KEY)
			.append("&").append(String.format(API_LIMIT, API_MAX_RESULTS))
			.append("&").append(API_FORMAT)
			.append("&").append(String.format(API_OFFSET, offset));
		return url.toString();
	}
}

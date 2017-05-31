package com.niketest.giphyview.net;

import java.net.URLEncoder;

/**
 * A {@link GiphyNetRequest} for sending a "search" API request
 */
public class GiphySearchRequest extends GiphyNetRequest
{
	private static final String API_QUERY_STRING = "q=%s";

	private String queryString;
	private int offset;

	public GiphySearchRequest(String queryString, int offset)
	{
		super();
		this.queryString = queryString;
		this.offset = offset;
	}

	@Override
	public String getURL()
	{
		try
		{
			StringBuilder url = new StringBuilder(BASE_URL);
			url.append("/search")
				.append("?").append(API_KEY)
				.append("&").append(String.format(API_LIMIT, API_MAX_RESULTS))
				.append("&").append(API_FORMAT)
				.append("&").append(String.format(API_OFFSET, offset))
				.append("&").append(String.format(API_QUERY_STRING, URLEncoder.encode(queryString, "UTF-8")));
			return url.toString();
		}
		catch (Exception e)
		{
			return null;
		}
	}
}

package com.niketest.giphyview.net;

/**
 * Base class for all Giphy network requests
 */
public abstract class GiphyNetRequest
{
	// Base URL for all Giphy API calls
	protected static final String BASE_URL = "http://api.giphy.com/v1/gifs";

	// Max results per API
	public static final int API_MAX_RESULTS = 20;

	// Query parameters
	protected static final String API_KEY = "api_key=dc6zaTOxFJmzC";
	protected static final String API_LIMIT = "limit=%d";
	protected static final String API_FORMAT = "fmt=json";
	protected static final String API_OFFSET = "offset=%d";

	protected GiphyNetRequest() {}

	/**
	 * Gets the URL for the request
	 *
 	 * @return The URL string
	 */
	public abstract String getURL();
}

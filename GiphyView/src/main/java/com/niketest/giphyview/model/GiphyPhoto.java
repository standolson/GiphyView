package com.niketest.giphyview.model;

/**
 * The wire model class for an individual Giphy photo
 */
public class GiphyPhoto
{
	/**
	 * Giphy user
	 */
	public class GiphyUser
	{
		public String username;
		public String display_name;
	}

	/**
	 * Giphy image data
	 */
	public class GiphyImage
	{
		public String url;
		public int width;
		public int height;
		public int size;
	}

	/**
	 * Giphy supplies many variants of the base image
	 */
	public class GiphyImages
	{
		public GiphyImage fixed_height;
		public GiphyImage preview;
		public GiphyImage downsized;
	}

	/**
	 * Image type
	 */
	public String type;
	/**
	 * User who supplies the image
	 */
	public GiphyUser user;
	/**
	 * Photo rating
	 */
	public String rating;
	/**
	 * Photo source
	 */
	public String source;
	/**
	 * Image data
	 */
	public GiphyImages images;

	/**
	 * Returns the URL string of the fixed height image
	 *
	 * @return The URL
	 */
	public String getFixedHeightUrl()
	{
		if ((images != null) && (images.fixed_height != null))
			return images.fixed_height.url;
		return null;
	}

	/**
	 * Returns the URL of the preview image
	 *
	 * @return The URL
	 */
	public String getPreviewUrl()
	{
		if ((images != null) && (images.preview != null))
			return images.preview.url;
		return null;
	}

	/**
	 * Returns the URL of the downsized image
	 *
	 * @return The URL
	 */
	public String getDownsizedUrl()
	{
		if ((images != null) && (images.downsized != null))
			return images.downsized.url;
		return null;
	}
}

package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlickrFetchr {
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "9453ea17a607edf64eea7c2f775ce0ef";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            Log.d(TAG, "getUrlBytes: 1OVER");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            Log.d(TAG, "getUrlBytes: 2OVER");
            while ((bytesRead = in.read(buffer)) > 0) {
                Log.d(TAG, "getUrlBytes: OVER");
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            Log.d(TAG, "getUrlBytes: OVER");
            return out.toByteArray();
        } finally {
            connection.disconnect();
            Log.d(TAG, "getUrlBytes: Error");
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems() {
        ArrayList<GalleryItem> items = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ex) {
            Log.e(TAG, "Failed to fetch items", ex);
        } catch (JSONException je){ Log.e(TAG, "Failed to parse JSON", je); }
        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject photoJsonObjct = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photoJsonObjct.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject jsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObjct.getString("id"));
            item.setCaption(jsonObject.getString("title"));
            if (!jsonObject.has("url_s")) {
                continue;
            }
            item.setUrl(jsonObject.getString("url_s"));
            items.add(item);
        }

    }
}

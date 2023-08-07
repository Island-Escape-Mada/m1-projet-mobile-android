package com.example.island_escape_mada.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.island_escape_mada.R;
import com.example.island_escape_mada.factory.TrustAllSSLSocketFactory;
import com.example.island_escape_mada.helpers.HotelListAdapter;
import com.example.island_escape_mada.models.Hotel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView hotelListView;
    private List<Hotel> allHotels;
    private List<Hotel> filteredHotels;
    private HotelListAdapter adapter;
    private String API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search for Hotels");

        filteredHotels = new ArrayList<>();
        adapter = new HotelListAdapter(this, filteredHotels);

        searchEditText = findViewById(R.id.search_edit_text);
        hotelListView = findViewById(R.id.hotel_list_view);

        hotelListView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterHotels(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Fetch hotel data from the API
        fetchHotelData();
    }

    private void fetchHotelData() {
        String apiUrl = getString(R.string.api_url) + "api/hotels";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseHotelData(response);
                        Toast.makeText(SearchActivity.this, "Hotel data retrieved successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body;
                        //get status code here
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Toast.makeText(SearchActivity.this, body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        HttpsURLConnection.setDefaultSSLSocketFactory(TrustAllSSLSocketFactory.create());
        queue.add(request);
    }

    private void parseHotelData(JSONArray jsonArray) {
        allHotels = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String location = jsonObject.getString("location");
                double rating = jsonObject.getDouble("rating");
                String price = jsonObject.getString("price");
                JSONArray amenitiesArray = jsonObject.getJSONArray("amenities");
                List<String> amenities = new ArrayList<>();
                for (int j = 0; j < amenitiesArray.length(); j++) {
                    amenities.add(amenitiesArray.getString(j));
                }
                String image = jsonObject.getString("image");

                Hotel hotel = new Hotel(name, location, rating, price, amenities, image);
                allHotels.add(hotel);
            }
            filterHotels(searchEditText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void filterHotels(String query) {
        filteredHotels.clear();
        for (Hotel hotel : allHotels) {
            if (containsQuery(hotel, query)) {
                filteredHotels.add(hotel);
            }
        }adapter.notifyDataSetChanged();
    }

    private boolean containsQuery(Hotel hotel, String query) {
        query = query.toLowerCase();
        return hotel.getName().toLowerCase().contains(query)
                || hotel.getLocation().toLowerCase().contains(query)
                || String.valueOf(hotel.getRating()).contains(query)
                || hotel.getPrice().toLowerCase().contains(query)
                || containsQueryInAmenities(hotel, query);
    }

    private boolean containsQueryInAmenities(Hotel hotel, String query) {
        for (String amenity : hotel.getAmenities()) {
            if (amenity.toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }
}

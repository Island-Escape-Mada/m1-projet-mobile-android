package com.example.island_escape_mada.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.island_escape_mada.R;
import com.example.island_escape_mada.models.Hotel;

import java.util.List;

public class HotelListAdapter extends BaseAdapter {
    private Context context;
    private List<Hotel> hotelList;

    public HotelListAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hotel_list_item, parent, false);
        }

        Hotel hotel = hotelList.get(position);

        TextView hotelNameTextView = convertView.findViewById(R.id.hotel_name_text_view);
        TextView hotelLocationTextView = convertView.findViewById(R.id.hotel_location_text_view);
        TextView hotelRatingTextView = convertView.findViewById(R.id.hotel_rating_text_view);
        TextView hotelPriceTextView = convertView.findViewById(R.id.hotel_price_text_view);
        TextView hotelAmenitiesTextView = convertView.findViewById(R.id.hotel_amenities_text_view);

        hotelNameTextView.setText(hotel.getName());
        hotelLocationTextView.setText(hotel.getLocation());
        hotelRatingTextView.setText(String.valueOf(hotel.getRating()));
        hotelPriceTextView.setText(hotel.getPrice());
        hotelAmenitiesTextView.setText(android.text.TextUtils.join(", ", hotel.getAmenities()));

        return convertView;
    }
}

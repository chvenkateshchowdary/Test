package com.example.cyclone.cyclone;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chven on 3/9/2017.
 */

public class LogCursorAdapter extends CursorAdapter {

    public LogCursorAdapter(MainActivity context, Cursor c) {

        super(context, c , 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View root1 =  LayoutInflater.from(context).inflate(R.layout.log_item, viewGroup, false);
             return root1;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text_view_name = (TextView) view.findViewById(R.id.text_view_name);
        TextView text_view_time = (TextView) view.findViewById(R.id.text_view_time);
        ImageView image_view = (ImageView) view.findViewById(R.id.image_view);


        String NAME = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String TIME = cursor.getString(cursor.getColumnIndexOrThrow("Time_Used"));
        byte[] IMAGE = cursor.getBlob(cursor.getColumnIndexOrThrow("app_Image"));
        Bitmap bitmap = BitmapFactory.decodeByteArray(IMAGE, 0, IMAGE.length);

        text_view_name.setText(NAME);
        text_view_time.setText(TIME +"min");
        image_view.setBackgroundColor(Color.TRANSPARENT);
        image_view.setLayerType(image_view.LAYER_TYPE_SOFTWARE, null);
        image_view.setImageBitmap(bitmap);
    }
}

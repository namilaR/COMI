package com.example.namilaradith.comi_beta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by dealwis on 4/27/16.
 */
public class SelectedIntrestActivity extends AppCompatActivity {


    public String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_intrest);

        ImageUrl = getIntent().getExtras().getString("url");

        EditText caption_field = (EditText) findViewById(R.id.selected_intrest_caption);
        ImageView Image_field = (ImageView) findViewById(R.id.selected_intrest_image);


        caption_field.setText(ImageUrl);




        Bitmap bmp = null;
        try {
            URL url = new URL(ImageUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image_field.setImageBitmap(bmp);




    }

}

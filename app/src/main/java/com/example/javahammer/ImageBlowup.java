package com.example.javahammer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ImageBlowup extends Fragment {

    ImageView blowupIv;
    int imageRes;

    public ImageBlowup(int imageRes) {
       this.imageRes = imageRes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_blowup, container, false);
        blowupIv = view.findViewById(R.id.blowup_iv);
        blowupIv.setImageResource(imageRes);
        blowupIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .remove(ImageBlowup.this)
                        .commit();
            }
        });
        Log.v("Thomas", "IV");
        return view;

    }

    public void setBlowupIv(int imageRes) {
        //     blowupIv =
        blowupIv.setImageResource(imageRes);
    }

}
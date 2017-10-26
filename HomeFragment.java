package com.iqbalproject.aplikasigis.Fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.iqbalproject.aplikasigis.MapsActivity;
import com.iqbalproject.aplikasigis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewFlipper mViewFlipper;
    private Animation fadeIn, fadeOut;
    private ImageView imgBukaMap;
    private RelativeLayout rlBukaPeta;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipper);
        fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        rlBukaPeta = (RelativeLayout) view.findViewById(R.id.rlBukaPeta);
        imgBukaMap = (ImageView) view.findViewById(R.id.imgBukaPeta);
        rlBukaPeta.setBackgroundResource(android.R.color.transparent);

        //set image slider
        mViewFlipper.setInAnimation(fadeIn);
        mViewFlipper.setOutAnimation(fadeOut);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(5000); //5 detik
        mViewFlipper.startFlipping();


        imgBukaMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlBukaPeta.setBackgroundResource(R.color.colorBayang);
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });

        return view;
    }

}

package com.comfacesar.comfacesar.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.SplashScreenActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro_two_Fragment extends Fragment {

    private View view;
    private Button  button;



    public Intro_two_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_intro_two_, container, false);

        button= view.findViewById(R.id.id_boton_two);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "presiono boton", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent (getActivity(), SplashScreenActivity.class);
                startActivity(intent);
            }
        });


        //Toast.makeText(context, "presiono boton", Toast.LENGTH_SHORT).show();
        return view ;
    }

}

package com.comfacesar.comfacesar.adapterViewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.AlertTempranaFragment;
import com.comfacesar.comfacesar.fragment.AsesoriaFragment;
import com.comfacesar.comfacesar.fragment.HomeFragment;
import com.comfacesar.comfacesar.fragment.UbicacionFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter{
    private Context context;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch(i) {
            case 0:

                fragment = new HomeFragment();

                break;
            case 1:
                HomeFragment.fragmentConsultarNoticiasActivo = false;
                AsesoriaFragment.fragmentManager = ContainerActivity.fragmentManager;
                fragment = new AsesoriaFragment();
                break;
            case 2:
                HomeFragment.fragmentConsultarNoticiasActivo = false;
                fragment = new AlertTempranaFragment();
                break;
            case 3:
                HomeFragment.fragmentConsultarNoticiasActivo = false;
                fragment = new UbicacionFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#71CDF5"));
                return "";
            case 1:
                return "ASESOR";
            case 2:
                return "ALERTA";
            case 3:
                return "SEDES";
        }
        return null;
    }
}
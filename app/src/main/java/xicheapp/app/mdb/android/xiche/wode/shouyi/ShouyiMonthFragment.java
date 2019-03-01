package xicheapp.app.mdb.android.xiche.wode.shouyi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xicheapp.app.mdb.android.xiche.R;

/**
 * A simple {@link Fragment} subclass.
 * 月收益
 */
public class ShouyiMonthFragment extends Fragment {


    public ShouyiMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shouyi_month, container, false);
    }

}

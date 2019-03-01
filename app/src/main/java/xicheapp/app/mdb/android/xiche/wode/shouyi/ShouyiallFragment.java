package xicheapp.app.mdb.android.xiche.wode.shouyi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xicheapp.app.mdb.android.xiche.R;

/**
 * A simple {@link Fragment} subclass.
 * 总收益
 */
public class ShouyiallFragment extends Fragment {


    public ShouyiallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shouyi_all, container, false);
    }

}

package xicheapp.app.mdb.android.xiche.wode.shouyi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.R;

/**
 * A simple {@link Fragment} subclass.
 * 收益明细
 */
public class ShouyiMoreFragment extends Fragment {

    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouyi_more, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setinfo();
    }

    /**
     * 查询信息
     */
    @BindView(R.id.liyi_xiangqing)
    TextView tv_xiangqing;
    private void setinfo() {
        tv_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShouyiMoreActivity.class);
                startActivity(intent);
            }
        });
    }
}

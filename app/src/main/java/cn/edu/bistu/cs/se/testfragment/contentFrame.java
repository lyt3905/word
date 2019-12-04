package cn.edu.bistu.cs.se.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class contentFrame extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_m_s, container, false);
        return view;
    }

    public void refresh(String newword, String newmean,String newsentence) {

        TextView word = (TextView) view.findViewById (R.id.textView1);
        TextView mean = (TextView) view.findViewById(R.id.textView2);
        TextView sentence = (TextView) view.findViewById(R.id.textView3);
        word.setText(newword); // 刷新新闻的标题
        mean.setText(newmean); // 刷新新闻的内容
        sentence.setText(newsentence); // 刷新新闻的内容
    }
}

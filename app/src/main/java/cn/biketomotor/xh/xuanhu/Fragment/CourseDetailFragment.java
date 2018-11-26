package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.biketomotor.xh.xuanhu.R;

public class CourseDetailFragment extends Fragment {
    private View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        return view;
    }

}

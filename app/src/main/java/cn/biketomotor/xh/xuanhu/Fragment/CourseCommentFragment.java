package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.HistoryCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class CourseCommentFragment extends Fragment {
    private View view = null;
    List<CommentItem> courseComments = new ArrayList<>();
    HistoryCommentItemAdapter historyCommentItemAdapter = new HistoryCommentItemAdapter(courseComments);
    RecyclerView rvCourseComment = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_comment, container, false);
        rvCourseComment = view.findViewById(R.id.rv_comment_list);
        rvCourseComment.setAdapter(historyCommentItemAdapter);
        rvCourseComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        getCourseComments();
        return view;
    }

    private void getCourseComments(){
        courseComments.clear();
        courseComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        courseComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        courseComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }
}

package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.OthersHomeActivity;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryUserCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.CourseApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

//课程评论Fragment，用来显示一个课程的所有评论
public class CourseCommentFragment extends Fragment {
    private View view = null;
    List<Comment> courseComments = new ArrayList<>();
    HistoryCourseCommentItemAdapter historyCommentItemAdapter;
    RecyclerView rvCourseComment = null;
    //创建Fragment的界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_comment, container, false);
        rvCourseComment = view.findViewById(R.id.rv_comment_list);
        historyCommentItemAdapter = new HistoryCourseCommentItemAdapter(this.getActivity(), courseComments, 0);
        historyCommentItemAdapter.setItemClickListener(new HistoryCourseCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Comment comment = courseComments.get(position);
                OthersHomeActivity.actionActivity(getActivity(), comment.user.id);
            }
        });
        rvCourseComment.setAdapter(historyCommentItemAdapter);
        rvCourseComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseComments.clear();
        pollingForCourseComments();
        return view;
    }

    //获取课程的评论
    ///其实应该命名为getCourseComments()...
    private void pollingForCourseComments(){
        courseComments.clear();
        final int courseId = getActivity().getIntent().getIntExtra("courseID", -1);
        if(courseId == -1)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<Comment>> courseComentsResult = CourseApi.INSTANCE.getCourseComments(courseId);
                if(!courseComentsResult.isOk())return;
                courseComments.addAll(courseComentsResult.get());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyCommentItemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    //获取适配器，用于在用户添加评论时更新数据
    public HistoryCourseCommentItemAdapter getCourseCommentAdapter(){
        return historyCommentItemAdapter;
    }

    //获取课程评论数
    public List<Comment> getCourseComments(){
        return courseComments;
    }
}

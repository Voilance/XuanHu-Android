package cn.biketomotor.xh.xuanhu.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.CourseDetail;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class UserCommentFragment extends Fragment {
    public static final int COMMENT = 0;
    public static final int LIKE = 1;
    public static final int UNLIKE = 2;
    private View view = null;
    private RecyclerView recyclerView = null;
    private List<CommentItem> userComments = new ArrayList<>();
    private HistoryCommentItemAdapter historyCommentItemAdapter = new HistoryCommentItemAdapter(userComments);
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_comment, container, false);
        mainActivity = (MainActivity)getActivity();
        recyclerView = view.findViewById(R.id.user_comment_recycler_view);
        recyclerView.setAdapter(historyCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyCommentItemAdapter.setItemClickListener(new HistoryCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(mainActivity, commentList.get(position).getCourseTitle(), Toast.LENGTH_SHORT).show();
                CourseDetail.actionActivity(mainActivity);
            }
        });
        Bundle bundle = this.getArguments();
        int type = bundle.getInt("type");
        switch (type){
            case COMMENT:
                //load comment data to recycle view
                getMyComment();
                break;
            case LIKE:
                //load like data to recycle view
                getMyAgree();
                break;
            case UNLIKE:
                //load unlike data to recycle view
                getMyDisagree();
                break;
        }
        return view;
    }

    // 获取我的评论
    private void getMyComment() {
        userComments.clear();
        userComments.add(new CommentItem(0, "MyComment", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }

    // 获取我的赞同
    private void getMyAgree() {
        userComments.clear();
        userComments.add(new CommentItem(0, "MyAgree", "userName", "content", "createdAt", 0, 0));
        userComments.add(new CommentItem(0, "MyAgree", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }

    // 获取我的反对
    private void getMyDisagree() {
        userComments.clear();
        userComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        userComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        userComments.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }
}
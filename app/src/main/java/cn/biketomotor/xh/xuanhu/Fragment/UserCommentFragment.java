package cn.biketomotor.xh.xuanhu.Fragment;

import android.app.Activity;
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

import cn.biketomotor.xh.xuanhu.Activity.CourseDetailActivity;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryUserCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.UserApi;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class UserCommentFragment extends Fragment {
    public static final int COMMENT = 0;
    public static final int LIKE = 1;
    public static final int UNLIKE = 2;
    private View view = null;
    private RecyclerView recyclerView = null;
    private List<Comment> userComments = new ArrayList<>();
    private HistoryUserCommentItemAdapter historyCommentItemAdapter;
    private Activity activity;
    private int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_comment, container, false);
        activity = getActivity();
        historyCommentItemAdapter = new HistoryUserCommentItemAdapter(activity, userComments);
        userId = activity.getIntent().getIntExtra("userId", -1);
        recyclerView = view.findViewById(R.id.user_comment_recycler_view);
        recyclerView.setAdapter(historyCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyCommentItemAdapter.setItemClickListener(new HistoryUserCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CourseDetailActivity.actionActivity(getContext(), userComments.get(position).course.id);
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
                getCommentVotes(1);
                break;
            case UNLIKE:
                //load unlike data to recycle view
                getCommentVotes(-1);
                break;
        }
        return view;
    }

    // 获取我的评论
    private void getMyComment() {
        if(userId == -1)return;
        userComments.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<Comment>>commentsResult = UserApi.INSTANCE.getUserComments(userId);
                if(!commentsResult.isOk())return;
                userComments.addAll(commentsResult.get());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyCommentItemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void getCommentVotes(final int voteValue){
        if(userId == -1)return;
        userComments.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<Comment>>commentsResult = UserApi.INSTANCE.getUserVotes(userId);
                if(!commentsResult.isOk())return;
                List<Comment>comments = commentsResult.get();
                for(Comment comment : comments){
                    if(comment.voteValue == voteValue){
                        userComments.add(comment);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyCommentItemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

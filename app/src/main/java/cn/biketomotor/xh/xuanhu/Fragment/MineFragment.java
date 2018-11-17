package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.LoginActivity;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class MineFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TagMine";

    private ImageView ivAvatar;
    private TextView tvName;
    private Button btComment;
    private Button btAgree;
    private Button btDisagree;

    private MainActivity mainActivity;
    private List<CommentItem> commentList;
    private HistoryCommentItemAdapter historyCommentItemAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_mine, container, false);

        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvName = view.findViewById(R.id.tv_name);
        btComment = view.findViewById(R.id.bt_comment);
        btAgree = view.findViewById(R.id.bt_agree);
        btDisagree = view.findViewById(R.id.bt_disagree);
        ivAvatar.setOnClickListener(this);
        btComment.setOnClickListener(this);
        btAgree.setOnClickListener(this);
        btDisagree.setOnClickListener(this);

        mainActivity = (MainActivity)getActivity();
        recyclerView = view.findViewById(R.id.rv_comment_list);
        commentList = new ArrayList<>();
        historyCommentItemAdapter = new HistoryCommentItemAdapter(commentList);
        historyCommentItemAdapter.setItemClickListener(new HistoryCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mainActivity, commentList.get(position).getCourseTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(historyCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        getMyComment();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                // 点击头像，如果已经登陆，则跳转到编辑个人信息活动，否则跳转到登陆活动
                LoginActivity.actionActivity(mainActivity);
                break;
            case R.id.bt_comment:
                getMyComment();
                break;
            case R.id.bt_agree:
                getMyAgree();
                break;
            case R.id.bt_disagree:
                getMyDisagree();
                break;
            default:
                break;
        }
    }

    // 获取我的评论
    private void getMyComment() {
        commentList.clear();
        commentList.add(new CommentItem(0, "MyComment", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }

    // 获取我的赞同
    private void getMyAgree() {
        commentList.clear();
        commentList.add(new CommentItem(0, "MyAgree", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "MyAgree", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }

    // 获取我的反对
    private void getMyDisagree() {
        commentList.clear();
        commentList.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "MyDisagree", "userName", "content", "createdAt", 0, 0));
        historyCommentItemAdapter.notifyDataSetChanged();
    }
}

package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.CourseDetailActivity;
import cn.biketomotor.xh.xuanhu.Adapter.NewCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TagHome";
    private List<CommentItem>     commentList;
    private NewCommentItemAdapter newCommentItemAdapter;
    private RecyclerView          recyclerView;
    private SwipyRefreshLayout swipyRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_comment_list);
        swipyRefreshLayout = view.findViewById(R.id.swipy_layout);
        commentList = new ArrayList<>();
        newCommentItemAdapter = new NewCommentItemAdapter(commentList);
        newCommentItemAdapter.setItemClickListener(new NewCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CourseDetailActivity.actionActivity(getContext());
            }
        });
        recyclerView.setAdapter(newCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipyRefreshLayout.setDistanceToTriggerSync(10);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                getComment();
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, newCommentItemAdapter.getItemCount() - 1);
                swipyRefreshLayout.setRefreshing(false);
            }
        });
        getComment();


        return view;
    }

    private void getComment() {
        // 获取5条评论
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        newCommentItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_more:
                getComment();
                break;
            default:
                break;
        }
    }
}

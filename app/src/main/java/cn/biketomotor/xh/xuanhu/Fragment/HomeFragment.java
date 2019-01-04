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
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.NewCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.CommentApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.R;
//首页Fragment，显示最近的评论
public class HomeFragment extends Fragment {
    private static final String TAG = "TagHome";

    private List<Comment>     commentList;
    private NewCommentItemAdapter newCommentItemAdapter;
    private RecyclerView          recyclerView;
    private SwipyRefreshLayout swipyRefreshLayout;

    private MainActivity mainActivity;
    private int CommentPage;

    //创建界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_comment_list);
        swipyRefreshLayout = view.findViewById(R.id.swipy_layout);
        mainActivity = (MainActivity)getActivity();
        commentList = new ArrayList<>();
        newCommentItemAdapter = new NewCommentItemAdapter(commentList, mainActivity);
        newCommentItemAdapter.setItemClickListener(new NewCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CourseDetailActivity.actionActivity(getContext(), commentList.get(position).course.id);
            }
        });
        recyclerView.setAdapter(newCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipyRefreshLayout.setDistanceToTriggerSync(100);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                getComment();
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, newCommentItemAdapter.getItemCount() - 1);
                swipyRefreshLayout.setRefreshing(false);
            }
        });

        CommentPage = 1;
        getComment();

        return view;
    }

    //获取最近的评论
    private void getComment() {
        // 获取一页评论
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<Comment>> result = CommentApi.INSTANCE.latest(CommentPage);
                if (result.isOk()) {
                    List<Comment> comments = result.get();
                    commentList.addAll(comments);
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newCommentItemAdapter.notifyDataSetChanged();
                        }
                    });
                    CommentPage += 1;
                } else {
                    Log.e(TAG, "getComment: " + result.getErrorMessage());
                }
            }
        }).start();
    }
}

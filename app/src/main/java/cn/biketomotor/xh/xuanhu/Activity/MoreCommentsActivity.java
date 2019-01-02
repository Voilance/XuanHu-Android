package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.R;

//当嵌套评论过多时，点击省略号跳转到这里
public class MoreCommentsActivity extends BaseActivity {
    private View view = null;
    List<Comment> courseComments = new ArrayList<>();
    HistoryCourseCommentItemAdapter historyCommentItemAdapter;
    RecyclerView rvCourseComment = null;
    int depth = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_comments);
        rvCourseComment = findViewById(R.id.rv_comment_list);
        historyCommentItemAdapter = new HistoryCourseCommentItemAdapter(this, courseComments, depth);
        historyCommentItemAdapter.setItemClickListener(new HistoryCourseCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OthersHomeActivity.actionActivity(MoreCommentsActivity.this, courseComments.get(position).user.id);
            }
        });
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("depth")){
            depth = intent.getIntExtra("depth", depth);
        }

        rvCourseComment.setAdapter(historyCommentItemAdapter);
        rvCourseComment.setLayoutManager(new LinearLayoutManager(MoreCommentsActivity.this));
        Comment item = GlobalDataChannel.targetCommentItem;
        if(item != null){
            historyCommentItemAdapter.setDepth(this.depth);
            courseComments.add(item);
            historyCommentItemAdapter.notifyDataSetChanged();
        }

    }

    public static void actionActivity(Context context, int depth) {
        Intent intent = new Intent(context, MoreCommentsActivity.class);
        intent.putExtra("depth", depth);
        context.startActivity(intent);
    }
}

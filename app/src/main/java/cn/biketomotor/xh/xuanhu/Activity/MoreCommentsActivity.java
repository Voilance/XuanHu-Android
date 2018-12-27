package cn.biketomotor.xh.xuanhu.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Interface.AddCommentDialogPopupable;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

//当嵌套评论过多时，点击省略号跳转到这里
public class MoreCommentsActivity extends BaseActivity implements AddCommentDialogPopupable {
    private View view = null;
    List<CommentItem> courseComments = new ArrayList<>();
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
                OthersHomeActivity.actionActivity(MoreCommentsActivity.this);
            }
        });
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("depth")){
            depth = intent.getIntExtra("depth", depth);
        }

        rvCourseComment.setAdapter(historyCommentItemAdapter);
        rvCourseComment.setLayoutManager(new LinearLayoutManager(MoreCommentsActivity.this));
        CommentItem item = GlobalDataChannel.targetCommentItem;
        if(item != null){
            historyCommentItemAdapter.setDepth(this.depth);
            courseComments.add(item);
            historyCommentItemAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void popupAddCommentDialog(final HistoryCourseCommentItemAdapter adapter, final List<CommentItem> comments, final CommentItem parent){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_new_comment, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView).setCancelable(true);
        final EditText userInput = promptsView.findViewById(R.id.et_comment_content);
        final TextView btOk = promptsView.findViewById(R.id.bt_ok);
        final TextView btCancel = promptsView.findViewById(R.id.bt_cancel);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = userInput.getText().toString();
                CommentItem item = new CommentItem(0, "MyDisagree", "userName", commentContent, "createdAt", 0, 0);
                if(parent != null){
                    parent.addReply(item);
                }
                else{
                    comments.add(item);
                }
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    public static void actionActivity(Context context, int depth) {
        Intent intent = new Intent(context, MoreCommentsActivity.class);
        intent.putExtra("depth", depth);
        context.startActivity(intent);
    }
}

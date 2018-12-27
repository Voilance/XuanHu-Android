package cn.biketomotor.xh.xuanhu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.CourseDetailActivity;
import cn.biketomotor.xh.xuanhu.Activity.MoreCommentsActivity;
import cn.biketomotor.xh.xuanhu.Activity.OthersHomeActivity;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Interface.AddCommentDialogPopupable;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class HistoryCourseCommentItemAdapter extends RecyclerView.Adapter<HistoryCourseCommentItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUser;
        private TextView tvTime;
        private TextView tvVoteUp;
        private TextView tvVoteDown;
        private TextView tvContent;
        private TextView btVoteUp;
        private TextView btVoteDown;
        private TextView btReply;
        private RecyclerView rvNestedComments;
        private TextView tvMore;

        ViewHolder(View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tv_user);
            tvTime = itemView.findViewById(R.id.tv_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            btReply = itemView.findViewById(R.id.bt_reply);
            tvVoteUp = itemView.findViewById(R.id.tv_vote_up);
            btVoteUp = itemView.findViewById(R.id.bt_vote_up);
            tvVoteDown = itemView.findViewById(R.id.tv_vote_down);
            btVoteDown = itemView.findViewById(R.id.bt_vote_down);
            rvNestedComments = itemView.findViewById(R.id.rv_nested_comments);
            tvMore = itemView.findViewById(R.id.tv_more_comments);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<CommentItem> commentItemList;
    private HistoryCourseCommentItemAdapter.onItemClickListener clickListener;
    private Context context;
    private int depth;
    private static int MAX_NESTED_NUM = 5;
    public HistoryCourseCommentItemAdapter(Context context, List<CommentItem> list, int depth) {
        this.context = context;
        this.commentItemList = list;
        this.depth = depth;
    }

    @Override
    public HistoryCourseCommentItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_course_comment_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick((Integer)v.getTag());
//                    switch (v.getId()) {
//                    }
                }
            }
        });
        return new HistoryCourseCommentItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryCourseCommentItemAdapter.ViewHolder holder, int position) {
        final CommentItem item = commentItemList.get(position);
        holder.tvTime.setText(item.getCreatedAt());
        holder.tvVoteUp.setText(String.valueOf(item.getVoteUp()));
        holder.tvVoteDown.setText(String.valueOf(item.getVoteDown()));
        holder.tvContent.setText(item.getContent());
        holder.tvUser.setText(item.getUserName());

        //solve the bug when nested comments are too many

        final List<CommentItem> replies = item.getReplies();
        final HistoryCourseCommentItemAdapter hccia = new HistoryCourseCommentItemAdapter(context, replies, depth);
        hccia.setItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OthersHomeActivity.actionActivity(context);
            }
        });

        if(item.getParentCommentCount() - depth * MAX_NESTED_NUM < MAX_NESTED_NUM) {
            holder.rvNestedComments.setAdapter(hccia);
            holder.rvNestedComments.setLayoutManager(new LinearLayoutManager(context));
            holder.btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddCommentDialogPopupable)context).popupAddCommentDialog(hccia, replies, item);
                }
            });
        }
        else{
            holder.tvMore.setVisibility(View.VISIBLE);
            holder.btReply.setVisibility(View.INVISIBLE);
            holder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalDataChannel.targetCommentItem = item;
                    MoreCommentsActivity.actionActivity(context, depth + 1);
                }
            });
        }



        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    public void setItemClickListener(HistoryCourseCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }
}
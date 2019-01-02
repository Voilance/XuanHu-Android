package cn.biketomotor.xh.xuanhu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.CourseDetailActivity;
import cn.biketomotor.xh.xuanhu.Activity.MoreCommentsActivity;
import cn.biketomotor.xh.xuanhu.Activity.OthersHomeActivity;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Class.Util;
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
        private ImageView ivAvatar;

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
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<Comment> commentItemList;
    private HistoryCourseCommentItemAdapter.onItemClickListener clickListener;
    private Activity activity;
    private int depth;
    private static int MAX_NESTED_NUM = 5;
    public HistoryCourseCommentItemAdapter(Activity activity, List<Comment> list, int depth) {
        this.activity = activity;
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
        final Comment item = commentItemList.get(position);
        holder.tvTime.setText(item.getCreateAtAsString());
        holder.tvVoteUp.setText(String.valueOf(item.voteUp));
        holder.tvVoteDown.setText(String.valueOf(item.voteDown));
        holder.tvContent.setText(item.content);
        holder.tvUser.setText(item.user.name);
        String url = item.user.avatar_url;
        if(url == null){
            holder.ivAvatar.setImageResource(R.drawable.default_avatar);
        }
        else{
            Util.loadImageFromUrl(url, holder.ivAvatar, activity);
        }


        final List<Comment> replies = item.nestedComment;
        final HistoryCourseCommentItemAdapter hccia = new HistoryCourseCommentItemAdapter(activity, replies, depth);
        hccia.setItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Comment comment = replies.get(position);
                OthersHomeActivity.actionActivity(activity, comment.user.id);
            }
        });

        //solve the bug when there are too many nested comments
        if(item.getParentCommentCount() - depth * MAX_NESTED_NUM < MAX_NESTED_NUM) {
            holder.rvNestedComments.setAdapter(hccia);
            holder.rvNestedComments.setLayoutManager(new LinearLayoutManager(activity));
            holder.btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDetailActivity.popupAddCommentDialog(activity, hccia, item, null, null);
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
                    MoreCommentsActivity.actionActivity(activity, depth + 1);
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
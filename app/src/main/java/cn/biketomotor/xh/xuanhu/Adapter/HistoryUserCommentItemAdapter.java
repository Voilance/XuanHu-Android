package cn.biketomotor.xh.xuanhu.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Class.Util;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class HistoryUserCommentItemAdapter extends RecyclerView.Adapter<HistoryUserCommentItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvVoteUp;
        private TextView tvVoteDown;
        private TextView tvContent;
        private ImageView ivAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_course_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvVoteUp = itemView.findViewById(R.id.tv_vote_up);
            tvVoteDown = itemView.findViewById(R.id.tv_vote_down);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<Comment> commentItemList;
    private onItemClickListener clickListener;
    private Activity activity;
    public HistoryUserCommentItemAdapter(Activity activity, List<Comment> list) {
        this.activity = activity;
        this.commentItemList = list;
    }

    @Override
    public HistoryUserCommentItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_user_comment_item, parent, false);
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryUserCommentItemAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(commentItemList.get(position).course.title);
        holder.tvTime.setText(commentItemList.get(position).getCreateAtAsString());
        holder.tvVoteUp.setText(String.valueOf(commentItemList.get(position).voteUp));
        holder.tvVoteDown.setText(String.valueOf(commentItemList.get(position).voteDown));
        holder.tvContent.setText(commentItemList.get(position).content);
        holder.itemView.setTag(position);
        String url = commentItemList.get(position).user.avatar_url;
        if(url == null){
            holder.ivAvatar.setImageResource(R.drawable.default_avatar);
        }
        else{
            Util.loadImageFromUrl(url, holder.ivAvatar, activity);
        }
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    public void setItemClickListener(HistoryUserCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

}

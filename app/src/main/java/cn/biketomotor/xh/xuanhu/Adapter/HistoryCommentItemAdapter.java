package cn.biketomotor.xh.xuanhu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class HistoryCommentItemAdapter extends RecyclerView.Adapter<HistoryCommentItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvVoteUp;
        private TextView tvVoteDown;
        private TextView tvContent;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_course_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvVoteUp = itemView.findViewById(R.id.tv_vote_up);
            tvVoteDown = itemView.findViewById(R.id.tv_vote_down);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<CommentItem> commentItemList;
    private onItemClickListener clickListener;

    public HistoryCommentItemAdapter(List<CommentItem> list) {
        this.commentItemList = list;
    }

    @Override
    public HistoryCommentItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(final HistoryCommentItemAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(commentItemList.get(position).getCourseTitle());
        holder.tvTime.setText(commentItemList.get(position).getCreatedAt());
        holder.tvVoteUp.setText(String.valueOf(commentItemList.get(position).getVoteUp()));
        holder.tvVoteDown.setText(String.valueOf(commentItemList.get(position).getVoteDown()));
        holder.tvContent.setText(commentItemList.get(position).getContent());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    public void setItemClickListener(HistoryCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

}

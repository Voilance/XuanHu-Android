package cn.biketomotor.xh.xuanhu.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.CourseDetailActivity;
import cn.biketomotor.xh.xuanhu.Activity.OthersHomeActivity;
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
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<CommentItem> commentItemList;
    private HistoryCourseCommentItemAdapter.onItemClickListener clickListener;
    private Context context;
    public HistoryCourseCommentItemAdapter(Context context, List<CommentItem> list) {
        this.context = context;
        this.commentItemList = list;
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
        holder.tvTime.setText(commentItemList.get(position).getCreatedAt());
        holder.tvVoteUp.setText(String.valueOf(commentItemList.get(position).getVoteUp()));
        holder.tvVoteDown.setText(String.valueOf(commentItemList.get(position).getVoteDown()));
        holder.tvContent.setText(commentItemList.get(position).getContent());
        holder.tvUser.setText(commentItemList.get(position).getUserName());
        final List<CommentItem>replies = commentItemList.get(position).getReplies();
        final HistoryCourseCommentItemAdapter hccia = new HistoryCourseCommentItemAdapter(context, replies);
        hccia.setItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OthersHomeActivity.actionActivity(context);
            }
        });
        holder.rvNestedComments.setAdapter(hccia);
        holder.rvNestedComments.setLayoutManager(new LinearLayoutManager(context));
        holder.btReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CourseDetailActivity)context).popupAddCommentDialog(hccia, replies);
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    public void setItemClickListener(HistoryCourseCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

}
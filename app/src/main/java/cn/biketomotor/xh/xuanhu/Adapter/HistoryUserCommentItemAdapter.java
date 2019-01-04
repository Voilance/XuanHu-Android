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

//用户个人主页处的评论的适配器
public class HistoryUserCommentItemAdapter extends RecyclerView.Adapter<HistoryUserCommentItemAdapter.ViewHolder> {
    //其实这个类可以与NewCommentItemAdapter合并成一个类
    //因为当时觉得用户个人信息处的评论和最新评论有细微的差别（比如用户名和时间），
    //所以将它们暂时分开
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
    //用户个人主页处的评论的适配器的构造函数
    public HistoryUserCommentItemAdapter(Activity activity, List<Comment> list) {
        this.activity = activity;
        this.commentItemList = list;
    }

    //创建ViewHolder
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

    //填充数据
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

    //获取评论的个数
    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    //设置监听器
    public void setItemClickListener(HistoryUserCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

}

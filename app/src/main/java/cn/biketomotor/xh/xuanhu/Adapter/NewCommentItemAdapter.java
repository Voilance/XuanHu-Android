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
import cn.biketomotor.xh.xuanhu.R;

//最新评论的适配器
public class NewCommentItemAdapter extends RecyclerView.Adapter<NewCommentItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private TextView tvTitleName;
        private TextView tvContent;
        private TextView tvVoteUp;
        private TextView tvVoteDown;
        private ImageView ivAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTitleName = itemView.findViewById(R.id.tv_title_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvVoteUp = itemView.findViewById(R.id.tv_vote_up);
            tvVoteDown = itemView.findViewById(R.id.tv_vote_down);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<Comment> commentList;
    private onItemClickListener clickListener;
    private Activity activity;
    //最新评论的适配器的构造函数
    public NewCommentItemAdapter(List<Comment> list, Activity activity) {
        this.commentList = list;
        this.activity = activity;
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_new_comment_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick((Integer)v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    //填充数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvUsername.setText(commentList.get(position).user.name);
        holder.tvTitleName.setText(commentList.get(position).course.title);
        holder.tvContent.setText(commentList.get(position).content);
        holder.tvVoteUp.setText(String.valueOf(commentList.get(position).voteUp));
        holder.tvVoteDown.setText(String.valueOf(commentList.get(position).voteDown));
        String url = commentList.get(position).user.avatar_url;
        if(url != null) {
            Util.loadImageFromUrl(url, holder.ivAvatar, activity);
        }
        else{
            holder.ivAvatar.setImageResource(R.drawable.default_avatar);
        }
        holder.itemView.setTag(position);
    }

    //获取评论数
    @Override
    public int getItemCount() {
        return commentList.size();
    }

    //设置监听器
    public void setItemClickListener(onItemClickListener listener) {
        this.clickListener = listener;
    }

}

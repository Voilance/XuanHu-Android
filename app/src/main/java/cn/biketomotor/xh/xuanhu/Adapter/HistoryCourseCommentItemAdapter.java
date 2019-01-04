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

//课程评论适配器，课程的每条评论界面与最新评论和用户个人主页处的评论不同
//课程评论有嵌套，同时有回复按钮
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
    //课程评论适配器的构造函数
    public HistoryCourseCommentItemAdapter(Activity activity, List<Comment> list, int depth) {
        this.activity = activity;
        this.commentItemList = list;
        this.depth = depth;
    }

    //创建ViewHolder
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

    //填充数据
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

    //获取评论的个数
    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

    //设置监听器
    public void setItemClickListener(HistoryCourseCommentItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }

    //修改当前适配器所在活动的深度
    //因为评论可以无限嵌套
    //当嵌套过多时界面会出现问题
    //所以当RecyclerView的嵌套层数达到MAX_NESTED_NUM时，
    //不再显示更上一层的评论，而是提供一个跳转按钮
    //可以跳转到MoreCommentsActivity查看更多的嵌套评论
    //当MoreCommentsActivity的嵌套评论过多时，
    //仍然可以跳转到另一个MoreCommentsActivity
    //以此类推，每跳转到下一个MoreCommentsActivity, depth加1
    //为了使得RecyclerView最多只能嵌套MAX_NESTED_NUM层
    //需要知道当前RecyclerView已经嵌套了多少层
    //但是似乎无法直接获取RecyclerView的父RecyclerView的个数
    //所以通过Comment获取Comment的ParentComment的个数
    //然后再根据depth计算出RecyclerView嵌套的层数
    //RecyclerView嵌套的层数=Comment的ParentComment的个数 - depth * 最多允许嵌套的层数
    public void setDepth(int depth){
        this.depth = depth;
    }
}
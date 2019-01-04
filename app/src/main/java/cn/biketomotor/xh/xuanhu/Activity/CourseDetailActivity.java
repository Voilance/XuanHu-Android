package cn.biketomotor.xh.xuanhu.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.CourseDetailPageAdapter;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.Beans.User;
import cn.biketomotor.xh.xuanhu.Api.CourseApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Class.LocalUser;
import cn.biketomotor.xh.xuanhu.Fragment.CourseCommentFragment;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

//显示课程详情（课程信息和课程评论）的活动
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener,TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "TagCourseDetail";

    private ViewPager vpCourseDetail;
    private CourseDetailPageAdapter pageAdapter;
    private TabLayout tlCourseDetail;
    private View btAddNewComment;
    private Course course;

    //创建活动时初始化界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //初始化界面，绑定事件
    private void initView() {
        setContentView(R.layout.activity_course_detail);

        vpCourseDetail = findViewById(R.id.vp_course_detail);
        tlCourseDetail = findViewById(R.id.tl_course_detail);
        btAddNewComment = findViewById(R.id.bt_new_comment);
        pageAdapter = new CourseDetailPageAdapter(getSupportFragmentManager(), tlCourseDetail.getTabCount());
        vpCourseDetail.setAdapter(pageAdapter);
        vpCourseDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlCourseDetail));
        tlCourseDetail.addOnTabSelectedListener(this);
        btAddNewComment.setOnClickListener(this);


    }

    //根据课程的id，启动CourseDetailActivity
    public static void actionActivity(Context context, int id) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("courseID", id);
        context.startActivity(intent);
    }

    //同步TabLayout和ViewPager的选项
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpCourseDetail.setCurrentItem(tab.getPosition());
    }

    //暂无作用
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    //暂无作用
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //处理“添加新的评论”按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_new_comment:
                if(!LocalUser.isOnline()){
                    LoginActivity.actionActivity(this);
                    return;
                }
                if(course == null)getCourse();
                vpCourseDetail.setCurrentItem(1);
                CourseCommentFragment fragment = (CourseCommentFragment)pageAdapter.getCurrentFragment();
                popupAddCommentDialog(this, fragment.getCourseCommentAdapter(), null, course, fragment.getCourseComments());
                break;
        }
    }


    //弹出“添加新的评论”对话框
    public static void popupAddCommentDialog(Context context, final HistoryCourseCommentItemAdapter adapter, final Comment parentComment, final Course course, final List<Comment>comments){
        if(!LocalUser.isOnline()){
            LoginActivity.actionActivity(context);
            return;
        }
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_new_comment, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                makeComment(commentContent, adapter, parentComment, course, comments);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    //创建新的评论
    public static Comment makeComment(String content, final HistoryCourseCommentItemAdapter adapter, final Comment parentItem, final Course course, final List<Comment> comments){
        Comment comment = new Comment();
        comment.content = content;
        User user = new User();
        comment.user = user;
        user.id = LocalUser.getId();
        user.avatar_url = LocalUser.getAvatar_url();
        user.created_at = LocalUser.getCreated_at();
        user.description = LocalUser.getDescription();
        user.email = LocalUser.getEmail();
        user.name = LocalUser.getName();
        user.teacher_id = LocalUser.getTeacher_id();
        user.updated_at = LocalUser.getUpdated_at();
        comment.voteValue = comment.voteDown = comment.voteUp = 0;
        comment.nestedComment = new ArrayList<>();
        Date date = new Date();
        comment.created_at = date;
        comment.updated_at = date;
        if(parentItem != null){
            parentItem.addReply(comment);
            comment.course = parentItem.course;
        }
        else{
            comments.add(comment);
            comment.course = course;
        }
        adapter.notifyDataSetChanged();
        return comment;
    }

    //从后台获取课程信息数据
    void getCourse(){
        final int courseId = getIntent().getIntExtra("courseID", -1);
        if(courseId == -1)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<Course>courseResult = CourseApi.INSTANCE.getCourse(courseId);
                if(!courseResult.isOk())return;
                course = courseResult.get();
            }
        }).start();
    }

}

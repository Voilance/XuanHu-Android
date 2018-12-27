package cn.biketomotor.xh.xuanhu.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.CourseDetailPageAdapter;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.CourseApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Fragment.CourseCommentFragment;
import cn.biketomotor.xh.xuanhu.Interface.AddCommentDialogPopupable;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener,TabLayout.BaseOnTabSelectedListener, AddCommentDialogPopupable {
    private static final String TAG = "TagCourseDetail";

    private ViewPager vpCourseDetail;
    private CourseDetailPageAdapter pageAdapter;
    private TabLayout tlCourseDetail;
    private View btAddNewComment;
    private Course course;
    private List<Comment> commentList;

    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

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

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", 0);

        commentList = new ArrayList<>();
        getCourse();
        getCourseComments();
    }

    private void getCourse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<Course> result = CourseApi.INSTANCE.getCourse(courseID);
                if (result.isOk()) {
                    course = result.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ;
                        }
                    });
                } else {
                    Log.e(TAG, result.getErrorMessage());
                }
            }
        }).start();
    }

    private void getCourseComments() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<Comment>> result = CourseApi.INSTANCE.getCourseComments(courseID);
                if (result.isOk()) {
                    commentList.addAll(result.get());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ;
                        }
                    });
                } else {
                    Log.e(TAG, result.getErrorMessage());
                }
            }
        }).start();
    }

    public static void actionActivity(Context context, int id) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("courseID", id);
        context.startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpCourseDetail.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_new_comment:
                vpCourseDetail.setCurrentItem(1);
                CourseCommentFragment fragment = (CourseCommentFragment)pageAdapter.getCurrentFragment();
                View view = fragment.getView();
                popupAddCommentDialog(fragment.getCourseCommentAdapter(), fragment.getCourseComments(), null);
                break;
        }
    }



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
}

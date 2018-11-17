package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.CommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Class.Sys;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class MainActivity extends BaseActivity {

    private static List<CommentItem> commentList;
    private static CommentItemAdapter commentItemAdapter;
    private static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onAutoLogin();
    }

    private void initView() {
        setContentView(R.layout.test_activity_main);

        // 初始化最新评论列表
        recyclerView = findViewById(R.id.rv_comment_list);
        commentList = new ArrayList<>();
        commentItemAdapter = new CommentItemAdapter(commentList);
        commentItemAdapter.setItemClickListener(new CommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toast(commentList.get(position).getCourseTitle());
            }
        });
        recyclerView.setAdapter(commentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void onAutoLogin() {
        // 读取存在本地的用户信息
        Sys.readSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
        // 如果存在本地的用户信息不为空且上次退出应用时处于登陆状态，则自动登陆
        if (!Sys.getEmail().equals("") && Sys.isLogin()) {
            // 登陆成功：初始化个人信息，右上角显示头像昵称等基本信息。
            // 登陆失败：右上角显示“登陆”按钮
        }

//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
//        commentItemAdapter.notifyDataSetChanged();
    }
}

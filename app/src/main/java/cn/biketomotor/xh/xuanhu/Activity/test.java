package cn.biketomotor.xh.xuanhu.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.biketomotor.xh.xuanhu.R;


public class test extends  AppCompatActivity {
    private static final String TAG = "test";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listView;
    private List<MessageObj> mData;
    private List<String> choices;
    private List<Integer> choiceIcon;
    private MyAdapter recyclerAdapter;
    private ImageView startbutton;
    private  Toolbar toolbar;
    @BindView(R.id.adminicon)

    ImageView _adminicon;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gohome);

        initData();
        initViews();
    }


    private void initViews() {
        /**
         *  初始化Toolbar
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu4);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                    {

                        Intent intent = new Intent(test.this,designsearchview.class);
                        startActivity(intent);
                        //Toast.makeText(HomepageActivity.this, "Search !", Toast.LENGTH_LONG).show();
                        break;
                    }

                    case R.id.action_notifications:
                        Toast.makeText(test.this, "Notification !", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });


        /**
         *  初始化RecyclerView
         */
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new MyAdapter(mData);
        recyclerView.setAdapter(recyclerAdapter);

        /**
         *  初始化swipeRefreshLayout
         */
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.reverse(mData);
                        try {
                            Thread.sleep(1000); //模拟耗时操作
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        /**
         *  初始化侧滑菜单 DrawerLayout
         */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close
        );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        listView = (ListView) findViewById(R.id.listview);
        ListAdapter adapter = new MenuAdapter(this,choices,choiceIcon);
        listView.setAdapter(adapter);

        startbutton=(ImageView)this.findViewById(R.id.adminicon);
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(test.this, "尚未登录!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initData() {

        mData = new ArrayList<MessageObj>();

        MessageObj obj1 = new MessageObj("STAR1",R.mipmap.shield,"5.6K","中国文学文化",
                "黄老师上课非常棒棒。");
        mData.add(obj1);
        MessageObj obj2 = new MessageObj("STAR2",R.mipmap.stark,"7.8K","岭南文化",
                "刘兴东老师的岭南文化可以说是非常好了！ 老师幽默风趣，讲课认真，积极与同学互动。 假如某一节课你有事去不了，完全没问题！完全不影响最后的高分。 期末的时候介绍一个岭南文化，形式不限 ppt 论文 散文 诗歌 流水账等等。。 考试是开卷的，老师会给复习资料，很简单！分数也很高！");
        mData.add(obj2);
        MessageObj obj3 = new MessageObj("STAR3",R.mipmap.thor,"7.8K","中国文化概论",
                "王文梅老师的中国文化概论慎选啊！ 你能想到的所有测试形式她都能给你实施出来。 英语论文，中国文化pre，平时作业，歌曲翻译，最后闭卷考试！ 整个课程下来可以说是丰富多彩！十分充实了！ 几乎节节课签到。");
        mData.add(obj3);
        MessageObj obj4 = new MessageObj("STAR4",R.mipmap.steven,"7.8K","大学生心理健康教育",
                "胡寒春老师的课总体氛围还是比较轻松的,每一堂课都会有一份PPT，上课方式比较多样，会有很多小游戏之类的，大班上课，还要分组，时不时也会推销一下大学心理咨询室。不过就是有时上课内容比较。。。simple，有点催眠。");
        mData.add(obj4);
        MessageObj obj5 = new MessageObj("STAR5",R.mipmap.widow,"7.8K","英语文化与英语歌曲",
                "屈薇老师人又好看，说话又好听，性格又有亲和度，上课也比较好玩，虽然全英授课，但是交流没有问题的，而且如果上课英语实在有点困难，（大家氛围不太好），也会适当的转换回中文，所以不用担心。");
        mData.add(obj5);

        choices = new ArrayList<String>();
        choiceIcon = new ArrayList<>();
        choices.add("个人主页");
        choices.add("我的点赞");
        choices.add("我的踩");
        choices.add("我的评论");
        choiceIcon.add(R.drawable.homepage);
        choiceIcon.add(R.drawable.like);
        choiceIcon.add(R.drawable.dislike);
        choiceIcon.add(R.drawable.comment_1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}

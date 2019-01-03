package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.SearchResultItemAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.Beans.Department;
import cn.biketomotor.xh.xuanhu.Api.Beans.Teacher;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.SearchApi;
import cn.biketomotor.xh.xuanhu.CustomUIElement.TouchableDrawableEditText;
import cn.biketomotor.xh.xuanhu.R;

//搜索课程活动，用于根据课程名搜索课程
public class SearchActivity extends BaseActivity implements View.OnClickListener, TouchableDrawableEditText.DrawableClickListener {

    private View btSearch;
    private TouchableDrawableEditText etSearch;
    private RecyclerView rvSearchResult;
    List<SearchApi.CourseSearched> courseList;
    SearchResultItemAdapter searchResultItemAdapter;
    private static final String[] candidateCourses = {
            "跨文化交际",
            "学术英语写作",
            "大学语文",
            "中国传统人生智慧",
            "中国情感文化学",
            "中国文学与文化",
            "岭南文化",
            "中国传统文化",
            "戏剧审美与剧场实验",
            "大学美育",
            "食品营养与安全",
            "走近微电子",
            "生物科学与工程前沿",
            "环境科学与工程导论",
            "生涯规划与求职技巧",
            "物流与社会",
            "大学生心理健康教育",
            "知识产权概论",
            "舞蹈美学和经典作品鉴赏",
            "数学文化"
    };

    //创建活动时，获取搜索框和搜索按钮，绑定搜索事件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btSearch = findViewById(R.id.bt_search);
        etSearch = findViewById(R.id.et_search);
        rvSearchResult = findViewById(R.id.rv_search_result);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, candidateCourses);
        etSearch.setAdapter(adapter);

        btSearch.setOnClickListener(this);
        etSearch.setDrawableClickListener(this);
        etSearch.setOnClickListener(this);
        courseList = new ArrayList<>();
        searchResultItemAdapter = new SearchResultItemAdapter(courseList);
        rvSearchResult.setAdapter(searchResultItemAdapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        searchResultItemAdapter.setItemClickListener(new SearchResultItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CourseDetailActivity.actionActivity(SearchActivity.this, courseList.get(position).id);
            }
        });
    }

    //启动搜索活动
    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    //处理搜索事件，以及在用户点击搜索框时显示可搜索的课程
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                String keyword = etSearch.getText().toString();
                search(keyword);
                break;
            case R.id.et_search:
                etSearch.showDropDown();
                break;
        }
    }

    //处理搜索框右侧清除文本的按钮的点击事件
    @Override
    public void onClick(DrawablePosition target) {
        switch (target){
            case RIGHT:
                etSearch.setText("");
                break;
            default:
                break;
        }
    }

    //根据课程名搜索课程并更新界面
    private void search(final String keyword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<List<SearchApi.CourseSearched>>searchedResult = SearchApi.INSTANCE.searchCourse(keyword);
                if(searchedResult.isErr())return;
                List<SearchApi.CourseSearched>courses = searchedResult.get();
                courseList.clear();
                courseList.addAll(courses);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchResultItemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
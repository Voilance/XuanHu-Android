package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.NewCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TagHome";

    private EditText etSearch;
    private Button btSearch;
    private Button btMore;

    private MainActivity          mainActivity;
    private List<CommentItem>     commentList;
    private NewCommentItemAdapter newCommentItemAdapter;
    private RecyclerView          recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_home, container, false);

        etSearch = view.findViewById(R.id.et_search);
        btSearch = view.findViewById(R.id.bt_search);
        btMore = view.findViewById(R.id.bt_more);
        btSearch.setOnClickListener(this);
        btMore.setOnClickListener(this);

        mainActivity = (MainActivity)getActivity();
        recyclerView = view.findViewById(R.id.rv_comment_list);
        commentList = new ArrayList<>();
        newCommentItemAdapter = new NewCommentItemAdapter(commentList);
        newCommentItemAdapter.setItemClickListener(new NewCommentItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mainActivity, commentList.get(position).getCourseTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(newCommentItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        getComment();

        return view;
    }

    private void getComment() {
        // 获取5条评论
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        commentList.add(new CommentItem(0, "courseTitle", "userName", "content", "createdAt", 0, 0));
        newCommentItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                break;
            case R.id.bt_more:
                getComment();
                break;
            default:
                break;
        }
    }
}

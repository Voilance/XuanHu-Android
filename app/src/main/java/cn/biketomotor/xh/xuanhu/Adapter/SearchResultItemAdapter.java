package cn.biketomotor.xh.xuanhu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.SearchApi;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

//搜索结果的适配器
public class SearchResultItemAdapter extends RecyclerView.Adapter<SearchResultItemAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDepartment;
        private TextView tvTeacher;
        private TextView tvTitle;
        private TextView tvIntro;
        private TextView tvType;

        ViewHolder(View itemView) {
            super(itemView);
            tvDepartment = itemView.findViewById(R.id.tv_course_department);
            tvTeacher = itemView.findViewById(R.id.tv_course_teacher);
            tvTitle = itemView.findViewById(R.id.tv_course_title);
            tvIntro = itemView.findViewById(R.id.tv_course_intro);
            tvType = itemView.findViewById(R.id.tv_course_type);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private List<SearchApi.CourseSearched> courseList;
    private SearchResultItemAdapter.onItemClickListener clickListener;
    //构造函数
    public SearchResultItemAdapter(List<SearchApi.CourseSearched> list) {
        this.courseList = list;
    }

    //创建ViewHolder
    @Override
    public SearchResultItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_search_result_item, parent, false);
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
        return new SearchResultItemAdapter.ViewHolder(view);
    }

    //填充数据
    @Override
    public void onBindViewHolder(final SearchResultItemAdapter.ViewHolder holder, int position) {
        holder.tvDepartment.setText(courseList.get(position).department);
        holder.tvTeacher.setText(courseList.get(position).getNameOfTeachers());
        holder.tvTitle.setText(courseList.get(position).title);
        holder.tvIntro.setText(String.valueOf(courseList.get(position).intro));
        holder.tvType.setText(String.valueOf(courseList.get(position).course_type));
        holder.itemView.setTag(position);
    }

    //获取评论数
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    //设置监听器
    public void setItemClickListener(SearchResultItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }
}

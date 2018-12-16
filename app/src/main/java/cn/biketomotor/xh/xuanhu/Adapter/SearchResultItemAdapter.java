package cn.biketomotor.xh.xuanhu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

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

    private List<Course> courseList;
    private SearchResultItemAdapter.onItemClickListener clickListener;

    public SearchResultItemAdapter(List<Course> list) {
        this.courseList = list;
    }

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

    @Override
    public void onBindViewHolder(final SearchResultItemAdapter.ViewHolder holder, int position) {
        holder.tvDepartment.setText(courseList.get(position).department.name);
        holder.tvTeacher.setText(courseList.get(position).getNameOfTeachers());
        holder.tvTitle.setText(courseList.get(position).title);
        holder.tvIntro.setText(String.valueOf(courseList.get(position).intro));
        holder.tvType.setText(String.valueOf(courseList.get(position).course_type));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void setItemClickListener(SearchResultItemAdapter.onItemClickListener listener) {
        this.clickListener = listener;
    }
}

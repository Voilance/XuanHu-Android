package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.CourseApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.R;
//课程详情Fragment，显示课程的名字、开课学院、教师、类型等信息
public class CourseDetailFragment extends Fragment {
    private View view = null;
    private Course course;
    private TextView tvCourseTitle;
    private TextView tvCourseTeacher;
    private TextView tvCourseDepartment;
    private TextView tvCourseType;
    private TextView tvCourseIntroduction;
    //创建界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        tvCourseTitle = view.findViewById(R.id.tv_course_title);
        tvCourseTeacher = view.findViewById(R.id.tv_course_teacher);
        tvCourseDepartment = view.findViewById(R.id.tv_course_department);
        tvCourseType = view.findViewById(R.id.tv_course_type);
        tvCourseIntroduction = view.findViewById(R.id.tv_course_introduction);
        getCourse();
        return view;
    }

    //从后台获取课程信息
    void getCourse(){
        final int courseId = getActivity().getIntent().getIntExtra("courseID", -1);
        if(courseId == -1)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<Course>courseResult = CourseApi.INSTANCE.getCourse(courseId);
                if(!courseResult.isOk())return;
                course = courseResult.get();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCourseTitle.setText(course.title);
                        tvCourseTeacher.setText(course.getNameOfTeachers());
                        tvCourseDepartment.setText(course.department.name);
                        tvCourseType.setText(course.course_type);
                        tvCourseIntroduction.setText(course.intro);
                    }
                });
            }
        }).start();
    }
}

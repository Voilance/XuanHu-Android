package cn.biketomotor.xh.xuanhu.Api;

import org.junit.Test;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;

import static org.junit.Assert.assertTrue;

public class CourseApiUnitTest {
    @Test
    public void shouldGetCourse() {
        Result<Course> course = CourseApi.INSTANCE.getCourse(5);
        assertTrue(course.isOk());
    }

    @Test
    public void shouldGetCourseComments() {
        Result<List<Comment>> course = CourseApi.INSTANCE.getCourseComments(20);
        assertTrue(course.isOk());
    }
}

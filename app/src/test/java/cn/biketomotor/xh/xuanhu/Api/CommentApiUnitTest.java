package cn.biketomotor.xh.xuanhu.Api;

import org.junit.Test;

import java.util.List;
import static org.junit.Assert.assertTrue;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;

public class CommentApiUnitTest {
    @Test
    public void shouldGetLatestComments() {
        Result<List<Comment>> comments = CommentApi.INSTANCE.latest(1);
        assertTrue(comments.isOk());
    }
}

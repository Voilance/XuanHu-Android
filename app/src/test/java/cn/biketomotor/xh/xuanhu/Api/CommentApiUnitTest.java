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

    @Test
    public void shouldCreateComment() {
        String email = "test@test.com";
        String password = "testtest";
        Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isOk());

        // Change the id here if fails
        Result<Comment> comment = CommentApi.INSTANCE.create(20, "测试测试测试测试测试", 81);
        assertTrue(comment.isOk());
    }

    @Test
    public void shouldVote() {
        String email = "test@test.com";
        String password = "testtest";
        Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isOk());

        // Change the id here if fails
        Result<Comment> comment = CommentApi.INSTANCE.vote(20, 81, 1);
        assertTrue(comment.isOk());
    }
}

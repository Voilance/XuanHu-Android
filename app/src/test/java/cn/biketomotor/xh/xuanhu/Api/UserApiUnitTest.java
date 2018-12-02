package cn.biketomotor.xh.xuanhu.Api;

import org.junit.Test;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserApiUnitTest {
    @Test
    public void shouldGetUserInfo() {
        Result<UserApi.UserInfo> info = UserApi.INSTANCE.getUserInfo(21);
        assertTrue(info.isOk());
    }

    @Test
    public void shouldRegister() {
        String name = String.valueOf(System.currentTimeMillis());
        String email = name + "@qq.com";
        String password = "foobarfoo";
        Result<UserApi.UserInfo> info = UserApi.INSTANCE.register(name, email, password);
        assertTrue(info.isOk());
        assertEquals(info.get().name, name);

        // 邮箱被占用的情况
        Result<UserApi.UserInfo> dupResult = UserApi.INSTANCE.register(name, email, password);
        assertTrue(dupResult.isErr());
        assertEquals(dupResult.getErrorMessage(), "邮箱已被占用");
    }

    @Test
    public void shouldGetUserComments() {
        Result<List<Comment>> result = UserApi.INSTANCE.getUserComments(15);
        assertTrue(result.isOk());
        System.out.println(result.get().get(0).content);
    }

    @Test
    public void shouldGetUserVotes() {
        Result<List<Comment>> result = UserApi.INSTANCE.getUserVotes(15);
        assertTrue(result.isOk());
        System.out.println(result.get().get(0).content);
    }
}

package cn.biketomotor.xh.xuanhu.Api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SessionApiUnitTest {
    @Test
    public void deserializeCorrectLoginResult() {
        String json = "{\n" +
                "\"id\": 21,\n" +
                "\"email\": \"test@test.com\",\n" +
                "\"name\": \"test\",\n" +
                "\"created_at\": \"2018-11-17T02:56:06.702Z\",\n" +
                "\"updated_at\": \"2018-11-17T02:56:06.702Z\",\n" +
                "\"avatar_url\": null,\n" +
                "\"teacher_id\": null,\n" +
                "\"description\": \"\"\n" +
                "}";
        Result<SessionApi.LoginResultParser.LoginResult> result = SessionApi.LoginResultParser.parse(json);
        assertTrue(result.isOk());
        SessionApi.LoginResultParser.LoginResult loginResult = result.get();
        assertTrue(loginResult.success);
        assertEquals(loginResult.id, 21);
        assertEquals(loginResult.email, "test@test.com");
        assertEquals(loginResult.name, "test");
        assertEquals(loginResult.description, "");
        assertNull(loginResult.avatar_url);
        assertNull(loginResult.teacher_id);
    }

    @Test
    public void deserializeFailureLoginResult() {
        String json = "{\"success\": false}";
        Result<SessionApi.LoginResultParser.LoginResult> result = SessionApi.LoginResultParser.parse(json);
        assertTrue(result.isOk());
        SessionApi.LoginResultParser.LoginResult loginResult = result.get();
        assertFalse(loginResult.success);
    }

    @Test
    public void deserializeBadLoginResult() {
        String json = "bad json";
        Result<SessionApi.LoginResultParser.LoginResult> result = SessionApi.LoginResultParser.parse(json);
        assertTrue(result.isErr());
        assertEquals(result.getErrorMessage(), "JSON解析失败");
    }

    @Test
    public void shouldLoginWithCorrectInfo() {
        String email = "test@test.com";
        String password = "testtest";
        Result<SessionApi.LoginResultParser.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isOk());
        SessionApi.LoginResultParser.LoginResult loginResult = result.get();
        assertTrue(loginResult.success);
        assertEquals(loginResult.email, "test@test.com");
    }

    @Test
    public void shouldNotLoginWithIncorrectInfo() {
        String email = "test@test.com";
        String password = "testtest2";
        Result<SessionApi.LoginResultParser.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isErr() || (!result.get().success));
    }
}

package cn.biketomotor.xh.xuanhu.Api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SessionApiUnitTest {

    @Test
    public void shouldLoginWithCorrectInfo() {
        String email = "test@test.com";
        String password = "testtest";
        Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isOk());
        SessionApi.LoginResult loginResult = result.get();
        assertTrue(loginResult.success);
        assertEquals(loginResult.email, "test@test.com");
    }

    @Test
    public void shouldNotLoginWithIncorrectInfo() {
        String email = "test@test.com";
        String password = "testtest2";
        Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(email, password);
        assertTrue(result.isErr() || (!result.get().success));
    }

    @Test
    public void logoutBeforeLoginShouldFail() {
        SessionApi.INSTANCE.logout();
        Result<SessionApi.LogoutResult> result = SessionApi.INSTANCE.logout();
        assertTrue(result.isErr() || (!result.get().success));
    }

    @Test
    public void shouldLogoutAfterLogin() {
        String email = "test@test.com";
        String password = "testtest";
        Result<SessionApi.LoginResult> result1 = SessionApi.INSTANCE.login(email, password);
        assertTrue(result1.isOk());
        Result<SessionApi.LogoutResult> result2 = SessionApi.INSTANCE.logout();
        assertTrue(result2.isOk());
    }
}

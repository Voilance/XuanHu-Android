package cn.biketomotor.xh.xuanhu.Class;

import android.content.SharedPreferences;

public class Sys {

    private static String email = "";
    private static String password = "";
    private static String name = "";
    private static boolean login = false;
    public static final String SPName = "SYS_SP";
    private static SharedPreferences.Editor editor = null;

    public static String getEmail() {
        return Sys.email;
    }

    public static void setEmail(String email) {
        Sys.email = email;
    }

    public static String getPassword() {
        return Sys.password;
    }

    public static void setPassword(String password) {
        Sys.password = password;
    }

    public static String getName() {
        return Sys.name;
    }

    public static void setName(String name) {
        Sys.name = name;
    }

    public static boolean isLogin() {
        return Sys.login;
    }

    public static void setLogin(boolean login) {
        Sys.login = login;
    }

    public static void readSP(SharedPreferences sp) {
        Sys.email = sp.getString("email", "");
        Sys.password = sp.getString("password", "");
        Sys.name = sp.getString("name", "");
        Sys.login = sp.getBoolean("login", false);
    }

    public static void writeSP(SharedPreferences sp) {
        Sys.editor = sp.edit();
        Sys.editor.putString("email", Sys.email);
        Sys.editor.putString("password", Sys.password);
        Sys.editor.putString("name", Sys.name);
        Sys.editor.putBoolean("login", Sys.login);
        Sys.editor.commit();
        Sys.editor = null;
    }
}

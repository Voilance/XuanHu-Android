package cn.biketomotor.xh.xuanhu.Class;

import java.util.Date;

import cn.biketomotor.xh.xuanhu.Api.Beans.User;

public class LocalUser {

    public static boolean online = false;

    private static int id = -1;
    private static String email = "";
    private static String name = "";
    private static Date created_at;
    private static Date updated_at;
    private static String avatar_url;
    private static Integer teacher_id;
    private static String description = "";

    public static void initLocalUser(
            int id,
            String email,
            String name,
            Date created_at,
            Date updated_at,
            String avatar_url,
            Integer teacher_id,
            String description
    ) {
        LocalUser.id = id;
        LocalUser.email = email;
        LocalUser.name = name;
        LocalUser.created_at = created_at;
        LocalUser.updated_at = updated_at;
        LocalUser.avatar_url = avatar_url;
        LocalUser.teacher_id = teacher_id;
        LocalUser.description = description;
    }

    public static boolean isOnline() {
        return online;
    }

    public static int getId() {
        return id;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public static Date getCreated_at() {
        return created_at;
    }

    public static Date getUpdated_at() {
        return updated_at;
    }

    public static String getAvatar_url() {
        return avatar_url;
    }

    public static Integer getTeacher_id() {
        return teacher_id;
    }

    public static String getDescription() {
        return description;
    }
}

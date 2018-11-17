package cn.biketomotor.xh.xuanhu.Class;

public class User {

    private static int userID = -1;
    private static String email = "";
    private static String name = "";
    private static String description = "";
    private static String avatar = "";
    private static String createdAt = "";
    private static String updatedAt = "";

    public static int getUserID() {
        return User.userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }

    public static String getEmail() {
        return User.email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getName() {
        return User.name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getDescription() {
        return User.description;
    }

    public static void setDescription(String description) {
        User.description = description;
    }

    public static String getAvatar() {
        return User.avatar;
    }

    public static void setAvatar(String avatar) {
        User.avatar = avatar;
    }

    public static String getCreatedAt() {
        return User.createdAt;
    }

    public static void setCreatedAt(String createdAt) {
        User.createdAt = createdAt;
    }

    public static String getUpdatedAt() {
        return User.updatedAt;
    }

    public static void setUpdatedAt(String updatedAt) {
        User.updatedAt = updatedAt;
    }
}

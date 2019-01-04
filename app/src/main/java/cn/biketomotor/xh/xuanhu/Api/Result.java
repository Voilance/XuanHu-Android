package cn.biketomotor.xh.xuanhu.Api;

/**
 * 表示从服务器获取的结果的工具类
 * @param <T> 成功结果的类型
 */
public class Result<T> {
    enum ResultType {
        Ok, Err
    }

    private ResultType type;

    private T inner;

    private String errorMessage;

    private Result() {
    }

    /**
     * 构造一个成功的结果
     * @param inner 实际的结果
     * @param <T> 结果的类型
     * @return 构造出的结果
     */
    static <T> Result<T> ok(T inner) {
        Result<T> result = new Result<>();
        result.type = ResultType.Ok;
        result.inner = inner;
        return result;
    }

    /**
     * 构造一个有错误的结果
     * @param message 错误信息
     * @param <T> 结果的类型
     * @return 构造出的结果
     */
    static <T> Result<T> err(String message) {
        Result<T> result = new Result<>();
        result.type = ResultType.Err;
        result.errorMessage = message;
        return result;
    }

    /**
     * 判断该结果是否是成功的
     * @return 成功则返回 true，否则返回 false
     */
    public boolean isOk() {
        return type == ResultType.Ok;
    }

    /**
     * 判断该结果是否是有错误的
     * @return 成功则返回 true，否则返回 false
     */
    public boolean isErr() {
        return type == ResultType.Err;
    }

    /**
     * 获取成功结果中的实际对象
     * @return 如果该结果有错误，会返回 null。
     */
    public T get() {
        return inner;
    }

    /**
     * 获取错误结果中的错误信息
     * @return 如果该结果没有错误，会返回 null。
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}

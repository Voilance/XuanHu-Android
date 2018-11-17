package cn.biketomotor.xh.xuanhu.Api;

public class Result<T> {
    enum ResultType {
        Ok, Err
    }

    private ResultType type;

    private T inner;

    private String errorMessage;

    private Result() {
    }

    static <T> Result<T> ok(T inner) {
        Result<T> result = new Result<>();
        result.type = ResultType.Ok;
        result.inner = inner;
        return result;
    }

    static <T> Result<T> err(String message) {
        Result<T> result = new Result<>();
        result.type = ResultType.Err;
        result.errorMessage = message;
        return result;
    }

    public boolean isOk() {
        return type == ResultType.Ok;
    }

    public boolean isErr() {
        return type == ResultType.Err;
    }

    public T get() {
        return inner;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

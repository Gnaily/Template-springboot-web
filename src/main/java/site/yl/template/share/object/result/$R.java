package site.yl.template.share.object.result;

public class $R {
    protected static String OK="ok";
    protected static String DEFAULT_MSG="成功";

    public static  EmptyResult success(){
        return new EmptyResult(OK,DEFAULT_MSG);
    }

    public static  <T> DataResult<T> success(T data){
        return new DataResult<>(OK,data,DEFAULT_MSG);
    }

    public static  <T> DataResult<T> success(T data, String msg){
        return new DataResult<>(OK,data,msg);
    }

    public static EmptyResult fail(String code, String msg){
        return new EmptyResult(code,msg);
    }
}

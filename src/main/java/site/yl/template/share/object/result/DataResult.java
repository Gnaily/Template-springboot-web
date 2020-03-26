package site.yl.template.share.object.result;

public class DataResult<T> extends AbsResult {

  private T data;

  public DataResult(String code, T data,String message){
    super(code,message);
    this.data=data;
  }

  public T getData() {
    return data;
  }

}

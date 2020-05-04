package site.yl.template.share.apiresult;

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

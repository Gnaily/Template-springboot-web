package site.yl.template.share.apiresult;

public abstract class AbsResult implements IResult {

  private String code;
  private String message;

  public AbsResult(String code, String message){
    this.code=code;
    this.message=message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}

package site.yl.template.share.apiresult;

public class EmptyResult  extends AbsResult{

  private String code;
  private String message;

  public EmptyResult(String code, String message){
   super(code,message);
  }

}

package site.yl.template.share.object.result;

public class EmptyResult  extends AbsResult{

  private String code;
  private String message;

  public EmptyResult(String code, String message){
   super(code,message);
  }

}

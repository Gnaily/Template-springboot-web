package site.yl.template;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.exception.DataAccessException;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.yl.template.share.exception.BusinessException;
import site.yl.template.share.object.result.$R;
import site.yl.template.share.object.result.IResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
@ControllerAdvice
public class GlobleExceptionHandler extends ResponseEntityExceptionHandler{

  private final Log logger = LogFactory.getLog(GlobleExceptionHandler.class);

  private final Environment env;
  public GlobleExceptionHandler(Environment environment){
    this.env=environment;
  }

  @Override
  public ResponseEntity<Object>  handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    logError(request,ex);

    IResult result ;
    if(!isProductionEnv()){

      List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
      String msg="参数验证失败";
      if(!CollectionUtils.isEmpty(fieldErrors)) {
        msg+="[";
        StringBuilder msgBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
          msgBuilder.append(fieldError.getDefaultMessage()).append(",");
        }
        String errorMsg = msgBuilder.toString();
        if (errorMsg.length() > 1) {
          errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
        }
        msg+=(errorMsg+"]");
      }
      result=$R.fail(String.valueOf(status.value()),msg);
    } else {
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return handleExceptionInternal(ex, result, headers, status, request);
  }



  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<IResult> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException exception) {

    logError(request,exception);

    HttpStatus status=HttpStatus.EXPECTATION_FAILED;
    IResult result;
    if(!isProductionEnv()){
      Set<ConstraintViolation<?>> violations= exception.getConstraintViolations();

      String msg="参数验证失败";
      if(!CollectionUtils.isEmpty(violations)) {
        msg += "[";

        StringBuilder msgBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
          msgBuilder.append(violation.getMessage()).append(",");
        }

        String errorMsg = msgBuilder.toString();
        if (errorMsg.length() > 1) {
          errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
        }
        msg += (errorMsg + "]");
      }
      result=$R.fail(String.valueOf(status.value()),msg);
    } else {
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return new ResponseEntity<>(result, status);
  }



  /* (non-Javadoc)
   * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    logError(request,ex);

    IResult result;
    if(!isProductionEnv()){
      List<ObjectError>  objectErrors = ex.getBindingResult().getAllErrors();
      if(!CollectionUtils.isEmpty(objectErrors)) {
        StringBuilder msgBuilder = new StringBuilder();
        for (ObjectError objectError : objectErrors) {
          msgBuilder.append(objectError.getDefaultMessage()).append(",");
        }
        String errorMessage = msgBuilder.toString();
        if (errorMessage.length() > 1) {
          errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        result=$R.fail(String.valueOf(status.value()),errorMessage);
      }else {
        result=$R.fail(String.valueOf(status.value()),ex.getMessage());
      }
    } else {
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return handleExceptionInternal(ex, result, headers, status, request);
  }

  @Override
  public  ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    logError(request,ex);
    IResult result;
    if(!isProductionEnv()){
      result=$R.fail(String.valueOf(status.value()),"请求参数缺失：" + ex.getMessage());
    }else{
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return handleExceptionInternal(ex, result, headers,status, request);
  }


  @Override
  public  ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    logError(request,ex);

    IResult result;
    if(!isProductionEnv()){
      result=$R.fail(String.valueOf(status.value()),"不支持的请求方法:" + ex.getMessage());
    }else{
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return handleExceptionInternal(ex, result, headers, status, request);
  }

  /* (non-Javadoc)
   * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleHttpMediaTypeNotSupported(org.springframework.web.HttpMediaTypeNotSupportedException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

    logError(request,ex);

    IResult result;
    if(!isProductionEnv()){
      result=$R.fail(String.valueOf(status.value()),"请求的媒体类型不支持:" + ex.getMessage());
    }else{
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return handleExceptionInternal(ex, result, headers,status, request);
  }


  /* (non-Javadoc)
   * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleServletRequestBindingException(org.springframework.web.bind.ServletRequestBindingException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
   */
  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                        HttpHeaders headers, HttpStatus status,
                                                                        WebRequest request) {

    logError(request,ex);

    IResult result;
    if(!isProductionEnv()){
      result=$R.fail(String.valueOf(status.value()),"请求参数绑定异常:" + ex.getMessage());
    }else{
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return  handleExceptionInternal(ex, result, headers, status, request);
  }


  @ExceptionHandler({DataAccessException.class})
  public ResponseEntity<IResult> handleIntegrityViolationException(HttpServletRequest request,
                                                                      DataIntegrityViolationException exception) {
    logError(request,exception);
    HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;

    IResult result;
    if(!isProductionEnv()){
      result=$R.fail(String.valueOf(status.value()),"数据库访问异常:" + exception.getMessage());
    }else{
      result=$R.fail(String.valueOf(status.value()),prodExceptionMsg(request));
    }
    return new ResponseEntity<>(result, status);
  }


  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<IResult> handleBusinessException(HttpServletRequest request, BusinessException exception) {

    logError(request,exception);

    IResult result = $R.fail(exception.getCode(),exception.getMessage());
    return  new ResponseEntity<>(result, HttpStatus.OK) ;
  }



  @ExceptionHandler({Exception.class})
  public ResponseEntity<IResult> handleException(HttpServletRequest request, Exception exception) {

    logError(request,exception);

    HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;
    IResult result;
    if(isProductionEnv()){
      result=$R.fail("Unknown-Error",prodExceptionMsg(request));
    }else {
      result=$R.fail("Unknown-Error", exception.fillInStackTrace().toString());
    }
    return new ResponseEntity<>(result, status);
  }


  //------------------------helper function

  private void logError(HttpServletRequest request,Exception exception) {
   StringBuilder message=new StringBuilder("请求出错：");
    message.append(request.getRequestURL().toString())
           .append("\r")
           .append("请求参数：").append(request.getQueryString());
    logger.error(message, exception);
  }


  private void logError(WebRequest request,Exception exception) {
    Object object=((ServletWebRequest)request).getNativeRequest();
    HttpServletRequest httpServletRequest=((HttpServletRequest)object);
    logError(httpServletRequest,exception);
  }

  private  String prodExceptionMsg(WebRequest request){
    Object object=((ServletWebRequest)request).getNativeRequest();
    HttpServletRequest httpServletRequest=((HttpServletRequest)object);
    return  prodExceptionMsg(httpServletRequest);
  }

  private  String prodExceptionMsg(HttpServletRequest request){
    return  "出错啦("+request.getRequestURI()+")";
  }


  private  boolean isProductionEnv(){
    return env.acceptsProfiles("prod");
  }

  private  boolean isDevolopEnv(){
    return env.acceptsProfiles("dev");
  }

  private  boolean isTestEnv(){
    return env.acceptsProfiles("test");
  }

}

package co.com.usc.hostalusc.api.exceptions;

import co.com.usc.hostalusc.api.utils.RequestInformationExtractor;
import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.exceptions.HeaderNotFoundException;
import co.com.usc.hostalusc.domain.exceptions.InvalidInputDataException;
import co.com.usc.hostalusc.domain.exceptions.UscException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler extends RequestBodyAdviceAdapter {

  private Object requestBody;

  @ExceptionHandler(HeaderNotFoundException.class)
  public ResponseEntity<OutputContract<Object>> handleHeaderNotFoundException(
      HeaderNotFoundException ex,
      HttpServletRequest request) {
    String message = ex.getMessage();
    MessageCode messageCode = MessageCode.HEADER_NOT_FOUND;
    int statusCode = HttpStatus.BAD_REQUEST.value();

    logRequestInfo(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractFailureResponse(message, messageCode, statusCode);
  }

  @ExceptionHandler(UscException.class)
  public ResponseEntity<OutputContract<Object>> handleEventsCreationException(
          UscException ex,
      HttpServletRequest request) {
    String message = ex.getMessage();
    MessageCode messageCode = new MessageCode(ex.getMessage());
    int statusCode = HttpStatus.BAD_REQUEST.value();

    logRequestInfo(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractFailureResponse(message, messageCode, statusCode);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<OutputContract<Object>> accessDeniedException(
          AccessDeniedException ex,
          HttpServletRequest request) {
    String message = ex.getMessage();
    MessageCode messageCode = new MessageCode(ex.getMessage());
    int statusCode = HttpStatus.FORBIDDEN.value();

    logRequestInfo(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractFailureResponse(message, messageCode, statusCode);
  }

  @ExceptionHandler(InvalidInputDataException.class)
  public ResponseEntity<OutputContract<Object>> handleInvalidInputDataException(
      InvalidInputDataException ex, HttpServletRequest request) {
    String message = ex.getMessage();
    MessageCode messageCode = MessageCode.INVALID_INPUT_DATA;
    int statusCode = HttpStatus.BAD_REQUEST.value();

    logRequestError(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractFailureResponse(message, messageCode, statusCode);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<OutputContract<Object>> handleGenericException(Exception ex, HttpServletRequest request) {
    String message = ex.getMessage();
    String showMessage = "Something went wrong with the request";
    MessageCode messageCode = MessageCode.INTERVAL_SERVER_ERROR;
    int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    logRequestError(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractErrorResponse(showMessage, messageCode, statusCode);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<OutputContract<Object>> handleRuntimeException(Exception ex, HttpServletRequest request) {
    String message = ex.getMessage();
    String showMessage = "Something went wrong with the request";
    MessageCode messageCode = MessageCode.INTERVAL_SERVER_ERROR;
    int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    logRequestError(message, messageCode, statusCode, request, ex);
    return ResponseUtils.buildOutputContractErrorResponse(showMessage, messageCode, statusCode);
  }

  private void logRequestError(String message, MessageCode messageCode, int statusCode,
      HttpServletRequest request, Exception e) {
    String resultMessage = logRequestMessage(message, messageCode, statusCode, request);
    log.error(resultMessage, e);
  }

  private void logRequestInfo(String message, MessageCode messageCode, int statusCode,
      HttpServletRequest request, Exception e) {
    String resultMessage = logRequestMessage(message, messageCode, statusCode, request);
    log.info(resultMessage, e);
  }

  private String logRequestMessage(String message, MessageCode messageCode, int statusCode,
      HttpServletRequest request) {
    String requestInfo = RequestInformationExtractor.extractInformation(request, requestBody);
    return String.format(
        "Something went wrong with the request. Message: %s. MessageCode: %s, statusCode: %d, RequestInfo: %s",
        message, messageCode.getCode(), statusCode,
        requestInfo);
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    this.requestBody = body;
    return body;
  }

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }
}

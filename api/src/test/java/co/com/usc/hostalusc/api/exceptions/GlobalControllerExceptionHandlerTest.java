package co.com.usc.hostalusc.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.exceptions.HeaderNotFoundException;
import co.com.usc.hostalusc.domain.exceptions.InvalidInputDataException;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

class GlobalControllerExceptionHandlerTest {

  private static final String EXTERNAL_STATUS_CODE_MESSAGE = "Unexpected external status code";
  private static final String INTERNAL_STATUS_CODE_MESSAGE = "Unexpected internal status code";
  private static final String MESSAGE_CODE_MESSAGE = "Unexpected message code";
  private static final String SHOULD_NOT_BE_NULL_MESSAGE = "Should not be null";

  private GlobalControllerExceptionHandler exceptionHandler;
  private HttpServletRequest httpServletRequest;

  @BeforeEach
  public void setUp() {
    exceptionHandler = new GlobalControllerExceptionHandler();
    httpServletRequest = Mockito.mock(HttpServletRequest.class);
  }

  @Test
  void test_handle_invalid_input_data_exception() {
    String message = "Invalid input data exception";

    InvalidInputDataException exception = new InvalidInputDataException(message);

    //SUT
    ResponseEntity<OutputContract<Object>> responseEntity =
        exceptionHandler.handleInvalidInputDataException(exception, httpServletRequest);

    //Assertions
    OutputContract<Object> outputContract = responseEntity.getBody();
    assertNotNull(outputContract, SHOULD_NOT_BE_NULL_MESSAGE);

    Integer externalStatusCode = responseEntity.getStatusCodeValue();
    Integer internalStatusCode = outputContract.getStatusCode();
    MessageCode messageCode = new MessageCode(outputContract.getMessageCode());

    assertEquals(OK.value(), externalStatusCode, EXTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(BAD_REQUEST.value(), internalStatusCode, INTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(MessageCode.INVALID_INPUT_DATA.getCode(), messageCode.getCode(), MESSAGE_CODE_MESSAGE);
  }

  @Test
  void test_handle_header_not_found_exception() {
    String message = "Header not found exception";
    HeaderNotFoundException exception = new HeaderNotFoundException(message);

    //SUT
    ResponseEntity<OutputContract<Object>> responseEntity =
        exceptionHandler.handleHeaderNotFoundException(exception, httpServletRequest);

    //Assertions
    OutputContract<Object> outputContract = responseEntity.getBody();
    assertNotNull(outputContract, SHOULD_NOT_BE_NULL_MESSAGE);

    Integer externalStatusCode = responseEntity.getStatusCodeValue();
    Integer internalStatusCode = outputContract.getStatusCode();
    MessageCode messageCode = new MessageCode(outputContract.getMessageCode());

    assertEquals(OK.value(), externalStatusCode, EXTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(BAD_REQUEST.value(), internalStatusCode, INTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(MessageCode.HEADER_NOT_FOUND.getCode(), messageCode.getCode(), MESSAGE_CODE_MESSAGE);
  }

  @Test
  void test_handle_internal_error_exception() {
    Exception exception = new RuntimeException();

    //SUT
    ResponseEntity<OutputContract<Object>> responseEntity =
        exceptionHandler.handleGenericException(exception, httpServletRequest);

    //Assertions
    OutputContract<Object> outputContract = responseEntity.getBody();
    assertNotNull(outputContract, SHOULD_NOT_BE_NULL_MESSAGE);

    Integer externalStatusCode = responseEntity.getStatusCodeValue();
    Integer internalStatusCode = outputContract.getStatusCode();
    MessageCode messageCode = new MessageCode(outputContract.getMessageCode());

    assertEquals(INTERNAL_SERVER_ERROR.value(), externalStatusCode, EXTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(INTERNAL_SERVER_ERROR.value(), internalStatusCode, INTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(MessageCode.INTERVAL_SERVER_ERROR.getCode(), messageCode.getCode(), MESSAGE_CODE_MESSAGE);
  }

  @Test
  void test_handle_internal_error_exception_with_null_request() {
    Exception exception = new RuntimeException();

    //SUT
    ResponseEntity<OutputContract<Object>> responseEntity =
        exceptionHandler.handleGenericException(exception, null);

    //Assertions
    OutputContract<Object> outputContract = responseEntity.getBody();
    assertNotNull(outputContract, SHOULD_NOT_BE_NULL_MESSAGE);

    Integer externalStatusCode = responseEntity.getStatusCodeValue();
    Integer internalStatusCode = outputContract.getStatusCode();
    MessageCode messageCode = new MessageCode(outputContract.getMessageCode());

    assertEquals(INTERNAL_SERVER_ERROR.value(), externalStatusCode, EXTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(INTERNAL_SERVER_ERROR.value(), internalStatusCode, INTERNAL_STATUS_CODE_MESSAGE);
    assertEquals(MessageCode.INTERVAL_SERVER_ERROR.getCode(), messageCode.getCode(), MESSAGE_CODE_MESSAGE);
  }
}
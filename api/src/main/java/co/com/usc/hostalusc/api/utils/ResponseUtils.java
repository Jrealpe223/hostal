package co.com.usc.hostalusc.api.utils;

import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    public static ResponseEntity<OutputContract<Object>> buildOutputContractSuccessResponse(
            Object contract, Integer statusCode) {
        OutputContract<Object> outputContract = buildOutputContractResponse(contract,  null, null, null,
                statusCode, Boolean.TRUE, null);
        return ResponseEntity.status(HttpStatus.OK).body(outputContract);
    }

    public static ResponseEntity<OutputContract<Object>> buildOutputContractSuccessResponse(
             Integer statusCode) {
        OutputContract<Object> outputContract = buildOutputContractResponse(null, null, null, null,
                statusCode, Boolean.TRUE, null);
        return ResponseEntity.status(HttpStatus.OK).body(outputContract);
    }

    public static ResponseEntity<OutputContract<Object>> buildOutputContractSuccessResponse(
            Object contract, Integer statusCode, Long total) {
        OutputContract<Object> outputContract = buildOutputContractResponse(contract,  null, null, null,
                statusCode, Boolean.TRUE, total);
        return ResponseEntity.status(HttpStatus.OK).body(outputContract);
    }

    public static ResponseEntity<OutputContract<Object>> buildOutputContractFailureResponse(
            String messageError, MessageCode messageCode, Integer statusCode) {
        OutputContract<Object> outputContract = buildOutputContractResponse( null, null, messageError,
                messageCode, statusCode, Boolean.FALSE, null);
        return ResponseEntity.status(HttpStatus.OK).body(outputContract);
    }

    public static ResponseEntity<OutputContract<Object>> buildOutputContractErrorResponse(
            String messageError, MessageCode messageCode, Integer statusCode) {
        OutputContract<Object> outputContract = buildOutputContractResponse( null, null, messageError,
                messageCode, statusCode, Boolean.FALSE, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(outputContract);
    }

    public static ResponseEntity<OutputContract<Object>> buildOutputContractErrorResponse(
            Object messageError, MessageCode messageCode, Integer statusCode) {
        OutputContract<Object> outputContract = buildOutputContractResponse( null, messageError, null,
                messageCode, statusCode, Boolean.FALSE, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(outputContract);
    }

    private static OutputContract<Object> buildOutputContractResponse(Object contract,
                                                                      Object errorMessage,
                                                                      String messageError,
                                                                      MessageCode messageCode,
                                                                      Integer statusCode,
                                                                      Boolean success,
                                                                      Long total) {
        return OutputContract.builder()
                .contract(contract)
                .errorMessage(errorMessage)
                .messageError(messageError)
                .messageCode(Objects.isNull(messageCode) ? null : messageCode.getCode())
                .statusCode(statusCode)
                .total(total)
                .success(success)
                .build();
    }
}
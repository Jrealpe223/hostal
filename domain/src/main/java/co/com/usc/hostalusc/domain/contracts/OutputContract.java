package co.com.usc.hostalusc.domain.contracts;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OutputContract<T extends Object> implements Serializable {

    T contract;
    Integer statusCode;
    T errorMessage;
    String messageError;
    String messageCode;
    Boolean success;
    Long total;
}

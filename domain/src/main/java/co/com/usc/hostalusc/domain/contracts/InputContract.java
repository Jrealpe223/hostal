package co.com.usc.hostalusc.domain.contracts;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class InputContract<T extends Serializable> implements Serializable {

  T contract;
  Integer page;
  Integer size;

  public InputContract(T contract) {
    this.contract = contract;
    this.page = 0;
    this.size = 10;
  }
}

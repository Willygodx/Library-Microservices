package by.ruslan.project.exception.validation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResponse {

  private List<Validation> validationList;

}

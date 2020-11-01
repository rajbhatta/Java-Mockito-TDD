package exception.mockito.coverage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringManipulator {
  public String getAddressFromString(String inputString) throws StringManipulationException {
    if (inputString == null) {
      throw new StringManipulationException("Provided String is NULL: ");
    }

    List<String> nameAddressList =
        Stream.of(inputString.split(",", 2)).collect(Collectors.toList());

    if (nameAddressList.size() == 1) {
      throw new StringManipulationException("Provided String does not have address: ");
    }
    return nameAddressList.get(1);
  }
}

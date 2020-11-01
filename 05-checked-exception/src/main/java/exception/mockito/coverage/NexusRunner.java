package exception.mockito.coverage;

public class NexusRunner {
  public static void main(String[] args) throws StringManipulationException {

    String nameAddress = "RajBhatta,922 Anders Road";
    StringManipulator stringManipulator = new StringManipulator();
    stringManipulator.getAddressFromString(nameAddress);
  }
}

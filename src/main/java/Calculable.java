import Exceptions.IncorrectDataLength;

public interface Calculable {
    void makeCalculation(Calculable object) throws IncorrectDataLength;
    Calculable multiply(Calculable object, DataHandler dataHandler) throws IncorrectDataLength;
    Calculable sum(Calculable object, DataHandler dataHandler) throws IncorrectDataLength;
    Calculable subtract(Calculable object, DataHandler dataHandler) throws IncorrectDataLength;
}

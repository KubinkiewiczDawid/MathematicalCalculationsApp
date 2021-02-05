package Utils;

import Exceptions.IncorrectDataLength;
import Handlers.DataHandler;

public interface Calculable {
    void makeCalculation(Calculable object) throws IncorrectDataLength;
    Calculable multiply(Calculable object) throws IncorrectDataLength;
    Calculable sum(Calculable object) throws IncorrectDataLength;
    Calculable subtract(Calculable object) throws IncorrectDataLength;
}
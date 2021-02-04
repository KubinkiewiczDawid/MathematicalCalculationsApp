import Exceptions.IncorrectDataLength;

public interface Calculable {
    void makeCalculation(Calculable object) throws IncorrectDataLength;
    void multiply(Calculable object) throws IncorrectDataLength;
    void sum(Calculable object) throws IncorrectDataLength;
    void subtract(Calculable object) throws IncorrectDataLength;
}

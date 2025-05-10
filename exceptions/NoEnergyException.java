package exceptions;

public class NoEnergyException extends Exception {
    public NoEnergyException() {
        super("No energy");
    }
    public NoEnergyException(String message) {
        super(message);
    }
}

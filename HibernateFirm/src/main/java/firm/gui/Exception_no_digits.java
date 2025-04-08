package firm.gui;

public class Exception_no_digits extends Exception {
	public Exception_no_digits() {
		super("В данном поле нельзя использовать цифры или символы *;@#! и т.д.");
	}
}

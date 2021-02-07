package pl.edu.wit.application.port.secondary;

import java.util.Locale;

public interface PhoneNumberProvider {

    Boolean isPossibleNumber(String phoneNumber);

    Boolean isPossibleNumber(String phoneNumber, Locale locale);

}

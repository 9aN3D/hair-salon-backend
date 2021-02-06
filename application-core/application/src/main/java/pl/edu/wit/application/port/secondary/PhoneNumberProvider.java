package pl.edu.wit.application.port.secondary;

import pl.edu.wit.domain.model.PhoneNumber;

import java.util.Locale;

public interface PhoneNumberProvider {

    PhoneNumber verify(String phoneNumber);

    PhoneNumber verify(String phoneNumber, Locale locale);

}

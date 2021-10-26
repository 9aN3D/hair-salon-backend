package pl.edu.wit.common.port.secondary;

import pl.edu.wit.common.port.secondary.PhoneNumberProvider;

import java.util.Locale;

public class MockPhoneNumberProvider implements PhoneNumberProvider {

    @Override
    public Boolean isPossibleNumber(String phoneNumber) {
        return true;
    }

    @Override
    public Boolean isPossibleNumber(String phoneNumber, Locale locale) {
        return true;
    }

}

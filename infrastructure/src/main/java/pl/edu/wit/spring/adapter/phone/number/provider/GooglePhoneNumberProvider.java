package pl.edu.wit.spring.adapter.phone.number.provider;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import java.util.Locale;

import static com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED;

@Component
public class GooglePhoneNumberProvider implements PhoneNumberProvider {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public Boolean isPossibleNumber(String phoneNumber) {
        return phoneNumberUtil.isPossibleNumber(phoneNumber, UNSPECIFIED.name());
    }

    @Override
    public Boolean isPossibleNumber(String phoneNumber, Locale locale) {
        return phoneNumberUtil.isPossibleNumber(phoneNumber, locale.getLanguage());
    }

}

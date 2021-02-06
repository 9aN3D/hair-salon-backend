package pl.edu.wit.spring.adapter.phone.number.provider;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.domain.exception.PhoneNumberNotValidException;
import pl.edu.wit.domain.model.PhoneNumber;

import java.util.Locale;

import static com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED;
import static java.lang.String.format;

@Component
public class GooglePhoneNumberProvider implements PhoneNumberProvider {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public PhoneNumber verify(String phoneNumber) {
        if (phoneNumberUtil.isPossibleNumber(phoneNumber, UNSPECIFIED.name())) {
            return new PhoneNumber(phoneNumber);
        }
        throw new PhoneNumberNotValidException(format("Phone number %s is not valid", phoneNumber));
    }

    @Override
    public PhoneNumber verify(String phoneNumber, Locale locale) {
        if (phoneNumberUtil.isPossibleNumber(phoneNumber, locale.getLanguage())) {
            return new PhoneNumber(phoneNumber);
        }
        throw new PhoneNumberNotValidException(format("Phone number %s is not valid", phoneNumber));
    }

}

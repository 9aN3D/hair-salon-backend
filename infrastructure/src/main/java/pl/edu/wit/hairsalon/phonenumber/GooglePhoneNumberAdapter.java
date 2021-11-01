package pl.edu.wit.hairsalon.phonenumber;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import pl.edu.wit.hairsalon.member.PhoneNumberPort;

import java.util.Locale;

import static com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED;

class GooglePhoneNumberAdapter implements PhoneNumberPort {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isPossibleNumber(String phoneNumber) {
        return phoneNumberUtil.isPossibleNumber(phoneNumber, UNSPECIFIED.name());
    }

    @Override
    public boolean isPossibleNumber(String phoneNumber, Locale locale) {
        return phoneNumberUtil.isPossibleNumber(phoneNumber, locale.getLanguage());
    }

}

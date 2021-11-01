package pl.edu.wit.hairsalon.member;

import java.util.Locale;

public interface PhoneNumberPort {

    boolean isPossibleNumber(String phoneNumber);

    boolean isPossibleNumber(String phoneNumber, Locale locale);

}

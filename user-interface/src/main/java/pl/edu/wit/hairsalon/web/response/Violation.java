package pl.edu.wit.hairsalon.web.response;

import lombok.Value;

@Value
public class Violation {

    String field;
    String message;

}

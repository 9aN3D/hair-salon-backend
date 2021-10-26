package pl.edu.wit.web.response;

import lombok.Value;

@Value
public class Violation {

    String field;
    String message;

}

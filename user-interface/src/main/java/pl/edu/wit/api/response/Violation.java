package pl.edu.wit.api.response;

import lombok.Value;

@Value
public class Violation {

    String field;
    String message;

}

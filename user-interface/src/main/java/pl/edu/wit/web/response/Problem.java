package pl.edu.wit.web.response;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.List;

@Value
@Builder
public class Problem {

    String title;
    HttpStatus status;
    String detail;
    List<Violation> violations;

}

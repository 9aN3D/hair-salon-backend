package pl.edu.wit.application.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PageSliceSort {

    String order;

    String direction;

    public static PageSliceSort empty() {
        return new PageSliceSort("id", "ASC");
    }

}

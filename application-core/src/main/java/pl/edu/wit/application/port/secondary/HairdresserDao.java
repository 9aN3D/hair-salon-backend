package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.HairdresserFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import java.util.Optional;

public interface HairdresserDao {

    String save(HairdresserDto hairdresserDto);

    Optional<HairdresserDto> findOne(HairdresserFindQuery query);

    PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery pageableQuery);

}

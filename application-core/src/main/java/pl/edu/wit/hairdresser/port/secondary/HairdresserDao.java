package pl.edu.wit.hairdresser.port.secondary;

import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;

import java.util.Optional;

public interface HairdresserDao {

    String save(HairdresserDto hairdresserDto);

    Optional<HairdresserDto> findOne(HairdresserFindQuery query);

    PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery pageableQuery);

}

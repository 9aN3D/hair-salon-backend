package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;

import java.util.Optional;

public interface HairdresserPort {

    HairdresserDto save(HairdresserDto hairdresserDto);

    Optional<HairdresserDto> findOne(HairdresserFindQuery query);

    HairdresserDto findOneOrThrow(HairdresserFindQuery findQuery);

    Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable);

}

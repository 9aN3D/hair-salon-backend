package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;

public interface HairdresserFacade {

    String create(HairdresserCreateCommand command);

    void update(String hairdresserId, HairdresserUpdateCommand command);

    Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable);

    void uploadPhoto(String hairdresserId, FileUploadCommand command);

    HairdresserDto findOne(String hairdresserId);

}

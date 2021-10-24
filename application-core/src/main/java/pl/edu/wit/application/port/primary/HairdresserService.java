package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.command.hairdresser.HairdresserCreateCommand;
import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.HairdresserFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

public interface HairdresserService {

    String create(HairdresserCreateCommand command);

    PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery paramsQuery);

    void uploadPhoto(String hairdresserId, FileUploadCommand command);

    HairdresserDto findOne(String hairdresserId);

}

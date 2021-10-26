package pl.edu.wit.hairdresser.port.primary;

import pl.edu.wit.uploadable.file.command.FileUploadCommand;
import pl.edu.wit.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;

public interface HairdresserService {

    String create(HairdresserCreateCommand command);

    PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery paramsQuery);

    void uploadPhoto(String hairdresserId, FileUploadCommand command);

    HairdresserDto findOne(String hairdresserId);

}

package pl.edu.wit.web.facade;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.web.response.HairdresserResponse;
import pl.edu.wit.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.hairdresser.query.HairdresserFindQuery;

public interface HairdresserFacade {

    String create(HairdresserCreateCommand command);

    void uploadPhoto(String hairdresserId, MultipartFile file);

    HairdresserResponse findOne(String hairdresserId);

    PageSlice<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable);

}

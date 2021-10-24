package pl.edu.wit.api.facade;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.api.response.HairdresserResponse;
import pl.edu.wit.application.command.hairdresser.HairdresserCreateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.HairdresserFindQuery;

public interface HairdresserFacade {

    String create(HairdresserCreateCommand command);

    void uploadPhoto(String hairdresserId, MultipartFile file);

    HairdresserResponse findOne(String hairdresserId);

    PageSlice<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable);

}

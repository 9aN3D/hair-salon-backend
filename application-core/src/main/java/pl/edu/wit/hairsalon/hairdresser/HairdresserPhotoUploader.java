package pl.edu.wit.hairsalon.hairdresser;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

@RequiredArgsConstructor
class HairdresserPhotoUploader {

    private final HairdresserPort hairdresserPort;
    private final UploadableFileFacade uploadableFileFacade;

    HairdresserDto upload(String hairdresserId, FileUploadCommand command) {
        var hairdresserDto = hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
        var hairdresser = new Hairdresser(hairdresserDto)
                .deletePhoto(uploadableFileFacade::delete)
                .uploadPhoto(uploadableFileFacade::store, command)
                .validate();
        return hairdresserPort.save(hairdresser.toDto());
    }

}

package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

class HairdresserPhotoUploader {

    private final HairdresserPort hairdresserPort;
    private final UploadableFileFacade uploadableFileFacade;

    HairdresserPhotoUploader(HairdresserPort hairdresserPort, UploadableFileFacade uploadableFileFacade) {
        this.hairdresserPort = hairdresserPort;
        this.uploadableFileFacade = uploadableFileFacade;
    }

    HairdresserDto upload(String hairdresserId, FileUploadCommand command) {
        var hairdresserDto = hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
        var hairdresser = new Hairdresser(hairdresserDto)
                .deletePhoto(uploadableFileFacade::delete)
                .uploadPhoto(uploadableFileFacade::store, command)
                .validate();
        return hairdresserPort.save(hairdresser.toDto());
    }

}

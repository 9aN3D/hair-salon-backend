package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;
import pl.edu.wit.hairsalon.uploadablefile.UploadableFileFacade;

@Configuration
class HairdresserConfiguration {

    @Bean
    HairdresserFacade hairdresserFacade(IdGenerator idGenerator,
                                        HairdresserPort hairdresserPort,
                                        UploadableFileFacade uploadableFileFacade) {
        var creator = new HairdresserCreator(idGenerator, hairdresserPort);
        var updater = new HairdresserUpdater(hairdresserPort);
        var photoUploader = new HairdresserPhotoUploader(hairdresserPort, uploadableFileFacade);
        return new AppHairdresserFacade(hairdresserPort, creator, updater, photoUploader);
    }

}

package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;

@Configuration(proxyBeanMethods = false)
class HairdresserConfiguration {

    @Bean
    HairdresserFacade hairdresserFacade(IdGenerator idGenerator,
                                        HairdresserPort hairdresserPort,
                                        UploadableFileFacade uploadableFileFacade,
                                        HairdresserAvailabilityFetcher hairdresserAvailabilityFetcher) {
        var creator = new HairdresserCreator(idGenerator, hairdresserPort);
        var updater = new HairdresserUpdater(hairdresserPort);
        var photoUploader = new HairdresserPhotoUploader(hairdresserPort, uploadableFileFacade);
        return new LoggableHairdresserFacade(
                new AppHairdresserFacade(hairdresserPort, creator, updater, photoUploader, hairdresserAvailabilityFetcher)
        );
    }

    @Bean
    HairdresserDayOverrideFacade hairdresserDayOverrideFacade(HairdresserDayOverridePort hairdresserDayOverridePort) {
        var creator = new HairdresserDayOverrideCreator(hairdresserDayOverridePort);
        var updater = new HairdresserDayOverrideUpdater(hairdresserDayOverridePort);
        return new LoggableHairdresserDayOverrideFacade(
                new AppHairdresserDayOverrideFacade(creator, updater)
        );
    }
    
    @Bean
    HairdresserAvailabilityFetcher hairdresserAvailabilityFetcher(HairdresserPort hairdresserPort,
                                                                  HairdresserDayOverridePort hairdresserDayOverridePort,
                                                                  SettingFacade settingFacade,
                                                                  ScheduledEventFacade scheduledEventFacade,
                                                                  ServiceFacade serviceFacade) {
        return new HairdresserAvailabilityFetcher(
                hairdresserPort, hairdresserDayOverridePort, settingFacade, scheduledEventFacade, serviceFacade
        );
    }

}

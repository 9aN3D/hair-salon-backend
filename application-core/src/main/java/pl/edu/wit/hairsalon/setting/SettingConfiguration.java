package pl.edu.wit.hairsalon.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SettingConfiguration {

    @Bean
    SettingFacade settingFacade(SettingPort settingPort) {
        var creator = new SettingCreator(settingPort);
        var updater = new SettingUpdater(settingPort);
        return new AppSettingFacade(settingPort, creator, updater);
    }

}

package pl.edu.wit.hairsalon.socialIntegration.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.socialIntegration.dto.SocialProviderDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkAddingCalendarEventGenerateCommand {

    private SocialProviderDto socialProvider;

    private DateRangeDto times;

    private String eventName;

    private String location;

}

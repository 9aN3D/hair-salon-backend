package pl.edu.wit.hairsalon.socialintegration.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.socialintegration.dto.SocialProviderDto;

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

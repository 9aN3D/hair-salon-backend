package pl.edu.wit.domain.salon.port.primary;

import pl.edu.wit.domain.cqrs.CommandHandler;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.CreateSalonResult;

public interface CreateSalonHandler extends CommandHandler<CreateSalonResult, CreateSalonCommand> {

}

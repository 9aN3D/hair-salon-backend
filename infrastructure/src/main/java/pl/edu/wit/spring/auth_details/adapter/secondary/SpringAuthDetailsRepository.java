package pl.edu.wit.spring.auth_details.adapter.secondary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.spring.auth_details.adapter.mapper.AuthDetailsMapper;

@Repository
@RequiredArgsConstructor
public class SpringAuthDetailsRepository implements AuthDetailsRepository {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public String save(AuthDetailsDto dto) {
        return repository.save(mapper.toAuthDetails(dto)).getId();
    }

}

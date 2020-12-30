package pl.edu.wit.spring.adapter.repository.auth_details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.spring.adapter.repository.auth_details.mapper.AuthDetailsMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringAuthDetailsRepository implements AuthDetailsRepository {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public String save(AuthDetails authDetails) {
        return repository.save(mapper.toDocument(authDetails)).getId();
    }

    @Override
    public Optional<AuthDetailsDto> findByEmail(String email) {
        return repository.findOneByEmail(email)
                .map(mapper::toDto);
    }

}

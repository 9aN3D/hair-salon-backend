package pl.edu.wit.spring.adapter.repository.auth_details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.application.port.secondary.AuthDetailsRepository;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.spring.adapter.repository.auth_details.mapper.AuthDetailsMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringAuthDetailsRepository implements AuthDetailsRepository {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    @Transactional
    public String save(AuthDetails authDetails) {
        return repository.save(mapper.toDocument(authDetails)).getId();
    }

    @Override
    public Optional<AuthDetailsDto> findByEmail(String email) {
        return repository.findOneByEmail(email)
                .map(mapper::toDto);
    }

}

package pl.edu.wit.spring.adapter.persistence.auth_details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoAuthDetailsDao implements AuthDetailsDao {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public Optional<AuthDetails> findOne(String email) {
        return repository.findOneByEmail(email)
                .map(mapper::toDomain);
    }

}

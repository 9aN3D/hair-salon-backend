package pl.edu.wit.hairsalon.setting;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.exception.SettingNotFoundException;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Repository
class MongoSettingAdapter implements SettingPort {

    private final MongoSettingRepository repository;
    private final SettingMapper mapper;

    MongoSettingAdapter(MongoSettingRepository repository, SettingMapper settingMapper) {
        this.repository = repository;
        this.mapper = settingMapper;
    }

    @Override
    public SettingDto save(SettingDto setting) {
        var settingDocument = mapper.toDocument(setting);
        return mapper.toDto(repository.save(settingDocument));
    }

    @Override
    public SettingDto findOneOrThrow(SettingIdDto settingId) {
        return repository.findById(settingId)
                .map(mapper::toDto)
                .orElseThrow(() -> new SettingNotFoundException(
                        format("Setting not found by settingId: %s", settingId))
                );
    }

    @Override
    public List<SettingDto> findAll(SettingGroupDto settingGroup) {
        return stream(buildPredicate(settingGroup)
                .map(repository::findAll)
                .orElseGet(repository::findAll)
                .spliterator(), false)
                .map(mapper::toDto)
                .collect(toList());
    }

    private Optional<Predicate> buildPredicate(SettingGroupDto findQuery) {
        if (isNull(findQuery)) {
            return empty();
        }
        var qSetting = QSettingDocument.settingDocument;
        return QuerydslPredicateBuilder.create()
                .in(qSetting.id, findQuery.getSettingIds())
                .build();
    }

}

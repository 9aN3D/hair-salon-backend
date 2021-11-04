package pl.edu.wit.hairsalon.setting;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.exception.SettingNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Repository
@RequiredArgsConstructor
class MongoSettingAdapter implements SettingPort {

    private final MongoSettingRepository repository;
    private final SettingMapper mapper;

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
                .orElseGet(ArrayList::new)
                .spliterator(), false)
                .map(mapper::toDto)
                .collect(toList());
    }

    private Optional<Predicate> buildPredicate(SettingGroupDto findQuery) {
        var qSetting = QSettingDocument.settingDocument;
        var builder = new BooleanBuilder();
        builder.and(qSetting.id.in(findQuery.getSettingIds()));
        return ofNullable(builder.getValue());
    }

}

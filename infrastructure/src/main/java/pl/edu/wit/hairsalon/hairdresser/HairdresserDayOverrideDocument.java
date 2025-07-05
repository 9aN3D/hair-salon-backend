package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableTimeRange;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.nonNull;

@QueryEntity
@Document(value = "HairdresserDayOverride")
class HairdresserDayOverrideDocument {

    @Id
    private EmbeddableHairdresserDayOverrideId id;

    private EmbeddableTimeRange timeRange;

    public HairdresserDayOverrideDocument() {
    }

    public HairdresserDayOverrideDocument(EmbeddableHairdresserDayOverrideId id,
                                          EmbeddableTimeRange timeRange) {
        this.id = id;
        this.timeRange = timeRange;
    }

    public EmbeddableHairdresserDayOverrideId getId() {
        return id;
    }

    public void setId(EmbeddableHairdresserDayOverrideId id) {
        this.id = id;
    }

    public EmbeddableTimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(EmbeddableTimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public boolean hasTimeRange() {
        return nonNull(timeRange);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HairdresserDayOverrideDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HairdresserDayOverrideDocument.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("timeRange=" + timeRange)
                .toString();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private EmbeddableHairdresserDayOverrideId id;
        private EmbeddableTimeRange timeRange;

        Builder id(EmbeddableHairdresserDayOverrideId id) {
            this.id = id;
            return this;
        }

        Builder timeRange(EmbeddableTimeRange timeRange) {
            this.timeRange = timeRange;
            return this;
        }

        HairdresserDayOverrideDocument build() {
            return new HairdresserDayOverrideDocument(id, timeRange);
        }

    }

}

package pl.edu.wit.hairsalon.setting;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

import java.util.Objects;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "Setting")
class SettingDocument {

    @Id
    private SettingIdDto id;

    private String value;

    SettingDocument() {
    }

    SettingDocument(SettingIdDto id, String value) {
        this.id = id;
        this.value = value;
    }

    public SettingIdDto getId() {
        return id;
    }

    public void setId(SettingIdDto id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SettingDocument that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SettingDocument.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("value='" + value + "'")
                .toString();
    }

}

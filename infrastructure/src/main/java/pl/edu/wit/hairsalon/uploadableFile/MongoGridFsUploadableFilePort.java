package pl.edu.wit.hairsalon.uploadableFile;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;
import pl.edu.wit.hairsalon.uploadableFile.exception.UploadableFileNotFoundException;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
class MongoGridFsUploadableFilePort implements UploadableFilePort {

    private final GridFsTemplate gridFsTemplate;
    private final UploadableFileMapper mapper;

    @Override
    public String store(FileUploadCommand command) {
        var metadata = new BasicDBObject();
        metadata.put("fileSize", command.getSize());
        return gridFsTemplate.store(
                command.getContent(),
                command.getOriginalFilename(),
                command.getContentType(),
                metadata).toString();
    }

    @Override
    public Optional<UploadableFileDto> findOne(String fileId) {
        return getFromGridFsTemplate(fileId);
    }

    @Override
    public UploadableFileDto findOneOrThrow(String fileId) {
        return getFromGridFsTemplate(fileId)
                .orElseThrow(() -> new UploadableFileNotFoundException(
                        format("Uploadable file not found by fileId %s", fileId)
                ));
    }

    private Optional<UploadableFileDto> getFromGridFsTemplate(String fileId) {
        var query = new Query(Criteria.where("_id").is(fileId));
        return Optional.ofNullable(gridFsTemplate.findOne(query))
                .filter(gridFSFile -> nonNull(gridFSFile.getMetadata()))
                .map(gridFSFile -> mapper.toDto(gridFSFile, gridFsTemplate.getResource(gridFSFile)));
    }

    @Override
    public void delete(String fileId) {
        var query = new Query(Criteria.where("_id").is(fileId));
        gridFsTemplate.delete(query);
    }

}

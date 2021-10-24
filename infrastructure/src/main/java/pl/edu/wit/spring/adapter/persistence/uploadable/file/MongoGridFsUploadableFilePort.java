package pl.edu.wit.spring.adapter.persistence.uploadable.file;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.dto.UploadableFileDto;
import pl.edu.wit.application.port.secondary.UploadableFilePort;
import pl.edu.wit.spring.adapter.persistence.uploadable.file.mapper.UploadableFileMapper;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class MongoGridFsUploadableFilePort implements UploadableFilePort {

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

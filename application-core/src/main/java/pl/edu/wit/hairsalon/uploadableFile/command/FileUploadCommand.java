package pl.edu.wit.hairsalon.uploadableFile.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.InputStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "content")
public class FileUploadCommand {

    String originalFilename;

    String contentType;

    Long size;

    InputStream content;

}

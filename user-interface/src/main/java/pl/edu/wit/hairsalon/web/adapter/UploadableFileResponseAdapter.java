package pl.edu.wit.hairsalon.web.adapter;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class UploadableFileResponseAdapter {

    private final Logger log;
    private final UploadableFileFacade uploadableFileFacade;

    public UploadableFileResponseAdapter(UploadableFileFacade uploadableFileFacade) {
        this.log = LoggerFactory.getLogger(UploadableFileResponseAdapter.class);
        this.uploadableFileFacade = uploadableFileFacade;
    }

    public StreamingResponseBody getOneImage(String fileId, HttpServletResponse response) {
        return outputStream -> {
            var uploadableFile = uploadableFileFacade.findOne(fileId);
            response.setContentType(MediaType.parseMediaType(uploadableFile.contentType()).toString());
            response.setContentLength(uploadableFile.length().intValue());
            response.setHeader("Cache-Control", "no-cache");
            tryCopyStream(uploadableFile, outputStream);
        };
    }

    private void tryCopyStream(UploadableFileDto file, OutputStream outputStream) {
        try (var inputStream = file.content()) {
            IOUtils.copyLarge(inputStream, outputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            tryFlushAndClose(outputStream);
        }
    }

    private void tryFlushAndClose(OutputStream outputStream) {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}

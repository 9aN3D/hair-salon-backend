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

/**
 * Adapter odpowiedzialny za obsługę plików możliwych do pobrania (np. zdjęć profilowych).
 * <p>
 * Umożliwia strumieniowe pobieranie pliku binarnego z systemu, ustawiając odpowiednie nagłówki HTTP.
 * Dane pobierane są za pomocą fasady {@link UploadableFileFacade}.
 * </p>
 *
 * <p>Główne zastosowanie to pobieranie zdjęć fryzjerów lub innych zasobów typu plik.</p>
 *
 * @see UploadableFileFacade
 * @see UploadableFileDto
 */

@Service
public class UploadableFileResponseAdapter {

    private final Logger log;
    private final UploadableFileFacade uploadableFileFacade;

    /**
     * Tworzy instancję adaptera z podaną fasadą plików.
     *
     * @param uploadableFileFacade fasada odpowiedzialna za logikę dostępu do plików
     */
    public UploadableFileResponseAdapter(UploadableFileFacade uploadableFileFacade) {
        this.log = LoggerFactory.getLogger(UploadableFileResponseAdapter.class);
        this.uploadableFileFacade = uploadableFileFacade;
    }

    /**
     * Zwraca strumień danych obrazu, ustawiając odpowiednie nagłówki HTTP.
     *
     * @param fileId   identyfikator pliku do pobrania
     * @param response obiekt odpowiedzi HTTP, do którego zostaną ustawione nagłówki
     * @return ciało odpowiedzi jako {@link StreamingResponseBody}
     */
    public StreamingResponseBody getOneImage(String fileId, HttpServletResponse response) {
        return outputStream -> {
            var uploadableFile = uploadableFileFacade.findOne(fileId);
            response.setContentType(MediaType.parseMediaType(uploadableFile.contentType()).toString());
            response.setContentLength(uploadableFile.length().intValue());
            response.setHeader("Cache-Control", "no-cache");
            tryCopyStream(uploadableFile, outputStream);
        };
    }

    /**
     * Kopiuje zawartość pliku do strumienia odpowiedzi.
     *
     * @param file         obiekt DTO zawierający dane pliku
     * @param outputStream strumień odpowiedzi
     */
    private void tryCopyStream(UploadableFileDto file, OutputStream outputStream) {
        try (var inputStream = file.content()) {
            IOUtils.copyLarge(inputStream, outputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            tryFlushAndClose(outputStream);
        }
    }

    /**
     * Flushuje i zamyka strumień odpowiedzi.
     *
     * @param outputStream strumień do flushowania i zamykania
     */
    private void tryFlushAndClose(OutputStream outputStream) {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}

package pl.edu.wit.application.port.secondary;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor
public class MockPasswordEncoder implements PasswordEncoder {

    @Override
    @SneakyThrows
    public String encode(String value) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(value.getBytes(UTF_8));
        return toHex(messageDigest.digest()).toUpperCase();
    }

    public String toHex(byte[] bytes) {
        byte[] hexArray = "0123456789ABCDEF".getBytes(US_ASCII);
        byte[] hexChars = new byte[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars, UTF_8);
    }

}

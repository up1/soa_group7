package com.shenzhentagram.utility;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import net.sf.jmimemagic.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Meranote on 4/2/2017.
 */
public class FileUtility {

    private static Log log = LogFactory.getLog(FileUtility.class);

    public static class FileDetail {
        public final InputStream inputStream;
        public final String extension;
        public final String type;
        public final long size;

        public FileDetail(InputStream inputStream, String extension, String type, long size) {
            this.inputStream = inputStream;
            this.extension = extension;
            this.type = type;
            this.size = size;
        }
    }

    private FileUtility() {}

    public static FileDetail extractFileFromBase64(String base64file) throws IOException, MagicParseException, MagicException, MagicMatchNotFoundException {
        String[] split = base64file.split(";");
        String base64String = split[1].replace("base64,", "");

        byte[] decodedBytes = Base64.decodeBase64(base64String);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        MagicMatch type = Magic.getMagicMatch(decodedBytes);

        log.info("extractFileFromBase64() : mime type = " + type.getMimeType());
        log.info("extractFileFromBase64() : extension = " + type.getExtension());
        log.info("extractFileFromBase64() : size = " + (inputStream.available() / 1024.0f / 1024.0f) + "MB");

        return new FileDetail(inputStream, type.getExtension(), type.getMimeType(), inputStream.available());
    }

}

package code.utils;

import static code.constants.JsonConstants.MAPPER;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import code.Main;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileReaderUtils {

    private FileReaderUtils() {}

    public static <T> T readJsonFileAsObject(String filePath, TypeReference<T> type) {
        try {
            final String content = FileUtils.readFileToString(new File(filePath), "UTF-8");

            return MAPPER.readValue(content, type);
        } catch (Exception e) {
            log.error("Failed to read JSON file: {}", filePath, e);
            return null;
        }
    }

    public static <T> T readResourceJsonFileAsObject(String filePath, TypeReference<T> type) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(filePath)) {
            return MAPPER.readValue(is, type);
        } catch (Exception e) {
            log.error("Failed to read resource JSON file: {}", filePath, e);
            return null;
        }
    }
}
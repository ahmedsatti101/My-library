package com.ahmedM.mylibrary.Books;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetBooksFromJson {
    public static String GetBooksFromJson(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}

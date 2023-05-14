package com.basejava.storage.files;

import com.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    Resume doRead(InputStream inputStream) throws IOException;

    void doWrite(Resume resume, OutputStream outputStream) throws IOException;
}

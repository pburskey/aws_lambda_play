package com.burskey;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.*;

public class StreamHandler implements RequestStreamHandler {


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))
        )
        {
            writer.write(java.util.UUID.randomUUID().toString());
            writer.flush();
        }

    }
}

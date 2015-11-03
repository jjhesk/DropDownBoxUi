package com.hypebeast.sdk.api.gson;

import java.io.IOException;
import java.io.OutputStream;

import retrofit.mime.TypedOutput;

/**
 * Created by hesk on 29/10/15.
 */
public class StringTypedOutput implements TypedOutput {

    private final byte[] xmlBytes;

    StringTypedOutput(byte[] xmlBytes) {
        this.xmlBytes = xmlBytes;
    }

    @Override
    public String fileName() {
        return null;
    }

    @Override
    public String mimeType() {
        return "application/xml; charset=UTF-8";
    }

    @Override
    public long length() {
        return xmlBytes.length;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        out.write(xmlBytes);
    }
}
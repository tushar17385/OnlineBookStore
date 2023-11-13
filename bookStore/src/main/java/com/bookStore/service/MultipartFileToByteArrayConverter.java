package com.bookStore.service;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
	public class MultipartFileToByteArrayConverter implements Converter<MultipartFile, byte[]> {
	    @Override
	    public byte[] convert(MultipartFile multipartFile) {
	        try {
	            byte[] bytes = multipartFile.getBytes();
	            return bytes;
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Log the error
	            return null;
	        }
	    }
}

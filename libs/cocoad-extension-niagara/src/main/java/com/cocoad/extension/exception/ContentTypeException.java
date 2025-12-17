package com.cocoad.extension.exception;

@SuppressWarnings("serial")
public class ContentTypeException extends Exception {
    public ContentTypeException(String contentType){
        super("only content type " + contentType + " is allowed");
    }
}

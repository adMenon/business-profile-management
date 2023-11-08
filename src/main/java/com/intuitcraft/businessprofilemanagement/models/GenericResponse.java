package com.intuitcraft.businessprofilemanagement.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record GenericResponse<T>(T data, Boolean error) {

    public static <T> ResponseEntity<GenericResponse<T>> ok(T data) {
        return new ResponseEntity<>(new GenericResponse<>(data, false), HttpStatus.OK);
    }

    public static <T> ResponseEntity<GenericResponse<T>> error(T data) {
        return new ResponseEntity<>(new GenericResponse<>(data, true),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<GenericResponse<T>> error(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new GenericResponse<>(data, true), httpStatus);
    }

    public static <T> ResponseEntity<GenericResponse<T>> with(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new GenericResponse<>(data, false), httpStatus);
    }

    public static <T> ResponseEntity<GenericResponse<T>> with(T data,
                                                              HttpStatus httpStatus, boolean error) {
        return new ResponseEntity<>(new GenericResponse<>(data, error), httpStatus);
    }
}

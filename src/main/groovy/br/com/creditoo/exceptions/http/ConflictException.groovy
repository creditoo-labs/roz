package br.com.creditoo.exceptions.http

class ConflictException extends RuntimeException {

    ConflictException(String errorMessage) {
        super(errorMessage)
    }
}

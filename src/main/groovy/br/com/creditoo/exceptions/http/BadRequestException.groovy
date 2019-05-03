package br.com.creditoo.exceptions.http

class BadRequestException extends RuntimeException {

    BadRequestException(String errorMessage) {
        super(errorMessage)
    }
}

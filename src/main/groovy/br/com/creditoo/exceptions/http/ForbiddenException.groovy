package br.com.creditoo.exceptions.http

class ForbiddenException extends RuntimeException {

    ForbiddenException(String errorMessage) {
        super(errorMessage)
    }
}

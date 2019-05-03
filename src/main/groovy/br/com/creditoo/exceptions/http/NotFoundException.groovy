package br.com.creditoo.exceptions.http

class NotFoundException extends RuntimeException {

    NotFoundException(String errorMessage) {
        super(errorMessage)
    }
}

package br.com.creditoo.aws.responses

class GenerateDataKeyResponse {
    private String ciphertext

    private String plainText

    GenerateDataKeyResponse(String ciphertext, String plainText) {
        this.ciphertext = ciphertext
        this.plainText = plainText
    }

    String getPlainText() {
        return plainText
    }

    String getCiphertext() {
        return ciphertext
    }
}

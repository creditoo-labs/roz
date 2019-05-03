package br.com.creditoo.aws

import com.amazonaws.services.kms.AWSKMSClient
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.kms.model.DecryptResult
import com.amazonaws.services.kms.model.EncryptRequest
import com.amazonaws.services.kms.model.EncryptResult

import java.nio.ByteBuffer

class Kms {

    private AWSKMSClient awskmsClient = new AWSKMSClient()

    String encrypt(byte[] value, String keyId) {
        ByteBuffer buffer = ByteBuffer.wrap(value)

        EncryptRequest encryptRequest = new EncryptRequest().withKeyId(keyId).withPlaintext(buffer)
        EncryptResult encryptResult = awskmsClient.encrypt(encryptRequest)
        String encryptedValue = new String(Base64.getEncoder().encode(encryptResult.ciphertextBlob.array()))

        return encryptedValue
    }

    String decrypt(String encryptedValue) {
        byte[] cipherText = Base64.getDecoder().decode(encryptedValue)
        ByteBuffer buffer = ByteBuffer.wrap(cipherText)

        DecryptRequest decryptRequest = new DecryptRequest().withCiphertextBlob(buffer)
        DecryptResult decryptResult = awskmsClient.decrypt(decryptRequest)
        String decryptedValue = new String(decryptResult.getPlaintext().array())

        return decryptedValue
    }
}

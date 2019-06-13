package br.com.creditoo.aws

import br.com.creditoo.aws.responses.GenerateDataKeyResponse
import com.amazonaws.services.kms.AWSKMSClient
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.kms.model.DecryptResult
import com.amazonaws.services.kms.model.EncryptRequest
import com.amazonaws.services.kms.model.EncryptResult
import com.amazonaws.services.kms.model.GenerateDataKeyRequest
import com.amazonaws.services.kms.model.GenerateDataKeyResult

import java.nio.ByteBuffer

class Kms {

    private AWSKMSClient awskmsClient = new AWSKMSClient()

    String encrypt(String value, String keyId) {
        byte[] encryptedData = encrypt(value.bytes, keyId)

        return this.getBase64String(encryptedData)
    }

    byte[] encrypt(byte[] data, String keyId) {
        ByteBuffer buffer = ByteBuffer.wrap(data)

        EncryptRequest encryptRequest = new EncryptRequest().withKeyId(keyId).withPlaintext(buffer)
        EncryptResult encryptResult = awskmsClient.encrypt(encryptRequest)

        return encryptResult.ciphertextBlob
    }

    String decrypt(String encryptedValue) {
        byte[] cipherText = Base64.getDecoder().decode(encryptedValue)

        ByteBuffer decryptedData = decrypt(ByteBuffer.wrap(cipherText))

        return new String(decryptedData.array())
    }

    ByteBuffer decrypt(ByteBuffer encryptedData) {
        DecryptRequest decryptRequest = new DecryptRequest().withCiphertextBlob(encryptedData)
        DecryptResult decryptResult = awskmsClient.decrypt(decryptRequest)

        return decryptResult.getPlaintext()
    }

    GenerateDataKeyResponse generateDataKey(String keyId, String keySpec = "AES_256") {
        GenerateDataKeyRequest generateDataKeyRequest = new GenerateDataKeyRequest().withKeyId(keyId).withKeySpec(keySpec)

        GenerateDataKeyResult generatedKeyResult = awskmsClient.generateDataKey(generateDataKeyRequest)

        return new GenerateDataKeyResponse(
            this.getBase64String(generatedKeyResult.ciphertextBlob),
            this.getBase64String(generatedKeyResult.plaintext)
        )
    }

    private String getBase64String(ByteBuffer data)
    {
        return new String(Base64.getEncoder().encode(data.array()))
    }

}

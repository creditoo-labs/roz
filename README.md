# Roz

### biblioteca para compartilhamento de estruturas entre aplicações

![roz](roz.jpg "roz")


### configurando no projeto

no `build.gradle`, adicionar

	allprojects {
	    repositories {
	        maven { url 'https://jitpack.io' }
	    }
	}

e dentro de `dependencies` adicionar

	dependencies {
		...
		implementation 'com.github.creditoo-labs:roz:<commit:10>'
		...
	}

onde o `<commit:10>` são os 10 primeiros caracteres do hash do commit que quer utilizar

	commit 19f259a8251078f415f38621462fc4a0839ba3af
	commit:10 19f259a825

o roz já traz uma implementação do HikariCP, caso queira utilizá-lo, pode remover a dependência do HikariCP da lib que está utilizando


    compile('com.dieselpoint:norm:0.8.2') {
        exclude module: 'HikariCP'
    }


### properties.json

adicionar arquivo `application.json` na pasta `src/main/resources` com o seguinte formato

	{
	  "key-name": {
	    "value": "some text here",
	    "encrypted": true
	  }
	}

caso a chave `encrypted` seja `true`, é utilizado o `aws kms` para descriptografar o value. o valor descriptografado fica salvo em memória no momento que sobe a aplicação.

para utilizar o valor

	JsonProperties.instance.get('key-name')


### DefaultHikariConfig

ao criar uma configuração para o datasource do hikari, utilizar o construtor do DefaultHikariConfig

    DefaultHikariConfig defaultHikariConfig = new DefaultHikariConfig(
            'jdbc:mysql://localhost:3306/database',
            'user',
            'password',
            5
    )
    HikariDataSource datasource = new HikariDataSource(defaultHikariConfig)

### S3

pra fazer upload de um arquivo, criar um objeto do tipo S3 e chamar o método upload

    S3 s3 = new S3()
    s3.upload(
            'sa-east-1',
            'some-bucket', 
            'some/simple/path/file.txt', 
            'file content'.bytes
    )

ele irá retornar um Map com o `path` que contém o caminho completo para o arquivo e o `result`com o objeto de retorno da aws

	[
		path: 's3://some-bucket/some/simple/path/file.txt', 
		result: <PutObjectResult>
	]

### KMS

para criptografar um conteúdo utilizando o AWS KMS, que retorna uma String do `ciphertext-blob`

    Kms kms = new Kms()
    kms.encrypt('some plain text', 'aws-kms-key-id')

para descriptografar só precisa passar o `ciphertext-blob`, e retorna a String do conteúdo 

	Kms kms = new Kms()
	kms.decrypt('some-cyphertext-blob')



package br.com.creditoo

import br.com.creditoo.aws.Kms
import com.amazonaws.util.IOUtils
import com.google.gson.Gson

@Singleton
class JsonProperties {

    private Map propertyMap
    private Kms kmsHelper

    {
        load()
    }

    void load() {
        if (propertyMap != null) {
            return
        }
        kmsHelper = new Kms()
        InputStream input = null

        try {
            input = JsonProperties.class.getClassLoader().getResourceAsStream('properties.json')
            String jsonString = IOUtils.toString(input)
            Gson gson = new Gson()
            propertyMap = gson.fromJson(jsonString, Map.class)
        } catch (IOException ex) {
            ex.printStackTrace()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (IOException ioe) {
                    ioe.printStackTrace()
                }
            }
        }
    }

    def get(String key) {
        def property = propertyMap[key]
        def value = property?.value
        if (property?.encrypted) {
            value = kmsHelper.decrypt(value)
            propertyMap[key] = [value: value]
        }
        return value
    }
}

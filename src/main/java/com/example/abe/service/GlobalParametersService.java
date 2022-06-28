package com.example.abe.service;

import com.example.abe.dcpabe.other.GlobalParameters;
import com.example.abe.dcpabe.other.SetupGP;
import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.PairingParameters;
import com.example.abe.jpbc.pairing.PairingFactory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

@Service
public class GlobalParametersService {

    private final String gpFilePath = "src/main/resources/globalParameters.json";

    @Autowired
    public GlobalParametersService() {
    }

    public GlobalParameters getGlobalParameters() throws IOException, ClassNotFoundException {
        return readGlobalParameters();
    }

    public void createGlobalParameters() throws IOException {
        GlobalParameters globalParameters = SetupGP.globalSetup(160);
        writeGlobalParameters(globalParameters);
    }

    // serialization into file
    private void writeGlobalParameters(GlobalParameters globalParameters) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(gpFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(globalParameters);
        }
    }

    // deserialization from file
    private GlobalParameters readGlobalParameters() throws IOException, ClassNotFoundException {
        try (
                ObjectInputStream inputGlobalParameters = new ObjectInputStream(
                        new FileInputStream(gpFilePath))) {
            return (GlobalParameters) inputGlobalParameters.readObject();
        }
    }

    // additional unused methods to make serialization and deserialization
    private void write(GlobalParameters globalParameters) throws IOException {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();
        JsonGenerator generator = factory.createGenerator(jsonObjectWriter);
        serialize(globalParameters, generator);
    }

    private void serialize(Object o, JsonGenerator jsonGenerator) throws IOException {
        if (!(o instanceof GlobalParameters))
            throw new RuntimeException("Unable to serialize GlobalParameters, wrong class " + o.getClass().getCanonicalName());

        GlobalParameters gp = (GlobalParameters) o;

        jsonGenerator.writeStartObject();

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(gp.getPairingParameters());

            jsonGenerator.writeBinaryField("pairingParameters", baos.toByteArray());
        }
        jsonGenerator.writeBinaryField("g1", gp.getG1().toBytes());
        jsonGenerator.writeEndObject();
    }

    private GlobalParameters read(String jsonAsString) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(jsonAsString);
        return deserialize(parser);
    }

    public GlobalParameters deserialize(JsonParser jsonParser) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (!node.isObject()) throw new RuntimeException("Unable to de-serialize GlobalParameters, not an object");

        PairingParameters pairingParameters = null;
        byte[] g1Bytes = null;

        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            final Map.Entry<String, JsonNode> field = it.next();

            final String fieldName = field.getKey();
            final JsonNode fieldValue = field.getValue();

                /*if (!fieldValue.isBinary())
                    throw new RuntimeException("Unable to deserialize GlobalParameters: wrong value type for field " + fieldName);*/

            if ("pairingParameters".equals(fieldName)) {
                try (
                        ByteArrayInputStream bais = new ByteArrayInputStream(field.getValue().binaryValue());
                        ObjectInputStream ois = new ObjectInputStream(bais)
                ) {
                    pairingParameters = (PairingParameters) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Unable to deserialize GlobalParameters: unable to deserialize  " + fieldName);
                }

            } else if ("g1".equals(fieldName)) {
                g1Bytes = fieldValue.binaryValue();
            } else {
                throw new RuntimeException("Unable to deserialize GlobalParameters: unknown field " + fieldName);
            }
        }

        if (null == pairingParameters)
            throw new RuntimeException("Unable to deserialize GlobalParameters: missing field pairingParameters");
        if (null == g1Bytes) throw new RuntimeException("Unable to deserialize GlobalParameters: missing field g1");

        Pairing pairing = PairingFactory.getPairing(pairingParameters);
        Element g1 = pairing.getG1().newElement();
        g1.setFromBytes(g1Bytes);
        g1 = g1.getImmutable();

        final GlobalParameters gp = new GlobalParameters();
        gp.setPairingParameters(pairingParameters);
        gp.setG1(g1);

        return gp;
    }

}

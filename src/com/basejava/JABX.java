package com.basejava;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JABX {
    public static void main(String[] args) throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(Aclass.class, Bclass.class);

        Aclass a1 = new Aclass();
        Aclass a2 = new Aclass();
        Bclass b = new Bclass();
        a1.bclass = b;
        a2.bclass = b;

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (OutputStreamWriter outputStreamWriter =
                     new OutputStreamWriter(Files.newOutputStream(Paths.get("/Users" + "/Danylo/Desktop/Workspace" +
                             "/basejava/atest1")), StandardCharsets.UTF_8)) {
            marshaller.marshal(a1, outputStreamWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStreamWriter outputStreamWriter =
                     new OutputStreamWriter(Files.newOutputStream(Paths.get("/Users" + "/Danylo/Desktop/Workspace" +
                             "/basejava/atest2")), StandardCharsets.UTF_8)) {
            marshaller.marshal(a2, outputStreamWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Aclass a3;
        Aclass a4;

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get("/Users" +
                "/Danylo/Desktop/Workspace/basejava/atest1")), StandardCharsets.UTF_8)) {
            a3 = (Aclass) unmarshaller.unmarshal(inputStreamReader);
        } catch (RuntimeException | IOException e) {
            throw e;
        }
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get("/Users" +
                "/Danylo/Desktop/Workspace/basejava/atest2")), StandardCharsets.UTF_8)) {
            a4 = (Aclass) unmarshaller.unmarshal(inputStreamReader);
        } catch (RuntimeException | IOException e) {
            throw e;
        }


        // Check if the objects reference the same instance of B
        System.out.println(a3.bclass == a4.bclass);
        System.out.println(a3.bclass.equals(a4.bclass));
    }
}

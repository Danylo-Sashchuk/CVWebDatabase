package com.basejava;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializationOne {
    public static void main(String[] args) {
        Aclass a1 = new Aclass();
        Aclass a2 = new Aclass();
        Bclass b = new Bclass();
        a1.bclass = b;
        a2.bclass = b;
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(Paths.get("/Users" + "/Danylo/Desktop" +
                             "/Workspace" + "/basejava/aserializ1")))) {
            objectOutputStream.writeObject(a1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(Paths.get("/Users" + "/Danylo/Desktop" +
                             "/Workspace" + "/basejava/aserializ2")))) {
            objectOutputStream.writeObject(a2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get("/Users" +
                "/Danylo/Desktop" + "/Workspace" + "/basejava/aserializ1")))) {
            a1 = (Aclass) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get("/Users" +
                "/Danylo/Desktop" + "/Workspace" + "/basejava/aserializ1")))) {
            a2 = (Aclass) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }

        System.out.println(a1.bclass == a2.bclass);
    }
}

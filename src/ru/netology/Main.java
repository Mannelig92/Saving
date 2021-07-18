package ru.netology;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        File savegames = new File("C://Games/savegames");
        System.out.println("Каталог savegames создан: " + savegames.mkdir());

        File save = new File("C://Games/savegames/save.dat");
        try {
            if (save.createNewFile()) System.out.println(("Файл save.dat создан"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File zip = new File("C://Games/savegames/zip.zip");
        try {
            if (zip.createNewFile()) System.out.println(("Файл zip.zip создан"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String savePath = "C://Games/savegames/save.dat";
        String zipPath = "C://Games/savegames/zip.zip";

        GameProgress gameProgress1 = new GameProgress(2, 5, 7, 2.4);
        GameProgress gameProgress2 = new GameProgress(5, 2, 9, 1.9);
        GameProgress gameProgress3 = new GameProgress(9, 6, 3, 8.6);
        saveGame(savePath, gameProgress1);
        zipFiles(zipPath, savePath);

        if(save.delete()) System.out.println("Файл вне архива удалён");
    }

    static void saveGame(String savePath, GameProgress gameProgress1) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void zipFiles(String zipPath, String savePath) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath));
             FileInputStream fileInputStream = new FileInputStream(savePath)) {
            ZipEntry zipEntry = new ZipEntry("C://Games/savegames/packed_save.dat");
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            zipOutputStream.write(buffer);
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

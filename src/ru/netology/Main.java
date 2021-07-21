package ru.netology;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(2, 5, 7, 2.4);
        GameProgress gameProgress2 = new GameProgress(5, 2, 9, 1.9);
        GameProgress gameProgress3 = new GameProgress(9, 6, 3, 8.6);

        List<GameProgress> progress = Arrays.asList(gameProgress1, gameProgress2, gameProgress3);

        File savegames = new File("C://Games/savegames");  //создание папки savegames
        System.out.println("Каталог savegames создан: " + savegames.mkdir());

        List<String> saveProgress = new ArrayList<>();
        List<ZipEntry> zipSaveFiles = new ArrayList<>();

        for (int i = 0; i < progress.size(); i++) {
            File save = new File("C://Games/savegames/save" + i + ".dat");
            try {
                if (save.createNewFile()) System.out.println(("Файл save" + i + ".dat создан"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            String savePath = "C://Games/savegames/save" + i + ".dat";
            saveProgress.add(savePath);
            ZipEntry zipEntry = new ZipEntry("packed_save" + i + ".dat");
            zipSaveFiles.add(zipEntry);
        }

        File zip = new File("C://Games/savegames/zipSaves.zip");
        String zipPath = "C://Games/savegames/zipSaves.zip";
        try {
            if (zip.createNewFile()) System.out.println(("Файл zipSaves.zip создан"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < progress.size(); i++) {
            saveGame(saveProgress.get(i), progress.get(i));
            zipFiles(zipPath, saveProgress.get(i), zipSaveFiles.get(i));
        }

        //        if (save.delete()) System.out.println("Файл вне архива удалён"); //Потом восстановлю

    }

    static void saveGame(String saveProgress, GameProgress gameProgress1) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(saveProgress);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void zipFiles(String zipPath, String saveProgress, ZipEntry zipEntryPath) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath)); //+
             FileInputStream fileInputStream = new FileInputStream(saveProgress)) {  //+
            ZipEntry zipEntry = new ZipEntry(zipEntryPath);
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
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

        List<File> saveProgress = new ArrayList<>();

        for (int i = 0; i < progress.size(); i++) {
            File save = new File("C://Games/savegames/save" + i + ".dat");
            try {
                if (save.createNewFile()) System.out.println(("Файл save" + i + ".dat создан"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            saveProgress.add(save);
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
        }
        zipFiles(zipPath, saveProgress);
        for (File i : saveProgress) {
            if (i.delete()) System.out.println("Файл вне архива удалён");
        }

    }

    static void saveGame(File saveProgress, GameProgress gameProgress1) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(saveProgress);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void zipFiles(String zipPath, List<File> saveProgress) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < saveProgress.size(); i++) {
                FileInputStream fileInputStream = new FileInputStream(saveProgress.get(i));
                ZipEntry zipEntry = new ZipEntry("packed_save" + i + ".dat");
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
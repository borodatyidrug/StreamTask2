package inputoutputtask2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class InputOutputTask2 {
    
    static final String DIR_PREFIX = "/home/aurumbeats/games/savegames";
    
    public static List<GameProgress> generator(int count) {
        Random r = new Random();
        List<GameProgress> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new GameProgress(r.nextInt(100), r.nextInt(100), 
                    r.nextInt(100), r.nextInt(100)));
        }
        return list;
    }
    
    public static void saveGame(String path, GameProgress gp) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(path))) {
            oos.writeObject(gp);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void zipFiles(String archPath, List<String> filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(archPath))) {
            
            for (String path : filePaths) {
                
                try (FileInputStream fis = new FileInputStream(path)) {
                    
                    String[] pathParts = path.split("/");
                    String inZipPath = pathParts[pathParts.length - 1];
                    ZipEntry z = new ZipEntry(inZipPath + ".packed");
                    zos.putNextEntry(z);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                    
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void deleteNotZip(String path) {
        File[] fileList = new File(path).listFiles();
        if (fileList.length != 0) {
            for (File file : fileList) {
                if (!file.getName().contains(".zip")) {
                    file.delete();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        List<GameProgress> list = generator(10);
        
        for (int i = 0; i < list.size(); i++) {
            saveGame(DIR_PREFIX + "/save" + i + ".dat", list.get(i));
        }
        
        List<String> paths = Arrays.stream(new File(DIR_PREFIX).list())
                .map(s -> DIR_PREFIX + "/" + s)
                .collect(Collectors.toList());
        
        zipFiles(DIR_PREFIX + "/zip.zip", paths);
        deleteNotZip(DIR_PREFIX);
    }
}

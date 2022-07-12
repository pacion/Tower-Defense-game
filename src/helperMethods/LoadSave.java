package helperMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("./sprites/spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException event) {
            event.printStackTrace();
        }

        return image;
    }

    public static void CreateFile() {
        File txtFile = new File("./resources/testTextFile.txt");

        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveLevel(String name, int[][] idArr) {
        File lvlFile = new File("./resources/" + name + ".txt");

        if(lvlFile.exists()) {
            WriteToFile(lvlFile, Utils.Flat2DArray(idArr));
        } else {
            // do more
            return;
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }

            scanner.close();
        } catch (FileNotFoundException e)  {
            e.printStackTrace();
        }

        return list;
    }

    public static int[][] GetLevelData(String name) {
        File lvlFile = new File("./resources/new_level.txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utils.ArrayListTo2DInt(list, 20, 20);
        } else {
            System.out.println("File doesn't exists: y " + name);
            return null;
        }
    }

    public static void CreateLevel (String name, int[] idArr) {
        File newLevel = new File("./resources/" + name + ".txt");

        if(newLevel.exists()) {
            System.out.println("lvl exists");
            return;
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(newLevel, idArr);
        }
    }

    public static void WriteToFile(File f, int[] idArr) {
        try {
            PrintWriter printWriter = new PrintWriter(f);

            for(Integer id: idArr)
                printWriter.println(id);

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

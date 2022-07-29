package helperMethods;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "TowerDefense_game";
    public static String levelFile = "level.txt";
    public static String filePath = homePath + File.separator + saveFolder + File.separator + levelFile;
    private static final File lvlFile = new File(filePath);

    public static void CreateFolder() {
        File folder = new File(homePath + File.separator + saveFolder);

        if (!folder.exists())
            folder.mkdir();
    }

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

    public static void SaveLevel(int[][] idArr, PathPoint start, PathPoint end) {
        if (lvlFile.exists()) {
            WriteToFile(Utils.Flat2DArray(idArr), start, end);
        } else {
            System.out.println("File doesn't exists: " + lvlFile);
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoints() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File doesn't exists: " + lvlFile);
            return null;
        }
    }

    public static int[][] GetLevelData() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utils.ArrayListTo2DInt(list, 20, 20);
        } else {
            System.out.println("File doesn't exists: " + lvlFile);
            return null;
        }
    }

    public static void CreateLevel(int[] idArr) {
        if (lvlFile.exists()) {
            System.out.println(lvlFile + " already exists");
        } else {
            try {
                lvlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    public static void WriteToFile(int[] idArr, PathPoint start, PathPoint end) {
        try {
            PrintWriter printWriter = new PrintWriter(lvlFile);

            for (Integer id : idArr)
                printWriter.println(id);

            printWriter.println(start.getX());
            printWriter.println(start.getY());
            printWriter.println(end.getX());
            printWriter.println(end.getY());

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

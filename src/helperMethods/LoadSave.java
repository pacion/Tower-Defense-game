package helperMethods;

import objects.PathPoint;

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

    public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        File lvlFile = new File("./resources/" + name + ".txt");

        if(lvlFile.exists()) {
            WriteToFile(lvlFile, Utils.Flat2DArray(idArr), start, end);
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

    public static ArrayList<PathPoint> GetLevelPathPoints(String name) {
        File lvlFile = new File("./resources/new_level.txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File doesn't exists: " + name);
            return null;
        }
    }

    public static int[][] GetLevelData(String name) {
        File lvlFile = new File("./resources/new_level.txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utils.ArrayListTo2DInt(list, 20, 20);
        } else {
            System.out.println("File doesn't exists: " + name);
            return null;
        }
    }

    public static void CreateLevel (String name, int[] idArr) {
        File newLevel = new File("./resources/" + name + ".txt");

        System.out.println("ssokoko");

        if(newLevel.exists()) {
            System.out.println("lvl exists");
            return;
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    public static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end) {
        try {
            PrintWriter printWriter = new PrintWriter(f);

            for(Integer id: idArr)
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

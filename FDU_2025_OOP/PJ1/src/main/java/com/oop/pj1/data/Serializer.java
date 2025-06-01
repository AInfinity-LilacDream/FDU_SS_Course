package com.oop.pj1.data;

import com.oop.pj1.Game;
import com.oop.pj1.controller.GameController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {
    private static void serializeToFile(String filename, GameController[] gameList, int currentGameID,
                                        int gameTotal, GameController currentGame) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(gameList);
            oos.writeInt(currentGameID);
            oos.writeInt(gameTotal);
            oos.writeObject(currentGame);
        }
    }

    private static Object[] deserializeFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object objGameList = ois.readObject();
            Object currentGameID = ois.readInt();
            Object gameTotal = ois.readInt();
            Object currentGame = ois.readObject();
            return new Object[] {objGameList, currentGameID, gameTotal, currentGame};
        }
    }

    public static void saveState(GameController[] gameList, int currentGameID, int gameTotal, GameController currentGame) {
        String filename = "src/main/resources/save/pj.game";
        try {
            serializeToFile(filename, gameList, currentGameID, gameTotal, currentGame);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean restoreState() throws IOException {
        String filename = "src/main/resources/save/pj.game";
        if (Files.size(Paths.get(filename)) == 0) { return false; }
        try {
            Object[] deserializedData = deserializeFromFile(filename);
            Game.setGameList((GameController[]) deserializedData[0]);
            Game.setCurrentGameID((int) deserializedData[1]);
            Game.setGameTotal((int) deserializedData[2]);
            Game.setCurrentGame((GameController) deserializedData[3]);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}

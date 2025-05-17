package model;


import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;



public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String savePath;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savePath = "save/" + username + "/data.txt";
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSavePath() { return savePath; }

    // 在User.java中添加
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(this);
        }
    }
}

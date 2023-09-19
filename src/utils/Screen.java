package utils;

public class Screen {
    public static void clear(){
        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder process;

            if (os.contains("win")) {
              process = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                process = new ProcessBuilder("clear");
            }

            process.inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

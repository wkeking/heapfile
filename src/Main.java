import config.TableConfig;

public class Main {
    public static void main(String[] args) {
        String s = "0 0";
        String[] split = s.split (TableConfig.NULL);
        System.out.println(split.length);
        System.out.println(split[0]);
        System.out.println(split[1]);
        long l = Long.parseLong (split[0]);
        System.out.println(l);
    }
}

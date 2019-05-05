import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces ();
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : allStackTraces.entrySet ()) {
            Thread key = stackTrace.getKey ();
            if (Thread.currentThread ().equals (key)) continue;
            StackTraceElement[] value = stackTrace.getValue ();
            System.out.println("Thread Name " + key.getName ());
            Stream.of (value).forEach (System.out :: println);
        }
    }
}

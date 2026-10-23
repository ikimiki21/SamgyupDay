import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FormUtils {
    private static final Path DATA_DIR = Paths.get("data");
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private FormUtils() {}

    // Appends one row to data/<fileName>, writing the header first if the file is new.
    // A timestamp column is added automatically as the first column.
    public static void appendCsv(String fileName, String[] header, Object... values) throws IOException {
        Files.createDirectories(DATA_DIR);
        Path file = DATA_DIR.resolve(fileName);

        StringBuilder sb = new StringBuilder();
        if (!Files.exists(file)) {
            sb.append("timestamp");
            for (String h : header) sb.append(',').append(escape(h));
            sb.append(System.lineSeparator());
        }
        sb.append(LocalDateTime.now().format(TIMESTAMP));
        for (Object v : values) sb.append(',').append(escape(v == null ? "" : v.toString()));
        sb.append(System.lineSeparator());

        Files.writeString(file, sb, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static String escape(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // TextArea normally inserts a tab character; make Tab / Shift+Tab move focus like other fields.
    public static void tabMovesFocus(TextArea area) {
        area.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.TAB && !e.isControlDown()) {
                e.consume();
                area.fireEvent(new KeyEvent(e.getSource(), e.getTarget(), e.getEventType(),
                        e.getCharacter(), e.getText(), e.getCode(),
                        e.isShiftDown(), true, e.isAltDown(), e.isMetaDown()));
            }
        });
    }
}

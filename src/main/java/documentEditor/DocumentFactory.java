package documentEditor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class DocumentFactory {
    private final Map<String, Supplier<Document>> registry = new HashMap<>();

    public DocumentFactory register(String format, Supplier<Document> creator) {
        String key = normalize(format);
        registry.put(key, Objects.requireNonNull(creator, "creator"));
        return this;
    }

    public Document create(String format) {
        Supplier<Document> creator = registry.get(normalize(format));
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported document format: " + format);
        }
        return creator.get();
    }

    private String normalize(String format) {
        if (format == null) {
            return "";
        }
        return format.trim().toLowerCase(Locale.ROOT);
    }

    public static DocumentFactory createDefaultFactory() {
        return new DocumentFactory()
                .register("pdf", PdfDocument::new)
                .register("word", WordDocument::new)
                .register("html", HtmlDocument::new)
                .register("markdown", MarkdownDocument::new)
                .register("md", MarkdownDocument::new);
    }
}

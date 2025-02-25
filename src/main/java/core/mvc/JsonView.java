package core.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;

public class JsonView implements View {

    private final HttpStatus httpStatus;
    private final URI location;

    public JsonView(final HttpStatus httpStatus) {
        this(httpStatus, URI.create(""));
    }

    public JsonView(final HttpStatus httpStatus, final URI location) {
        this.httpStatus = httpStatus;
        this.location = location;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(this.httpStatus.value());
        if (!this.location.equals(URI.create(""))) {
            response.addHeader("location", this.location.toString());
        }
        final PrintWriter writer = response.getWriter();
        this.writeJson(model, writer);
    }

    private void writeJson(final Map<String, ?> model, final PrintWriter writer) throws JsonProcessingException {
        if (model.size() == 0) {
            return;
        }

        final Object target = this.pickValueToJson(model);
        writer.print(JsonUtils.toString(target));
    }

    private Object pickValueToJson(final Map<String, ?> model) {
        if (model.size() == 1) {
            final Map.Entry<String, ?> next = model.entrySet().iterator().next();
            return next.getValue();
        }
        return model;
    }
}
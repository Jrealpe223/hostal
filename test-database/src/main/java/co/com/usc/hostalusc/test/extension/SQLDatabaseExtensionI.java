package co.com.usc.hostalusc.test.extension;

import co.com.usc.hostalusc.test.annotations.SQLDatabaseScript;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface SQLDatabaseExtensionI extends BeforeAllCallback, BeforeEachCallback {

    @SuppressWarnings("java:S3655")
    default Optional<SQLDatabaseScript> getSQLDatabaseScript(ExtensionContext context) {
        if (!context.getElement().isPresent()) {
            return Optional.empty();
        }

        SQLDatabaseScript fieldValue = context.getElement().get().getAnnotation(SQLDatabaseScript.class);
        if(Objects.isNull(fieldValue)){
            return Optional.empty();
        }
        return Optional.of(fieldValue);
    }

}

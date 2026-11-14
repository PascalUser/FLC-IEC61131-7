package utils.supports;

import compiler.generator.ControllerModel;
import compiler.generator.lowers.ControllerLower;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.diagnostics.Diagnostic;
import utils.builders.SourceCode;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ControllerLowerSupport extends ParserTestSupport {
    private final DiagnosticsHandler diagnostics = new DiagnosticsHandler();

    protected Optional<ControllerModel> lower(SourceCode source) throws Exception {
        ParsingResults results = parse(new StringReader(source.build()));
        return ControllerLower.newInstance(results.symbolTable(), results.abstractSyntaxTree(), diagnostics).adapt();
    }

    protected void assertControllerDiagnostic(String fragment, SourceCode source) throws Exception {
        Optional<ControllerModel> model = lower(source);
        assertFalse(model.isPresent(), "No model must be built");

        List<String> messages = messagesOf(compiler.repository.diagnostics.general.UnsupportedFeature.class);
        boolean hasMatch = messages.stream().anyMatch(message -> message.contains(fragment));

        assertTrue(hasMatch, () -> String.format(
                "Expected a %s containing '%s' but found:\n%s",
                compiler.repository.diagnostics.general.UnsupportedFeature.class.getSimpleName(),
                fragment,
                String.join("\n", messages)
        ));
    }

    protected List<String> messagesOf(Class<? extends Diagnostic> kind) {
        return diagnostics.getDiagnostics()
                .stream()
                .filter(kind::isInstance)
                .map(Diagnostic::getMessage)
                .collect(Collectors.toList());
    }

    protected ControllerModel lowerAndGetModel(SourceCode source) throws Exception {
        return lower(source).orElseThrow(() ->
                new AssertionError("Expected model to be built, but it failed. Diagnostics: " +
                        diagnostics.getDiagnostics()
                                .stream()
                                .map(Diagnostic::getMessage)
                                .collect(Collectors.joining(", "))));
    }
}

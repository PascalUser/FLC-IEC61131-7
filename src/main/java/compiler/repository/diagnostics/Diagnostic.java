package compiler.repository.diagnostics;

public interface Diagnostic {

    String getMessage();

    boolean fatalForCompilation();
}

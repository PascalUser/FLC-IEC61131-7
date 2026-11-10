package compiler.parser.semantics;

public interface SemanticAnalyzer<Subject> {
    boolean check(Subject subject);
}

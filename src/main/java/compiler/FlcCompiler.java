package compiler;

import compiler.ast.AbstractSyntaxTree;
import compiler.generator.CodeGenerator;
import compiler.generator.ControllerModel;
import compiler.lexer.Lexer;
import compiler.generator.lowers.ControllerLower;
import compiler.parser.Parser;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Entry point of the whole pipeline: FCL program to Vivado HLS C++ files.
 * <p>
 * The pipeline is lexer + parser (symbol table, rule block AST and order of the inputs and outputs), lowering
 * to the controller model ({@link ControllerLower}, which also reports what the generator does not support)
 * and code generation with the port of Luca Sarramone's generator ({@link CodeGenerator}). The generated files
 * follow the naming of that generator: {@code <base>_out.cpp}, {@code <base>_out.h} and {@code <base>_outTB.cpp}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class FlcCompiler {
    private final String sourceName;
    private final Reader sourceCode;
    private String report;

    /**
     * Creates a compiler that reports every lexical, syntactic and semantic problem to the given handler.
     */
    public FlcCompiler(final String sourceName, final Reader sourceCode) {
        Objects.requireNonNull(sourceCode, "sourceCode");
        Objects.requireNonNull(sourceName, "sourceName");
        this.sourceName = sourceName;
        this.sourceCode = sourceCode;
        this.report = "";
    }

    /**
     * Compiles an FCL program.
     *
     * @return the generated files; empty if the program has errors (see the diagnostics handler)
     * @throws RuntimeException if an unexpected error happend
     */
    public List<GeneratedFile> compile() {
        // AST creation
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        Parser parser = new Parser(new Lexer(sourceCode, symbolTable, diagnostics), symbolTable);
        try {
            boolean hasErrors = !parser.parse() || diagnostics.hasErrors();
            if (hasErrors) {
                report = diagnostics.getOutput();
                return Collections.emptyList();
            }
        } catch (IOException e) {
            report = diagnostics.getOutput();
            return Collections.emptyList();
        }
        // Code Generation
        AbstractSyntaxTree root = parser.getAST();
        Optional<ControllerModel> model = ControllerLower.newInstance(symbolTable, root, diagnostics).adapt();
        if (!model.isPresent()) {
            report = diagnostics.getOutput();
            return Collections.emptyList();
        }
        CodeGenerator generator = new CodeGenerator(model.get());
        report = diagnostics.getOutput();
        String header = sourceName + "_out.h";
        return Arrays.asList(
                new GeneratedFile(sourceName + "_out.cpp", generator.generateCpp()),
                new GeneratedFile(header, generator.generateHeader()),
                new GeneratedFile(sourceName + "_outTB.cpp", generator.generateTestBench(header))
        );
    }

    public String log() {
        return report;
    }
}

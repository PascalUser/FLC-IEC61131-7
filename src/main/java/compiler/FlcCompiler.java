package compiler;

import codegen.CodeGenerator;
import codegen.ControllerModel;
import lexer.Lexer;
import lowering.ControllerLowering;
import parser.Parser;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

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
 * to the controller model ({@link ControllerLowering}, which also reports what the generator does not support)
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
    private final DiagnosticsHandler diagnostics;

    /**
     * Creates a compiler that reports every lexical, syntactic and semantic problem to the given handler.
     *
     * @param diagnostics collector of errors and warnings of the whole pipeline
     */
    public FlcCompiler(final DiagnosticsHandler diagnostics) {
        this.diagnostics = Objects.requireNonNull(diagnostics, "diagnostics");
    }

    /**
     * Compiles an FCL program.
     *
     * @param source   the FCL program
     * @param baseName base name of the generated files (it also appears inside the test bench)
     * @return the generated files; empty if the program has errors (see the diagnostics handler)
     * @throws IOException if the source cannot be read
     */
    public List<GeneratedFile> compile(final Reader source, final String baseName) throws IOException {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(baseName, "baseName");
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(new Lexer(source, symbolTable, diagnostics), symbolTable);
        if (!parser.parse() || diagnostics.hasErrors()) {
            return Collections.emptyList();
        }
        Optional<ControllerModel> model = new ControllerLowering(symbolTable, parser.getFunctionBlockName(),
                diagnostics).lower(parser.getAST());
        if (!model.isPresent()) {
            return Collections.emptyList();
        }
        CodeGenerator generator = new CodeGenerator(model.get());
        String header = baseName + "_out.h";
        return Arrays.asList(
                new GeneratedFile(baseName + "_out.cpp", generator.generateCpp()),
                new GeneratedFile(header, generator.generateHeader()),
                new GeneratedFile(baseName + "_outTB.cpp", generator.generateTestBench(header)));
    }
}

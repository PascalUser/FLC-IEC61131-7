package utils;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.defuzzification.CentroidDefuzzifier;
import codegen.defuzzification.Defuzzifier;
import codegen.evaluation.MinMaxMethod;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SShapeFuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import codegen.fuzzysets.TrapezoidFuzzySet;
import codegen.fuzzysets.TriangularFuzzySet;
import codegen.fuzzysets.ZShapeFuzzySet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test-only reader of the DSL of Luca Sarramone's generator (the {@code .txt} of the reference examples) into a
 * {@link ControllerModel}, built the way his parser fills {@code IOVars} and the rule matrix: variables in the
 * order of the {@code FUZZ} block, every rule kept in source order.
 * <p>
 * It lets the ported code generator be checked against his output with his own inputs, independently of the
 * FCL front-end. It assumes well-formed examples and does not report errors.
 * </p>
 */
public final class LucaDslReader {
    private static final Pattern BIT_WIDTH = Pattern.compile("<\\s*(\\d+)\\s*>\\s*:");
    private static final Pattern DECLARATION = Pattern.compile("(\\w+)\\s*:\\s*(IN|OUT)\\s*;");
    private static final Pattern FUZZ = Pattern.compile("FUZZ\\s*\\{(.*)\\}\\s*RULES", Pattern.DOTALL);
    private static final Pattern VARIABLE = Pattern.compile("(\\w+)\\s*\\{(.*?)\\}\\s*;", Pattern.DOTALL);
    private static final Pattern SET = Pattern.compile("(\\w+)\\s*=\\s*(\\w+)\\s*\\(([^)]*)\\)\\s*;");
    private static final Pattern RULES =
            Pattern.compile("RULES\\s*<\\s*(?:MINMAX|MANDANI)\\s*>\\s*\\{(.*?)\\}", Pattern.DOTALL);
    private static final Pattern FORM = Pattern.compile("\\(\\s*([\\w\\s*]+?)\\s*=\\s*([\\w\\s,]+?)\\s*\\)");
    private static final Pattern RULE = Pattern.compile("([\\w\\s*]+?)\\s*=\\s*([\\w\\s,]+?)\\s*;");

    private LucaDslReader() {
    }

    /**
     * Reads a controller written in Luca's DSL.
     *
     * @param source the content of the {@code .txt}
     * @return the controller model his parser would build
     */
    public static ControllerModel read(String source) {
        int bitWidth = Integer.parseInt(find(BIT_WIDTH, source).group(1));
        Map<String, Boolean> isInput = new LinkedHashMap<>();
        Matcher declarations = DECLARATION.matcher(source);
        while (declarations.find()) {
            isInput.put(declarations.group(1), "IN".equals(declarations.group(2)));
        }

        List<Variable> inputs = new ArrayList<>();
        List<Variable> outputs = new ArrayList<>();
        Matcher variables = VARIABLE.matcher(find(FUZZ, source).group(1));
        while (variables.find()) {
            Variable variable = new Variable(variables.group(1), sets(variables.group(2)));
            (isInput.get(variable.name()) ? inputs : outputs).add(variable);
        }

        String rulesPart = find(RULES, source).group(1);
        Matcher form = find(FORM, rulesPart);
        List<String> formInputs = split(form.group(1), "\\*");
        List<String> formOutputs = split(form.group(2), ",");
        List<int[]> rules = new ArrayList<>();
        Matcher rule = RULE.matcher(rulesPart.substring(form.end()));
        while (rule.find()) {
            int[] row = new int[inputs.size() + outputs.size()];
            List<String> inputSets = split(rule.group(1), "\\*");
            List<String> outputSets = split(rule.group(2), ",");
            for (int i = 0; i < formInputs.size(); i++) {
                int index = indexOf(inputs, formInputs.get(i));
                row[index] = setIndex(inputs.get(index), inputSets.get(i));
            }
            for (int i = 0; i < formOutputs.size(); i++) {
                int index = indexOf(outputs, formOutputs.get(i));
                row[inputs.size() + index] = setIndex(outputs.get(index), outputSets.get(i));
            }
            rules.add(row);
        }

        Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
        for (Variable output : outputs) {
            defuzzifiers.put(output.name(), new CentroidDefuzzifier());
        }
        return new ControllerModel(bitWidth, inputs, outputs, new MinMaxMethod(rules), defuzzifiers);
    }

    private static List<FuzzySet> sets(String body) {
        List<FuzzySet> sets = new ArrayList<>();
        Matcher set = SET.matcher(body);
        while (set.find()) {
            String name = set.group(1);
            List<Integer> p = new ArrayList<>();
            for (String parameter : split(set.group(3), ",")) {
                p.add(Integer.parseInt(parameter));
            }
            switch (set.group(2)) {
                case "SINGLETON":
                    sets.add(new SingletonFuzzySet(name, p.get(0)));
                    break;
                case "Z_SHAPE":
                    sets.add(new ZShapeFuzzySet(name, p.get(0), p.get(1)));
                    break;
                case "S_SHAPE":
                    sets.add(new SShapeFuzzySet(name, p.get(0), p.get(1)));
                    break;
                case "TRIANGULAR":
                    sets.add(new TriangularFuzzySet(name, p.get(0), p.get(1), p.get(2)));
                    break;
                case "TRAPEZOID":
                    sets.add(new TrapezoidFuzzySet(name, p.get(0), p.get(1), p.get(2), p.get(3)));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown fuzzy set type " + set.group(2));
            }
        }
        return sets;
    }

    private static int indexOf(List<Variable> variables, String name) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).name().equals(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown variable " + name);
    }

    private static int setIndex(Variable variable, String setName) {
        for (int i = 0; i < variable.size(); i++) {
            if (variable.sets().get(i).name().equals(setName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown set " + setName + " of " + variable.name());
    }

    private static List<String> split(String text, String separator) {
        List<String> parts = new ArrayList<>();
        for (String part : text.trim().split("\\s*" + separator + "\\s*")) {
            parts.add(part.trim());
        }
        return parts;
    }

    private static Matcher find(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Not found: " + pattern.pattern());
        }
        return matcher;
    }
}

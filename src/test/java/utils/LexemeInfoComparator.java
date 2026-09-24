package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Generic reflection-based comparator for test assertions.
 * <p>
 * Compares two objects of the same type field by field using reflection,
 * traversing class hierarchies, ignoring infrastructure fields, and applying
 * custom evaluation rules for AST domain objects.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.3
 * @since 1.0
 */
public class LexemeInfoComparator {

    /**
     * Convenience overload that looks up the actual {@link LexemeInfo} in a
     * {@link SymbolTable} before comparing it against the expected value.
     */
    public static List<String> compare(SymbolTable symbolTable, String key, LexemeInfo expected) {
        LexemeInfo actual = symbolTable.get(key);

        if (actual == null) {
            List<String> diffs = new ArrayList<>();
            diffs.add("key '" + key + "' not found in symbol table");
            return diffs;
        }

        return compare(expected, actual);
    }

    /**
     * Compares two objects field by field using reflection.
     */
    private static List<String> compare(Object expected, Object actual) {
        List<String> diffs = new ArrayList<>();

        if (expected == null || actual == null) {
            if (expected != actual) {
                diffs.add("whole object (one of them is null)");
            }
            return diffs;
        }

        for (Field field : getAllFields(expected.getClass())) {
            int modifiers = field.getModifiers();

            // Omitir constantes estáticas, sintéticas y referencias a SymbolTable
            if (Modifier.isStatic(modifiers) || field.isSynthetic() || SymbolTable.class.isAssignableFrom(field.getType())) {
                continue;
            }

            field.setAccessible(true);

            try {
                Object expectedValue = field.get(expected);
                Object actualValue = field.get(actual);

                if (!areEqual(expectedValue, actualValue)) {
                    diffs.add(String.format(
                            "%s (expected=%s, actual=%s)",
                            field.getName(), unwrapToString(expectedValue), unwrapToString(actualValue)
                    ));
                }
            } catch (IllegalAccessException e) {
                diffs.add(field.getName() + " (could not be read: " + e.getMessage() + ")");
            }
        }
        return diffs;
    }

    /**
     * Determines whether two objects are equal by handling collections,
     * struct initializations, domain initializations, and deep reflection.
     */
    private static boolean areEqual(Object expected, Object actual) {
        if (Objects.equals(expected, actual)) {
            return true;
        }
        if (expected == null || actual == null) {
            return false;
        }

        // 1. Extraer Mapas (ya sea si son Map nativos o StructInitialization)
        Map<?, ?> mapExpected = expected instanceof Map ? (Map<?, ?>) expected : extractMapFromStruct(expected);
        Map<?, ?> mapActual = actual instanceof Map ? (Map<?, ?>) actual : extractMapFromStruct(actual);

        if (mapExpected != null && mapActual != null) {
            return areMapsEqual(mapExpected, mapActual);
        }

        // 2. Manejo de Listas / Colecciones
        if (expected instanceof Iterable && actual instanceof Iterable) {
            return areIterablesEqual((Iterable<?>) expected, (Iterable<?>) actual);
        }

        // 3. Reglas para objetos que implementan Inicialización
        boolean isExpInit = expected.getClass().getSimpleName().endsWith("Initialization");
        boolean isActInit = actual.getClass().getSimpleName().endsWith("Initialization");

        if (isExpInit && isActInit) {
            String expClass = expected.getClass().getSimpleName();
            String actClass = actual.getClass().getSimpleName();

            if (expClass.equals(actClass)) {
                // Inicializaciones por defecto sin estado propio
                if (expClass.equals("BooleanInitialization") || expClass.equals("BoolInitialization")
                        || expClass.equals("RealInitialization") || expClass.equals("IntInitialization")
                        || expClass.equals("IntegerInitialization")) {
                    return true;
                }

                // Para VariableInitialization u otras con valor, comparar sus contenidos
                String valExp = extractInitializationValue(expected);
                String valAct = extractInitializationValue(actual);
                if (valExp != null && valAct != null) {
                    return Objects.equals(valExp, valAct);
                }
                return true;
            }
        }

        // 4. Comparación reflexiva profunda si son exactamente la misma clase de dominio
        if (expected.getClass().equals(actual.getClass()) && isCustomClass(expected.getClass())) {
            return compare(expected, actual).isEmpty();
        }

        // 5. Fallback a representación en String
        return Objects.equals(unwrapToString(expected), unwrapToString(actual));
    }

    private static boolean areMapsEqual(Map<?, ?> expected, Map<?, ?> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (Map.Entry<?, ?> entry : expected.entrySet()) {
            Object key = entry.getKey();
            if (!actual.containsKey(key)) {
                return false;
            }
            if (!areEqual(entry.getValue(), actual.get(key))) {
                return false;
            }
        }
        return true;
    }

    private static boolean areIterablesEqual(Iterable<?> expected, Iterable<?> actual) {
        java.util.Iterator<?> it1 = expected.iterator();
        java.util.Iterator<?> it2 = actual.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            if (!areEqual(it1.next(), it2.next())) {
                return false;
            }
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    /**
     * Extrae de forma reflexiva el Map interno buscando en la clase y en sus superclases.
     */
    private static Map<?, ?> extractMapFromStruct(Object structObj) {
        if (structObj == null) return null;
        for (Field field : getAllFields(structObj.getClass())) {
            if (Map.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    Object val = field.get(structObj);
                    if (val != null) {
                        return (Map<?, ?>) val;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    /**
     * Intenta extraer el valor textual de un objeto de inicialización
     * (ej. mediante getVariableValue() o inspeccionando atributos de tipo String).
     */
    private static String extractInitializationValue(Object obj) {
        if (obj == null) return null;
        try {
            Method method = obj.getClass().getMethod("getVariableValue");
            Object res = method.invoke(obj);
            if (res != null) return res.toString();
        } catch (Exception ignored) {
        }

        for (Field f : getAllFields(obj.getClass())) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || f.isSynthetic() || SymbolTable.class.isAssignableFrom(f.getType())) {
                continue;
            }
            if (f.getType().equals(String.class)) {
                f.setAccessible(true);
                try {
                    Object res = f.get(obj);
                    if (res != null) return res.toString();
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    /**
     * Devuelve todos los campos declarados en la clase y en su jerarquía de herencia.
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Field f : current.getDeclaredFields()) {
                fields.add(f);
            }
            current = current.getSuperclass();
        }
        return fields;
    }

    private static boolean isCustomClass(Class<?> clazz) {
        return !clazz.getName().startsWith("java.") && !clazz.isEnum();
    }

    /**
     * Convierte cualquier objeto a una cadena representativa legible para el reporte de errores.
     */
    private static String unwrapToString(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                if (!first) sb.append(", ");
                sb.append(entry.getKey()).append("=").append(unwrapToString(entry.getValue()));
                first = false;
            }
            return sb.append("}").toString();
        }

        Map<?, ?> structMap = extractMapFromStruct(obj);
        if (structMap != null) {
            return unwrapToString(structMap);
        }

        if (!isCustomClass(obj.getClass())) {
            return obj.toString();
        }

        String initVal = extractInitializationValue(obj);
        if (initVal != null) {
            return initVal;
        }

        return obj.getClass().getSimpleName();
    }
}
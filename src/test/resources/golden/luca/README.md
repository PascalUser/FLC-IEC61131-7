Here is the translation of your document into English:

# Luca Sarramone's Generator References

Reference outputs (*golden files*) to validate the port of Luca Sarramone's FLC generator ([https://github.com/LucaSarramone/Generador-FLC](https://github.com/LucaSarramone/Generador-FLC)), used with his permission.

## Provenance

* Repository: `LucaSarramone/Generador-FLC`, commit `c304c1d` ("Readme modificado + nuevos ejemplos" / "Modified Readme + new examples").
* `<example>.txt`: input in Luca's DSL, copied from `Ejemplos/` without changes. The only difference is that `pendulo invertido.txt` was renamed to `pendulo_invertido.txt`. His generator builds the output name by replacing spaces with `_`, so the output remains the same.
* `original/`: output (`<base>_out.cpp`, `<base>_out.h`, `<base>_outTB.cpp`) **regenerated with the code from that commit**, untouched. For `pendulo_invertido` and `Autoenfoque`, it matches byte-by-byte with the one versioned in his repo. The versioned one for `EstacionamientoCamion` came from an earlier version of the generator (which used `double`), which is why it was regenerated.
* `expected/`: what our compiler is expected to generate. It is `original/` with the triangle correction applied (see below); the `.h` and the testbench are identical.

## Corrections Regarding the Original Generator

The original generator has two bugs in the membership functions. The Z and S ramps and the singleton are correct:

* **Triangle** (`TriangularFS`): it uses the rising slope (`function<var><i>1`) for the falling side (`x > b`) and the falling slope (`function<var><i>2`) for the rising side. For example, in the pendulum (`nm = TRIANGULAR(0, 99, 113)`) it yields 242 at x = 0 and 226 at x = 113, where it should yield 0.
* **Trapezoid** (`TrapezoidFS`): the outside condition is inverted (it calculates the slopes when x is outside `[a, d]` and yields 0 inside) and on the plateau it assigns `1` instead of `2^N - 1`. No example uses a trapezoid; its corrected code is fixed in `TrapezoidFuzzySetTest`.

`expected/<base>_out.cpp` was obtained from `original/` by swapping, in each triangle, the right-hand sides of the two assignments (`function…1 (x-a)` ⇄ `function…2 (c - x)`). This way, the reference remains independent of our generator. When executed for all 8-bit x values, the corrected code matches the ideal shape calculated with the same integer slopes (`255/(b - a)`). The fact that these slopes are integers is a design limitation, not a bug: in `TRIANGULAR(0, 99, 113)` the peak reaches 198 and not 255.

The base name (`pendulo_invertido`, `Autoenfoque`, `EstacionamientoCamion`) is the name of the input file. It appears inside the output (`#include "<base>_out.h"` in the testbench).

## Translation to FCL (`<base>.fcl`)

Each `<base>.fcl` is the same controller written in FCL (IEC 61131-7). It was mechanically translated from the `.txt` using this mapping:

| Luca's DSL                                               | FCL                                                                                        |
|----------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `nombre <8>:`                                            | `OPTION PRAGMA bit_width 8; END_OPTION`; the FB name is the base name                      |
| `x : IN;` / `y : OUT;`                                   | `VAR_INPUT x : USINT;` / `VAR_OUTPUT y : USINT;` (8-bit unsigned integer)                  |
| `Z_SHAPE(c, d)`                                          | `TERM t := (c, 1) (d, 0);`                                                                 |
| `S_SHAPE(a, b)`                                          | `TERM t := (a, 0) (b, 1);`                                                                 |
| `TRIANGULAR(a, b, c)`                                    | `TERM t := (a, 0) (b, 1) (c, 0);`                                                          |
| `TRAPEZOID(a, b, c, d)`                                  | `TERM t := (a, 0) (b, 1) (c, 1) (d, 0);`                                                   |
| `SINGLETON(v)` (outputs)                                 | `TERM t := v;` inside `DEFUZZIFY`                                                          |
| `RULES <MINMAX>`, shape `(x * y = z)`, rule `a * b = c;` | `RULEBLOCK reglas AND : MIN; ACCU : MAX;` and `RULE i : IF x IS a AND y IS b THEN z IS c;` |
| `z = CENTROID;`                                          | `METHOD : CoGS;`                                                                           |
| — (his generator has no default)                         | `DEFAULT := NC;`                                                                           |

The order of variables, terms, and rules is preserved. `Autoenfoque` infers three outputs per rule (Extension Level of the standard). The other two are Basic Level programs. Luca's generator does not implement `DEFAULT`: if no rule is triggered, the centroid divides by zero.

## How to Regenerate

1. Clone the repository at `c304c1d` and compile `Compilador/src` (Java 8+, ISO-8859-1 encoding):
   `javac -encoding ISO-8859-1 -d classes $(find Compilador/src -name "*.java")`.
2. Compile and run this driver, which executes the generator without opening the GUI, passing the **absolute** paths of the `.txt` files. The generator writes the output in each input's folder:

```java
import java.lang.reflect.Field;

public class Driver {
    public static void main(String[] args) throws Exception {
        // MainFrame.printOutput writes to a static JTextArea that is only created by the GUI.
        Field output = Class.forName("gui.MainFrame").getDeclaredField("outputText");
        output.setAccessible(true);
        output.set(null, new javax.swing.JTextArea());
        for (String path : args) {
            new compiler.Compiler().compile(path);
        }
    }
}

```

`java -Djava.awt.headless=true -cp classes Driver <path>/pendulo_invertido.txt ...`

3. Those files go to `original/`. For `expected/`, copy the `.h` and the testbench, and apply the triangle correction in the `.cpp`. Using Python:

```python
import re
TRIANGLE = re.compile(r"(\t\t\t(\w+)Fuzz\[(\d+)\] = )(function\w+1 \* \(\w+-\d+\))"
                      r"(; \n\t\telse \n\t\t\t\2Fuzz\[\3\] = )(function\w+2 \* \(\d+ - \w+\))(;\n)")
corrected, count = TRIANGLE.subn(r"\1\6\5\4\7", original_cpp)   # count = number of TRIANGULAR in the .txt
```

## How It Compares

The output is not required to be identical byte-by-byte, but rather **equivalent**: `LucaGoldenIT` compares the C++ token sequences, ignoring spaces, line breaks (the generator writes `\n`; git might convert them to `\r\n`), comments, and case sensitivity. IEC 61131 names are case-insensitive and the front-end normalizes them. Any other change, even one that does not alter behavior, requires the reference to be reviewed explicitly.
# Referencias del generador de Luca Sarramone

Salidas de referencia (*golden files*) para validar el port del generador FLC de Luca Sarramone
(<https://github.com/LucaSarramone/Generador-FLC>), usado con su permiso.

## Procedencia

- Repositorio: `LucaSarramone/Generador-FLC`, commit `c304c1d` ("Readme modificado + nuevos ejemplos").
- `<ejemplo>.txt`: entrada en el DSL de Luca, copiada de `Ejemplos/` sin cambios. La única diferencia es
  que `pendulo invertido.txt` se renombró a `pendulo_invertido.txt`. Su generador arma el nombre de
  salida reemplazando los espacios por `_`, así que la salida es la misma.
- `expected/`: salida (`<base>_out.cpp`, `<base>_out.h`, `<base>_outTB.cpp`) **regenerada con el código
  de ese commit**. Para `pendulo_invertido` y `Autoenfoque` coincide byte a byte con la que está versionada en su repo.
  La versionada de `EstacionamientoCamion` venía de una versión anterior del generador (usaba
  `double`), por eso se regeneró.

El nombre base (`pendulo_invertido`, `Autoenfoque`, `EstacionamientoCamion`) es el nombre del archivo de
entrada. Aparece dentro de la salida (`#include "<base>_out.h"` en el testbench).

## Traducción a FCL (`<base>.fcl`)

Cada `<base>.fcl` es el mismo controlador escrito en FCL (IEC 61131-7). Se tradujo mecánicamente del
`.txt` con esta correspondencia:

| DSL de Luca | FCL |
|---|---|
| `nombre <8>:` | `OPTION PRAGMA bit_width 8; END_OPTION`; el nombre del FB es el nombre base |
| `x : IN;` / `y : OUT;` | `VAR_INPUT x : USINT;` / `VAR_OUTPUT y : USINT;` (entero sin signo de 8 bits) |
| `Z_SHAPE(c, d)` | `TERM t := (c, 1) (d, 0);` |
| `S_SHAPE(a, b)` | `TERM t := (a, 0) (b, 1);` |
| `TRIANGULAR(a, b, c)` | `TERM t := (a, 0) (b, 1) (c, 0);` |
| `TRAPEZOID(a, b, c, d)` | `TERM t := (a, 0) (b, 1) (c, 1) (d, 0);` |
| `SINGLETON(v)` (salidas) | `TERM t := v;` dentro de `DEFUZZIFY` |
| `RULES <MINMAX>`, forma `(x * y = z)`, regla `a * b = c;` | `RULEBLOCK reglas AND : MIN; ACCU : MAX;` y `RULE i : IF x IS a AND y IS b THEN z IS c;` |
| `z = CENTROID;` | `METHOD : CoGS;` |
| — (su generador no tiene default) | `DEFAULT := NC;` |

Se respetan el orden de las variables, de los términos y de las reglas. `Autoenfoque` concluye
tres salidas por regla (Extension Level del estándar). Los otros dos son programas de Basic Level.
El generador de Luca no implementa `DEFAULT`: si no se activa ninguna regla, el centroide divide por cero.

## Cómo regenerar

1. Clonar el repositorio en `c304c1d` y compilar `Compilador/src` (Java 8+, codificación ISO-8859-1):
   `javac -encoding ISO-8859-1 -d classes $(find Compilador/src -name "*.java")`.
2. Compilar y ejecutar este driver, que corre el generador sin abrir la GUI, pasándole las rutas
   **absolutas** de los `.txt`. El generador escribe la salida en la carpeta de cada entrada:

```java
import java.lang.reflect.Field;

public class Driver {
    public static void main(String[] args) throws Exception {
        // MainFrame.printOutput escribe en un JTextArea estático que solo crea la GUI.
        Field output = Class.forName("gui.MainFrame").getDeclaredField("outputText");
        output.setAccessible(true);
        output.set(null, new javax.swing.JTextArea());
        for (String path : args) {
            new compiler.Compiler().compile(path);
        }
    }
}
```

`java -Djava.awt.headless=true -cp classes Driver <ruta>/pendulo_invertido.txt ...`

## Cómo se compara

No se exige que la salida sea idéntica byte a byte, sino **equivalente**: `LucaGoldenIT` compara las
secuencias de tokens de C++, ignorando espacios, saltos de línea (el generador escribe `\n`; git puede
convertirlos a `\r\n`), comentarios y mayúsculas/minúsculas. Los nombres de IEC 61131 no distinguen
mayúsculas y el front-end los normaliza. Cualquier otro cambio, incluso uno que no altere el
comportamiento, obliga a revisar la referencia de forma explícita.

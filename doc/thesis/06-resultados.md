# Resultados: cobertura del estándar

## Subconjunto soportado

A partir de la gramática (`Parser.y`) y el lexer (`Lexer.flex`), el
compilador reconoce:

- Los cuatro tipos derivados de IEC 61131-3 Anexo B: enumerado,
  subrango, arreglo y estructura, incluyendo estructuras anidadas y
  arreglos multidimensionales de estructuras con inicializadores
  parciales y factores de repetición (`N(valor)`).
- Los 24 subtipos elementales listados en `Subtype.java`: booleano,
  enteros con/sin signo (8/16/32/64 bits), reales (32/64 bits),
  temporales (`TIME`, `DATE`, `TIME_OF_DAY`, `DATE_AND_TIME`), cadenas
  de bits (`BYTE`/`WORD`/`DWORD`/`LWORD`) y cadenas de caracteres
  (`STRING`/`WSTRING`).
- Bloques `FUZZIFY`/`DEFUZZIFY` (términos lingüísticos como *singleton*
  o lista de puntos, métodos de defuzzificación `COG`/`COGS`/`COA`/
  `LM`/`RM`) y `RULEBLOCK` (operadores `AND`/`OR`/`ACT`/`ACCU`,
  condiciones con `IS`/`NOT`, conclusiones múltiples con peso opcional
  `WITH`), a nivel sintáctico.
- Bloques `OPTION` con pragmas.

## Trabajo pendiente (marcado explícitamente en el código)

El propio código fuente documenta, vía comentarios `// TODO:`, las
verificaciones semánticas que quedan fuera del alcance de esta versión:
control de errores sobre enumerados literales inexistentes
(`initialized_custom_with_identifier`, `initialized_enumerated`),
chequeo semántico de rangos de subrango (`range`), conversión de
constantes con prefijo de tipo (`numeric_constant`), y verificación de
compatibilidad de tipo entre un prefijo temporal y su literal
(`time_constant`). Estas quedan propuestas como trabajo futuro en el
Capítulo 7.

## Calidad y pruebas

El proyecto usa JUnit 5 + Mockito para pruebas unitarias por
componente (transformadores léxicos, analizadores semánticos,
`SymbolTable`) y pruebas de integración por tipo derivado
(`EnumerateTypeIT`, `StructTypeIT`, `SubrangeTypeIT`, `ArrayTypeIT`,
`PrimitiveTypeIT`) que parsean un fragmento FCL completo y verifican,
lexema por lexema, el `LexemeInfo` resultante contra el esperado. El
build (`pom.xml`) integra además JaCoCo (cobertura), Checkstyle y
SpotBugs como gates de calidad en la fase `verify`.

# Introducción

## Motivación

IEC 61131-7 es la extensión del estándar IEC 61131-3 que define **Fuzzy
Control Language (FCL)**, un lenguaje de programación textual para
controladores de lógica difusa en el ámbito industrial. A diferencia de
los cinco lenguajes definidos en IEC 61131-3 (Ladder, FBD, SFC, ST, IL),
FCL no cuenta con la misma disponibilidad de herramientas de código
abierto para su análisis y compilación.

Este trabajo presenta el diseño e implementación de un compilador para
el subconjunto declarativo e inicializador de FCL: declaraciones de
bloques de función (`FUNCTION_BLOCK`), tipos definidos por el usuario
(`STRUCT`, enumerados, subrangos, arreglos) y bloques de fuzzificación,
defuzzificación y reglas, con reconocimiento sintáctico completo y
resolución semántica de tipos e inicializaciones.

## Objetivos

**Objetivo general:** construir un compilador (analizador léxico y
sintáctico, con verificación semántica de tipos) para el subconjunto de
IEC 61131-7 descripto arriba.

**Objetivos específicos:**

- Implementar un analizador léxico basado en JFlex capaz de reconocer
  todos los literales del estándar (numéricos con base, temporales,
  cadenas de caracteres simples y anchas) con sus reglas de rango y
  normalización.
- Implementar un analizador sintáctico LALR(1) con Bison que reconozca
  la gramática de declaración de tipos, variables y bloques de FCL.
- Diseñar una tabla de símbolos centralizada que resuelva nombres
  compuestos (tipos anidados, campos de estructuras, valores de
  enumerados) mediante un esquema de *name mangling*.
- Resolver, para cada identificador declarado, su clasificación
  semántica completa (tipo, subtipo, uso, fuente, límites, parámetros e
  inicialización), incluyendo inicializaciones anidadas para arreglos
  de estructuras.

## Alcance

Quedan fuera del alcance de este trabajo: la generación de código
ejecutable, la evaluación en tiempo de ejecución de las reglas difusas,
y la verificación semántica de las expresiones dentro de los bloques
`RULEBLOCK` (el reconocimiento sintáctico de reglas está implementado;
su chequeo semántico queda marcado como trabajo futuro — ver
`TODO` en `src/main/java/parser/Parser.y`, reglas de `condition` y
`conclusion_list`).

## Organización del documento

El Capítulo 2 presenta el marco teórico de construcción de
compiladores aplicado a este proyecto. El Capítulo 3 describe la
arquitectura general. Los Capítulos 4 y 5 detallan, respectivamente, el
análisis léxico y sintáctico implementados. El Capítulo 6 resume el
subconjunto del estándar efectivamente soportado, con métricas
extraídas directamente del código fuente. El Capítulo 7 concluye y
propone trabajo futuro.

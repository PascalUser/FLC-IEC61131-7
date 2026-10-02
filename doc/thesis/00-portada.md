# Compilador FLC para IEC 61131-7

## Diseño, implementación y validación de un compilador para Fuzzy Control Language

---

**Autor:** Matías Ortiz  
**Director:** Victoriano Etcheverría  
**Institución:** Universidad Nacional de Córdoba  
**Facultad:** Facultad de Ciencias Exactas, Físicas y Naturales  
**Carrera:** Ingeniería en Computación  

**Fecha:** Octubre 2026  

---

## Resumen

Esta tesis presenta el diseño, implementación y validación de un compilador para el lenguaje Fuzzy Control Language (FCL) definido en la norma IEC 61131-7. El compilador implementa análisis léxico (JFlex), análisis sintáctico LALR(1) (GNU Bison), y una tabla de símbolos semánticamente rica con resolución de nombres jerárquica mediante *name mangling*. La arquitectura modular prioriza la **mantenibilidad** como atributo de calidad principal, siguiendo un ciclo de vida iterativo con una única entrega final.

**Palabras clave:** compilador, FCL, IEC 61131-7, análisis léxico, análisis sintáctico, tabla de símbolos, mantenibilidad, JFlex, Bison.
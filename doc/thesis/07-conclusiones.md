# Conclusiones y trabajo futuro

## Conclusiones

Se implementó un compilador léxico-sintáctico completo para el
subconjunto declarativo e inicializador de IEC 61131-7/FCL, con
resolución semántica de tipos derivados anidados (estructuras dentro de
estructuras, arreglos de estructuras) mediante una tabla de símbolos
centralizada y un esquema de *name mangling* jerárquico. La separación
en capas (transformación léxica → análisis semántico léxico →
reducción gramatical → publicación) permitió aislar cada
responsabilidad detrás de patrones de diseño estándar (Chain of
Responsibility, Builder, Strategy, Repository), lo que a su vez hizo
posible cubrir el sistema con pruebas unitarias e de integración
independientes por componente.

## Trabajo futuro

1. **Verificación semántica de reglas difusas**: completar los `TODO`
   pendientes en `condition`/`conclusion_list` de `Parser.y` para
   validar que los identificadores usados en una regla (`humidity IS
   middle`) correspondan efectivamente a variables fuzzificadas y
   términos lingüísticos declarados.
2. **Chequeo de rangos y de enumerados inexistentes**: resolver los
   `TODO` de control de error listados en el Capítulo 6.
3. **Generación de código**: una vez cerrada la verificación semántica,
   generar una representación intermedia ejecutable (o código C/Java)
   a partir de la tabla de símbolos poblada.
4. **Cobertura de IEC 61131-3 Anexo B restante**: tipos `RETAIN`/
   `NON_RETAIN`, function blocks estándar (`standard_function_block_specification`,
   actualmente sin resolución semántica más allá del reconocimiento
   sintáctico).

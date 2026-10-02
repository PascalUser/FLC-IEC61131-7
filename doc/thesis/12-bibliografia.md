# Bibliografía

## Estándares

- **IEC 61131-3:2013** — *Programmable controllers — Part 3: Programming languages*. International Electrotechnical Commission.
- **IEC 61131-7:2019** — *Programmable controllers — Part 7: Fuzzy control programming*. International Electrotechnical Commission.
- **IEC 61131-3:2003** — *Anexo B: Data types*. (Referencia para tipos elementales y derivados).

## Compiladores y teoría

- **Aho, A. V., Lam, M. S., Sethi, R., & Ullman, J. D. (2006).** *Compilers: Principles, Techniques, and Tools* (2nd ed.). Addison-Wesley. — "Dragon Book", referencia canónica de análisis léxico, sintáctico, tablas de símbolos.
- **Grune, D., & Jacobs, C. J. H. (2008).** *Parsing Techniques: A Practical Guide* (2nd ed.). Springer. — Cobertura exhaustiva de LL, LR, LALR, Earley, GLR.
- **Appel, A. W. (1998).** *Modern Compiler Implementation in Java* (2nd ed.). Cambridge University Press. — Enfoque práctico en Java, tablas de símbolos, IR.
- **Muchnick, S. S. (1997).** *Advanced Compiler Design and Implementation*. Morgan Kaufmann. — Optimizaciones, SSA, análisis de flujo de datos.

## Herramientas

- **JFlex Manual** — *The Fast Scanner Generator for Java*. http://jflex.de/manual.html
- **GNU Bison Manual** — *GNU Bison: The LALR(1) Parser Generator*. https://www.gnu.org/software/bison/manual/
- **JFlex + Bison Integration** — *Using JFlex with CUP/Bison*. http://jflex.de/manual.html#cup

## Patrones de diseño

- **Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994).** *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley. — "Gang of Four", referencia de: Chain of Responsibility, Builder, Template Method, Strategy, Composite, Publisher/Subscriber.
- **Fowler, M. (2002).** *Patterns of Enterprise Application Architecture*. Addison-Wesley. — Repository, Unit of Work, Identity Map.

## Atributos de calidad

- **ISO/IEC 25010:2011** — *Systems and software Quality Requirements and Evaluation (SQuaRE) — System and software quality models*. — Modelo de calidad: mantenibilidad, funcionalidad, fiabilidad, eficiencia, seguridad, usabilidad, portabilidad.
- **Bass, L., Clements, P., & Kazman, R. (2012).** *Software Architecture in Practice* (3rd ed.). Addison-Wesley. — Atributos de calidad, tácticas de mantenibilidad.

## Métricas y análisis estático

- **McCabe, T. J. (1976).** *A Complexity Measure*. IEEE Transactions on Software Engineering, 2(4), 308-320. — Complejidad ciclomática.
- **Chidamber, S. R., & Kemerer, C. F. (1994).** *A Metrics Suite for Object Oriented Design*. IEEE Transactions on Software Engineering, 20(6), 476-493. — Métricas CK (WMC, DIT, NOC, CBO, RFC, LCOM).

## Testing y calidad

- **Meszaros, G. (2007).** *xUnit Test Patterns: Refactoring Test Code*. Addison-Wesley. — Patrones de test: Test Double, Test Fixture, Test Data Builder.
- **Myers, G. J., Badgett, T., & Sandler, C. (2011).** *The Art of Software Testing* (3rd ed.). Wiley. — Técnicas de testing: caja blanca, caja negra, partición de equivalencia, análisis de valores límite.
- **Humble, J., & Farley, D. (2010).** *Continuous Delivery*. Addison-Wesley. — Pipeline, quality gates, automatización.

## Herramientas usadas

- **JFlex 1.8.2** — http://jflex.de/
- **GNU Bison 3.8.2** — https://www.gnu.org/software/bison/
- **JUnit 5 (Jupiter)** — https://junit.org/junit5/
- **Mockito 5.x** — https://site.mockito.org/
- **JaCoCo 0.8.x** — https://www.jacoco.org/
- **Checkstyle 10.x** — https://checkstyle.org/
- **SpotBugs 4.x** — https://spotbugs.github.io/
- **PMD 6.x** — https://pmd.github.io/
- **Maven 3.9.x** — https://maven.apache.org/
- **Gradle 8.x** — https://gradle.org/
- **Pandoc 3.x** — https://pandoc.org/
- **Mermaid CLI (@mermaid-js/mermaid-cli)** — https://github.com/mermaid-js/mermaid-cli
- **Puppeteer / chrome-headless-shell** — https://pptr.dev/

## Metodologías

- **Beck, K. (2003).** *Test-Driven Development: By Example*. Addison-Wesley. — TDD, red-green-refactor.
- **Martin, R. C. (2008).** *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall. — Principios SOLID, código limpio.
- **Martin, R. C. (2017).** *Clean Architecture*. Prentice Hall. — Arquitectura limpia, independencia de frameworks.
- **Kruchten, P. (2003).** *The Rational Unified Process: An Introduction* (3rd ed.). Addison-Wesley. — Iterativo e incremental, arquitectura centrada en casos de uso.

## Lógica difusa y control

- **Zadeh, L. A. (1965).** *Fuzzy Sets*. Information and Control, 8(3), 338-353. — Artículo fundacional.
- **Mamdani, E. H., & Assilian, S. (1975).** *An experiment in linguistic synthesis with a fuzzy logic controller*. International Journal of Man-Machine Studies, 7(1), 1-13. — Controlador Mamdani.
- **Takagi, T., & Sugeno, M. (1985).** *Fuzzy identification of systems and its applications to modeling and control*. IEEE Transactions on Systems, Man, and Cybernetics, 15(1), 116-132. — Modelo Takagi-Sugeno.
- **IEC 61131-7:2019** — Cláusulas 6-10 (FUZZIFY, DEFUZZIFY, RULEBLOCK, términos lingüísticos, funciones de membresía, métodos de defuzzificación).

## Recursos en línea

- **OpenCode Documentation** — https://opencode.ai/docs
- **Mermaid Documentation** — https://mermaid.js.org/
- **Pandoc User's Guide** — https://pandoc.org/MANUAL.html
- **JaCoCo Documentation** — https://www.jacoco.org/jacoco/trunk/doc/
- **Checkstyle Checks** — https://checkstyle.org/checks.html
- **SpotBugs Bug Descriptions** — https://spotbugs.readthedocs.io/
- **PMD Rule Reference** — https://pmd.github.io/pmd-6.55.0/pmd_rules_java.html
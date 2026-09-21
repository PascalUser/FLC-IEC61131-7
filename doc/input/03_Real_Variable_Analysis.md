# Real Variable

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
VAR_OUTPUT
    power1 : REAL;
    power2 : REAL := 0.3e10;
END_VAR
```

### power1

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683964000652&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | POWER1 | Identifier string extracted from the source code\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.VARIABLE | Indicates the identifier functions as a variable\. |
| source | Source\.OUTPUT | Indicates that values will come from outside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\(\) "0\.0" \(Initialization\) | Initial literal value assigned |

power2

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683964000653&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | POWER1 | Identifier string extracted from the source code\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.VARIABLE | Indicates the identifier functions as a variable\. |
| source | Source\.OUTPUT | Indicates that values will come from outside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\(\) "0\.3e10" \(Initialization\) | Initial literal value assigned |


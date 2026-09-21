# Subrange Variable

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    Dia : INT (0..31);
END_TYPE

VAR
    fecha_trabajo : DIA := 21;
END_VAR
```

## Symbol Table Documentation

### Dia

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682931508885&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | DIA | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SUBRANGE  | Subrange type classification |
| subtype | Subtype\.REAL | Primitive type classification |
| customType | null | Does not apply |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | 0 | Lower bound constraint |
| superiorLimit | 31 | Upper bound constraint |
| parameters | null | Does not apply |
| initialValue | Subrange\("0"\) \(Initialization\) | Initial literal value assigned |

### fecha\_trabajo

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682934440037&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | FECHA\_TRABAJO | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE  | Simple type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere\. |
| customType | DIA | Defined type |
| use | Use\.VARIABLE | Indicates the identifier functions as a variable\. |
| source | Source\.INTERNAL | Indicates that values will come from inside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("21"\) | Initial literal value assigned |

## **Code Declaration \(IEC 61131\-7 / FCL\)**

```javascript

VAR
    Porcentaje : INT (0..100) := 50;

END_VAR
```

## Symbol Table Documentation

### Porcentaje

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682935076895&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | PORCENTAJE | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SUBRANGE  | Subrange type classification |
| subtype | Subtype\.INT | Primitive type classification |
| customType | null | Does not apply |
| use | Use\.VARIABLE | Indicates the identifier functions as a variable\. |
| source | Source\.INTERNAL | Indicates that values will come from inside the controller |
| inferiorLimit | 0 | Lower bound constraint |
| superiorLimit | 100 | Upper bound constraint |
| parameters | null | Does not apply |
| initialValue | Constant\("50"\) | Initial literal value assigned |


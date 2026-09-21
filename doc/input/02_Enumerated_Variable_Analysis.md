# Enumerated Variable

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    MethodType : (CENTROID, AVERAGE);
END_TYPE

VAR
    defuzz_method : MethodType := CENTROID;
END_VAR
```

## Symbol Table Documentation

### defuzz\_method

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683939767824&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | DEFUZZ\_METHOD | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere |
| customType | "METHODTYPE" | Defined type |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.INTERNAL | Indicates that values will come from inside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("METHODTYPE\#CENTROID"\) | Initial literal value assigned |

### MethodType

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682911451148&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | METHODTYPE | Identifier string extracted from the source code in uppercase\. |
| type | Type\.ENUMERATE | Enumerate type classification |
| subtype | Subtype\.INT | Primitive type classification\. |
| customType | null | Dos not apply |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \["CENTROID", "AVERAGE"\] | Name of the values the enumerate holds |
| initialValue | Enumerated\(\) "0" \(Initialization\) | Initial literal value assigned |

### CENTROID

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682914413982&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | METHODTYPE\#CENTROID | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Dos not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Output block declaration scope in IEC 61131\-7\. |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("0"\) | Initial literal value assigned |

### AVERAGE

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682915420884&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | METHODTYPE\#AVERAGE | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Dos not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("1"\) | Initial literal value assigned |

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
VAR
    defuzz_method : (CENTROID, AVERAGE) := CENTROID;
END_VAR
```

## Symbol Table Documentation

### defuzz\_method

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682912778640&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | DEFUZZ\_METHOD | Identifier string extracted from the source code in uppercase\. |
| type | Type\.ENUMERATE | Enumerate type classification |
| subtype | Subtype\.INT | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.VARIABLE | Indicates the identifier as a variable\. |
| source | Source\.INTERNAL | Indicates that values will come from inside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \["CENTROID", "AVERAGE"\] | Name of the values the enumerate holds |
| initialValue | Constant\("0"\) | Initial literal value assigned |

### CENTROID

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682934214200&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | CENTROID | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("0"\) | Initial literal value assigned |

### AVERAGE

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682934214252&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | AVERAGE | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("1"\) | Initial literal value assigned |


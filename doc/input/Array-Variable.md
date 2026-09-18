# Array Variable

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    color_type : 
        STRUCT 
            classification : (WHITE, GRAY, BLACK);
            gamma: REAL := 0.5;
        END_STRUCT
END_TYPE

VAR
    pixels : ARRAY [0..1, 1..10, 3..4] OF color_type := [ 
       20 (classification := WHITE, gamma := 1), 
          (classification := BLACK, gamma := 0),
       10 (classification := WHITE, gamma := 1),
        8 (classification := BLACK, gamma := 0),
        2 (classification := GRAY)
    ];
END_VAR
```

## Symbol Table Documentation

### color\_type

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683188702642&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE | Identifier string extracted from the source code in uppercase |
| type | Type\.STRUCT | Structure type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.TYPE | Indicates the identifier as a data type |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \[ "CLASSIFICATION", GAMMA" \] | Name of the variables the structure holds |
| initialValue | \{  "CLASSIFICATION":"COLOR\_TYPE\#CLASSIFICATION",  "GAMMA":"COLOR\_TYPE\#GAMMA" \} | Map that stores fields and their initial value\. |

### classification

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683187857631&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#CLASSIFICATION | Identifier string extracted from the source code in uppercase |
| type | Type\.ENUMERATED | Enumerate type classification |
| subtype | Subtype\.INT | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.FIELD | Indicates the identifier as a field of a structure\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \["WHITE", "GRAY", "BLACK"\] | Name of the values the enumerate holds |
| initialValue | Enumerated\(\) "0" \(Initialization\) | Initial literal value assigned |

### WHITE

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683188302563&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#CLASSIFICATION\# WHITE | Identifier string extracted from the source code in uppercase |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("0"\) | Initial literal value assigned |

### GRAY

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683188494594&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#CLASSIFICATION\# GRAY | Identifier string extracted from the source code in uppercase |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("1"\) | Initial literal value assigned |

### BLACK

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683188702084&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#CLASSIFICATION\# BLACK | Identifier string extracted from the source code in uppercase |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.MACRO | Indicates the identifier is a macro |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Constant\("2"\) | Initial literal value assigned |

### gamma

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683188893366&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#GAMMA | Identifier string extracted from the source code in uppercase |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.FIELD | Indicates the identifier functions as a variable\. |
| source | Source\.NONE | Does not apply\. |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\("0\.5"\) | Initial literal value assigned |

### pixels

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682937069108&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | PIXELS | Identifier string extracted from the source code in uppercase |
| type | Type\.ARRAY | Array type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere |
| customType | COLOR\_TYPE | Definedt ype |
| use | Use\.VARIABLE | Indicates the identifier functions as a variable\. |
| source | Source\.INTERNAL | Indicates that values will come from inside the controller |
| inferiorLimit | \[ 0,   1,  3 \] | Lower bound constraints |
| superiorLimit | \[ 1, 10 , 4 \] | Upper bound constraints |
| parameters | null | Does not apply |
| initialValue | \{     \{20,     \{     "CLASSIFICATION":"COLOR\_TYPE\#CLASSIFICATION\#WHITE",      "GAMMA":"1\.0"     \}\},     \{1,     \{      "CLASSIFICATION":"COLOR\_ TYPE\#CLASSIFICATION\#BLACK",      "GAMMA":"0\.0"     \}\},     \{10,     \{      "CLASSIFICATION":"COLOR\_TYPE\#CLASSIFICATION\#WHITE",      "GAMMA":"1\.0"     \}\},     \{8,     \{      "CLASSIFICATION":"COLOR\_TYPE\#CLASSIFICATION\#BLACK",      "GAMMA":"0\.0"     \}\},     \{2,     \{      "CLASSIFICATION":"COLOR\_TYPE\#CLASSIFICATION\#GRAY",      "GAMMA":"COLOR\_TYPE\#GAMMA"     \}\}, \} | Initial array values assigned during declaration\. |


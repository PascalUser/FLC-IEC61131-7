# Structured Type

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    color_type :
        STRUCT
            brown: REAL;
            light: REAL;
        END_STRUCT;
END_TYPE

VAR_INPUT
    color : color_type;
END_VAR
```

## Symbol Table Documentation

### color\_type

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682910473255&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE | Identifier string extracted from the source code in uppercase\. |
| type | Type\.STRUCT | Structure type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Does not apply |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \[ "BROWN", "LIGHT" \] | Name of the variables the structure holds |
| initialValue | \{ "BROWN":"COLOR\_TYPE\#BROWN", "LIGHT":"COLOR\_TYPE\#LIGHT"\} | Map that stores fields and their initial value\. |

### brown

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682911737094&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#BROWN | Identifier string extracted from the source code in uppercase\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.FIELD | Indicates the identifier as a field of a structure\. |
| source | Source\.NONE | Does not apply\. |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\(\) "0\.0" \(Initialization\) | Initial literal value assigned |

### light

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682913001279&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE\#LIGHT | Compound identifier from struct |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.FIELD | Indicates the identifier functions as a variable\. |
| source | Source\.NONE | Does not apply\. |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\(\) "0\.0" \(Initialization\) | Initial literal value assigned |

### color

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764682926022164&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR | Identifier string extracted from the source code\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere |
| customType | COLOR\_TYPE | Defined type |
| use | Use\.VARIABLE | Indicates the identifier functions as a data type\. |
| source | Source\.IN | Indicates that values will come from outside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | \{ "BROWN":"COLOR\_TYPE\#BROWN", "LIGHT":"COLOR\_TYPE\#LIGHT"\} | Map that stores fields and their initial value\. |

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    color_type :
        STRUCT
            brown: REAL;
            light: REAL;
        END_STRUCT;
END_TYPE

VAR_INPUT
    color : color_type := (light := 1.0);
END_VAR
```

## Symbol Table Documentation

### color

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683209179205&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR | Identifier string extracted from the source code\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere |
| customType | COLOR\_TYPE | Defined type |
| use | Use\.VARIABLE | Indicates the identifier functions as a data type\. |
| source | Source\.IN | Indicates that values will come from outside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | \{ "BROWN":"COLOR\_TYPE\#BROWN", "LIGHT":"1\.0"\} | Map that stores fields and their initial value\. |

## Code Declaration \(IEC 61131\-7 / FCL\)

```javascript
TYPE
    rgb_type:
        STRUCT
            gamma_r: REAL;
            gamma_g: REAL;
            gamma_b: REAL;
        END_STRUCT
        
    color_type :
        STRUCT
            white: BOOL;
            rgb  : rgb_type := (gammar_g := 3.0 );
        END_STRUCT;
END_TYPE

VAR_INPUT
    color : color_type := (white := TRUE, rgb := ( gamma_r := 10.2 ));
END_VAR
```

## Symbol Table Documentation

### rgb\_type

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683444871246&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | RGB\_TYPE | Identifier string extracted from the source code\. |
| type | Type\.STRUCT | Structure type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Applicable only when subtype == Subtype\.CUSTOM\. |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | \[ "GAMMA\_R", "GAMMA\_G", "GAMMA\_B" \] | Name of the variables the structure holds |
| initialValue | \{ "RGB\#GAMMA\_R":"RGB\_TYPE\#GAMMA\_R", "RGB\#GAMMA\_G":"RGB\_TYPE\#GAMMA\_G", "RGB\#GAMMA\_B":"RGB\_TYPE\#GAMMA\_B" \} | Map that stores fields and their initial value\. |

### gamma\_r

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683445241320&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | RGB\_TYPE\#GAMMA\_R | Compound identifier from struct |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.REAL | Primitive type classification\. |
| customType | null | Does not apply |
| use | Use\.FIELD | Indicates the identifier functions as a variable\. |
| source | Source\.NONE | Does not apply\. |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | Real\(\) "0\.0" \(Initialization\) | Initial literal value assigned |

### color\_type

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683445326775&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR\_TYPE | Identifier string extracted from the source code\. |
| type | Type\.STRUCT | Structure type classification\. |
| subtype | Subtype\.NONE | Does not apply |
| customType | null | Defined type |
| use | Use\.TYPE | Indicates the identifier as a data type\. |
| source | Source\.NONE | Does not apply |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | \{  "WHITE":"COLOR\_TYPE\#WHITE", "RGB\#GAMMA\_R":"RGB\_TYPE\#GAMMA\_R", "RGB\#GAMMA\_G":Constant\("3\.0"\), "RGB\#GAMMA\_B":"RGB\_TYPE:GAMMA\_B" \} | Map that stores fields and their initial value\. |

### color

*[Vista de Tabla](https://miro.com/app/board/uXjVLA4bN8g=/?moveToWidget=3458764683446251042&cot=14)*

| Attribute | Expected Value | Description |
| --- | --- | --- |
| Lexeme | COLOR | Identifier string extracted from the source code\. |
| type | Type\.SIMPLE | Simple type classification\. |
| subtype | Subtype\.CUSTOM | Defined Elsewhere |
| customType | COLOR\_TYPE | Defined type |
| use | Use\.VARIABLE | Indicates the identifier functions as a data type\. |
| source | Source\.IN | Indicates that values will come from outside the controller |
| inferiorLimit | null | Does not apply |
| superiorLimit | null | Does not apply |
| parameters | null | Does not apply |
| initialValue | \{  "WHITE":Constant\("TRUE"\), "RGB\#GAMMA\_R":"Constant\(10\.2\)",  "RGB\#GAMMA\_G":Constant\("3\.0"\), "RGB\#GAMMA\_B":"RGB\_TYPE:GAMMA\_B" \} | Map that stores fields and their initial value\. |


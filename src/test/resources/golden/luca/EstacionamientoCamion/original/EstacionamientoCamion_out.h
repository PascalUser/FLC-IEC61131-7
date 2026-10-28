#include <iostream> 
#include <ap_int.h> 
 
typedef ap_uint<8> fixed_int; 
typedef ap_uint<23> fixed_aux_anguloDireccion; 
typedef ap_uint<8> fixed_out; 
using namespace std; 
 
void fuzzifier (fixed_int posicion, fixed_int anguloCamion); 
void rulesEvaluation(); 
fixed_int defuzzifieranguloDireccion();
fixed_out fuzzyController(fixed_int posicion, fixed_int anguloCamion); 

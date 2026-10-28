#include <iostream> 
#include <ap_int.h> 
 
typedef ap_uint<8> fixed_int; 
typedef ap_uint<23> fixed_aux_anguloDireccion; 
typedef ap_uint<8> fixed_out; 
using namespace std; 
 
const fixed_int functionposicion0 = 255/(96 - 30); 
const fixed_int functionposicion11 = 255/(102 - 76); 
const fixed_int functionposicion12 = 255/(127 - 102); 
const fixed_int functionposicion21 = 255/(127 - 114); 
const fixed_int functionposicion22 = 255/(140 - 127); 
const fixed_int functionposicion31 = 255/(153 - 127); 
const fixed_int functionposicion32 = 255/(178 - 153); 
const fixed_int functionposicion4 = 255/(224 - 158); 

const fixed_int functionanguloCamion01 = 255/(31 - 0); 
const fixed_int functionanguloCamion02 = 255/(70 - 31); 
const fixed_int functionanguloCamion11 = 255/(82 - 58); 
const fixed_int functionanguloCamion12 = 255/(106 - 82); 
const fixed_int functionanguloCamion21 = 255/(110 - 93); 
const fixed_int functionanguloCamion22 = 255/(127 - 110); 
const fixed_int functionanguloCamion31 = 255/(127 - 119); 
const fixed_int functionanguloCamion32 = 255/(136 - 127); 
const fixed_int functionanguloCamion41 = 255/(144 - 127); 
const fixed_int functionanguloCamion42 = 255/(161 - 144); 
const fixed_int functionanguloCamion51 = 255/(172 - 148); 
const fixed_int functionanguloCamion52 = 255/(196 - 172); 
const fixed_int functionanguloCamion61 = 255/(223 - 184); 
const fixed_int functionanguloCamion62 = 255/(255 - 223); 

fixed_int posicionFuzz[5] = {0, 0, 0, 0, 0}; 
fixed_int anguloCamionFuzz[7] = {0, 0, 0, 0, 0, 0, 0}; 
fixed_int anguloDireccionMembershipValues[7] = {0, 0, 0, 0, 0, 0, 0}; 

 
void fuzzifier (fixed_int posicion, fixed_int anguloCamion) { 
 #pragma HLS PIPELINE 
 #pragma HLS ARRAY_PARTITION variable=posicionFuzz complete dim=1 
 #pragma HLS ARRAY_PARTITION variable=anguloCamionFuzz complete dim=1 

 //posicion------------------------------------

	if (posicion < 30) 
		posicionFuzz[0] = 255;
	else 
		if (posicion > 96) 
			posicionFuzz[0] = 0; 
		else 
			posicionFuzz[0] = functionposicion0 * (96 - posicion);


	if (posicion < 76 || posicion > 127) 
		posicionFuzz[1] = 0;
	else 
		if (posicion > 102) 
			posicionFuzz[1] = functionposicion11 * (posicion-76); 
		else 
			posicionFuzz[1] = functionposicion12 * (127 - posicion);


	if (posicion < 114 || posicion > 140) 
		posicionFuzz[2] = 0;
	else 
		if (posicion > 127) 
			posicionFuzz[2] = functionposicion21 * (posicion-114); 
		else 
			posicionFuzz[2] = functionposicion22 * (140 - posicion);


	if (posicion < 127 || posicion > 178) 
		posicionFuzz[3] = 0;
	else 
		if (posicion > 153) 
			posicionFuzz[3] = functionposicion31 * (posicion-127); 
		else 
			posicionFuzz[3] = functionposicion32 * (178 - posicion);


	if (posicion < 158) 
		posicionFuzz[4] = 0;
	else 
		if (posicion > 224) 
			posicionFuzz[4] = 255;
		else 
			posicionFuzz[4] = functionposicion4 * (posicion - 158);


 //anguloCamion------------------------------------

	if (anguloCamion < 0 || anguloCamion > 70) 
		anguloCamionFuzz[0] = 0;
	else 
		if (anguloCamion > 31) 
			anguloCamionFuzz[0] = functionanguloCamion01 * (anguloCamion-0); 
		else 
			anguloCamionFuzz[0] = functionanguloCamion02 * (70 - anguloCamion);


	if (anguloCamion < 58 || anguloCamion > 106) 
		anguloCamionFuzz[1] = 0;
	else 
		if (anguloCamion > 82) 
			anguloCamionFuzz[1] = functionanguloCamion11 * (anguloCamion-58); 
		else 
			anguloCamionFuzz[1] = functionanguloCamion12 * (106 - anguloCamion);


	if (anguloCamion < 93 || anguloCamion > 127) 
		anguloCamionFuzz[2] = 0;
	else 
		if (anguloCamion > 110) 
			anguloCamionFuzz[2] = functionanguloCamion21 * (anguloCamion-93); 
		else 
			anguloCamionFuzz[2] = functionanguloCamion22 * (127 - anguloCamion);


	if (anguloCamion < 119 || anguloCamion > 136) 
		anguloCamionFuzz[3] = 0;
	else 
		if (anguloCamion > 127) 
			anguloCamionFuzz[3] = functionanguloCamion31 * (anguloCamion-119); 
		else 
			anguloCamionFuzz[3] = functionanguloCamion32 * (136 - anguloCamion);


	if (anguloCamion < 127 || anguloCamion > 161) 
		anguloCamionFuzz[4] = 0;
	else 
		if (anguloCamion > 144) 
			anguloCamionFuzz[4] = functionanguloCamion41 * (anguloCamion-127); 
		else 
			anguloCamionFuzz[4] = functionanguloCamion42 * (161 - anguloCamion);


	if (anguloCamion < 148 || anguloCamion > 196) 
		anguloCamionFuzz[5] = 0;
	else 
		if (anguloCamion > 172) 
			anguloCamionFuzz[5] = functionanguloCamion51 * (anguloCamion-148); 
		else 
			anguloCamionFuzz[5] = functionanguloCamion52 * (196 - anguloCamion);


	if (anguloCamion < 184 || anguloCamion > 255) 
		anguloCamionFuzz[6] = 0;
	else 
		if (anguloCamion > 223) 
			anguloCamionFuzz[6] = functionanguloCamion61 * (anguloCamion-184); 
		else 
			anguloCamionFuzz[6] = functionanguloCamion62 * (255 - anguloCamion);


} 
 
void rulesEvaluation(){ 
 #pragma HLS PIPELINE 
 #pragma HLS ARRAY_PARTITION variable=posicionFuzz complete dim=1 
 #pragma HLS ARRAY_PARTITION variable=anguloCamionFuzz complete dim=1 
 #pragma HLS ARRAY_PARTITION variable=anguloDireccionMembershipValues complete dim=1 

	fixed_int aux = 0; 
	anguloDireccionMembershipValues[0] = min(posicionFuzz[0], anguloCamionFuzz[4]); 

	aux = min(posicionFuzz[0], anguloCamionFuzz[5]); 
	if (anguloDireccionMembershipValues[0] < aux) 
		 anguloDireccionMembershipValues[0] = aux; 

	aux = min(posicionFuzz[1], anguloCamionFuzz[5]); 
	if (anguloDireccionMembershipValues[0] < aux) 
		 anguloDireccionMembershipValues[0] = aux; 

	aux = min(posicionFuzz[0], anguloCamionFuzz[6]); 
	if (anguloDireccionMembershipValues[0] < aux) 
		 anguloDireccionMembershipValues[0] = aux; 

	aux = min(posicionFuzz[1], anguloCamionFuzz[6]); 
	if (anguloDireccionMembershipValues[0] < aux) 
		 anguloDireccionMembershipValues[0] = aux; 

	anguloDireccionMembershipValues[1] = min(posicionFuzz[0], anguloCamionFuzz[2]); 

	aux = min(posicionFuzz[0], anguloCamionFuzz[3]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	aux = min(posicionFuzz[1], anguloCamionFuzz[3]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	aux = min(posicionFuzz[1], anguloCamionFuzz[4]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	aux = min(posicionFuzz[2], anguloCamionFuzz[5]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	aux = min(posicionFuzz[2], anguloCamionFuzz[6]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[6]); 
	if (anguloDireccionMembershipValues[1] < aux) 
		 anguloDireccionMembershipValues[1] = aux; 

	anguloDireccionMembershipValues[2] = min(posicionFuzz[0], anguloCamionFuzz[1]); 

	aux = min(posicionFuzz[1], anguloCamionFuzz[2]); 
	if (anguloDireccionMembershipValues[2] < aux) 
		 anguloDireccionMembershipValues[2] = aux; 

	aux = min(posicionFuzz[2], anguloCamionFuzz[4]); 
	if (anguloDireccionMembershipValues[2] < aux) 
		 anguloDireccionMembershipValues[2] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[5]); 
	if (anguloDireccionMembershipValues[2] < aux) 
		 anguloDireccionMembershipValues[2] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[6]); 
	if (anguloDireccionMembershipValues[2] < aux) 
		 anguloDireccionMembershipValues[2] = aux; 

	anguloDireccionMembershipValues[3] = min(posicionFuzz[2], anguloCamionFuzz[3]); 

	anguloDireccionMembershipValues[4] = min(posicionFuzz[0], anguloCamionFuzz[0]); 

	aux = min(posicionFuzz[1], anguloCamionFuzz[1]); 
	if (anguloDireccionMembershipValues[4] < aux) 
		 anguloDireccionMembershipValues[4] = aux; 

	aux = min(posicionFuzz[2], anguloCamionFuzz[2]); 
	if (anguloDireccionMembershipValues[4] < aux) 
		 anguloDireccionMembershipValues[4] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[4]); 
	if (anguloDireccionMembershipValues[4] < aux) 
		 anguloDireccionMembershipValues[4] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[5]); 
	if (anguloDireccionMembershipValues[4] < aux) 
		 anguloDireccionMembershipValues[4] = aux; 

	anguloDireccionMembershipValues[5] = min(posicionFuzz[1], anguloCamionFuzz[0]); 

	aux = min(posicionFuzz[2], anguloCamionFuzz[0]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	aux = min(posicionFuzz[2], anguloCamionFuzz[1]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[2]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[3]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[3]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[4]); 
	if (anguloDireccionMembershipValues[5] < aux) 
		 anguloDireccionMembershipValues[5] = aux; 

	anguloDireccionMembershipValues[6] = min(posicionFuzz[3], anguloCamionFuzz[0]); 

	aux = min(posicionFuzz[4], anguloCamionFuzz[0]); 
	if (anguloDireccionMembershipValues[6] < aux) 
		 anguloDireccionMembershipValues[6] = aux; 

	aux = min(posicionFuzz[3], anguloCamionFuzz[1]); 
	if (anguloDireccionMembershipValues[6] < aux) 
		 anguloDireccionMembershipValues[6] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[1]); 
	if (anguloDireccionMembershipValues[6] < aux) 
		 anguloDireccionMembershipValues[6] = aux; 

	aux = min(posicionFuzz[4], anguloCamionFuzz[2]); 
	if (anguloDireccionMembershipValues[6] < aux) 
		 anguloDireccionMembershipValues[6] = aux; 

} 

const fixed_int outputValuesanguloDireccion[7] = {29, 63, 99, 127, 155, 191, 225}; 
fixed_int defuzzifieranguloDireccion() {
 #pragma HLS PIPELINE 
 #pragma HLS ARRAY_PARTITION variable=anguloDireccionMembershipValues complete dim=1 
 #pragma HLS ARRAY_PARTITION variable=outputValuesanguloDireccion complete dim=1 

	fixed_aux_anguloDireccion numerator = 0; 
 	fixed_aux_anguloDireccion denominator = 0; 
 	for(int i=0; i<7; i++){ 
		numerator = numerator + anguloDireccionMembershipValues[i] * outputValuesanguloDireccion[i]; 
		denominator = denominator + anguloDireccionMembershipValues[i]; 
	} 
	return numerator/denominator; 
}

fixed_out fuzzyController(fixed_int posicion, fixed_int anguloCamion){ 
 #pragma HLS DATAFLOW 

	fuzzifier(posicion, anguloCamion);
	rulesEvaluation(); 

	fixed_out output0 = defuzzifieranguloDireccion();
	return output0;
}

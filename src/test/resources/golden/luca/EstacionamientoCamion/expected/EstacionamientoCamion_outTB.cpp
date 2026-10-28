#include <iostream> 
#include "EstacionamientoCamion_out.h" 
#include <ap_int.h> 

typedef ap_uint<8> fixed_int;
typedef ap_uint<18> fixed_aux;


using namespace std;

int main(int argc, char **argv){ 

	fixed_int posicion = 127; 
	fixed_int anguloCamion = 127; 

	cout <<  fuzzyController(posicion, anguloCamion); 

	return 0;
}
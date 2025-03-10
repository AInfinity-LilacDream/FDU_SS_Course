#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>

uint64_t s, myexp, frac;

union {
	float f;
	unsigned int i;
} fn, in;

union {
	double d;
	uint64_t i;
} dn;

float genFloat(uint64_t s, uint64_t exp, uint64_t frac) {
	fn.i = (s << 31) | (exp << 23) | frac;
	return fn.f;
}

double genDouble(uint64_t s, uint64_t exp, uint64_t frac) {
	frac = frac << (52 - 23); // change from float to double
	dn.i = (s << 63) | (exp << 52) | frac;
	return dn.d;
}

void printFloat(float x) {
	unsigned int* intVal = (unsigned int*)&x;
	for (int i = 31; i >= 0; --i) {
		printf("%d", ((*intVal >> i) & 1));
		if (i == 31 || i == 23) printf(" ");
	}
	printf("\n");
}

void printDouble(double x) {
	unsigned long long* intVal = (unsigned long long*)&x;
	for (int i = 63; i >= 0; --i) {
		printf("%d", ((*intVal >> i) & 1));
		if (i == 63 || i == 52) printf(" ");
	}
	printf("\n");
}

int main() {
	float value;
	scanf("%f", &value);

    in.f = value;

    s = (in.i >> 31) & 0x1;
    myexp = (in.i >> 23) & 0xFF;
    frac = in.i & 0x7FFFFF;

	float fans = genFloat(s, myexp, frac);
	printf("float number: %f\n", fans);
	printFloat(fans);

	double dans = genDouble(s, myexp - 127 + 1023, frac); // change from float to double
	printf("double number: %lf\n", dans);
	printDouble(dans);

	// +∞
	fans = genFloat(0, 0xFF, 0); // s = 0, exp = 0xFF (all 1), frac = 0
    printf("Positive infinity: %f\n", fans);
    printFloat(fans);

    // NaN
    fans = genFloat(0, 0xFF, 0x7FFFFF); // s = 0, exp = 0xFF (all 1), frac = 0x7FFFFF (max fraction)
    printf("NaN: %f\n", fans);
    printFloat(fans);
	return 0;
}
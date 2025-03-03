#include <stdio.h>

signed int a = -6;
unsigned int b = 10;

void printSignedInt(signed int x) {
	printf("%d ", x);
	int t = 1;
	while (t < -x) t *= 2;
	int tmp = t + x;
	t /= 2;
	printf("1");
	while (t) {
		printf("%d", t <= tmp ? 1 : 0);
		tmp -= t <= tmp ? t : 0;
		t /= 2;
	}
	printf("\n");
}

void printUnsignedInt(unsigned int x) {
	printf("%u ", x);
	int t = 1;
	while (t <= x) t *= 2;
	t /= 2;
	while (t) {
		printf("%d", t <= x ? 1 : 0);
		x -= t <= x ? t : 0;
		t /= 2;
	}
	printf("\n");
}

int main() {
	printSignedInt(a);
	printUnsignedInt(b);
	printf("signed int -6 to unsigned int: %u\n", (unsigned int)a);
	return 0;
}
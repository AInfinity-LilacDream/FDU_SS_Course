#include <stdio.h>

int main() {
	int a = 1000000;
	int b = 1000000;
	int result = a * b;
	printf("%d: overflow\n", result);

	result = 2000000000 + 2000000000;
	printf("%d: overflow\n", result);

	int c = 1000000;
	int d = -1000000;
	int result2 = c * d;
	printf("%d: underflow\n", result2);

	result2 = -2000000000 - 2000000000;
	printf("%d: underflow\n", result2);

	return 0;
}
#include <stdio.h>

short a[100] = {0x0f0f};

int main() {
	short *x = a + 1000;
	printf("%p\n", x);
	printf("%hd\n", *x);
	return 0;
}

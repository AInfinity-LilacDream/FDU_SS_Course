#include <stdio.h>

int a = 0b100;

int main() {
	printf("%d\n", a & 0b111); // expected 4 (100)
	printf("%d\n", a | 0b111); // expected 7 (111)
	printf("%d\n", ~0x41); // expected -66
	printf("%d\n", 0b1011 ^ 0b0111); // expected 12 (1100)
	return 0;
}

// & 按位与
// | 按位或
// ~ 按位取反
// ^ 按位异或
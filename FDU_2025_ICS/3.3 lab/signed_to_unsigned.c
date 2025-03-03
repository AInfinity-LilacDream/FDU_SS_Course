#include <stdio.h>

signed int a = -6;
unsigned int b = 10;

void printBinary(int num, int bits) {
    for (int i = bits - 1; i >= 0; i--) {
        // 检查第 i 位是否为 1
        if (num & (1 << i)) {
            printf("1");
        } else {
            printf("0");
        }
    }
}

void printSignedInt(signed int x) {
	printf("%d ", x);
	printBinary(x, 4);
	printf("\n");
}

void printUnsignedInt(unsigned int x) {
	printf("%u ", x);
	printBinary(x, 4);
	printf("\n");
}

int main() {
	printSignedInt(a);
	printUnsignedInt(b);
	printf("signed int -6 to unsigned int: %u\n", (unsigned int)a);
	return 0;
}
#include <stdio.h>

char array_store(unsigned int index, char val) {
	char arr[10];
	char ch = arr[index];
	arr[index] = val;
	return ch;
}

int main() {
	for (int i = 1; i <= 3; ++i) {
		for (int j = 0; j < 10; ++j) {
			char ret = array_store(j, '0' + j);
			printf("%c\n", ret);
		}
	}
	return 0;
}

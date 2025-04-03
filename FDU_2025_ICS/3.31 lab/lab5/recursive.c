#include <stdio.h>

void test(int i) {
	if (i > 10) return;
	
	int a[11];
	a[i] = i;
	for (int j = 0; j < 11; ++j)
		printf("%d ", a[j]);
	printf("\n");
	test(i + 1);
}

int main() {
	test(0);
	return 0;
}

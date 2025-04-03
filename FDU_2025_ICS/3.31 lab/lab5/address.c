#include <stdio.h>

long a[10], b[10][100];

int main() {
	for (int i = 0; i < 10; ++i) a[i] = i;
	for (int i = 0; i < 10; ++i)
		for (int j = 0; j < 100; ++j) b[i][j] = i * 100 + j;
	printf("%lu\n", sizeof(long)); 
	printf("%p %p %ld %ld", (a + 1), (b + 1), (a + 1) - a, (b + 1) - b);
	printf("%p %p %ld %ld", (a + 2), (b + 2), (a + 2) - a, (b + 2) - b);
	printf("%p %p %ld %ld", (a + 12), (b + 12), (a + 12) - a, (b + 12) - b);
	return 0;
}

#include <stdio.h>

#define N 1024

int main() {
	long a[N][N];
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < N; ++j) {
			a[i][j] = i * N + j;
		}
	}
	printf("%ld\n", a[N - 1][N - 1]);
	return 0;
}

#include <stdio.h>
#define N 1024

// 使用for循环实现累加
int sum_for(int a[N][N]) {
	int sum = 0;
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			sum += a[i][j];
		}
	}
	return sum;
}

// 使用while循环实现累加
int sum_while(int a[N][N]) {
	int sum = 0, i = 0, j;
	while (i < N) {
		j = 0;
		while (j < N) {
			sum += a[i][j];
			j++;
		}
		i++;
	}
	return sum;
}

int main() {
	int a[N][N];
	// 初始化数组
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < N; ++j) {
			a[i][j] = 1;
		}
	}
	printf("Sum (for): %d\n",sum_for(a));
	printf("Sum (while): %d\n", sum_while(a));
	return 0;
}
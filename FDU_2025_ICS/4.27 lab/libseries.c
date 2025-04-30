// libseries.c
#include <stdio.h>
#include <stdint.h>

int64_t linear_sum(int n) {
	return n * (n + 1) / 2;
}

int64_t square_sum(int n) {
	return n * (n + 1) * (2 * n + 1) / 6;
}

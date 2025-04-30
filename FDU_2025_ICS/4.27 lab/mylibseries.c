#ifdef RUNTIME
#define _GNU_SOURCE

#include <stdio.h>
#include <time.h>
#include <stdint.h>
#include <dlfcn.h>
#include <stdlib.h>

// linear_sum wrapper function
int64_t linear_sum(int n) {
	int64_t (*default_linear_sum)(int) = NULL;
	void *handle;
	char *error;

	// open .so
	handle = dlopen("libseries.so", RTLD_LAZY);
	if (!handle) {
		fprintf(stderr, "%s\n", dlerror());
		exit(EXIT_FAILURE);
	}

	dlerror();

	default_linear_sum = dlsym(handle, "linear_sum");

	if ((error = dlerror()) != NULL) {
		fprintf(stderr, "%s\n", error);
		exit(EXIT_FAILURE);
	}

	// clock
	struct timespec start, end;
	clock_gettime(CLOCK_MONOTONIC, &start);

	int64_t res = default_linear_sum(n);

	clock_gettime(CLOCK_MONOTONIC, &end);

	long long elapsed_ns = (end.tv_sec - start.tv_sec) * 1000000000LL + (end.tv_nsec - start.tv_nsec);

	printf("linear_sum used time: %lld nanoseconds\n", elapsed_ns);

	return res;
}

// square_sum wrapper function
int64_t square_sum(int n) {
	int64_t (*default_square_sum)(int) = NULL;
	void *handle;
	char *error;

	// open .so
	handle = dlopen("libseries.so", RTLD_LAZY);
	if (!handle) {
		fprintf(stderr, "%s\n", dlerror());
		exit(EXIT_FAILURE);
	}

	dlerror();

	default_square_sum = dlsym(handle, "square_sum");

	if ((error = dlerror()) != NULL) {
		fprintf(stderr, "%s\n", error);
		exit(EXIT_FAILURE);
	}

	// clock
	struct timespec start, end;
	clock_gettime(CLOCK_MONOTONIC, &start);

	int64_t res = default_square_sum(n);

	clock_gettime(CLOCK_MONOTONIC, &end);

	long long elapsed_ns = (end.tv_sec - start.tv_sec) * 1000000000LL + (end.tv_nsec - start.tv_nsec);

	printf("square_sum used time: %lld nanoseconds\n", elapsed_ns);

	return res;
}
#endif

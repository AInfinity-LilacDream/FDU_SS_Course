// 24302010004 俞楚凡
#include "cachelab.h"

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <limits.h>
#include <math.h>

struct cache_line {
	int valid;
	int tag;
	int timestamp;
} cache[999][999];

int s, e, b, S, E, B;
int missCnt, hitCnt, evictionCnt, idx;

char *filePath;

int mypow(int x) {
	int ans = 1;
	for (int i = 1; i <= x; ++i) ans *= 2;
	return ans;
}

void writeToCache(int ops, int optag, int validpos) {
	if (validpos != -1) {
		cache[ops][validpos] = (struct cache_line) {1, optag, idx};
		return;
	}

	int mnts = INT_MAX, pos = -1;
	for (int i = 0; i < E; ++i) {
		if (cache[ops][i].timestamp < mnts) {
			mnts = cache[ops][i].timestamp;
			pos = i;
		}
	}

	evictionCnt++;
	cache[ops][pos] = (struct cache_line) {1, optag, idx};
}

void readFromCache(int ops, int optag) {
	int validpos = -1;
	for (int i = 0; i < E; ++i) {
		if (cache[ops][i].tag == optag && cache[ops][i].valid) {
			hitCnt++;
			cache[ops][i].timestamp = idx;
			return;
		}
		if (!cache[ops][i].valid && validpos == -1) validpos = i;
	}

	// miss
	missCnt++;
	writeToCache(ops, optag, validpos);
}

int main(int argc, char **argv) {
	int opt;

	// parse arguments
	while (-1 != (opt = getopt(argc, argv, "s:E:b:t:"))) {
		switch (opt) {
			case 's':
				s = atoi(optarg);
				S = mypow(s);
				break;
			case 'E':
				E = atoi(optarg);
				break;
			case 'b':
				b = atoi(optarg);
				B = mypow(b);
				break;
			case 't':
				filePath = optarg;
				break;
			default:
				printf("wrong argument\n");
				break;
		}
	}

	// initialize
	for (int i = 0; i < S; ++i) {
		for (int j = 0; j < E; ++j) {
			cache[i][j] = (struct cache_line) {0, -1, 0};
		}
	}


	// read from file
	FILE * pFile;
	pFile = fopen(filePath, "r");

	char identifier;
	unsigned address;
	int size;

	while (fscanf(pFile, "%c %x, %d", &identifier, &address, &size) > 0) {
		idx++;
		int optag = address >> (s + b);
		int ops = (address >> b) & ((unsigned)(-1) >> (8 * sizeof(unsigned) - s));

		switch (identifier) {
			case 'M':
				readFromCache(ops, optag);
				readFromCache(ops, optag);
				break;
			case 'L':
				readFromCache(ops, optag);
				break;
			case 'S':
				readFromCache(ops, optag);
				break;
		}
	}

	fclose(pFile);

	printSummary(hitCnt, missCnt, evictionCnt);
	return 0;
}

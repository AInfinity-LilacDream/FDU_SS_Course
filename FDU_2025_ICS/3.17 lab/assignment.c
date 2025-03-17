#include <stdio.h>

int i;
short s;
char c;
long l;

int* ip;
short* sp;
char* cp;
long* lp;

int array[9] = {};

int main() {
	i = 1;
	s = 2;
	c = 'a';
	l = 3;

	ip = &i;
	sp = &s;
	cp = &c;
	lp = &l;

	for (int i = 1; i <= 8; ++i) array[i] = i;
	printf("%d\n", array[1]);
	for (int i = 1; i <= 8; ++i) printf("%d ", array[i]);
	printf("\n");
	for (int i = 8; i >= 1; --i) printf("%d ", *(array + i));
	return 0;
}
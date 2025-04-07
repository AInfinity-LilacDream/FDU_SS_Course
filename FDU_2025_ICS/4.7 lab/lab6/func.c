#include <stdio.h>

int myfunc(unsigned int len) {
	int ans = 0;
	for (int i = 1; i <= len; ++i) {
		ans += i * i;
	}
	return ans;	
}

int main() {
	int (*pfun)(unsigned int);
	pfun = myfunc;
	printf("%d\n", pfun(10));
	printf("%d\n", (*pfun)(10));

	pfun = &myfunc;
	printf("%d\n", pfun(10));
	printf("%d\n", (*pfun)(10));
	return 0;
}

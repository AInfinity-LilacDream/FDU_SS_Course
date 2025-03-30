#include <stdio.h>
int main() {
	int x = 10;
	int res = 0;
	printf("please input x(ranges from 100 to 110):\n");
	scanf("%d", &x);
	switch(x) {
	case 100:
		res = 1;
		break;
	case 102:
		res = 2;
		break;
	case 103:
		res = 3;
	case 104:
		res += 4;
	case 105:
		res += 5;
		break;
	case 106:
		res = 6;
		break;
	case 108:
	case 107:
		res = 7;
		break;
	default:
		res = 10;
	}
	printf("the res is %d", res);
	return 0;
}

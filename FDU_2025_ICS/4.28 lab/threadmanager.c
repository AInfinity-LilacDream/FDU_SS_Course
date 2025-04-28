#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <time.h>
#include <sys/wait.h>

int main(){
	pid_t pidList[4];
	for (int i = 1; i <= 3; ++i) {
		pidList[i] = fork();
		if (pidList[i] == 0) {
			srand(time(0));
			unsigned int slt = rand() % 9 + 1;
			sleep(slt);
			exit(slt);
		}
		else if (pidList[i] < 0) {
			printf("fork failed");
			exit(1);
		}
		else {
			int status;
			printf("the pid for the child process is: %d\n", pidList[i]);
			waitpid(pidList[i], &status, 0);
			if (WIFEXITED(status)) {
				printf("sleep for: %ds\n", WEXITSTATUS(status));
			}
		}
	}
	return 0;
}

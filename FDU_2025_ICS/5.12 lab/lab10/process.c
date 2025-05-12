#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <setjmp.h>

int ccount;
jmp_buf b;
int flag;

void childHandler(int sig) {
	pid_t pid;
	while ((pid = wait(NULL)) > 0) {
		ccount--;
	}
}

void usr1Handler(int sig) {
	longjmp(b, 2);
}

void usr2Handler(int sig) {
	flag = 0;
}

int main() {
	char input[19];
	flag = 1;
	
	signal(SIGCHLD, childHandler);
	signal(SIGUSR1, usr1Handler);
	signal(SIGUSR2, usr2Handler);

	setjmp(b);

	fgets(input, sizeof(input), stdin);
	input[strcspn(input, "\n")] = 0; // remove \n

	while (flag) {
		ccount = rand() % 3 + 1;

		for (int i = 1; i <= ccount; ++i) {
			pid_t pid = fork();
			if (pid == -1) {
				perror("fork failed");
				return EXIT_FAILURE;
			}
			else if (pid == 0) {
				sleep(1);
				printf("%s\n", input);
				exit(EXIT_SUCCESS);
			}
		}

		sleep(1);
		printf("Processing...\n");

		while (ccount);

		printf("Process finished.\n\n");
	}	

	printf("Exiting!\n");
	return 0;
}
	

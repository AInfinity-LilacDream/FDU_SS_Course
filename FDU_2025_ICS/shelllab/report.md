# ICS 24302010004 俞楚凡 shell lab

lab要求我们实现一个简单的linux shell。tsh中要求支持quit, jobs, bg, fg以及运行指定位置的程序五个指令。现按trace的顺序依次复盘思路。

trace01默认通过。

trace02实现quit。在eval中判断quit直接退出即可。

trace03实现运行前台程序，trace04实现运行后台程序。在parseline中返回了bg变量指示是否是前台程序还是后台程序。对于前台程序，我们应该使用waitfg函数进行等待执行完毕。对于后台程序我们直接返回即可。这里waitfg函数用一个死循环阻塞程序运行。需要注意的是可能存在创建进程之后还没来得及添加到joblist子进程就结束了的情况，这需要我们阻塞SIGCHLD信号的返回，并且在Addjob执行完之后在子进程和父进程分别取消阻塞。

trace05实现jobs功能，只需要调用系统内置的listjobs函数即可。

trace06, 07, 08分别在测试信号处理。对于SIGINT和SIGSTOP，我们分别在收到信号后转发信号给子进程所在的进程组，随后通过SIGCHLD接受子进程的行为，最后打印提示信息即可。

trace09, 10分别测试了fg和bg。在do_bgfg中，我们首先解析后面的pid或jid参数，随后给作业所在的进程组发送SIGCONT信号恢复运行。同样的，如果是前台任务就waitfg，如果是后台任务就不等待直接返回。

trace11-13无需实现额外功能。

trace14是错误处理。检测参数缺漏等一些情况打印相应的提示即可。

trace15-16无需实现额外功能。
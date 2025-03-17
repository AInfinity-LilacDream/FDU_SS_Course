	.file	"assignment.c"
	.text
	.comm	i, 4, 2
	.comm	s, 2, 1
	.comm	c, 1, 0
	.comm	l, 4, 2
	.comm	ip, 8, 3
	.comm	sp, 8, 3
	.comm	cp, 8, 3
	.comm	lp, 8, 3
	.globl	array
	.bss
	.align 32
array:
	.space 36
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "%d\12\0"
.LC1:
	.ascii "%d \0"
	.text
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$48, %rsp
	.seh_stackalloc	48
	.seh_endprologue
	call	__main
	leaq	i(%rip), %rax
	movl	$1, (%rax)
	leaq	s(%rip), %rax
	movw	$2, (%rax)
	leaq	c(%rip), %rax
	movb	$97, (%rax)
	leaq	l(%rip), %rax
	movl	$3, (%rax)
	leaq	ip(%rip), %rax
	leaq	i(%rip), %rdx
	movq	%rdx, (%rax)
	leaq	sp(%rip), %rax
	leaq	s(%rip), %rdx
	movq	%rdx, (%rax)
	leaq	cp(%rip), %rax
	leaq	c(%rip), %rdx
	movq	%rdx, (%rax)
	leaq	lp(%rip), %rax
	leaq	l(%rip), %rdx
	movq	%rdx, (%rax)
	movl	$1, -4(%rbp)
	jmp	.L2
.L3:
	movl	-4(%rbp), %eax
	cltq
	leaq	0(,%rax,4), %rdx
	leaq	array(%rip), %rax
	movl	-4(%rbp), %ecx
	movl	%ecx, (%rdx,%rax)
	addl	$1, -4(%rbp)
.L2:
	cmpl	$8, -4(%rbp)
	jle	.L3
	movl	4+array(%rip), %eax
	movl	%eax, %edx
	leaq	.LC0(%rip), %rcx
	call	printf
	movl	$1, -8(%rbp)
	jmp	.L4
.L5:
	movl	-8(%rbp), %eax
	cltq
	leaq	0(,%rax,4), %rdx
	leaq	array(%rip), %rax
	movl	(%rdx,%rax), %eax
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	addl	$1, -8(%rbp)
.L4:
	cmpl	$8, -8(%rbp)
	jle	.L5
	movl	$10, %ecx
	call	putchar
	movl	$8, -12(%rbp)
	jmp	.L6
.L7:
	movl	-12(%rbp), %eax
	cltq
	leaq	0(,%rax,4), %rdx
	leaq	array(%rip), %rax
	addq	%rdx, %rax
	movl	(%rax), %eax
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	subl	$1, -12(%rbp)
.L6:
	cmpl	$0, -12(%rbp)
	jg	.L7
	movl	$0, %eax
	addq	$48, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef
	.def	putchar;	.scl	2;	.type	32;	.endef

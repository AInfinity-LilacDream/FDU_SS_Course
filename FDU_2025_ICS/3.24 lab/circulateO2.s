	.file	"circulate.c"
	.text
	.p2align 4,,15
	.globl	sum_for
	.def	sum_for;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_for
sum_for:
	.seh_endprologue
	xorl	%eax, %eax
	leaq	4096(%rcx), %r8
	addq	$4198400, %rcx
.L2:
	leaq	-4096(%r8), %rdx
	.p2align 4,,10
.L3:
	addl	(%rdx), %eax
	addq	$4, %rdx
	cmpq	%r8, %rdx
	jne	.L3
	leaq	4096(%rdx), %r8
	cmpq	%rcx, %r8
	jne	.L2
	ret
	.seh_endproc
	.p2align 4,,15
	.globl	sum_while
	.def	sum_while;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_while
sum_while:
	.seh_endprologue
	jmp	sum_for
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "Sum (for): %d\12\0"
.LC1:
	.ascii "Sum (while): %d\12\0"
	.section	.text.startup,"x"
	.p2align 4,,15
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
	pushq	%rsi
	.seh_pushreg	%rsi
	movl	$4194344, %eax
	pushq	%rbx
	.seh_pushreg	%rbx
	call	___chkstk_ms
	subq	%rax, %rsp
	.seh_stackalloc	4194344
	.seh_endprologue
	call	__main
	leaq	4128(%rsp), %rbx
	leaq	4198432(%rsp), %rsi
	movq	%rbx, %rdx
.L9:
	leaq	-4096(%rdx), %rax
	.p2align 4,,10
.L10:
	movl	$1, (%rax)
	addq	$4, %rax
	cmpq	%rdx, %rax
	jne	.L10
	leaq	4096(%rax), %rdx
	cmpq	%rsi, %rdx
	jne	.L9
	movq	%rbx, %rcx
	xorl	%edx, %edx
.L11:
	leaq	-4096(%rcx), %rax
	.p2align 4,,10
.L12:
	addl	(%rax), %edx
	addq	$4, %rax
	cmpq	%rcx, %rax
	jne	.L12
	leaq	4096(%rax), %rcx
	cmpq	%rsi, %rcx
	jne	.L11
	leaq	.LC0(%rip), %rcx
	call	printf
	xorl	%edx, %edx
.L14:
	leaq	-4096(%rbx), %rax
	.p2align 4,,10
.L15:
	addl	(%rax), %edx
	addq	$4, %rax
	cmpq	%rax, %rbx
	jne	.L15
	addq	$4096, %rbx
	cmpq	%rsi, %rbx
	jne	.L14
	leaq	.LC1(%rip), %rcx
	call	printf
	xorl	%eax, %eax
	addq	$4194344, %rsp
	popq	%rbx
	popq	%rsi
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef

	.file	"circulate.c"
	.text
	.globl	sum_for
	.def	sum_for;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_for
sum_for:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movq	%rcx, 16(%rbp)
	movl	$0, -4(%rbp)
	movl	$0, -8(%rbp)
	jmp	.L2
.L5:
	movl	$0, -12(%rbp)
	jmp	.L3
.L4:
	movl	-8(%rbp), %eax
	cltq
	salq	$12, %rax
	movq	%rax, %rdx
	movq	16(%rbp), %rax
	addq	%rax, %rdx
	movl	-12(%rbp), %eax
	cltq
	movl	(%rdx,%rax,4), %eax
	addl	%eax, -4(%rbp)
	addl	$1, -12(%rbp)
.L3:
	cmpl	$1023, -12(%rbp)
	jle	.L4
	addl	$1, -8(%rbp)
.L2:
	cmpl	$1023, -8(%rbp)
	jle	.L5
	movl	-4(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.globl	sum_while
	.def	sum_while;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_while
sum_while:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movq	%rcx, 16(%rbp)
	movl	$0, -4(%rbp)
	movl	$0, -8(%rbp)
	jmp	.L8
.L11:
	movl	$0, -12(%rbp)
	jmp	.L9
.L10:
	movl	-8(%rbp), %eax
	cltq
	salq	$12, %rax
	movq	%rax, %rdx
	movq	16(%rbp), %rax
	addq	%rax, %rdx
	movl	-12(%rbp), %eax
	cltq
	movl	(%rdx,%rax,4), %eax
	addl	%eax, -4(%rbp)
	addl	$1, -12(%rbp)
.L9:
	cmpl	$1023, -12(%rbp)
	jle	.L10
	addl	$1, -8(%rbp)
.L8:
	cmpl	$1023, -8(%rbp)
	jle	.L11
	movl	-4(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "Sum (for): %d\12\0"
.LC1:
	.ascii "Sum (while): %d\12\0"
	.text
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
	pushq	%rbp
	.seh_pushreg	%rbp
	movl	$4194352, %eax
	call	___chkstk_ms
	subq	%rax, %rsp
	.seh_stackalloc	4194352
	leaq	128(%rsp), %rbp
	.seh_setframe	%rbp, 128
	.seh_endprologue
	call	__main
	movl	$0, 4194220(%rbp)
	jmp	.L14
.L17:
	movl	$0, 4194216(%rbp)
	jmp	.L15
.L16:
	movl	4194216(%rbp), %eax
	cltq
	movl	4194220(%rbp), %edx
	movslq	%edx, %rdx
	salq	$10, %rdx
	addq	%rdx, %rax
	movl	$1, -96(%rbp,%rax,4)
	addl	$1, 4194216(%rbp)
.L15:
	cmpl	$1023, 4194216(%rbp)
	jle	.L16
	addl	$1, 4194220(%rbp)
.L14:
	cmpl	$1023, 4194220(%rbp)
	jle	.L17
	leaq	-96(%rbp), %rax
	movq	%rax, %rcx
	call	sum_for
	movl	%eax, %edx
	leaq	.LC0(%rip), %rcx
	call	printf
	leaq	-96(%rbp), %rax
	movq	%rax, %rcx
	call	sum_while
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$4194352, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef

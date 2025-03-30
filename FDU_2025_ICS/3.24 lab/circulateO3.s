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
	pxor	%xmm0, %xmm0
	.p2align 4,,10
.L3:
	movdqu	(%rdx), %xmm2
	addq	$16, %rdx
	cmpq	%rdx, %r8
	paddd	%xmm2, %xmm0
	jne	.L3
	movdqa	%xmm0, %xmm1
	addq	$4096, %r8
	psrldq	$8, %xmm1
	paddd	%xmm1, %xmm0
	movdqa	%xmm0, %xmm1
	psrldq	$4, %xmm1
	paddd	%xmm1, %xmm0
	movd	%xmm0, %edx
	addl	%edx, %eax
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
	xorl	%eax, %eax
	leaq	4096(%rcx), %r8
	addq	$4198400, %rcx
.L8:
	leaq	-4096(%r8), %rdx
	pxor	%xmm0, %xmm0
	.p2align 4,,10
.L9:
	movdqu	(%rdx), %xmm2
	addq	$16, %rdx
	cmpq	%rdx, %r8
	paddd	%xmm2, %xmm0
	jne	.L9
	movdqa	%xmm0, %xmm1
	addq	$4096, %r8
	psrldq	$8, %xmm1
	paddd	%xmm1, %xmm0
	movdqa	%xmm0, %xmm1
	psrldq	$4, %xmm1
	paddd	%xmm1, %xmm0
	movd	%xmm0, %edx
	addl	%edx, %eax
	cmpq	%rcx, %r8
	jne	.L8
	ret
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC1:
	.ascii "Sum (for): %d\12\0"
.LC2:
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
	movdqa	.LC0(%rip), %xmm0
	leaq	4198432(%rsp), %rsi
	movq	%rbx, %rdx
.L13:
	leaq	-4096(%rdx), %rax
	.p2align 4,,10
.L14:
	movaps	%xmm0, (%rax)
	addq	$16, %rax
	cmpq	%rdx, %rax
	jne	.L14
	leaq	4096(%rax), %rdx
	cmpq	%rsi, %rdx
	jne	.L13
	movq	%rbx, %rcx
	xorl	%edx, %edx
.L15:
	leaq	-4096(%rcx), %rax
	pxor	%xmm0, %xmm0
	.p2align 4,,10
.L16:
	paddd	(%rax), %xmm0
	addq	$16, %rax
	cmpq	%rax, %rcx
	jne	.L16
	movdqa	%xmm0, %xmm1
	addq	$4096, %rcx
	psrldq	$8, %xmm1
	paddd	%xmm1, %xmm0
	movdqa	%xmm0, %xmm1
	psrldq	$4, %xmm1
	paddd	%xmm1, %xmm0
	movd	%xmm0, %eax
	addl	%eax, %edx
	cmpq	%rsi, %rcx
	jne	.L15
	leaq	.LC1(%rip), %rcx
	call	printf
	xorl	%edx, %edx
.L18:
	leaq	-4096(%rbx), %rax
	pxor	%xmm0, %xmm0
	.p2align 4,,10
.L19:
	paddd	(%rax), %xmm0
	addq	$16, %rax
	cmpq	%rbx, %rax
	jne	.L19
	leaq	4096(%rax), %rbx
	movdqa	%xmm0, %xmm1
	psrldq	$8, %xmm1
	paddd	%xmm1, %xmm0
	movdqa	%xmm0, %xmm1
	psrldq	$4, %xmm1
	paddd	%xmm1, %xmm0
	movd	%xmm0, %ecx
	addl	%ecx, %edx
	cmpq	%rsi, %rbx
	jne	.L18
	leaq	.LC2(%rip), %rcx
	call	printf
	xorl	%eax, %eax
	addq	$4194344, %rsp
	popq	%rbx
	popq	%rsi
	ret
	.seh_endproc
	.section .rdata,"dr"
	.align 16
.LC0:
	.long	1
	.long	1
	.long	1
	.long	1
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef

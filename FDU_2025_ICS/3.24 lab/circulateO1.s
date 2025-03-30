	.file	"circulate.c"
	.text
	.globl	sum_for
	.def	sum_for;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_for
sum_for:
	.seh_endprologue
	leaq	4096(%rcx), %r8
	addq	$4198400, %rcx
	movl	$0, %eax
	jmp	.L2
.L6:
	addq	$4096, %r8
	cmpq	%rcx, %r8
	je	.L1
.L2:
	leaq	-4096(%r8), %rdx
.L3:
	addl	(%rdx), %eax
	addq	$4, %rdx
	cmpq	%r8, %rdx
	jne	.L3
	jmp	.L6
.L1:
	ret
	.seh_endproc
	.globl	sum_while
	.def	sum_while;	.scl	2;	.type	32;	.endef
	.seh_proc	sum_while
sum_while:
	.seh_endprologue
	leaq	4096(%rcx), %r8
	addq	$4198400, %rcx
	movl	$0, %eax
	jmp	.L8
.L12:
	addq	$4096, %r8
	cmpq	%rcx, %r8
	je	.L7
.L8:
	leaq	-4096(%r8), %rdx
.L9:
	addl	(%rdx), %eax
	addq	$4, %rdx
	cmpq	%r8, %rdx
	jne	.L9
	jmp	.L12
.L7:
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
	pushq	%rbx
	.seh_pushreg	%rbx
	movl	$4194336, %eax
	call	___chkstk_ms
	subq	%rax, %rsp
	.seh_stackalloc	4194336
	.seh_endprologue
	call	__main
	leaq	4128(%rsp), %rdx
	leaq	4198432(%rsp), %rcx
	jmp	.L14
.L18:
	addq	$4096, %rdx
	cmpq	%rcx, %rdx
	je	.L16
.L14:
	leaq	-4096(%rdx), %rax
.L15:
	movl	$1, (%rax)
	addq	$4, %rax
	cmpq	%rdx, %rax
	jne	.L15
	jmp	.L18
.L16:
	leaq	32(%rsp), %rbx
	movq	%rbx, %rcx
	call	sum_for
	movl	%eax, %edx
	leaq	.LC0(%rip), %rcx
	call	printf
	movq	%rbx, %rcx
	call	sum_while
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$4194336, %rsp
	popq	%rbx
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef

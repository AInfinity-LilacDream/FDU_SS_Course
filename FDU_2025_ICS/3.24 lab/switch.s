	.file	"switch.c"
	.text
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
	.align 8
.LC0:
	.ascii "please input x(ranges from 100 to 110):\0"
.LC1:
	.ascii "%d\0"
.LC2:
	.ascii "the res is %d\0"
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
	movl	$10, -8(%rbp)
	movl	$0, -4(%rbp)
	leaq	.LC0(%rip), %rcx
	call	puts
	leaq	-8(%rbp), %rax
	movq	%rax, %rdx
	leaq	.LC1(%rip), %rcx
	call	scanf
	movl	-8(%rbp), %eax
	subl	$100, %eax
	cmpl	$8, %eax
	ja	.L2
	movl	%eax, %eax
	leaq	0(,%rax,4), %rdx
	leaq	.L4(%rip), %rax
	movl	(%rdx,%rax), %eax
	cltq
	leaq	.L4(%rip), %rdx
	addq	%rdx, %rax
	jmp	*%rax
	.section .rdata,"dr"
	.align 4
.L4:
	.long	.L10-.L4
	.long	.L2-.L4
	.long	.L9-.L4
	.long	.L8-.L4
	.long	.L7-.L4
	.long	.L6-.L4
	.long	.L5-.L4
	.long	.L3-.L4
	.long	.L3-.L4
	.text
.L10:
	movl	$1, -4(%rbp)
	jmp	.L11
.L9:
	movl	$2, -4(%rbp)
	jmp	.L11
.L8:
	movl	$3, -4(%rbp)
.L7:
	addl	$4, -4(%rbp)
.L6:
	addl	$5, -4(%rbp)
	jmp	.L11
.L5:
	movl	$6, -4(%rbp)
	jmp	.L11
.L3:
	movl	$7, -4(%rbp)
	jmp	.L11
.L2:
	movl	$10, -4(%rbp)
.L11:
	movl	-4(%rbp), %eax
	movl	%eax, %edx
	leaq	.LC2(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$48, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	puts;	.scl	2;	.type	32;	.endef
	.def	scanf;	.scl	2;	.type	32;	.endef
	.def	printf;	.scl	2;	.type	32;	.endef

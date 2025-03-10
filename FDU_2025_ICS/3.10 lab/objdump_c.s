	.file	"objdump_c.c"
	.text
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "1. int and int:\0"
.LC1:
	.ascii "%d + %d = %d\12\0"
.LC2:
	.ascii "%d - %d = %d\12\0"
.LC3:
	.ascii "%d * %d = %d\12\0"
.LC4:
	.ascii "%d / %d = %d\12\12\0"
.LC6:
	.ascii "2. int and float:\0"
.LC7:
	.ascii "%d + %.1f = %.2f\12\0"
.LC8:
	.ascii "%d - %.1f = %.2f\12\0"
.LC9:
	.ascii "%d * %.1f = %.2f\12\0"
.LC10:
	.ascii "%d / %.1f = %.2f\12\12\0"
.LC13:
	.ascii "3. float and double:\0"
.LC14:
	.ascii "%.2f + %.5f = %.6f\12\0"
.LC15:
	.ascii "%.2f * %.5f = %.6f\12\12\0"
.LC16:
	.ascii "5. force transition: \0"
.LC17:
	.ascii "%d / %d = %d\12\0"
.LC18:
	.ascii "(float)%d / %d = %.2f\12\0"
	.text
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$80, %rsp
	.seh_stackalloc	80
	.seh_endprologue
	call	__main
	movl	$10, -4(%rbp)
	movl	$3, -8(%rbp)
	leaq	.LC0(%rip), %rcx
	call	puts
	movl	-4(%rbp), %edx
	movl	-8(%rbp), %eax
	leal	(%rdx,%rax), %ecx
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%ecx, %r9d
	movl	%edx, %r8d
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	movl	-4(%rbp), %eax
	subl	-8(%rbp), %eax
	movl	%eax, %edx
	movl	-8(%rbp), %ecx
	movl	-4(%rbp), %eax
	movl	%edx, %r9d
	movl	%ecx, %r8d
	movl	%eax, %edx
	leaq	.LC2(%rip), %rcx
	call	printf
	movl	-4(%rbp), %eax
	imull	-8(%rbp), %eax
	movl	%eax, %edx
	movl	-8(%rbp), %ecx
	movl	-4(%rbp), %eax
	movl	%edx, %r9d
	movl	%ecx, %r8d
	movl	%eax, %edx
	leaq	.LC3(%rip), %rcx
	call	printf
	movl	-4(%rbp), %eax
	cltd
	idivl	-8(%rbp)
	movl	%eax, %ecx
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%ecx, %r9d
	movl	%edx, %r8d
	movl	%eax, %edx
	leaq	.LC4(%rip), %rcx
	call	printf
	movl	$5, -12(%rbp)
	movss	.LC5(%rip), %xmm0
	movss	%xmm0, -16(%rbp)
	leaq	.LC6(%rip), %rcx
	call	puts
	cvtsi2ss	-12(%rbp), %xmm0
	addss	-16(%rbp), %xmm0
	cvtss2sd	%xmm0, %xmm1
	cvtss2sd	-16(%rbp), %xmm0
	movq	%xmm1, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm1
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rax, %xmm0
	movl	-12(%rbp), %eax
	movq	%rcx, %xmm3
	movq	%xmm1, %r9
	movq	%rdx, %xmm2
	movq	%xmm0, %r8
	movl	%eax, %edx
	leaq	.LC7(%rip), %rcx
	call	printf
	cvtsi2ss	-12(%rbp), %xmm0
	subss	-16(%rbp), %xmm0
	cvtss2sd	%xmm0, %xmm1
	cvtss2sd	-16(%rbp), %xmm0
	movq	%xmm1, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm1
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rax, %xmm0
	movl	-12(%rbp), %eax
	movq	%rcx, %xmm3
	movq	%xmm1, %r9
	movq	%rdx, %xmm2
	movq	%xmm0, %r8
	movl	%eax, %edx
	leaq	.LC8(%rip), %rcx
	call	printf
	cvtsi2ss	-12(%rbp), %xmm0
	mulss	-16(%rbp), %xmm0
	cvtss2sd	%xmm0, %xmm1
	cvtss2sd	-16(%rbp), %xmm0
	movq	%xmm1, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm1
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rax, %xmm0
	movl	-12(%rbp), %eax
	movq	%rcx, %xmm3
	movq	%xmm1, %r9
	movq	%rdx, %xmm2
	movq	%xmm0, %r8
	movl	%eax, %edx
	leaq	.LC9(%rip), %rcx
	call	printf
	cvtsi2ss	-12(%rbp), %xmm0
	divss	-16(%rbp), %xmm0
	cvtss2sd	%xmm0, %xmm1
	cvtss2sd	-16(%rbp), %xmm0
	movq	%xmm1, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm1
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rax, %xmm0
	movl	-12(%rbp), %eax
	movq	%rcx, %xmm3
	movq	%xmm1, %r9
	movq	%rdx, %xmm2
	movq	%xmm0, %r8
	movl	%eax, %edx
	leaq	.LC10(%rip), %rcx
	call	printf
	movss	.LC11(%rip), %xmm0
	movss	%xmm0, -20(%rbp)
	movsd	.LC12(%rip), %xmm0
	movsd	%xmm0, -32(%rbp)
	leaq	.LC13(%rip), %rcx
	call	puts
	cvtss2sd	-20(%rbp), %xmm0
	addsd	-32(%rbp), %xmm0
	cvtss2sd	-20(%rbp), %xmm2
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm4
	movsd	-32(%rbp), %xmm1
	movsd	-32(%rbp), %xmm0
	movq	%xmm2, %rax
	movq	%rax, %rdx
	movq	%rcx, %xmm3
	movq	%xmm4, %r9
	movapd	%xmm1, %xmm2
	movq	%xmm0, %r8
	movq	%rdx, %xmm1
	movq	%rax, %rdx
	leaq	.LC14(%rip), %rcx
	call	printf
	cvtss2sd	-20(%rbp), %xmm0
	mulsd	-32(%rbp), %xmm0
	cvtss2sd	-20(%rbp), %xmm2
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm4
	movsd	-32(%rbp), %xmm1
	movsd	-32(%rbp), %xmm0
	movq	%xmm2, %rax
	movq	%rax, %rdx
	movq	%rcx, %xmm3
	movq	%xmm4, %r9
	movapd	%xmm1, %xmm2
	movq	%xmm0, %r8
	movq	%rdx, %xmm1
	movq	%rax, %rdx
	leaq	.LC15(%rip), %rcx
	call	printf
	movl	$7, -36(%rbp)
	movl	$2, -40(%rbp)
	leaq	.LC16(%rip), %rcx
	call	puts
	movl	-36(%rbp), %eax
	cltd
	idivl	-40(%rbp)
	movl	%eax, %ecx
	movl	-40(%rbp), %edx
	movl	-36(%rbp), %eax
	movl	%ecx, %r9d
	movl	%edx, %r8d
	movl	%eax, %edx
	leaq	.LC17(%rip), %rcx
	call	printf
	cvtsi2ss	-36(%rbp), %xmm0
	cvtsi2ss	-40(%rbp), %xmm1
	divss	%xmm1, %xmm0
	cvtss2sd	%xmm0, %xmm0
	movq	%xmm0, %rax
	movq	%rax, %rdx
	movq	%rdx, %rcx
	movq	%rax, %xmm0
	movl	-40(%rbp), %edx
	movl	-36(%rbp), %eax
	movq	%rcx, %xmm3
	movq	%xmm0, %r9
	movl	%edx, %r8d
	movl	%eax, %edx
	leaq	.LC18(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$80, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.section .rdata,"dr"
	.align 4
.LC5:
	.long	1075838976
	.align 4
.LC11:
	.long	1078523331
	.align 8
.LC12:
	.long	-1783957616
	.long	1074118409
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	puts;	.scl	2;	.type	32;	.endef
	.def	printf;	.scl	2;	.type	32;	.endef

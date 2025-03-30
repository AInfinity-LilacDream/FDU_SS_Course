	.file	"condition_code_alter.c"
	.text
	.globl	check_carry
	.def	check_carry;	.scl	2;	.type	32;	.endef
	.seh_proc	check_carry
check_carry:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	movl	%edx, 24(%rbp)
	movl	16(%rbp), %edx
	movl	24(%rbp), %eax
	addl	%edx, %eax
	cmpl	%eax, 16(%rbp)
	seta	%al
	movzbl	%al, %eax
	popq	%rbp
	ret
	.seh_endproc
	.globl	check_overflow
	.def	check_overflow;	.scl	2;	.type	32;	.endef
	.seh_proc	check_overflow
check_overflow:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	movl	%edx, 24(%rbp)
	movl	16(%rbp), %edx
	movl	24(%rbp), %eax
	addl	%edx, %eax
	movl	%eax, -4(%rbp)
	cmpl	$0, 16(%rbp)
	jle	.L4
	cmpl	$0, 24(%rbp)
	jle	.L4
	cmpl	$0, -4(%rbp)
	js	.L5
.L4:
	cmpl	$0, 16(%rbp)
	jns	.L6
	cmpl	$0, 24(%rbp)
	jns	.L6
	cmpl	$0, -4(%rbp)
	jle	.L6
.L5:
	movl	$1, %eax
	jmp	.L8
.L6:
	movl	$0, %eax
.L8:
	addq	$16, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.globl	check_zero
	.def	check_zero;	.scl	2;	.type	32;	.endef
	.seh_proc	check_zero
check_zero:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	cmpl	$0, 16(%rbp)
	sete	%al
	movzbl	%al, %eax
	popq	%rbp
	ret
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "\350\277\233\344\275\215\346\240\207\345\277\227 CF: %d\12\0"
.LC1:
	.ascii "\346\272\242\345\207\272\346\240\207\345\277\227 OF: %d\12\0"
.LC2:
	.ascii "\351\233\266\346\240\207\345\277\227 ZF: %d\12\0"
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
	movl	$2147483647, -4(%rbp)
	movl	$1, -8(%rbp)
	movl	-4(%rbp), %edx
	movl	-8(%rbp), %eax
	addl	%edx, %eax
	movl	%eax, -12(%rbp)
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%eax, %ecx
	call	check_carry
	movl	%eax, %edx
	leaq	.LC0(%rip), %rcx
	call	printf
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%eax, %ecx
	call	check_overflow
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	movl	-12(%rbp), %eax
	movl	%eax, %ecx
	call	check_zero
	movl	%eax, %edx
	leaq	.LC2(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$48, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.ident	"GCC: (x86_64-posix-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	printf;	.scl	2;	.type	32;	.endef

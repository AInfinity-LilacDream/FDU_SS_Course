	.file	"conditional_code.c"
	.text
	.globl	get_carry_flag
	.def	get_carry_flag;	.scl	2;	.type	32;	.endef
	.seh_proc	get_carry_flag
get_carry_flag:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	movl	%edx, 24(%rbp)
	movl	16(%rbp), %eax
	movl	24(%rbp), %edx
/APP
 # 7 "conditional_code.c" 1
	addl %edx, %eax
setc %al
 # 0 "" 2
/NO_APP
	movb	%al, -1(%rbp)
	movzbl	-1(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.globl	get_overflow_flag
	.def	get_overflow_flag;	.scl	2;	.type	32;	.endef
	.seh_proc	get_overflow_flag
get_overflow_flag:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	movl	%edx, 24(%rbp)
	movl	16(%rbp), %eax
	movl	24(%rbp), %edx
/APP
 # 20 "conditional_code.c" 1
	addl %edx, %eax
seto %al
 # 0 "" 2
/NO_APP
	movb	%al, -1(%rbp)
	movzbl	-1(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.globl	get_zero_flag
	.def	get_zero_flag;	.scl	2;	.type	32;	.endef
	.seh_proc	get_zero_flag
get_zero_flag:
	pushq	%rbp
	.seh_pushreg	%rbp
	movq	%rsp, %rbp
	.seh_setframe	%rbp, 0
	subq	$16, %rsp
	.seh_stackalloc	16
	.seh_endprologue
	movl	%ecx, 16(%rbp)
	movl	16(%rbp), %eax
/APP
 # 32 "conditional_code.c" 1
	testl %eax, %eax
setz %al
 # 0 "" 2
/NO_APP
	movb	%al, -1(%rbp)
	movzbl	-1(%rbp), %eax
	addq	$16, %rsp
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
	call	get_carry_flag
	movl	%eax, %edx
	leaq	.LC0(%rip), %rcx
	call	printf
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%eax, %ecx
	call	get_overflow_flag
	movl	%eax, %edx
	leaq	.LC1(%rip), %rcx
	call	printf
	movl	-12(%rbp), %eax
	movl	%eax, %ecx
	call	get_zero_flag
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


libseries.so:     file format elf64-x86-64


Disassembly of section .init:

0000000000001000 <_init>:
    1000:	48 83 ec 08          	sub    rsp,0x8
    1004:	48 8b 05 d5 2f 00 00 	mov    rax,QWORD PTR [rip+0x2fd5]        # 3fe0 <__gmon_start__>
    100b:	48 85 c0             	test   rax,rax
    100e:	74 02                	je     1012 <_init+0x12>
    1010:	ff d0                	call   rax
    1012:	48 83 c4 08          	add    rsp,0x8
    1016:	c3                   	ret

Disassembly of section .plt:

0000000000001020 <.plt>:
    1020:	ff 35 ca 2f 00 00    	push   QWORD PTR [rip+0x2fca]        # 3ff0 <_GLOBAL_OFFSET_TABLE_+0x8>
    1026:	ff 25 cc 2f 00 00    	jmp    QWORD PTR [rip+0x2fcc]        # 3ff8 <_GLOBAL_OFFSET_TABLE_+0x10>
    102c:	0f 1f 40 00          	nop    DWORD PTR [rax+0x0]

Disassembly of section .plt.got:

0000000000001030 <__cxa_finalize@plt>:
    1030:	ff 25 92 2f 00 00    	jmp    QWORD PTR [rip+0x2f92]        # 3fc8 <__cxa_finalize>
    1036:	66 90                	xchg   ax,ax

Disassembly of section .text:

0000000000001040 <deregister_tm_clones>:
    1040:	48 8d 3d c1 2f 00 00 	lea    rdi,[rip+0x2fc1]        # 4008 <completed.0>
    1047:	48 8d 05 ba 2f 00 00 	lea    rax,[rip+0x2fba]        # 4008 <completed.0>
    104e:	48 39 f8             	cmp    rax,rdi
    1051:	74 15                	je     1068 <deregister_tm_clones+0x28>
    1053:	48 8b 05 7e 2f 00 00 	mov    rax,QWORD PTR [rip+0x2f7e]        # 3fd8 <_ITM_deregisterTMCloneTable>
    105a:	48 85 c0             	test   rax,rax
    105d:	74 09                	je     1068 <deregister_tm_clones+0x28>
    105f:	ff e0                	jmp    rax
    1061:	0f 1f 80 00 00 00 00 	nop    DWORD PTR [rax+0x0]
    1068:	c3                   	ret
    1069:	0f 1f 80 00 00 00 00 	nop    DWORD PTR [rax+0x0]

0000000000001070 <register_tm_clones>:
    1070:	48 8d 3d 91 2f 00 00 	lea    rdi,[rip+0x2f91]        # 4008 <completed.0>
    1077:	48 8d 35 8a 2f 00 00 	lea    rsi,[rip+0x2f8a]        # 4008 <completed.0>
    107e:	48 29 fe             	sub    rsi,rdi
    1081:	48 89 f0             	mov    rax,rsi
    1084:	48 c1 ee 3f          	shr    rsi,0x3f
    1088:	48 c1 f8 03          	sar    rax,0x3
    108c:	48 01 c6             	add    rsi,rax
    108f:	48 d1 fe             	sar    rsi,1
    1092:	74 14                	je     10a8 <register_tm_clones+0x38>
    1094:	48 8b 05 35 2f 00 00 	mov    rax,QWORD PTR [rip+0x2f35]        # 3fd0 <_ITM_registerTMCloneTable>
    109b:	48 85 c0             	test   rax,rax
    109e:	74 08                	je     10a8 <register_tm_clones+0x38>
    10a0:	ff e0                	jmp    rax
    10a2:	66 0f 1f 44 00 00    	nop    WORD PTR [rax+rax*1+0x0]
    10a8:	c3                   	ret
    10a9:	0f 1f 80 00 00 00 00 	nop    DWORD PTR [rax+0x0]

00000000000010b0 <__do_global_dtors_aux>:
    10b0:	f3 0f 1e fa          	endbr64
    10b4:	80 3d 4d 2f 00 00 00 	cmp    BYTE PTR [rip+0x2f4d],0x0        # 4008 <completed.0>
    10bb:	75 2b                	jne    10e8 <__do_global_dtors_aux+0x38>
    10bd:	55                   	push   rbp
    10be:	48 83 3d 02 2f 00 00 	cmp    QWORD PTR [rip+0x2f02],0x0        # 3fc8 <__cxa_finalize>
    10c5:	00 
    10c6:	48 89 e5             	mov    rbp,rsp
    10c9:	74 0c                	je     10d7 <__do_global_dtors_aux+0x27>
    10cb:	48 8b 3d 2e 2f 00 00 	mov    rdi,QWORD PTR [rip+0x2f2e]        # 4000 <__dso_handle>
    10d2:	e8 59 ff ff ff       	call   1030 <__cxa_finalize@plt>
    10d7:	e8 64 ff ff ff       	call   1040 <deregister_tm_clones>
    10dc:	c6 05 25 2f 00 00 01 	mov    BYTE PTR [rip+0x2f25],0x1        # 4008 <completed.0>
    10e3:	5d                   	pop    rbp
    10e4:	c3                   	ret
    10e5:	0f 1f 00             	nop    DWORD PTR [rax]
    10e8:	c3                   	ret
    10e9:	0f 1f 80 00 00 00 00 	nop    DWORD PTR [rax+0x0]

00000000000010f0 <frame_dummy>:
    10f0:	f3 0f 1e fa          	endbr64
    10f4:	e9 77 ff ff ff       	jmp    1070 <register_tm_clones>

00000000000010f9 <linear_sum>:
    10f9:	55                   	push   rbp
    10fa:	48 89 e5             	mov    rbp,rsp
    10fd:	89 7d ec             	mov    DWORD PTR [rbp-0x14],edi
    1100:	48 c7 45 f8 00 00 00 	mov    QWORD PTR [rbp-0x8],0x0
    1107:	00 
    1108:	c7 45 f4 01 00 00 00 	mov    DWORD PTR [rbp-0xc],0x1
    110f:	eb 0d                	jmp    111e <linear_sum+0x25>
    1111:	8b 45 f4             	mov    eax,DWORD PTR [rbp-0xc]
    1114:	48 98                	cdqe
    1116:	48 01 45 f8          	add    QWORD PTR [rbp-0x8],rax
    111a:	83 45 f4 01          	add    DWORD PTR [rbp-0xc],0x1
    111e:	8b 45 f4             	mov    eax,DWORD PTR [rbp-0xc]
    1121:	3b 45 ec             	cmp    eax,DWORD PTR [rbp-0x14]
    1124:	7e eb                	jle    1111 <linear_sum+0x18>
    1126:	48 8b 45 f8          	mov    rax,QWORD PTR [rbp-0x8]
    112a:	5d                   	pop    rbp
    112b:	c3                   	ret

000000000000112c <square_sum>:
    112c:	55                   	push   rbp
    112d:	48 89 e5             	mov    rbp,rsp
    1130:	89 7d ec             	mov    DWORD PTR [rbp-0x14],edi
    1133:	48 c7 45 f8 00 00 00 	mov    QWORD PTR [rbp-0x8],0x0
    113a:	00 
    113b:	c7 45 f4 01 00 00 00 	mov    DWORD PTR [rbp-0xc],0x1
    1142:	eb 10                	jmp    1154 <square_sum+0x28>
    1144:	8b 45 f4             	mov    eax,DWORD PTR [rbp-0xc]
    1147:	0f af c0             	imul   eax,eax
    114a:	48 98                	cdqe
    114c:	48 01 45 f8          	add    QWORD PTR [rbp-0x8],rax
    1150:	83 45 f4 01          	add    DWORD PTR [rbp-0xc],0x1
    1154:	8b 45 f4             	mov    eax,DWORD PTR [rbp-0xc]
    1157:	3b 45 ec             	cmp    eax,DWORD PTR [rbp-0x14]
    115a:	7e e8                	jle    1144 <square_sum+0x18>
    115c:	48 8b 45 f8          	mov    rax,QWORD PTR [rbp-0x8]
    1160:	5d                   	pop    rbp
    1161:	c3                   	ret

Disassembly of section .fini:

0000000000001164 <_fini>:
    1164:	48 83 ec 08          	sub    rsp,0x8
    1168:	48 83 c4 08          	add    rsp,0x8
    116c:	c3                   	ret

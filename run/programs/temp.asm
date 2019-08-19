
; (2 + 3) * 5 = 25 = 0x19
mov 2, r0
mov 3, r1
mov 5, r2
push r0, r1, r2
call :math
pop r0
halt


; return (a + b) * c
math:
    pop r0, r1, r2

    add r0, r1, r0
    mul r0, r2, r0

    push r0
    jmp lr

end: nop
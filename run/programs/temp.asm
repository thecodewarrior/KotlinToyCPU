    mov r0, #0x10
    mov r1, #0x03
restart:
    add r0, r0, r1
    jmp restart
;start:
    ;add r0, r1, r0
    ;cmp r0, 0xf0
    ;jmp_lt start
    ;add r2, r2, 1
    ;jmp restart


mov 0xf12ae3, r4
mov 28, r3
loop:
    shr r4, r3, r2
    and r2, 0xf, r2
    mov 0, r0
    ldr'b r1 [:digits, r2]
    pcall 0
    cmp r3, 0
    eq? jmp :end
    sub r3, 4, r3
    jmp :loop

end:
    halt

digits: %data .ascii "0123456789abcdef"

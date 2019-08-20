
mov 0, r0
mov 32, r1
loop:
    cmp r1, 127
    ge? jmp :end
    pcall 0
    inc r1
    jmp :loop

end:
    halt

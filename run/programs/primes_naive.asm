; r0 = temporary value
; r1 = value to test against
; r2 = half of candidate
; r5 = candidate prime
; r10 = last prime

mov r5, #2
next:
    inc r5
    mov r1, #2

    div r2, r5, #2
    test:
        cmp r1, r2
        gt? jmp success

        mod r0, r5, r1
        cmp r0, #0
        eq? jmp failure
        inc r1
        jmp test
success:
    mov r10, r5
failure:
    inc r5
    jmp next

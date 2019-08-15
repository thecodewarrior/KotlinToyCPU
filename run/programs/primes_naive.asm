; r0 = temporary value
; r1 = value to test against
; r2 = half of candidate
; r5 = candidate prime
; r10 = last prime

mov #2, r5
next:
    inc r5
    mov #2, r1

    div r5, #2, r2
    test:
        cmp r1, r2
        gt? jmp success

        mod r5, r1, r0
        cmp r0, #0
        eq? jmp failure
        inc r1
        jmp test
success:
    mov r5, r10
failure:
    inc r5
    jmp next

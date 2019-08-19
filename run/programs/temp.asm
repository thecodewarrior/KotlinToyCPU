
.test = r1
.halfCandidate = r2
.index = r3
.candidate = r5
.latestPrime = r10

mov :end, index
mov 1, candidate
next:
    add candidate, 2, candidate
    div candidate, 2, halfCandidate

    mov 2, test
    test_loop:
        cmp test, halfCandidate
        gt? jmp :success

        mod candidate test, r0
        cmp r0, 0
        eq? jmp :failure
        inc test
        jmp :test_loop
success:
    str candidate, [index]
    ldr latestPrime, [index]
    add index, 4, index
failure:
    jmp :next

end: nop

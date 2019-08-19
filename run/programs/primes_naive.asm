.candidate = r5
.halfCandidate = r2
.test = r1
.latestPrime = r10

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
    mov candidate, latestPrime
failure:
    jmp :next

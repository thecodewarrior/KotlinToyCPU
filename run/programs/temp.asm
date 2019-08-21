#include print.asm

main:

.test = r1
.halfCandidate = r2
.index = r3
.candidate = r5
.latestPrime = r10

mov :program_end, index
mov 3, r1
str r1, [index]
add index, 4, latestPrime

mov 3, candidate
next:
    add candidate, 2, candidate
    div candidate, 2, halfCandidate

    mov :end, index
    test_loop:
        ldr test, [index]
        cmp test, halfCandidate
        gt? jmp :success

        mod candidate test, r0
        cmp r0, 0
        eq? jmp :failure
        add index, 4, index
        jmp :test_loop
success:
    push r0, r1, r2, r3
    mov candidate, r0
    call :print_hex
    call :println
    pop r0, r1, r2, r3
    str candidate, [latestPrime]
    add latestPrime, 4, latestPrime
failure:
    jmp :next

end: nop

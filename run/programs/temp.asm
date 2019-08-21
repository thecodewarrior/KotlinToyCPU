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

    mov :program_end, index

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
    str candidate, [latestPrime]
    add latestPrime, 4, latestPrime

    push candidate
    mov :found_message, r0
    pcall PTEXT, PRINTF
failure:
    jmp :next

found_message: %data .asciiz "Found a prime: %d\n"

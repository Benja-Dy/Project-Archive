# Name: Dinh, Benjamin
# project #5
# Due: 5/13/22
# Course: cs-2640-03-sp22
#
# Description:
# Solves the quadratic equation based on user input
#

	.data
intro:	.asciiz	"Quadratic Equation Solver by B. Dinh\n\n"

entera:	.asciiz	"Enter value for a? "
enterb:	.asciiz	"Enter value for b? "
enterc:	.asciiz	"Enter value for c? "
blank:	.asciiz	"\n"
nonquad:	.asciiz	"Not a quadratic equation"
lineq:	.asciiz	"x = "
imagroot:	.asciiz	"Roots are imaginary"
quad1:	.asciiz	"x1 = "
quad2:	.asciiz	"x2 = "

a.s:	.float	0.0
b.s: 	.float	0.0
c.s: 	.float	0.0

	.text

main:
	la 	$a0, intro  			# display intro
	li 	$v0, 4
	syscall


	la 	$a0, entera  			# Get user input for A
	li 	$v0, 4
	syscall

	li	$v0, 6
	syscall

	s.s 	$f0, a.s

	la 	$a0, enterb  			# Get user input for b
	li 	$v0, 4
	syscall

	li	$v0, 6
	syscall

	s.s 	$f0, b.s

	la 	$a0, enterc  			# Get user input for C
	li 	$v0, 4
	syscall

	li	$v0, 6
	syscall

	s.s 	$f0, c.s

	l.s	$f12, a.s				# loading the argument registers with a, b, and c
	l.s	$f13, b.s
	l.s	$f14, c.s

	jal	solveqe

	beq	$v0, -1, imaginary
	beqz	$v0, invalid
	beq	$v0, 1, linear
	b 	valid
invalid:
	la	$a0, nonquad
	li	$v0, 4
	syscall
	b 	done
imaginary:
	la	$a0, imagroot
	li	$v0, 4
	syscall
	b 	done
linear:
	la	$a0, lineq
	li	$v0, 4
	syscall
	mov.s 	$f12, $f0

	li	$v0, 2
	syscall
	b 	done

valid:
	la	$a0, quad1
	li	$v0, 4
	syscall

	mov.s 	$f12, $f0

	li	$v0, 2
	syscall

	la	$a0, quad1
	li	$v0, 4
	syscall

	mov.s 	$f12, $f1

	li	$v0, 2
	syscall

	b 	done
done:
	li	$v0, 10				# exit the program
	syscall


# f12 = a
# f13 = b
# f14 = c

# f0 = x1
# f1 = x2
# v0 is the type of answer the quadratic equation gives
solveqe:
	addi	$sp, $sp, -16 			# allocating space on the stack for ra
	sw 	$ra,  0($sp)
	s.s	$f20, 4($sp)
	s.s	$f21, 8($sp)
	s.s	$f22, 12($sp)

	li.s 	$f20, 0.0
	li.s 	$f21, 1.0
	c.eq.s	$f12, $f20
	bc1t	aequalz
	b 	solvequad	
aequalz:
	c.eq.s 	$f20, $f21
	c.eq.s 	$f13, $f20
	bc1t	bequalz
						# linear equation
	neg.s 	$f0, $f14
	div.s 	$f0, $f0, $f13
	li	$v0, 1

	b 	endsolve
bequalz:						# invalid equation
	li	$v0, 0
	b 	endsolve

solvequad:					#  valid equation

	mul.s	$f20, $f13, $f13			# b^2
	mul.s 	$f21, $f12, $f14			# a*c
	li.s	$f22, 4.0
	mul.s 	$f21, $f21, $f22			# 4ac
	sub.s 	$f20, $f20, $f21			# b^2 - 4ac
	li.s 	$f22, 0.0
	c.le.s	$f20, $f22
	bc1t	disltz
	mov.s 	$f21, $f12			# a
	mov.s 	$f22, $f13			# b
	mov.s 	$f12, $f20			# discriminant
	jal	sqrts				# sqrt(b^2 - 4ac) -> $f0
	neg.s 	$f22, $f22			# -b
	sub.s 	$f1, $f22, $f0			# x2 = -b - disc
	add.s 	$f0, $f22, $f0			# x1 = -b + disc
	li.s 	$f22, 2.0
	mul.s 	$f21, $f21, $f22

	div.s 	$f0, $f0, $f21
	div.s 	$f1, $f1, $f21
	li	$v0, 2

	b 	endsolve
disltz:
	li	$v0, -1				# discriminant less than zero (Imaginary)
	b 	endsolve
endsolve:	

	l.s	$f20, 4($sp)
	l.s	$f21, 8($sp)
	l.s	$f22, 12($sp)
	lw 	$ra,  0($sp)
	addi	$sp, $sp, 12			# loading ra back into the register

	jr	$ra


# f12 = (b^2 - 4ac)
# f0 = result
sqrts:
	
	li.s 	$f4, 0.0000001			# err = le-7
	mov.s 	$f5, $f12				# x = input
	li.s 	$f6, 1.0				# y - 1
	li.s 	$f8, 2.0
while:
	sub.s 	$f7, $f5, $f6
	c.lt.s 	$f7, $f4
	bc1t	donesqrt
	add.s 	$f5, $f5, $f6
	div.s 	$f5, $f5, $f8
	div.s 	$f6, $f12, $f5

	b 	while
donesqrt:
	mov.s 	$f0, $f5
	jr	$ra
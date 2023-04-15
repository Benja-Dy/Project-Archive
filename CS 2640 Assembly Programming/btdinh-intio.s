#
# Name: Dinh, Benjamin
# Homework #5
# Due: 5/05/22
# Course: cs-2640-03-sp22
#
# Description:
# Takes in a number and determines what base it is in
# then returns that number in a multitude of bases
#
	.data
intro:	.asciiz	"Integer I/O by B. Dinh\n\n"
prompt:	.asciiz	"Enter a value? "
blank:	.asciiz	"\n"
error:	.asciiz	"\nThe number is invalid"
bin:	.asciiz	" binary \n"
oct:	.asciiz	" octal \n"
hexi:	.asciiz	" hexidecimal \n"
deci:	.asciiz	" decimal \n"

	.text
main:
	la 	$a0, intro  		# display intro
	li 	$v0, 4
	syscall

	jal 	getint			# v0 = the number as an int
					# v1 = if the number is valid
	beqz	$v1, badnum				# v1 is invalid if = 0			

	move 	$s0, $v0

	la 	$a0, blank  		# display intro
	li 	$v0, 4
	syscall

	move	$a0, $s0
	li	$a1, 2
	jal 	printint

	la 	$a0, bin  		# display intro
	li 	$v0, 4
	syscall

	move	$a0, $s0
	li	$a1, 8
	jal 	printint

	la 	$a0, oct  		# display intro
	li 	$v0, 4
	syscall

	move	$a0, $s0
	li	$a1, 10
	jal 	printint

	la 	$a0, deci  		# display intro
	li 	$v0, 4
	syscall

	move	$a0, $s0
	li	$a1, 16
	jal 	printint

	la 	$a0, hexi  		# display intro
	li 	$v0, 4
	syscall

	b	good
badnum:

	la 	$a0, error  		# display error
	li 	$v0, 4
	syscall
good:
	li	$v0, 10			# end
	syscall




getint:
	addi 	$sp, $sp, -16
	sw	$s0, 0($sp)
	sw	$s1, 4($sp)
	sw	$s2, 8($sp)
	sw	$ra, 12($sp)


	la 	$a0, prompt  		# display prompt
	li 	$v0, 4
	syscall

	jal 	getchar

	bgt	$v0, '9', notdecimal
	blt	$v0, '1', notdecimal	# checks if value is in base 10

	li	$s1, 10
	b 	getdigval

notdecimal:
	bne	$v0, '0' invalid
	jal 	getchar
	bgt	$v0, '7', notoctal
	blt	$v0, '0', notoctal		# checks if value is in base 8

	li	$s1, 8
	b 	getdigval

notoctal:
	beq	$v0, 'x', hexidecimal
	beq	$v0, 'b', binary
	b 	invalid	

hexidecimal:
	li	$s1, 16
	b	getnum

binary:
	li	$s1, 2
	b 	getnum

	
getdigval:				# converts character value to number
	sub	$v0, $v0, '0'
	ble	$v0, 9, notletter

	sub	$v0, $v0, 39		# was hex 'a' - ':'
notletter:
	move	$s2, $v0


getnum:
	li	$v1, 1
	jal	getchar

while:	beq	$v1, 0, invalid
	beq	$v0, '\n', endgetint
	b 	valofdig
continue:	bge	$s0, $s1, invalid
	mul	$s2, $s2, $s1
	add	$s2, $s0,	$s2
	jal	getchar
	b 	while



valofdig:				# converts character value to number
	sub	$v0, $v0, '0'
	ble	$v0, 9, nothex

	sub	$v0, $v0, 39		# was hex 'a' - ':'
nothex:
	move	$s0, $v0
	b 	continue

invalid:
	li	$v1, 0			# inputted number is invalid
endgetint:

	move	$v0, $s2
	lw	$s0, 0($sp)
	lw	$s1, 4($sp)
	lw	$s2, 8($sp)
	lw	$ra, 12($sp)
	addi	$sp, $sp, 12
	jr 	$ra


# a0 is the number in integer form
# a1 is the base of the desired output
# void prints the number to the console
printint:
	addi	$sp, $sp, -12		# save n, base, and ra
	sw	$a0, 0($sp)
	sw	$a1, 4($sp)
	sw	$ra, 8($sp)
	bge	$a0, $a1, else		# if n < base
	jal	putdigit			#    putdigit(n)
	b 	endif
else:	div	$a0, $a0, $a1		# else
	jal	printint			#   printint(n / base)
	lw	$a0, 0($sp)		#   restore n
	lw	$a1, 4($sp)
	rem	$a0, $a0, $a1		#   n %= base
	jal	putdigit			#   putchar(n % base)	
endif:	lw	$a0, 0($sp)		# restore n, base, and ra
	lw	$a1, 4($sp)
	lw	$ra, 8($sp)
	addi	$sp, $sp, 12
	jr	$ra



putdigit:
	addi	$a0, $a0, '0'
	ble	$a0, '9', endif1
	addi	$a0, $a0, 39		# 'a' - ':', 97-58
endif1:	li	$v0, 11
	syscall
	jr	$ra



getchar:
	li	$v0, 12
	syscall
	jr 	$ra
# Name: Dinh, Benjamin
# Homework #4
# Due: 3/22/22
# Course: cs-2640-03-sp22
#
# Description:
#  Practice using arrays by creating and outputting an array of powers of 2
#

	.data
intro:	.asciiz	"Array by B. Dinh\n\n"
newline: 	.asciiz 	"\n"
powof2:	.word	0:40

	.text
main:
	la 	$a0, intro  			# display intro
	li 	$v0, 4
	syscall

	la 	$t1, powof2
	li 	$t0, 0				# start count at 0
	li 	$t2, 1
while0:
	bge	$t0, 10, endw0			# Loop 10 times
	sw	$t2, ($t1)			# Send 2^n to powof2
	mul	$t2, $t2, 2			# get the next power of 2
	addi 	$t1, $t1, 4			# get the next memory address
	addi 	$t0, $t0, 1			# count++ 
	b 	while0
endw0:

	la 	$t1, powof2 			# load address of powof2 into t1
	li 	$t0, 0				# start count at 0
while1:
	bge	$t0, 10, endw1			# Loop until t0 is 10

	lw 	$a0, ($t1)
	li 	$v0, 1
	syscall
	la 	$a0, newline  			# print new line
	li 	$v0, 4
	syscall


	addi 	$t1, $t1, 4			# go to next index of array
	addi 	$t0, $t0, 1			# count ++
	b	while1					
endw1:

	la 	$a0, newline  			# print new line
	li 	$v0, 4
	syscall

	li 	$v0,10
	syscall	
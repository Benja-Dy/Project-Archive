# Name: Dinh, Benjamin
# project #4
# Due: 4/23/22
# Course: cs-2640-03-sp22
#
# Description:
# Taking user input an d puting it inside of a list then outputting it again
#
MAXSIZE = 80

	.data
intro:	.asciiz	"Singly Link List by B. Dinh\n\n"
newline: 	.asciiz 	"\n"
head: 	.word 	0
input:	.space	82
prompt:	.asciiz	"Enter Text? "


	.text
main:
	addi	$sp, $sp, -4 			# allocating space on the stack for s0
	sw 	$s0, 0($sp)

	la 	$a0, intro  			# display intro
	li 	$v0, 4
	syscall

while:
	la	$a0, input
	li	$a1, MAXSIZE

	jal	gets				# grabs user input and stores it in input ( buffer )
	lb	$s0, input
	beq	$s0, '\n', endw

	la	$a0, input
	jal 	strdup				# duplicate inbuf to heap / v0 = address in heap

	
	move	$a0, $v0				# a0 = address of the dup[licated string in heap]
	lw	$a1, head				# a1 = the address inside of head

	jal 	getnode

	sw 	$v0, head				# storing the address of node into head
	b 	while				# looping until the break
endw:

	la 	$a0, newline  			# display intro
	li 	$v0, 4
	syscall

	lw 	$a0, head				# the address of the node stored inside head
	la 	$a1, print			# the address of the procedure ( print ) to be called during traverse

	

	jal 	traverse

	lw 	$s0, 0($sp)			# restoring the stack
	addi	$sp, $sp, 4
	
	li	$v0, 10				# end program
	syscall


# a0 = address of data
# a1 = the address of the next node
# v0 = address of this node
getnode:
	addi	$sp, $sp, -8			# allocate space in stack for ra and s0
	sw	$ra, 4($sp)
	sw	$s0, 0($sp)
	move 	$s0, $a0

	li	$a0, 8				# a0 = number of allocated bytes to add in heap
	jal 	malloc

	sw	$s0, 0($v0)			# storing data in offset 0 of allocated space in heap
	sw	$a1, 4($v0)			# storing the address of the next node in offset 4 in heap


	lw 	$ra, 4($sp)			# returning ra and s0 to original values
	lw	$s0, 0($sp)
	addi	$sp, $sp, 8			# restoring the stack
	jr 	$ra

# a0 = address of list ( head )
# a1 = address of proc
# void ( outputting strings in linked list to terminal)
traverse:

	addi	$sp, $sp, -8			# allocating space in stack for ra and s0/s1
	sw	$ra, 4($sp)
	sw	$s0, 0($sp)


	move 	$s0, $a0
	beqz	$s0, endTraverse			# if the node points to nothing



	lw	$a0, 4($s0)
	jal 	traverse

	lw	$a0, 0($s0)
	jalr 	$a1

endTraverse:
	lw	$ra, 4($sp)			# restoring stack variables to normal
	lw	$s0, 0($sp)
	addi 	$sp, $sp, 8

	jr	$ra

# a0 = address of cstring (inbuf) 
# v0 = int length of cstring
strlen:
	li	$v0, 0				# len = 0
	move	$t0, $a0
while2:
	lb 	$t1, ($t0)
	beqz	$t1, endw2
	
	addi 	$v0, $v0, 1			# len++
	addi 	$t0, $t0, 1
	b 	while2
endw2:
	jr 	$ra


# a0 = input
# v0 = adress of cstring in heap
strdup:
	addi	$sp, $sp, -8	# allocate space in stack for ra and a0
	sw	$ra, 4($sp)
	sw	$a0, 0($sp)

	jal 	strlen		# same a0 as strdup (inbuf address)
	addi 	$a0, $v0, 1	# reset the value of a0

	jal	malloc		# takes in # of bytes retruns adress of heap
	lw	$a0, 0($sp)	# restore the stack
	lw	$ra, 4($sp)	# restore the stack
	addi	$sp, $sp, 8

	move	$t0, $a0		# adress of inbuf
	move 	$t1, $v0		# address of heap
	

while5:	
	lb	$t2, ($t0)	# get chararacter from inbuf
	sb	$t2, ($t1)	# copy byte to heap
	beqz	$t2, endw5	# if char is \0 break
	addi	$t0, $t0, 1	# next charcter from inbuf
	addi	$t1, $t1, 1	# address of next heap byte
	b 	while5
endw5:
	jr 	$ra



# a0 = adress of c string in heap
print:
	li	$v0, 4
	syscall
	jr 	$ra



# a0 = address of input
# a1 = max length of line
# Reads user input and adds the characters of the string to input
gets:
	move	$t1, $a0

	la 	$a0, prompt  			# display prompt
	li 	$v0, 4
	syscall

	move 	$a0, $t1
	li 	$v0, 8
	syscall

	jr	$ra


# a0 - number of bytes
# v0 - address of allocated space
malloc:
	addi	$a0, $a0, 3			# make multiple of 4
	andi	$a0, $a0, 0xfffc
	li	$v0, 9				# malloc
	syscall
	jr 	$ra
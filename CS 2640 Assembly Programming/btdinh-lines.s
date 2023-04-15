#
# Name: Dinh, Benjamin
# Project: # 3
# Due: 4/14/22
# Course: cs-2640-03-sp22
#
# Description:
# Take in user input of strings and sends them to the heap
# then returns that input and prints it 
#

MAXLINES = 10
LINELEN = 80

	.data
lines:	.word	0:MAXLINES
inbuf:	.space	LINELEN
intro:	.asciiz	"Lines by B. Dinh\n\n"
prompt:	.asciiz	"Enter Text? "
blank:	.asciiz	"\n"
	.text
main:
	la 	$a0, intro  			# display intro
	li 	$v0, 4
	syscall

	addi 	$sp, $sp, -12
	sw	$s0, 8($sp)			# saving s0 to the stack
	sw	$s1, 4($sp)			# saving s1 to the stack
	sw	$s2, 0($sp)			# saving s1 to the stack

	li	$s0, 0				# numberOfLines = 0

while:
	bgt	$s0, MAXLINES, endw			# return to while

	la	$a0, inbuf			# parameters for gets
	li	$a1, LINELEN			# a0 = adress of buffer; a1 = max length of line
	jal	gets


	lb 	$s1, inbuf			# check if blank line is entered
	beq	$s1, '\n', endw			# and if so end while loop

	la	$a0, inbuf
	jal 	strdup				# duplicate inbuf to heap

	la 	$s2, lines			# load adress of lines
	sll 	$s0, $s0, 2			# mul line number by 4 to get index of lines
	add 	$s2, $s2, $s0			# add increment to address
	srl 	$s0, $s0, 2			# revert line number back to normal

	sw	$v0, ($s2)			# store address of cstring to lines[s0]

	addi	$s0, $s0, 1			# numberOfLines++
	b 	while
endw:
	la	$a0, blank
	li	$v0, 4
	syscall
	li 	$s1, 0				# new counter
	la 	$s2, lines			# address of array
while3:	
	bge	$s1, $s0, endw3			# while counter < number of lines
	lw	$a0, ($s2)
	jal 	puts
	addi 	$s2, $s2, 4
	addi 	$s1, $s1, 1			# counter++
	b 	while3

endw3:
	lw 	$s2, ($sp)			# return s values to normal
	addi	$sp, $sp, 4
	lw 	$s1, ($sp)
	addi	$sp, $sp, 4
	lw 	$s0, ($sp)
	addi	$sp, $sp, 4

	li	$v0, 10				# end program
	syscall


# a0 = address if inbuf
# a1 = max length of line
# Reads user input and adds the characters of the string to inbuf
gets:
	move	$t1, $a0

	la 	$a0, prompt  			# display prompt
	li 	$v0, 4
	syscall

	move 	$a0, $t1
	li 	$v0, 8
	syscall

	jr	$ra

# a0 = adress of c string in heap
puts:
	li	$v0, 4
	syscall
	jr 	$ra

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


# a0 = inbuf
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

# a0 - number of bytes
# v0 - address of allocated space
malloc:
	addi	$a0, $a0, 3			# make multiple of 4
	andi	$a0, $a0, 0xfffc
	li	$v0, 9				# malloc
	syscall
	jr 	$ra

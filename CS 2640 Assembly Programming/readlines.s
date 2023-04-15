
# /cpp/cos/cs/2640/sp22 - readlines

LINELEN = 81

	.data
prompt:	.asciiz	"? "
lines:	.asciiz	" line(s)\n"
inbuf:	.space	LINELEN

	.text
main:	li	$t1, 0		# t1:line counter
while:	la	$a0, prompt	# read a line
	li	$v0, 4
	syscall
	la	$a0, inbuf	# buffer
	li	$a1, LINELEN	# length
	li	$v0, 8
	syscall
	lb	$t0, inbuf	# while not empty line
	beq	$t0, '\n', endw
	la 	$a0, inbuf	# echo line
	li	$v0, 4
	syscall
	addi	$t1, $t1, 1	# line counter++
	b 	while
endw:	move	$a0, $t1	# output line count
	li	$v0, 1
	syscall
	la	$a0, lines
	li	$v0, 4
	syscall
	li	$v0, 10
	syscall


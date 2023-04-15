
# /cpp/cos/cs/2640/sp22 - heap

	.data
hello:	.asciiz	"hello\n"
dup:	.word	0		# reference to duplicate hello on the heap

	.text
main:	
	li	$t0, 6 + 1	# hello\n + '\0'

	addi	$a0, $t0, 3	# make multiple of 4
	addi	$a0, $a0, 0xfffc
	li	$v0, 9		# malloc
	syscall

	sw	$v0, dup	# save heap address in dup

	la 	$t0, hello	# t0:src (.data)
	lw	$t1, dup	# t1:dest (heap)
while:	lb	$t2, ($t0)	# get char from src
	sb	$t2, ($t1)	# put char to dest, ensure copy also \0
	beqz	$t2, endw	# if char is \0, done
	addi	$t0, $t0, 1	# next src char
	addi	$t1, $t1, 1	# next dest char
	b 	while
endw:	lw	$a0, dup	# ouput dup, notice the lw
	li	$v0, 4
	syscall
		
	li	$v0, 10
	syscall

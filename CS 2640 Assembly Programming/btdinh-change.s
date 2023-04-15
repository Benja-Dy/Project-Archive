#
# Name: Dinh, Benjamin
# Project: # 1
# Due: 3/17/22
# Course: cs-2640-03-sp22
#
# Description:
# Takes is a value between 1 - 99 and returns the amount in
# quarters, dimes, nickels, and pennies.
#
	.data
intro:	.ascii	"Change by B. Dinh\n\n"	# setting up input and output text
	.asciiz	"Enter the change? "

quarter:	.asciiz	"Quarter: "
dime:	.asciiz	"Dime: "
nickel:	.asciiz	"Nickel:  "
penny:	.asciiz	"Penny: "


	.text
main:
	la 	$a0, intro  		# display intro
	li 	$v0, 4
	syscall

	la 	$a0, intro  		# get input
	li 	$v0, 5
	syscall
	move 	$t1, $v0

	
					# QUARTER
	li	$t0, 25			# Creating number to divide total with			
	div 	$t1, $t0			# dividing total and putting output in lo and hi
	mflo	$t0
	beqz	$t0, endifq		# If quarter equal zero skip output

	la 	$a0, quarter  		# display quarter
	li 	$v0, 4
	syscall

	mflo	$a0			# setting up output for quarter
	li 	$v0, 1
	syscall

	li	$a0, '\n'			# print new line
	li	$v0, 11
	syscall

endifq:	
					# DIME
	li	$t0, 10			# Creating number to divide total with
	mfhi	$t1			# moving remainder to $t1
	div 	$t1, $t0			# dividing total and putting output in lo and hi
	mflo	$t0
	beqz	$t0, endifd		# If dime equal zero skip output
	
	la 	$a0, dime  		# display dime
	li 	$v0, 4
	syscall

	mflo	$a0			# setting up output for dime
	li 	$v0, 1
	syscall

	li	$a0, '\n'			# print new line
	li	$v0, 11
	syscall

endifd:
	li	$t0, 5			# Creating number to divide total with
	mfhi	$t1			# moving remainder to $t1	
	div 	$t1, $t0			# dividing total and putting output in lo and hi				# NICKEL
	mflo	$t0
	beqz	$t0, endifn		# If penny equal zero skip output
	
	la 	$a0, nickel  		# display nickel
	li 	$v0, 4
	syscall

	mflo	$a0			# setting up output for nickel
	li 	$v0, 1
	syscall

	li	$a0, '\n'			# print new line
	li	$v0, 11
	syscall
endifn:

					# PENNY
	mfhi	$t0
	beqz	$t0, endifp		# If penny equal zero skip output
	la 	$a0, penny  		# display penny
	li 	$v0, 4
	syscall

	mfhi	$a0			# setting up output for peniny
	li 	$v0, 1
	syscall
endifp:

	li   	$v0, 10  			# exit
       	syscall

# 
# Name:        Dinh, Benjamin
# Homework:    #3
# Due:         3/10/2022 
# Course:      cs-2640-03-sp22 
# 
# Description: 
#  A fahrenheit to Celsius converter
# 
       .data
output:     .ascii  "Temperature by B. Dinh\n\n"
            .asciiz  "Enter degree in F? "
      
result:     .asciiz " C"

       .text
main:
       la   $a0, output  # display output
       li   $v0, 4
       syscall

       li   $v0, 5       # get user input
       syscall

       addi $t0, $v0, -32 # use formula to find celsius
       mul $t0, $t0, 5
       div $t0, $t0, 9

       move $a0, $t0
       li $v0, 1
       syscall

       la   $a0, result  # display result
       li   $v0, 4
       syscall
       

       li   $v0, 10  # exit
       syscall
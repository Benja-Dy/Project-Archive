(defun flip(L)
	(if (null L) nil
		(if (null (cdr L))
			L
			(cons (car (cdr L)) (cons (car L) (flip (cdr (cdr L))))))
)
)

(defun remove-i (i L)
	(if (= i 1)
		(cdr L)
		(cons (car L) (remove-i (- i 1) (cdr L))
	)
))

(defun product-of-diff(L)
	(if (null (cdr L))
	1
	(* (product-of-diff-h (car L) (cdr L)) (product-of-diff (cdr L)))
	))

(defun product-of-diff-h (I L)
	(if (null (cdr L))
	1
	(* (- I (car (cdr L))) (product-of-diff-h I (cdr L)
))))


(defun dups (lst iterations)
	(if (null lst) nil
	(if (= iterations 1)
		(cons (car lst) (dups (cdr lst) iterations))
		(cons (car lst) (append (dups (cons (car lst) (cdr lst)) (- iterations 1)) (dups (cdr lst) iterations))))
))



(defun prime-factors (n)
  (let ((f 2) (factors ()))
    (loop until (<= n 1)
        do (if ((zerop (mod n f))
 			(progn (push f factors) (setf n (/ n f))) (setf f (1+ f)))))
    factors))
(defun palindromep (lst)
  (cond ((null lst) t)  ; empty list is a palindrome
        ((null (cdr lst)) t)  ; single element is a palindrome
        (t (and (equal (car lst) (car (last lst)))
                (palindromep (butlast (cdr lst))
                            )))))
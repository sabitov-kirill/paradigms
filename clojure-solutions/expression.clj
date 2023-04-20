(defn constant [value] (fn [variables] (double value)))
(defn variable [name] (fn [variables] (variables name)))

(defn fixed-numbers-divide
  ([a] (/ 1.0 a))
  ([a & other] (/ a (double (apply * other)))))

(defn operator [f]
  (fn [& arguments]
    (fn [variables]
      (apply f (mapv #(% variables) arguments)))))
(def add (operator +))
(def subtract (operator -))
(def multiply (operator *))
(def divide (operator fixed-numbers-divide))
(defn negate [argument] ((operator -) argument))

(defn build-ast [token]
  (cond
    (list? token) (let [ops {'+ add '- subtract '* multiply '/ divide 'negate negate}
                        op (ops (first token))
                        args (mapv build-ast (rest token))]
                    (apply op args))
    (number? token) (constant token)
    :else (variable (str token))))
(defn parseFunction [source] (build-ast (read-string source)))

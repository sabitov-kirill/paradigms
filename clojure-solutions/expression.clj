(def constant constantly)
(defn variable [name] (fn [variables] (variables name)))
(defn fixed-numbers-divide ([a] (/ 1.0 a)) ([a & other] (/ a (double (apply * other)))))
(defn operator [f] (fn [& arguments] (fn [variables] (apply f (mapv #(% variables) arguments)))))

(defmacro def-op [name op] `(def ~name (operator ~op)))
(def-op add +)
(def-op subtract -)
(def-op multiply *)
(def-op divide fixed-numbers-divide)
(def-op exp #(Math/exp %))
(def-op ln #(Math/log %))
(def-op arcTan2 #(Math/atan2 %1 %2))
(defn arcTan [argument] ((operator #(Math/atan %)) argument))
(defn negate [argument] ((operator -) argument))
(def ops {'+ add '- subtract '* multiply '/ divide 'exp exp 'ln ln 'negate negate 'atan arcTan 'atan2 arcTan2})

(def parseFunction
  (letfn [(build-ast [token]
            (cond
              (list? token) (let [op (ops (first token)) args (mapv build-ast (rest token))] (apply op args))
              (number? token) (constant token)
              :else (variable (name token))))]
    (comp build-ast read-string)))
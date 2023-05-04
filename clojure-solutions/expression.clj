(require '[clojure.math :as math])

; Functional expressions
(def constant constantly)
(defn variable [name] (fn [variables] (variables name)))
(defn fixed-numbers-divide ([a] (/ 1.0 a)) ([a & other] (/ a (double (apply * other)))))
(defn operator [f] (fn [& arguments] (fn [variables] (apply f (mapv #(% variables) arguments)))))

(defmacro def-op [name op] `(def ~name (operator ~op)))
(def-op add +)
(def-op subtract -)
(def-op multiply *)
(def-op divide fixed-numbers-divide)
(def-op exp math/exp)
(def-op ln math/log)
(def-op atan math/atan)
(def-op atan2 math/atan2)
(def    arcTan atan)
(def    arcTan2 atan2)
(def-op negate -)
(def ops-functional {'+ add '- subtract '* multiply '/ divide 'exp exp 'ln ln 'negate negate 'atan atan 'atan2 atan2})

; Expressions parser
(defn parser [const-constructor variable-constructor ops-constructors]
  (letfn [(parse [token]
            (cond
              (list? token) (let [op (ops-constructors (first token)) args (mapv parse (rest token))] (apply op args))
              (number? token) (const-constructor token)
              :else (variable-constructor (name token))))]
    (comp parse read-string)))

(def parseFunction (parser constant variable ops-functional))

; Object expressions
(definterface IExpression
  (^Number evaluate [vars]))

(deftype Const [value]
  IExpression
  (evaluate [this _] (.-value this))
  Object
  (toString [this] (str (.-value this))))
(defn Constant [value] (Const. value))

(deftype Var [name]
  IExpression
  (evaluate [this vars] (vars (.-name this)))
  Object
  (toString [this] (str (.-name this))))
(defn Variable [name] (Var. name))

(deftype Expression [f sign args]
  IExpression
  (evaluate [this vars] (apply f (mapv #(.evaluate % vars) (.-args this))))
  Object
  (toString [this] (str "(" (.-sign this) " " (clojure.string/join " " (.-args this)) ")")))
(defn Add      [& args] (Expression. + "+" args))
(defn Subtract [& args] (Expression. - "-" args))
(defn Multiply [& args] (Expression. * "*" args))
(defn Divide   [& args] (Expression. fixed-numbers-divide "/" args))
(defn Negate   [& args] (Expression. - "negate" args))
(defn Sin      [& args] (Expression. math/sin "sin" args))
(defn Cos      [& args] (Expression. math/cos "cos" args))
(defn Sinh     [& args] (Expression. math/sinh "sinh" args))
(defn Cosh     [& args] (Expression. math/cosh "cosh" args))
(defn Atan     [& args] (Expression. math/atan "atan" args))
(defn Atan2    [& args] (Expression. math/atan2 "atan2" args))
(def ArcTan Atan)
(def ArcTan2 Atan2)

(defn evaluate [expr vars] (.evaluate expr vars))
(defn toString [expr] (.toString expr))

(def ops-object {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate
                 'sin Sin 'cos Cos 'sinh Sinh 'cosh Cosh 'atan Atan 'atan2 Atan2})
(def parseObject (parser Constant Variable ops-object))

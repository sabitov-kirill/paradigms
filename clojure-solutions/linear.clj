(defn same-length? [args] (every? #(= (count (first args)) (count %)) (rest args)))
(defn test [coll] (every? true? coll))
(defn by-element [op args] (apply (partial mapv op) args))
(defn unfold-if-single [m] (if (empty? (rest m)) (first m) m))

; Vectors operations
(defn v? [v] (and (vector? v) (every? number? v)))
(defn vs-same-size? [args] (and (every? v? args) (same-length? args)))

(defn vs-by-element [op args] {:pre [(vs-same-size? args)]} (by-element op args))
(defn v+ [& args] (vs-by-element + args))
(defn v- [& args] (vs-by-element - args))
(defn v* [& args] (vs-by-element * args))
(defn vd [& args] (vs-by-element / args))

(defn v*s ([v & ss] {:pre [(and (v? v) (every? number? ss))]} (let [sss (reduce * ss)] (mapv #(* % sss) v))))
(defn scalar [& args] {:pre [(vs-same-size? args)]} (reduce + (apply v* args)))
(defn vect ([a] {:pre [(v? a)]} a)
  ([a b] {:pre [(vs-same-size? [a b])]} (vector (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
                                                (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
                                                (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))))
  ([a b & args] (reduce vect (vect a b) args)))

; Matrix operations
(defn m? [m] (and (vector? m) (every? v? m)))
(defn ms-same-size? [args] (and (every? m? args) (test (apply map #(vs-same-size? %&) args)) (same-length? args)))
(defn m-v-same-size? [m v] (and (m? m) (v? v) (test (map #(same-length? [% v]) m))))
(defn transpose [m] (apply mapv vector m))

(defn ms-by-element [op args] {:pre [(ms-same-size? args)]} (by-element #(by-element op %&) args))
(defn m+ [& args] (ms-by-element + args))
(defn m- [& args] (ms-by-element - args))
(defn m* [& args] (ms-by-element * args))
(defn md [& args] (ms-by-element / args))

(defn m*s [m & ss] {:pre [(and (m? m) (every? number? ss))]} (mapv #(apply v*s % ss) m))
(defn m*v [m v] {:pre [(m-v-same-size? m v)]} (mapv #(unfold-if-single (vector (reduce + (v* % v)))) m))
(defn m*m ([m] {:pre [(m? m)]} m)
  ([A B] {:pre [(and (m? B) (same-length? [(first A) B]))]} (transpose (mapv #(m*v A %) (transpose B))))
  ([A B & args] (reduce m*m (m*m A B) args)))